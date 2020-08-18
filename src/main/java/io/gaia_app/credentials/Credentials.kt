package io.gaia_app.credentials

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.springframework.data.annotation.Id


import com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME
import com.fasterxml.jackson.annotation.JsonView
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.gaia_app.teams.User
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document


@JsonTypeInfo(use = NAME, include = PROPERTY, property = "provider")
@JsonSubTypes(
    Type(value = AWSCredentials::class, name = "aws"),
    Type(value = GoogleCredentials::class, name = "google"),
    Type(value = AzureRMCredentials::class, name = "azurerm"),
    Type(value = VaultAWSCredentials::class, name = "vault-aws")
)
sealed class Credentials(var provider: String) {

    @Id
    lateinit var id: String

    lateinit var name: String

    @CreatedBy
    @DBRef
    lateinit var createdBy: User

    abstract fun toEnv(): List<String>
}

@Document
data class AWSCredentials(var accessKey:String, var secretKey:String):Credentials("aws") {
    override fun toEnv() = listOf("AWS_ACCESS_KEY_ID=$accessKey", "AWS_SECRET_ACCESS_KEY=$secretKey")
}

@Document
data class GoogleCredentials(var serviceAccountJSONContents:String, var projectId:String? = null):Credentials("google") {
    override fun toEnv() = listOf("GOOGLE_CREDENTIALS=$serviceAccountJSONContents", "GOOGLE_PROJECT=$projectId")
}

@Document
data class AzureRMCredentials(var clientId:String, var clientSecret:String):Credentials("azurerm") {
    override fun toEnv() = listOf("ARM_CLIENT_ID=$clientId", "ARM_CLIENT_SECRET=$clientSecret")
}

@Document
data class VaultAWSCredentials(var vaultAwsSecretEnginePath: String, var vaultAwsRole: String):Credentials("vault-aws") {
    override fun toEnv() = listOf("EMPTY")
}

data class EmptyCredentials(val id: String, val name: String, val provider: String)
