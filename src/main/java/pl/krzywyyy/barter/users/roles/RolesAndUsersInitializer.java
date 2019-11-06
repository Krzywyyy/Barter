package pl.krzywyyy.barter.users.roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.krzywyyy.barter.users.User;
import pl.krzywyyy.barter.users.UserRepository;

import java.util.Arrays;

@Component
public class RolesAndUsersInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RolesAndUsersInitializer(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup) return;
        createRoleIfNotExists("ROLE_ADMIN");
        createRoleIfNotExists("ROLE_USER");

        createAdminIfNotExists();

        alreadySetup = true;
    }

    @Transactional
    void createRoleIfNotExists(String name) {
        if (roleRepository.findByName(name) == null) {
            Role role = new Role();
            role.setName(name);
            roleRepository.save(role);
        }
    }

    @Transactional
    void createAdminIfNotExists() {
        String adminEmail = "admin@barter.pl";

        User user = userRepository.findByEmail(adminEmail);
        if (user == null) {
            user = new User();
            user.setFirstName("Admin");
            user.setEmail(adminEmail);
            user.setPassword(bCryptPasswordEncoder.encode("admin"));
            user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_ADMIN")));
            userRepository.save(user);
        }
    }
}
