package com.vibot.youtube

import com.vibot.youtube.binding.conf.ClientSecret
import com.vibot.youtube.binding.request.Create
import com.vibot.youtube.binding.response.Upload
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import java.io.File

private const val USER_AGENT = "youtube-cmdline-uploadvideo-sample Google-API-Java-Client Google-HTTP-Java-Client/1.28.0 (gzip)"
private const val YOUTUBE_UPLOAD_ENDPOINT = "https://www.googleapis.com/upload/youtube/v3"

@Service
class YoutubeGateway {

    fun getRefreshToken(credentials: ClientSecret): String {
        try {
            val clientId = credentials.installed.clientId
            val clientSecret = credentials.installed.clientSecret
            val authToken = credentials.installed.authToken
            val client = WebClient.builder().baseUrl("https://oauth2.googleapis.com/token")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .build()
            val request = "code=$authToken&client_id=$clientId&client_secret=$clientSecret&redirect_uri=urn:ietf:wg:oauth:2.0:oob&grant_type=authorization_code"
            val body = BodyInserters.fromObject(request)
            val response = client.post().body(body).retrieve().bodyToMono(HashMap::class.java).block()!! as HashMap<String, String>
            return response["refresh_token"]!!
        } catch (e: WebClientResponseException) {
            throw buildError(e)
        }
    }

    fun getAccessToken(credentials: ClientSecret): String {
        try {
            val clientId = credentials.installed.clientId
            val clientSecret = credentials.installed.clientSecret
            val refreshToken = credentials.installed.refreshToken
            val client = WebClient.builder().baseUrl("https://oauth2.googleapis.com/token")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .build()
            val request = "grant_type=refresh_token&client_id=$clientId&client_secret=$clientSecret&refresh_token=$refreshToken"
            val body = BodyInserters.fromObject(request)
            val response = client.post().body(body).retrieve().bodyToMono(HashMap::class.java).block()!! as HashMap<String, String>
            return response["access_token"]!!
        } catch (e: WebClientResponseException) {
            throw buildError(e)
        }
    }

    fun createVideo(accessToken: String, size: Long, request: Create): String {
        try {
            val client = WebClient.builder().baseUrl("$YOUTUBE_UPLOAD_ENDPOINT")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .defaultHeader("Authorization", "Bearer $accessToken")
                    .defaultHeader("User-Agent", USER_AGENT)
                    .defaultHeader("x-upload-content-type", "application/octet-stream")
                    .defaultHeader("x-upload-content-length", size.toString())
                    .build()
            val url = "/videos?part=snippet,statistics,status&uploadType=resumable"
            val body = BodyInserters.fromObject(request)
            var headers: ClientResponse.Headers? = null
            val response = client.post().uri(url).body(body).exchange().doOnSuccess { headers = it.headers() }.block()
            checkCreateVideoResponse(response)
            return headers!!.header("location").first()
        } catch (e: WebClientResponseException) {
            throw buildError(e)
        }
    }

    private fun checkCreateVideoResponse(response: ClientResponse?) {
        if (response?.statusCode() != HttpStatus.OK) {
            throw IllegalStateException("Youtube error: quota exceeded")
        }
    }

    fun uploadVideo(accessToken: String, videoUrl: String, file: File): Upload {
        try {
            val size = file.length()
            val client = WebClient.builder().baseUrl(videoUrl)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                    .defaultHeader("Authorization", "Bearer $accessToken")
                    .defaultHeader("User-Agent", USER_AGENT)
                    .defaultHeader("Content-Range", "bytes 0-${size - 1}/$size")
                    .build()
            return client.put().body(BodyInserters.fromResource(FileSystemResource(file))).retrieve().bodyToMono(Upload::class.java).block()!!
        } catch (e: WebClientResponseException) {
            throw buildError(e)
        }
    }

    fun uploadThumbnail(accessToken: String, videoId: String, file: File): Upload {
        try {
            val client = WebClient.builder().baseUrl(YOUTUBE_UPLOAD_ENDPOINT)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                    .defaultHeader("Authorization", "Bearer $accessToken")
                    .defaultHeader("User-Agent", USER_AGENT)
                    .build()
            val url = "/thumbnails/set?videoId=$videoId"
            val body = BodyInserters.fromResource(FileSystemResource(file))
            return client.post().uri(url).body(body).retrieve().bodyToMono(Upload::class.java).block()!!
        } catch (e: WebClientResponseException) {
            throw buildError(e)
        }
    }

    private fun buildError(e: WebClientResponseException) = IllegalStateException("Youtube error: ${e.responseBodyAsString}", e)
}
