package hu.grofandriska.os.service;

import hu.grofandriska.os.entity.menu.Menu;
import hu.grofandriska.os.entity.theme.Theme;
import hu.grofandriska.os.entity.theme.Wallpaper;
import hu.grofandriska.os.entity.user.Role;
import hu.grofandriska.os.entity.user.User;
import hu.grofandriska.os.entity.user.UserGroup;
import hu.grofandriska.os.entity.user.company.CompanyMember;
import hu.grofandriska.os.entity.user.family.Family;
import hu.grofandriska.os.entity.user.family.FamilyMember;
import hu.grofandriska.os.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User saveUser(User user) {
        return repository.save(user);
    }

    @Transactional
    public User createMemberForCurrentGroup(User currentUser,
                                            String lastName,
                                            String firstName,
                                            Role role) {
        User newUser;
        // A polimorfizmus ereje: a currentUser osztálya dönt!
        if (currentUser instanceof FamilyMember) {
            newUser = new FamilyMember();
        } else if (currentUser instanceof CompanyMember) {
            newUser = new CompanyMember();
        } else {
            throw new IllegalStateException("Ez a felhasználó nem vehet fel új tagot!");
        }

        // Alapadatok beállítása
        newUser.setId(UUID.randomUUID().toString());
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setFullName(lastName + " " + firstName);
        newUser.setGroup(currentUser.getGroup()); // Automatikus Group öröklődés
        newUser.setRole(role); // Az új tag alapból csak sima user

        return repository.save(newUser);
    }

    public User findUserByName(String fullName) {
        return repository.findByFullName(fullName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public void deleteUser(String fullName) {
        User user = repository.findByFullName(fullName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        repository.deleteById(user.getId());
    }

    public User initializeAdmin(String firstName, String lastName) {
        // Itt a konkrét osztályt példányosítjuk, ami örököl a Userből
        FamilyMember admin = new FamilyMember();
        admin.setId(UUID.randomUUID().toString());
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setRole(Role.ADMIN);
        admin.setFullName(lastName + " " + firstName);

        UserGroup userGroup = new Family();
        userGroup.setId("1");
        userGroup.setName("Gróf Család");
        admin.setGroup(userGroup);


        // Itt hozzuk létre hozzá a kezdő menüt is


        return repository.save(admin);
    }

    public User modifyUser(String fullName, User user) {
        User oldUser = repository.findByFullName(fullName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        oldUser.setRole(user.getRole());
        oldUser.setUpdatedAt(LocalDate.now());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setFullName(oldUser.getFirstName() + " " + oldUser.getLastName());

        return repository.save(oldUser);
    }

}
