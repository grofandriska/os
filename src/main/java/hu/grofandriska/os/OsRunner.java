package hu.grofandriska.os;

import hu.grofandriska.os.entity.app.Application;
import hu.grofandriska.os.entity.app.implementations.GameApplication;
import hu.grofandriska.os.entity.app.implementations.MapApplication;
import hu.grofandriska.os.entity.user.Role;
import hu.grofandriska.os.entity.user.User;
import hu.grofandriska.os.entity.user.UserGroup;
import hu.grofandriska.os.entity.user.company.CompanyMember;
import hu.grofandriska.os.entity.user.family.FamilyMember;
import hu.grofandriska.os.repository.ApplicationRepository;
import hu.grofandriska.os.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
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

    private final ApplicationRepository applicationRepository;

    private User currentUser;

    @Override
    public void run(String... args) {
        System.out.println("--- OS Rendszer Kezelő Felület ---");
        UserGroup userGroup = utilService.createGroup();
        utilService.initializeAdmin("András", "Gróf", userGroup);
        utilService.initializeAdmin("Béla", "Gróf", userGroup);
        GameApplication gameApplication = new GameApplication();
        gameApplication.setName("Minecraft");
        GameApplication gameApplication2 = new GameApplication();
        gameApplication.setName("World Of Tanks");
        MapApplication mapApplication = new MapApplication();
        MapApplication mapApplication2 = new MapApplication();
        mapApplication.setName("Terra GPS");
        mapApplication2.setName("Best GPS");
        applicationRepository.save(mapApplication);
        applicationRepository.save(mapApplication2);
        applicationRepository.save(gameApplication);
        applicationRepository.save(gameApplication2);
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

    @Transactional
    public void addMember() {
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
    }

    public void listMembers() {
        System.out.print("Csoportod tagjai:\n");
        List<User> members = userService.findUsersByGroupId(currentUser.getGroup().getId());
        for (int i = 0; i < members.size(); i++) {
            System.out.println(i + 1 + ". " + members.get(i).toString());
        }
    }

    public void modifyMember() {
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


    }

    public void deleteMember() {
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
                addMember();
                userSubMenu();
            }
            case "2" -> {
                listMembers();
                userSubMenu();
            }
            case "3" -> {
                modifyMember();
                userSubMenu();

            }
            case "4" -> {
                deleteMember();
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

        switch (choice) {
            case "1" -> {
                List<Application> apps = applicationRepository.findByOwner(currentUser);
                for (int i = 0; i < apps.size(); i++) {
                    System.out.println(i + 1 + ". " + apps.get(i).toString());
                }
            }

            case "2" -> {
                System.out.println("Alkalmazás hozzáadása az OS-hez\n");
                List<Application> apps = applicationRepository.findAll();

                for (int i = 0; i < apps.size(); i++) {
                    System.out.println(i + 1 + ". " + apps.get(i).toString());
                }
                String response = "";
                boolean done = false;

                List<Application> selectedApps = new ArrayList<>();
                while (!done) {
                    System.out.println("Adja meg az alkalmazás sorszámokat (pl: 1 vagy 1,2,3):");
                    response = sc.nextLine().trim();

                    if (response.isEmpty()) {
                        System.out.println("Hiba: Nem adtál meg semmit!");
                        continue;
                    }

                    // Regex ellenőrzés: Csak számok és vesszők lehetnek benne
                    // ^[0-9]+(,[0-9]+)*$  -> jelentése: szám, amit követhet (vessző + szám) tetszőlegesen
                    if (!response.matches("^[0-9]+(,[0-9]+)*$")) {
                        System.out.println("Hiba: Csak számokat és vesszőket használj (pl: 1,3,4)!");
                        continue;
                    }

                    String[] parts = response.split(",");
                    boolean allValid = true;
                    selectedApps.clear();

                    for (String part : parts) {
                        int index = Integer.parseInt(part.trim()) - 1; // Átalakítjuk index-szé

                        if (index >= 0 && index < apps.size()) {
                            selectedApps.add(apps.get(index));
                        } else {
                            System.out.println("Hiba: A(z) " + (index + 1) + " sorszám nem létezik!");
                            allValid = false;
                            break;
                        }
                    }

                    if (allValid) {
                        done = true;
                        System.out.println("Kiválasztott alkalmazások száma: " + selectedApps.size());
                    }
                }
            }


            case "3" -> {
                modifyMember();
                userSubMenu();

            }
            case "4" -> {
                deleteMember();
            }
            case "b" -> {
                mainMenu();
            }
        }

        // A Theme és Wallpaper menüket ugyanígy kell felépítened
    }
}
