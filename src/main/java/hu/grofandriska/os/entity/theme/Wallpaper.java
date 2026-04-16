package hu.grofandriska.os.entity.theme;

import hu.grofandriska.os.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Wallpaper {

    @Id
    private String id;
    private String name;
    private String description;

    @ManyToOne
    private User owner;
}
