package com.vibot.youtube


import com.vibot.youtube.api.CreateVideoRequest
import com.vibot.youtube.binding.conf.ClientSecret
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File

@Service
class VideoUploader @Autowired constructor(
        private val gateway: YoutubeGateway,
        private val credentialStorage: CredentialsStorage
){
    companion object {
        private val LOGGER = LoggerFactory.getLogger(VideoUploader::class.java)
    }

    lateinit var data: VideoData

    fun upload(file: File) {
        val credentials = getCredentials()
        requestRefreshToken(credentials)
        val accessToken = gateway.getAccessToken(credentials)
        val videoUrl = gateway.createVideo(accessToken, file.length(), data.toYoutubeCreateRequest())
        gateway.uploadVideo(accessToken, videoUrl, file)
        LOGGER.info("Video uploaded")
    }

    fun create(request: CreateVideoRequest) {
        data = VideoData(request.title, request.description, request.keywords, request.category)
        LOGGER.info("Video created")
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