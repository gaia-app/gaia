package io.codeka.gaia.teams.bo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * a Gaia user, which has granted access to modules
 */
public class User {

    @Id
    private String username;

    @DBRef
    private Team team;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean isAdmin() {
        return "admin".equals(this.username);
    }
}
