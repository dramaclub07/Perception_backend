package com.perception.backend.services;

import com.perception.backend.models.Note;
import com.perception.backend.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Optional<Note> getNoteById(Long id) {
        return noteRepository.findById(id);
    }

    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    public Note updateNote(Long id, Note noteDetails) {
        return noteRepository.findById(id).map(note -> {
            note.setTitle(noteDetails.getTitle());
            note.setContent(noteDetails.getContent());
            note.setColor(noteDetails.getColor());
            note.setUpdatedAt(java.time.LocalDateTime.now());
            // Set other fields as needed
            return noteRepository.save(note);
        }).orElse(null);
    }

    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }
}
