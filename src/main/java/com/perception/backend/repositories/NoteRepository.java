package com.perception.backend.repositories;

import com.perception.backend.models.Note;
import com.perception.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByUser(User user);
    Optional<Note> findByIdAndUser(Long id, User user);
    void deleteByIdAndUser(Long id, User user);
}
