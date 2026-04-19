package hu.grofandriska.os.entity.app.implementations;


import hu.grofandriska.os.entity.app.Application;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("GAME")
public class GameApplication extends Application {

    @Override
    public void onLaunch() {
        System.out.println("--- [ " + getName().toUpperCase() + " ] ---");
        System.out.println("A játék betöltődik... Sok sikert!");
    }
}
