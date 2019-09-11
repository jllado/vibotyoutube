package com.vibot.youtube

import com.vibot.youtube.binding.request.Create
import com.vibot.youtube.binding.request.Snippet
import com.vibot.youtube.binding.request.Status
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class VideoDataTest {

    @Test
    fun `should build youtube create video request`() {
        val title = "any title"
        val description = "This is description"
        val keywords = listOf("cool", "video", "other")
        val category = Category.NEWS
        val data = VideoData(title, description, keywords, category)

        val request = data.toYoutubeCreateRequest()

        assertThat(request, `is`(createVideoRequest(title, description, keywords, category)))
    }
}

fun createVideoRequest(name: String, text: String, keywords: List<String>, category: Category) =
        Create().apply {
            snippet = Snippet().apply { title = name; description = text; tags = keywords; categoryId = category.youtubeId }
            status  = Status().apply { privacyStatus = "public" } }