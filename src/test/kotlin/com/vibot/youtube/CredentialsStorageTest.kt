package com.vibot.youtube

import com.fasterxml.jackson.databind.ObjectMapper
import com.vibot.youtube.binding.conf.ClientSecret
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.springframework.util.FileSystemUtils
import java.io.File

class CredentialsStorageTest {

    private val storage = CredentialsStorage()

    @Test
    fun `should return credentials`() {
        assertThat(storage.get(credentialsFile).installed, `is`(notNullValue()))
    }

    @Test
    fun `should save credentials`() {
        val credentialsFile = File("client_secret.json")
        createCredentialsFile(credentialsFile)

        storage.save(getCredentialsWithRefreshToken(), credentialsFile)

        assertThat(storage.get(credentialsFile), `is`(getCredentialsWithRefreshToken()))
    }

    private fun createCredentialsFile(credentialsFile: File) {
        FileSystemUtils.deleteRecursively(credentialsFile)
        val source = File(UploaderTest::class.java.getResource("/client_secret.json").file)
        FileSystemUtils.copyRecursively(source, credentialsFile)
    }

    private fun getCredentialsWithRefreshToken() = ObjectMapper().readValue(UploaderTest::class.java.getResource("/client_secret_with_refresh_token.json"), ClientSecret::class.java)
}
