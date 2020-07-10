package com.vibot.youtube.api

import com.vibot.youtube.Uploader
import com.vibot.youtube.YoutubeException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
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

    companion object {
        private val LOGGER = LoggerFactory.getLogger(UploadVideoApi::class.java)
    }

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
        uploadVideo(videoFile, thumbnailFile)
    }

    private fun uploadVideo(videoFile: File, thumbnailFile: File) {
        uploader.uploadVideo(videoFile, thumbnailFile)
    }

    @ExceptionHandler(YoutubeException::class)
    fun handleEntityNotFound(e: YoutubeException): ResponseEntity<String> {
        LOGGER.error(e.message, e)
        return ResponseEntity(e.message, HttpHeaders(), HttpStatus.CONFLICT)
    }
}
