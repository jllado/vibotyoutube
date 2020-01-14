package com.vibot.youtube

import com.fasterxml.jackson.databind.ObjectMapper
import com.vibot.youtube.binding.conf.ClientSecret
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.startsWith
import org.junit.Assert.assertThat
import org.junit.Ignore
import org.junit.Test
import java.io.File

class YoutubeGatewayIT {

    private val gateway = YoutubeGateway()

    @Test
    fun `should create video`() {
        val videoTitle = "My video test"
        val videoFile = File(YoutubeGateway::class.java.getResource("/video.mp4").file)

        val size = videoFile.length()
        assertThat(size, `is`(2971464L))

        val credentials = getCredentials()
        val accessToken = gateway.getAccessToken(credentials)
        assertThat(accessToken, startsWith("ya29."))

        val locationId = gateway.createVideo(accessToken, size, createVideoRequest(videoTitle, "This is description", listOf("cool", "video", "other"), Category.NEWS))
        assertThat(locationId, containsString("upload_id"))

        val uploadResponse = gateway.uploadVideo(accessToken, locationId, videoFile)
        assertThat(uploadResponse.snippet.title, `is`(videoTitle))
    }

    //To get auth token
    //https://accounts.google.com/o/oauth2/auth?client_id=998349629449-pj2ifsp9b61t8lbjpcmea32cjjdoh1c9.apps.googleusercontent.com&redirect_uri=urn:ietf:wg:oauth:2.0:oob&scope=https://www.googleapis.com/auth/youtube.upload&response_type=code
    @Ignore("First should get a valid auth token")
    @Test
    fun `given auth token should return refresh token`() {
        val refreshToken = gateway.getRefreshToken(getCredentials())

        assertThat(refreshToken, startsWith("1/"))
    }

    private fun getCredentials() = ObjectMapper().readValue(credentialsFile, ClientSecret::class.java)
}