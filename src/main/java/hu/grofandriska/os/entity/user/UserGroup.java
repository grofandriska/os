package hu.grofandriska.os.entity.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.List;


@Entity
@Data
@RequiredArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UserGroup {

    @Id
    private String id;
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<User> members;

    private Timestamp createdAt;
    private Timestamp updatedAt;
}
