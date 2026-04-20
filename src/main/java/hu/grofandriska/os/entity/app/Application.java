package hu.grofandriska.os.entity.app;

import hu.grofandriska.os.entity.user.User;
import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@RequiredArgsConstructor
@DiscriminatorColumn(name = "app_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Application {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;

    @ManyToOne(targetEntity = User.class)
    @ToString.Exclude
    private User owner;

    public abstract void onLaunch();
}

