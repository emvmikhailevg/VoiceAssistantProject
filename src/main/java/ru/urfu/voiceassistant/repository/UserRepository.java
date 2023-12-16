package ru.urfu.voiceassistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.urfu.voiceassistant.entity.UserEntity;

@Component
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    UserEntity findUserEntityByActivationCode(String activationCode);

    UserEntity findUserEntityByResetToken(String resetToken);
}
