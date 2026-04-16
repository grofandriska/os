package hu.grofandriska.os.entity.menu;

import hu.grofandriska.os.entity.app.Application;
import hu.grofandriska.os.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Menu {

    @Id
    private String id;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @ToString.Exclude
    private Menu parentMenu;

    // Az almenük: egy menünek sok almenüje lehet (One-to-Many)
    // A mappedBy mondja meg, hogy a 'parentMenu' mező tartja karban a kapcsolatot
    @OneToMany(mappedBy = "parentMenu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> childMenus;

    // Itt jönnek az alkalmazások (Ikonok), amik a menüben vannak
    @ManyToMany
    @JoinTable(
            name = "menu_apps",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "app_id")
    )
    private List<Application> applications = new ArrayList<>();

    @ManyToOne
    private User owner;
}
