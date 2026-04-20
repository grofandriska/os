package hu.grofandriska.os.service;

import hu.grofandriska.os.entity.app.Application;
import hu.grofandriska.os.entity.menu.Menu;
import hu.grofandriska.os.entity.user.User;
import hu.grofandriska.os.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public Menu createStandardMenuForUser(User owner) {
        Menu menu = new Menu();
        menu.setId(UUID.randomUUID().toString());
        menu.setName("Főmenü");
        menu.setDescription("Rendszer alapértelmezett nézete");
        menu.setOwner(owner); // Összekötjük a felhasználóval

        // Itt inicializáljuk az üres listákat, hogy ne kapj NullPointer-t
        menu.setChildMenus(new ArrayList<>());
        menu.setApplications(new ArrayList<>());

        return menuRepository.save(menu);
    }

    // Létrehoz egy alap menüt egy felhasználónak
    @Transactional
    public Menu createMenu(String name, User owner, Menu parent) {
        Menu menu = new Menu();
        menu.setId(UUID.randomUUID().toString());
        menu.setName(name);
        menu.setOwner(owner);
        menu.setParentMenu(parent); // Ha null, akkor ez egy főmenü
        return menuRepository.save(menu);
    }

    // Alkalmazás (ikon) hozzáadása a menühöz
    @Transactional
    public void addApplicationToMenu(Menu menu, Application app) {
        // Frissítjük a listát a memóriában
        menu.getApplications().add(app);
        // Mentjük az adatbázisba (a @ManyToMany kapcsolótábla frissül)
        menuRepository.save(menu);
    }

    // Alkalmazás eltávolítása (Ikon törlése)
    @Transactional
    public void removeApplicationFromMenu(Menu menu, String appId) {
        menu.getApplications().removeIf(app -> app.getId().equals(appId));
        menuRepository.save(menu);
    }

    // Menü keresése ID alapján (ha a CLI-ben navigálsz)
    public Menu findById(String id) {
        return menuRepository.findById(id).orElse(null);
    }

}
