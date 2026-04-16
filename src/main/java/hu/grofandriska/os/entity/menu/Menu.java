package hu.grofandriska.os.entity.menu;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
public class Menu {

    @Id
    private String id;
    private String name;
    private String description;

    private List<Menu> childMenus;

    private Menu parentMenu;
}
