package hu.grofandriska.os.service;


import hu.grofandriska.os.entity.user.Role;
import hu.grofandriska.os.entity.user.User;
import hu.grofandriska.os.entity.user.UserGroup;
import hu.grofandriska.os.entity.user.company.CompanyMember;
import hu.grofandriska.os.entity.user.family.Family;
import hu.grofandriska.os.entity.user.family.FamilyMember;
import hu.grofandriska.os.repository.ApplicationRepository;
import hu.grofandriska.os.repository.UserGroupRepository;
import hu.grofandriska.os.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class UtilService {

    private UserRepository repository;

    private UserGroupRepository userGroupRepository;

    private ApplicationRepository applicationRepository;


    public UtilService(UserRepository repository, UserGroupRepository userGroupRepository, ApplicationRepository applicationRepository) {
        this.repository = repository;
        this.userGroupRepository = userGroupRepository;
        this.applicationRepository = applicationRepository;
    }

    @Transactional
    public User initializeAdmin(String firstName, String lastName,UserGroup userGroup) {
        FamilyMember admin = new FamilyMember();
        admin.setId(UUID.randomUUID().toString());
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setRole(Role.ADMIN);
        admin.setFullName(lastName + " " + firstName);
        admin.setGroup(userGroup);
        return repository.save(admin);
    }


    public UserGroup createGroup(){
        UserGroup userGroup = new Family();
        userGroup.setId("1");
        userGroup.setName("Gróf Család");
        userGroup.setCreatedAt(LocalDate.now());
        return userGroupRepository.save(userGroup);
    }

    @Transactional
    public User createMemberForCurrentGroup(User currentUser,
                                            String lastName,
                                            String firstName,
                                            Role role) {
        User newUser = null;

        if (currentUser instanceof FamilyMember) {
            newUser = new FamilyMember();
        } else if (currentUser instanceof CompanyMember) {
            newUser = new CompanyMember();
        } else {
            return null;
        }

        newUser.setId(UUID.randomUUID().toString());
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setFullName(lastName + " " + firstName);
        newUser.setGroup(currentUser.getGroup());
        newUser.setRole(role);

        return repository.save(newUser);
    }
}
