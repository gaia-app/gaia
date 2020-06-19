package io.gaia_app.credentials

interface Credentials {
    fun toEnv(): List<String>
}

data class AWSCredentials(val access_key:String, val secret_key:String):Credentials {
    override fun toEnv() = listOf("""AWS_ACCESS_KEY_ID=${access_key}""", """AWS_SECRET_ACCESS_KEY=${secret_key}""")
}

data class GCPCredentials(val serviceAccountJSONContents:String):Credentials {
    override fun toEnv() = listOf("""GOOGLE_CREDENTIALS=${serviceAccountJSONContents}""")
}

data class AzureCredentials(val clientId:String, val clientSecret:String):Credentials {
    override fun toEnv() = listOf("""ARM_CLIENT_ID=${clientId}""", """ARM_CLIENT_SECRET=${clientSecret}""")
}
