package io.codeka.gaia.teams

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef

/**
 * A Gaia Team, which groups users
 */
data class Team(val id: String)

/**
 * a Gaia user, which has granted access to modules
 */
data class User(
        @Id val username: String,
        @DBRef val team: Team?) {

    val isAdmin: Boolean
        get() = "admin" == this.username

    var oAuth2User: OAuth2User? = null
}

/**
 * Gather data of user identified by OAuth2
 */
data class OAuth2User(
    val provider: String?,
    @JsonIgnore val token: String?,
    val attributes: Map<String, Any>?)
