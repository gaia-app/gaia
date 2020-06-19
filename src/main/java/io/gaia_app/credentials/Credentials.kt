package io.gaia_app.credentials

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.springframework.data.annotation.Id


import com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME


@JsonTypeInfo(use = NAME, include = PROPERTY, property = "provider")
@JsonSubTypes(
    Type(value = AWSCredentials::class, name = "AWS"),
    Type(value = GCPCredentials::class, name = "GCP"),
    Type(value = AzureCredentials::class, name = "Azure")
)
abstract class Credentials {

    @Id
    lateinit var id: String

    abstract fun toEnv(): List<String>
    abstract fun provider(): String
}

data class AWSCredentials(val accessKey:String, val secretKey:String):Credentials() {
    override fun toEnv() = listOf("""AWS_ACCESS_KEY_ID=${accessKey}""", """AWS_SECRET_ACCESS_KEY=${secretKey}""")
    override fun provider() = "AWS"
}

data class GCPCredentials(val serviceAccountJSONContents:String):Credentials() {
    override fun toEnv() = listOf("""GOOGLE_CREDENTIALS=${serviceAccountJSONContents}""")
    override fun provider() = "GCP"
}

data class AzureCredentials(val clientId:String, val clientSecret:String):Credentials() {
    override fun toEnv() = listOf("""ARM_CLIENT_ID=${clientId}""", """ARM_CLIENT_SECRET=${clientSecret}""")
    override fun provider() = "Azure"
}
