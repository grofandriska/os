package hu.grofandriska.os.entity.user;

import hu.grofandriska.os.entity.app.Application;
import hu.grofandriska.os.entity.theme.Theme;
import hu.grofandriska.os.entity.theme.Wallpaper;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "app_users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class User {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @ToString.Exclude
    private UserGroup group;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Application> installedApplications = new HashSet<>();

    private LocalDate createdAt = LocalDate.now();
    private LocalDate updatedAt;

    @ManyToMany
    @JoinColumn(name = "wallpaper_id")
    private Wallpaper activeWallpaper;

    @ManyToMany
    @JoinColumn(name = "theme_id")
    private Theme theme;

    public boolean hasAdminPrivileges() {
        return Role.ADMIN.equals(this.role);
    }
}
