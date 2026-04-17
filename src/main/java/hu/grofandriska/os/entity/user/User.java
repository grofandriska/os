package hu.grofandriska.os.entity.user;

import hu.grofandriska.os.entity.app.Application;
import hu.grofandriska.os.entity.theme.Theme;
import hu.grofandriska.os.entity.theme.Wallpaper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private UserGroup group;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> installedApplications = new ArrayList<>();

    private Timestamp createdAt;
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "wallpaper_id")
    private Wallpaper activeWallpaper;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;

    public boolean hasAdminPrivileges() {
        return Role.ADMIN.equals(this.role);
    }
}
