package io.gaia_app.credentials

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.springframework.data.annotation.Id


import com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME


@JsonTypeInfo(use = NAME, include = PROPERTY, property = "provider")
@JsonSubTypes(
    Type(value = AWSCredentials::class, name = "aws"),
    Type(value = GoogleCredentials::class, name = "google"),
    Type(value = AzureRMCredentials::class, name = "azurerm")
)
abstract class Credentials {

    @Id
    lateinit var id: String

    lateinit var name: String

    abstract fun toEnv(): List<String>
    abstract fun provider(): String
}

data class AWSCredentials(val accessKey:String, val secretKey:String):Credentials() {
    override fun toEnv() = listOf("AWS_ACCESS_KEY_ID=$accessKey", "AWS_SECRET_ACCESS_KEY=$secretKey")
    override fun provider() = "aws"
}

data class GoogleCredentials(val serviceAccountJSONContents:String):Credentials() {
    override fun toEnv() = listOf("GOOGLE_CREDENTIALS=$serviceAccountJSONContents")
    override fun provider() = "google"
}

data class AzureRMCredentials(val clientId:String, val clientSecret:String):Credentials() {
    override fun toEnv() = listOf("ARM_CLIENT_ID=$clientId", "ARM_CLIENT_SECRET=$clientSecret")
    override fun provider() = "azurerm"
}
