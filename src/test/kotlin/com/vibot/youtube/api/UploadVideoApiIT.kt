package com.vibot.youtube.api

import com.vibot.youtube.Category
import com.vibot.youtube.VideoUploader
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.File


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class UploadVideoApiIT {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var uploader: VideoUploader

    @Test
    fun `given video data should upload video`() {
        val videoFile = UploadVideoApiIT::class.java.getResource("/video.mp4")
        val title = "any title"
        val description = "This is description"
        val category = "NEWS"
        val post = "{\"title\": \"$title\",\"description\": \"$description\",\"category\": \"$category\",\"keywords\": [\"cool\",\"video\",\"other\"]}"

        mvc.perform(post("/create").contentType(MediaType.APPLICATION_JSON_UTF8).content(post)).andExpect(status().isOk)
        mvc.perform(multipart("/upload")
                .file(MockMultipartFile("video", "video.mp4", MediaType.APPLICATION_OCTET_STREAM_VALUE, videoFile.readBytes())))
                .andExpect(status().isOk)

        verify(uploader).create(CreateVideoRequest(title, description, Category.NEWS, listOf("cool", "video", "other")))
        verify(uploader).upload(File("video.mp4"))
    }
}