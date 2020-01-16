package com.vibot.youtube

import com.vibot.youtube.api.CreateVideoRequest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test


class CreateVideoDataTest {

    private val uploader = Uploader(YoutubeGateway(), CredentialsStorage())

    @Test
    fun `given video request should create video data`() {
        val title = "any title"
        val description = "any description"
        val category = Category.NEWS
        val keywords = listOf("any_key")
        val request = CreateVideoRequest(title, description, category, keywords)

        uploader.createVideo(request)

        assertThat(uploader.data, `is`(VideoData(title, description, keywords, category)))
    }
}