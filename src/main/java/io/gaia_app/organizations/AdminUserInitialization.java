package io.gaia_app.organizations;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Initializes a 'admin' user after application startup, if it doesn't exist.
 */
@Component
class AdminUserInitialization implements ApplicationListener<ApplicationReadyEvent> {

    private final UserService userService;

    private String defaultAdminPassword;

    private static final Log logger = LogFactory.getLog(AdminUserInitialization.class);

    AdminUserInitialization(UserService userService) {
        this.userService = userService;
    }

    @Value("${gaia.defaultAdminPassword}")
    void setDefaultAdminPassword(String defaultAdminPassword) {
        this.defaultAdminPassword = defaultAdminPassword;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        // if no user is present at startup, create the 'admin' user
        if (!userService.existsById("admin")) {
            var admin = new User("admin", null);
            admin.setPassword(defaultAdminPassword);
            admin.setAdmin(true);

            userService.create(admin);

            logger.info("Created admin user");
        }
    }
}
