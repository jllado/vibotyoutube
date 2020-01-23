package com.vibot.youtube

import com.fasterxml.jackson.databind.ObjectMapper
import com.vibot.youtube.binding.conf.ClientSecret
import com.vibot.youtube.binding.response.Upload
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class UploaderTest {

    @Mock
    private lateinit var gateway: YoutubeGateway
    @Mock
    private lateinit var credentialsStorage: CredentialsStorage

    @InjectMocks
    private lateinit var uploader: Uploader

    private val videoFile = File(UploaderTest::class.java.getResource("/video.mp4").file)
    private val thumbnailFile = File(UploaderTest::class.java.getResource("/thumbnail.png").file)
    private val videoTitle = "Test video"
    private val videoDescription = "This is description"
    private val videoKeywords = listOf("cool", "video", "other")
    private val videoCategory = Category.NEWS
    private val videoData = VideoData(videoTitle, videoDescription, videoKeywords, videoCategory)
    private val videoUrl = "http://videourl.com"
    private val accessToken = "any_access_token"

    @Before
    fun setUp() {
        uploader.data = videoData
        doReturn(uploadVideoResponse()).`when`(gateway).uploadVideo(accessToken, videoUrl, videoFile)
    }

    @Test
    fun `should upload video`() {
        doReturn(getCredentials()).`when`(credentialsStorage).get(credentialsFile)
        doReturn("any_refresh_token").`when`(gateway).getRefreshToken(getCredentials())
        doReturn(accessToken).`when`(gateway).getAccessToken(getCredentialsWithRefreshToken())
        doReturn(videoUrl).`when`(gateway).createVideo(accessToken, 2971464L, createVideoRequest(videoTitle, videoDescription, videoKeywords, videoCategory))
        val videoId = "MmeMfc8gMZo"

        uploader.uploadVideo(videoFile, thumbnailFile)

        verify(gateway).uploadVideo(accessToken, videoUrl, videoFile)
    }

    @Test
    fun `should upload thumbnail`() {
        doReturn(getCredentials()).`when`(credentialsStorage).get(credentialsFile)
        doReturn("any_refresh_token").`when`(gateway).getRefreshToken(getCredentials())
        doReturn(accessToken).`when`(gateway).getAccessToken(getCredentialsWithRefreshToken())
        doReturn(videoUrl).`when`(gateway).createVideo(accessToken, 2971464L, createVideoRequest(videoTitle, videoDescription, videoKeywords, videoCategory))
        val videoId = "MmeMfc8gMZo"

        uploader.uploadVideo(videoFile, thumbnailFile)

        verify(gateway).uploadThumbnail(accessToken, videoId, thumbnailFile)
    }

    @Test
    fun `first time should create refresh token`() {
        doReturn(getCredentials()).`when`(credentialsStorage).get(credentialsFile)
        doReturn("any_refresh_token").`when`(gateway).getRefreshToken(getCredentials())
        doReturn(accessToken).`when`(gateway).getAccessToken(getCredentialsWithRefreshToken())
        doReturn(videoUrl).`when`(gateway).createVideo(accessToken, 2971464L, createVideoRequest(videoTitle, videoDescription, videoKeywords, videoCategory))

        uploader.uploadVideo(videoFile, thumbnailFile)

        verify(credentialsStorage).save(getCredentialsWithRefreshToken(), credentialsFile)
    }

    @Test
    fun `given credentials file with refresh token should not request it`() {
        doReturn(getCredentialsWithRefreshToken()).`when`(credentialsStorage).get(credentialsFile)
        doReturn(accessToken).`when`(gateway).getAccessToken(getCredentialsWithRefreshToken())
        doReturn(videoUrl).`when`(gateway).createVideo(accessToken, 2971464L, createVideoRequest(videoTitle, videoDescription, videoKeywords, videoCategory))

        uploader.uploadVideo(videoFile, thumbnailFile)

        verify(gateway, never()).getRefreshToken(getCredentials())
    }

    private fun getCredentials() = ObjectMapper().readValue(UploaderTest::class.java.getResource("/client_secret.json"), ClientSecret::class.java)

    private fun getCredentialsWithRefreshToken() = ObjectMapper().readValue(UploaderTest::class.java.getResource("/client_secret_with_refresh_token.json"), ClientSecret::class.java)

    private fun uploadVideoResponse(): Upload = ObjectMapper().readValue(UploaderTest::class.java.getResource("/upload_video_response.json"), Upload::class.java)
}
