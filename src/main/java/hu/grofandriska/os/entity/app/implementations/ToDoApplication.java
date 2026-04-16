package hu.grofandriska.os.entity.app.implementations;


import hu.grofandriska.os.entity.app.Application;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("TODO")
@NoArgsConstructor
public class ToDoApplication extends Application {
    @Override
    public void onLaunch() {
        System.out.println("--- [ " + getName().toUpperCase() + " ] ---");
        System.out.println("Teendő lista betöltése ... A feladatok készen állnak!");
    }
}
