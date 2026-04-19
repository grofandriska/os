package hu.grofandriska.os;

import hu.grofandriska.os.entity.user.Role;
import hu.grofandriska.os.entity.user.User;
import hu.grofandriska.os.entity.user.UserGroup;
import hu.grofandriska.os.entity.user.company.CompanyMember;
import hu.grofandriska.os.entity.user.family.FamilyMember;
import hu.grofandriska.os.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class OsRunner implements CommandLineRunner {

    private final Scanner sc = new Scanner(System.in);
    private final UserService userService;
    private final ThemeService themeService;
    private final WallpaperService wallpaperService;
    private final ApplicationService appService;
    private final UtilService utilService;

    private User currentUser;

    @Override
    public void run(String... args) {
        System.out.println("--- OS Rendszer Kezelő Felület ---");
        UserGroup userGroup = utilService.createGroup();
        utilService.initializeAdmin("András", "Gróf", userGroup);
        utilService.initializeAdmin("Béla", "Gróf", userGroup);
        while (currentUser == null) {
            loginProcess();
        }
        mainMenu();
    }

    private void loginProcess() {
        System.out.print("\nKérem adja meg a felhasználónevét: ");
        String name = sc.nextLine();

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
        System.out.println("1. Add member to your group");
        System.out.println("2. List Members");
        System.out.println("3. Edit User");
        System.out.println("4. Delete User");
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

                utilService.createMemberForCurrentGroup(currentUser, lastName, firstName, role);
                userSubMenu();
            }
            case "2" -> {
                System.out.print("Csoportod tagjai:\n");
                List<User> members = userService.findUsersByGroupId(currentUser.getGroup().getId());
                for (int i = 0; i < members.size(); i++) {
                    System.out.println(i + 1 + ". " + members.get(i).toString());
                }
                userSubMenu();
            }
            case "3" -> {
                String response = " ";
                String firstName = " ";
                String lastName = " ";
                System.out.print("Profil szerkesztése\n");

                while (!response.equals("I") && !response.equals("N")) {
                    System.out.print("Családnév módosul? (I/N)");
                    response = sc.nextLine();
                    if (response.equals("I")) {
                        System.out.print("Család név: ");
                        firstName = sc.nextLine();
                    } else if (response.equals("N")) {
                        firstName = currentUser.getFirstName();

                    }
                }
                response = " ";
                while (!response.equals("I") && !response.equals("N")) {
                    System.out.print("Keresztnév  módosul? (I/N)");
                    response = sc.nextLine();
                    if (response.equals("I")) {
                        System.out.print("Keresztnév: ");
                        lastName = sc.nextLine();
                    } else if (response.equals("N")) {
                        lastName = currentUser.getLastName();

                    }
                }

                User user;
                if (currentUser instanceof FamilyMember) {
                    user = new FamilyMember();
                } else {
                    user = new CompanyMember();
                }

                user.setId(currentUser.getId());
                user.setRole(currentUser.getRole());
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setFullName(lastName + " " + firstName);
                user.setInstalledApplications(currentUser.getInstalledApplications());
                user.setActiveWallpaper(currentUser.getActiveWallpaper());
                user.setTheme(currentUser.getTheme());
                user.setUpdatedAt(LocalDate.now());

                userService.modifyUser(currentUser.getFullName(), user);

                userSubMenu();

            }
            case "4" -> {
                String response = " ";
                while (!response.equals("I") && !response.equals("N")) {
                    System.out.println("Biztos törlöd a felhasználódat? (I/N)");
                    response = sc.nextLine();
                    if (response.equals("I")) {
                        userService.deleteUser(currentUser.getFullName());
                        loginProcess();
                    } else if (response.equals("N")) {
                        userSubMenu();
                    }
                }

            }
            case "b" -> {
                mainMenu();
            }
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
