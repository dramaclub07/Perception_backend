package com.perception.backend.controllers;

import com.perception.backend.models.Note;
import com.perception.backend.models.User;
import com.perception.backend.services.NoteService;
import com.perception.backend.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.MediaType;
import com.perception.backend.dtos.NoteResponseDTO;

import java.util.List;
// import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("[NoteController] Auth name: " + authentication.getName() + ", Authorities: " + authentication.getAuthorities());
        User user = userService.findByEmail(authentication.getName())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        System.out.println("[NoteController] Current user: " + user.getId() + ", " + user.getEmail());
        return user;
    }

    @Operation(summary = "Get all notes for the current user")
    @ApiResponse(responseCode = "200", description = "List of notes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponseDTO.class)))
    @GetMapping
    public List<NoteResponseDTO> getAllNotes() {
        User user = getCurrentUser();
        return noteService.getAllNotesForUser(user).stream().map(NoteResponseDTO::fromNote).toList();
    }

    @Operation(summary = "Get a note by ID for the current user")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Note found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> getNoteById(@Parameter(description = "ID of the note", required = true) @PathVariable Long id) {
        User user = getCurrentUser();
        return noteService.getNoteByIdForUser(id, user)
                .map(NoteResponseDTO::fromNote)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new note for the current user")
    @ApiResponse(responseCode = "201", description = "Note created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponseDTO.class)))
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteResponseDTO> createNote(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Note to create", required = true, content = @Content(schema = @Schema(implementation = Note.class))) @RequestBody Note note) {
        User user = getCurrentUser();
        note.setUser(user);
        note.setId(null);
        note.setCreatedAt(null);
        note.setUpdatedAt(null);
        Note created = noteService.createNote(note, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(NoteResponseDTO.fromNote(created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a note by ID for the current user")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Note updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Note not found")
    })
    public ResponseEntity<NoteResponseDTO> updateNote(
            @Parameter(description = "ID of the note", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated note", required = true, content = @Content(schema = @Schema(implementation = NoteResponseDTO.class))) @RequestBody Note noteDetails) {
        User user = getCurrentUser();
        Note updated = noteService.updateNote(id, noteDetails, user);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(NoteResponseDTO.fromNote(updated));
    }

    @Operation(summary = "Delete a note by ID for the current user")
    @ApiResponse(responseCode = "204", description = "Note deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@Parameter(description = "ID of the note", required = true) @PathVariable Long id) {
        User user = getCurrentUser();
        boolean deleted = noteService.deleteNote(id, user);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}
