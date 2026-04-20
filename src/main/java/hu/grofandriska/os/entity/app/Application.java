package hu.grofandriska.os.entity.app;

import hu.grofandriska.os.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@Data
@Entity
@RequiredArgsConstructor
@DiscriminatorColumn(name = "app_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;

    @ManyToOne(targetEntity = User.class)
    @ToString.Exclude
    private User owner;

    public abstract void onLaunch();
}

