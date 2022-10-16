package org.example.Configuration;

import org.example.User.Service.UserService;
import org.example.User.User;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class InitializedData implements ServletContextListener {

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
                .build();
        User jack = User.builder()
                .login("JackyS")
                .name("Jack")
                .surname("Sparrow")
                .email("jack.sparrow@piratebay.com")
                .password("JackyS123")
                .build();
        User will = User.builder()
                .login("WillT")
                .name("William")
                .surname("Turner")
                .email("william.turner@piratebay.com")
                .password("WillT123")
                .build();
        User elizabeth = User.builder()
                .login("LizzyS")
                .name("Elizabeth")
                .surname("Swann")
                .email("elizabeth.swann@piratebay.com")
                .password("LizzyS123")
                .build();
        userService.create(admin);
        userService.create(will);
        userService.create(jack);
        userService.create(elizabeth);
    }

//    @SneakyThrows
//    private byte[] getResourceAsByteArray(String name) {
//        try (InputStream is = this.getClass().getResourceAsStream(name)) {
//            return is.readAllBytes();
//        }
//    }

}
