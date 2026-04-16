package hu.grofandriska.os.entity.app.implementations;


import hu.grofandriska.os.entity.app.Application;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SOCIAL")
@NoArgsConstructor
public class SocialPlatformApplication extends Application {


    @Override
    public void onLaunch() {
        System.out.println("--- [ " + getName().toUpperCase() + " ] ---");
        System.out.println("Üdv a social platformon... Barátok betöltve.");
    }
}
