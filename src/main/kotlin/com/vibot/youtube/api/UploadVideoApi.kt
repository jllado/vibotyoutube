package com.vibot.youtube.api

import com.vibot.youtube.VideoUploader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.FileCopyUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files

@RestController
class UploadVideoApi @Autowired constructor(
        private val uploader: VideoUploader
) {

    @PostMapping("/create")
    fun create(@RequestBody request: CreateVideoRequest) {
        uploader.create(request)
    }

    @PostMapping("/upload")
    fun upload(@RequestParam("video") video: MultipartFile) {
        val videoFile = File(video.originalFilename)
        Files.write(videoFile.toPath(), video.bytes)
        uploader.upload(videoFile)
    }
}