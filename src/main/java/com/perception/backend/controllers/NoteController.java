package com.perception.backend.controllers;

import com.perception.backend.models.Note;
import com.perception.backend.services.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @Operation(summary = "Get all notes for the current user")
    @ApiResponse(responseCode = "200", description = "List of notes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Note.class)))
    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    @Operation(summary = "Get a note by ID for the current user")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Note found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Note.class))),
        @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@Parameter(description = "ID of the note", required = true) @PathVariable Long id) {
        return noteService.getNoteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new note for the current user")
    @ApiResponse(responseCode = "200", description = "Note created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Note.class)))
    @PostMapping
    public Note createNote(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Note to create", required = true, content = @Content(schema = @Schema(implementation = Note.class))) @RequestBody Note note) {
        return noteService.createNote(note);
    }

    @Operation(summary = "Update a note by ID for the current user")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Note updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Note.class))),
        @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(
            @Parameter(description = "ID of the note", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated note", required = true, content = @Content(schema = @Schema(implementation = Note.class))) @RequestBody Note noteDetails) {
        Note updated = noteService.updateNote(id, noteDetails);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete a note by ID for the current user")
    @ApiResponse(responseCode = "204", description = "Note deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@Parameter(description = "ID of the note", required = true) @PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
