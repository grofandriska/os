package hu.grofandriska.os.entity.app;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("MAP")
@NoArgsConstructor
public class MapApplication extends Application{
    @Override
    public void onLaunch() {
        System.out.println("--- [ " + getName().toUpperCase() + " ] ---");
        System.out.println("GPS koordináták lekérése... Pozíció meghatározva.");
    }
}
