package com.perception.backend.services;

import com.perception.backend.models.Note;
import com.perception.backend.models.User;
import com.perception.backend.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public List<Note> getAllNotesForUser(User user) {
        return noteRepository.findAllByUser(user);
    }

    public Optional<Note> getNoteByIdForUser(Long id, User user) {
        return noteRepository.findByIdAndUser(id, user);
    }

    public Note createNote(Note note, User user) {
        note.setUser(user);
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        return noteRepository.save(note);
    }

    public Note updateNote(Long id, Note noteDetails, User user) {
        return noteRepository.findByIdAndUser(id, user).map(note -> {
            note.setTitle(noteDetails.getTitle());
            note.setContent(noteDetails.getContent());
            note.setColor(noteDetails.getColor());
            note.setUpdatedAt(LocalDateTime.now());
            return noteRepository.save(note);
        }).orElse(null);
    }

    public boolean deleteNote(Long id, User user) {
        Optional<Note> noteOpt = noteRepository.findByIdAndUser(id, user);
        if (noteOpt.isPresent()) {
            noteRepository.deleteByIdAndUser(id, user);
            return true;
        }
        return false;
    }
}
