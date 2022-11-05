package org.example.Configuration;

import lombok.SneakyThrows;
import org.example.Harbor.Entity.Country;
import org.example.Harbor.Entity.Harbor;
import org.example.Harbor.Service.HarborService;
import org.example.Ship.Entity.Ship;
import org.example.Ship.Service.ShipService;
import org.example.User.Service.UserService;
import org.example.User.User;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class InitializedData {

    private final UserService userService;
    private final ShipService shipService;
    private final HarborService harborService;

    @Inject
    public InitializedData(UserService userService, ShipService shipService, HarborService harborService) {
        this.shipService = shipService;
        this.userService = userService;
        this.harborService = harborService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private synchronized void init() {
        String defaultAvatarPath = "avatars/default.png";
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
        List<Ship> empty_ships = new ArrayList<>();
        Harbor ams = Harbor.builder()
                .name("Amsterdam Noord")
                .code("AMS")
                .capacity(100)
                .budget(1357.62)
                .country(Country.Dutch)
                .ships(empty_ships)
                .build();
        Harbor gdn = Harbor.builder()
                .name("Gdansk Stocznia")
                .code("GDN")
                .capacity(100)
                .budget(1357.62)
                .country(Country.Poland)
                .ships(empty_ships)
                .build();
        Harbor ldn = Harbor.builder()
                .name("London docks")
                .code("LDN")
                .capacity(100)
                .budget(1357.62)
                .country(Country.England)
                .ships(empty_ships)
                .build();
        harborService.create(ams);
        harborService.create(ldn);
        harborService.create(gdn);
        Ship ship_one = Ship.builder()
                .name("Titanic")
                .completionDate(LocalDate.of(2022, 1, 13))
                .harbor(ams)
                .cost(98765.21)
                .build();
        Ship ship_two = Ship.builder()
                .name("Black Pearl")
                .completionDate(LocalDate.of(1975, 5, 13))
                .harbor(ams)
                .cost(123.21)
                .build();
        shipService.create(ship_one);
        shipService.create(ship_two);
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            assert is != null;
            return is.readAllBytes();
        }
    }

}
