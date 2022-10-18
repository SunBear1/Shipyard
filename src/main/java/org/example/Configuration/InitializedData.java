package org.example.Configuration;

import lombok.SneakyThrows;
import org.example.User.Service.UserService;
import org.example.User.User;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.InputStream;


@WebListener
public class InitializedData implements ServletContextListener {

    private final String defaultAvatarPath = "avatars/default.png";

    @Override
    public synchronized void contextInitialized(ServletContextEvent sce) {
        UserService userService = (UserService) sce.getServletContext().getAttribute("userService");
        init(userService);
    }

    private synchronized void init(UserService userService) {
        User admin = User.builder()
                .login("admin")
                .name("Lukasz")
                .surname("Niedzwiadek")
                .email("lukasz.niedzwiadek@outlook.com")
                .password("admin")
                .avatar(getResourceAsByteArray(defaultAvatarPath))
                .build();
        User jack = User.builder()
                .login("JackyS")
                .name("Jack")
                .surname("Sparrow")
                .email("jack.sparrow@piratebay.com")
                .password("JackyS123")
                .avatar(getResourceAsByteArray(defaultAvatarPath))
                .build();
        User will = User.builder()
                .login("WillT")
                .name("William")
                .surname("Turner")
                .email("william.turner@piratebay.com")
                .password("WillT123")
                .avatar(getResourceAsByteArray(defaultAvatarPath))
                .build();
        User elizabeth = User.builder()
                .login("LizzyS")
                .name("Elizabeth")
                .surname("Swann")
                .email("elizabeth.swann@piratebay.com")
                .password("LizzyS123")
                .avatar(getResourceAsByteArray(defaultAvatarPath))
                .build();
        userService.create(admin);
        userService.create(will);
        userService.create(jack);
        userService.create(elizabeth);
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            assert is != null;
            return is.readAllBytes();
        }
    }

}
