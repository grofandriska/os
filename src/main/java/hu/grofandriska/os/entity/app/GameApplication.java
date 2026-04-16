package hu.grofandriska.os.entity.app;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("GAME")
@NoArgsConstructor
public class GameApplication extends Application {

    @Override
    public void onLaunch() {
        System.out.println("--- [ " + getName().toUpperCase() + " ] ---");
        System.out.println("A játék betöltődik... Sok sikert!");
    }
}
