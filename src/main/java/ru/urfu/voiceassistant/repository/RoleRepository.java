package ru.urfu.voiceassistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.urfu.voiceassistant.model.role.Role;

@Component
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByLogin(String name);
}
