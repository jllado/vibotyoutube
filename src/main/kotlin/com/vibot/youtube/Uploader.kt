package com.vibot.youtube

import com.vibot.youtube.api.CreateVideoRequest
import com.vibot.youtube.binding.conf.ClientSecret
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File

@Service
class Uploader @Autowired constructor(
    private val gateway: YoutubeGateway,
    private val credentialStorage: CredentialsStorage
) {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(Uploader::class.java)
    }

    lateinit var data: VideoData

    fun createVideo(request: CreateVideoRequest) {
        data = VideoData(request.title, request.description, request.keywords, request.category)
        LOGGER.info("Video created: {}", data)
    }

    @Throws(YoutubeException::class)
    fun uploadVideo(videoFile: File, thumbnailFile: File) {
        val credentials = getCredentials()
        requestRefreshToken(credentials)
        val accessToken = gateway.getAccessToken(credentials)
        val videoUrl = gateway.createVideo(accessToken, videoFile.length(), data.toYoutubeCreateRequest())
        val videoId = uploadVideo(accessToken, videoUrl, videoFile)
        uploadThumbnail(accessToken, videoId, thumbnailFile)
        LOGGER.info("Video upload completed")
    }

    private fun uploadThumbnail(accessToken: String, videoId: String, thumbnailFile: File) {
        try {
            LOGGER.info("Uploading thumbnail")
            gateway.uploadThumbnail(accessToken, videoId, thumbnailFile)
            LOGGER.info("Thumbnail uploaded")
        } catch (e: Exception) {
            LOGGER.error("Thumbnail uploaded failed", e)
        }
    }

    private fun uploadVideo(accessToken: String, videoUrl: String, videoFile: File): String {
        LOGGER.info("Uploading video")
        val response = gateway.uploadVideo(accessToken, videoUrl, videoFile)
        LOGGER.info("Video uploaded")
        return response.id
    }

    private fun requestRefreshToken(credentials: ClientSecret) {
        if (credentials.installed.refreshToken != null) {
            return
        }
        val refreshToken = gateway.getRefreshToken(credentials)
        saveRefreshToken(refreshToken, credentials)
    }

    private fun saveRefreshToken(refreshToken: String, credentials: ClientSecret) {
        credentials.installed.refreshToken = refreshToken
        credentialStorage.save(credentials, credentialsFile)
    }

    private fun getCredentials(): ClientSecret = credentialStorage.get(credentialsFile)
}
