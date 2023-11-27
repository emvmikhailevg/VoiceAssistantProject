package ru.urfu.voiceassistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.urfu.voiceassistant.entity.FileEntity;

import java.util.List;

@Component
@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    List<FileEntity> findFileEntityByUserId(Long id);

    void deleteById(Long id);
}
