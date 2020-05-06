package com.vibot.youtube

import com.fasterxml.jackson.databind.ObjectMapper
import com.vibot.youtube.binding.conf.ClientSecret
import org.springframework.stereotype.Service
import java.io.File

@Service
class CredentialsStorage {

    fun get(file: File): ClientSecret {
        return ObjectMapper().readValue(file, ClientSecret::class.java)
    }

    fun save(credentials: ClientSecret, file: File) {
        ObjectMapper().writeValue(file, credentials)
    }
}

val credentialsFile = File(".youtube_client_secret.json")
