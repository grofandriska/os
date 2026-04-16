package hu.grofandriska.os.entity.app;

import hu.grofandriska.os.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@DiscriminatorColumn(name = "app_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Application {

    @Id
    private String id;
    private String name;

    @ManyToOne(targetEntity = User.class)
    private User owner;

    public abstract void onLaunch();
}

