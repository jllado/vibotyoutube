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
){
    companion object {
        private val LOGGER = LoggerFactory.getLogger(Uploader::class.java)
    }

    lateinit var data: VideoData

    fun uploadVideo(videoFile: File, thumbnailFile: File) {
        val credentials = getCredentials()
        requestRefreshToken(credentials)
        val accessToken = gateway.getAccessToken(credentials)
        val videoUrl = gateway.createVideo(accessToken, videoFile.length(), data.toYoutubeCreateRequest())
        LOGGER.info("Uploading video")
        val videoUploadResponse = gateway.uploadVideo(accessToken, videoUrl, videoFile)
        LOGGER.info("Video uploaded")
        LOGGER.info("Uploading thumbnail")
        gateway.uploadThumbnail(accessToken, videoUploadResponse.id, thumbnailFile)
        LOGGER.info("Thumbnail uploaded")
        LOGGER.info("Video upload completed")
    }

    fun createVideo(request: CreateVideoRequest) {
        data = VideoData(request.title, request.description, request.keywords, request.category)
        LOGGER.info("Video created: {}", data)
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