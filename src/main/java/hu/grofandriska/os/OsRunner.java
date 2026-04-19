package hu.grofandriska.os;

import hu.grofandriska.os.entity.user.Role;
import hu.grofandriska.os.entity.user.User;
import hu.grofandriska.os.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class OsRunner implements CommandLineRunner {

    private final Scanner sc = new Scanner(System.in);
    private final UserService userService;
    private final ThemeService themeService;
    private final WallpaperService wallpaperService;
    private final ApplicationService appService;

    private User currentUser;

    @Override
    public void run(String... args) {
        System.out.println("--- OS Rendszer Kezelő Felület ---");
        userService.initializeAdmin("András", "Gróf");
        while (currentUser == null) {
            loginProcess();
        }
        mainMenu();
    }

    private void loginProcess() {
        System.out.print("\nKérem adja meg a felhasználónevét: ");
        String name = sc.nextLine();

        // Itt hívjuk a szervizt, ami visszaadja a User-t vagy null-t
        User user = userService.findUserByName(name);

        if (user != null) {
            this.currentUser = user;
            System.out.println("\n>>> SIKERES BELÉPÉS! <<<");
            System.out.println("Üdvözöljük, " + user.getFirstName() + "!");
            System.out.println("Csoport: " + user.getGroup().getName());
        } else {
            System.out.println("!!! Hiba: Nincs ilyen felhasználó az adatbázisban.");
            System.out.println("Tipp: Futtassa az inicializálást, vagy ellenőrizze a nevet.");
        }
    }


    private void mainMenu() {
        while (true) {
            System.out.println("\n===== FŐMENÜ =====");
            System.out.println("1. User management");
            System.out.println("2. Theme management");
            System.out.println("3. Wallpaper management");
            System.out.println("4. App Management");
            System.out.println("q. Kilépés");
            System.out.print("Válassz opciót: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> userSubMenu();
                case "2" -> System.out.println("TBD");
                case "3" -> System.out.println("TBD");
                case "4" -> appSubMenu();
                case "q" -> System.exit(0);
                default -> System.out.println("Érvénytelen opció!");
            }
        }
    }

    // --- 1. USER MANAGEMENT ALMENÜ ---
    private void userSubMenu() {
        System.out.println("\n--- USER MANAGEMENT ---");
        System.out.println("1. Add User");
        System.out.println("2. Edit User");
        System.out.println("3. Remove User");
        System.out.println("b. Vissza");

        String choice = sc.nextLine();
        switch (choice) {
            case "1" -> {
                System.out.print("Család név: ");
                String lastName = sc.nextLine();
                System.out.print("Kereszt név: ");
                String firstName = sc.nextLine();

                Role role = null;
                while (role == null) {
                    System.out.print("Privilégium (1. Admin, 2. User)  : ");
                    String roleName = sc.nextLine();
                    if (roleName.equals("1")) {
                        role = Role.ADMIN;
                    } else if (roleName.equals("2")) {
                        role = Role.USER;
                    }
                }

                userService.createMemberForCurrentGroup(currentUser, lastName, firstName, role); // Hívás a saját szervizedbe
            }
            case "2" -> {
                //TODO -> listázz groupd Id alapján vagy saját magát modify
                System.out.print("TBD");
                //userService.modifyUser(); // Stb...
            }
            case "3" -> {
                System.out.print("TBD");
                //TODO -> listázz groupd Id alapján vagy saját magát modify és exit
                //userService.deleteUser();
            }
            case "b" -> {
                System.out.print("TBD");
            } // Üresen hagyva visszaugrik a főmenübe
        }
    }

    // --- 4. APP MANAGEMENT ALMENÜ ---
    private void appSubMenu() {
        System.out.println("\n--- APP MANAGEMENT ---");
        System.out.println("1. List apps");
        System.out.println("2. Add app");
        System.out.println("3. Edit app");
        System.out.println("4. Delete app");
        System.out.println("b. Vissza");

        String choice = sc.nextLine();
        // Itt is switch-case-el hívod az appService megfelelő metódusait
    }

    // A Theme és Wallpaper menüket ugyanígy kell felépítened
}
