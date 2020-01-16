package com.vibot.youtube.api

import com.vibot.youtube.Uploader
import com.vibot.youtube.binding.response.Thumbnail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files

@RestController
class UploadVideoApi @Autowired constructor(
        private val uploader: Uploader
) {

    @PostMapping("/create")
    fun create(@RequestBody request: CreateVideoRequest) {
        uploader.createVideo(request)
    }

    @PostMapping("/upload")
    fun upload(@RequestParam("video") video: MultipartFile, @RequestParam("thumbnail") thumbnail: MultipartFile) {
        val videoFile = File(video.originalFilename)
        val thumbnailFile = File(thumbnail.originalFilename)
        Files.write(videoFile.toPath(), video.bytes)
        Files.write(thumbnailFile.toPath(), thumbnail.bytes)
        uploader.uploadVideo(videoFile, thumbnailFile)
    }
}