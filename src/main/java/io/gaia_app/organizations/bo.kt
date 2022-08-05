package io.gaia_app.organizations

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef

/**
 * A Gaia Organization, which groups users
 */
data class Organization(val id: String)

/**
 * a Gaia user, which has granted access to modules
 */
data class User(
        @Id val username: String,
        @DBRef val organization: Organization?) {

    @JsonProperty("admin")
    var isAdmin: Boolean = false

    var oAuth2User: OAuth2User? = null

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var password: String? = null
}

/**
 * Gather data of user identified by OAuth2
 */
data class OAuth2User(
    val provider: String?,
    @JsonIgnore val token: String?,
    val attributes: Map<String, Any>?)
