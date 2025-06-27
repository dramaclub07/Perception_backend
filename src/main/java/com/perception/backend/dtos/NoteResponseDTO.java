package com.perception.backend.dtos;

import com.perception.backend.models.Note;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response DTO for a note")
public class NoteResponseDTO {
    @Schema(description = "Note ID", example = "1")
    private Long id;
    @Schema(description = "Title of the note", example = "Meeting Notes")
    private String title;
    @Schema(description = "Content of the note", example = "Discuss project milestones.")
    private String content;
    @Schema(description = "Color of the note", example = "yellow")
    private String color;
    @Schema(description = "Creation timestamp", example = "2025-06-24T06:10:33.766Z")
    private String createdAt;
    @Schema(description = "Update timestamp", example = "2025-06-24T06:10:33.766Z")
    private String updatedAt;
    @Schema(description = "User ID", example = "1")
    private Long userId;
    @Schema(description = "Timestamp", example = "2025-06-24T06:10:33.766Z")
    private String timestamp;
    @Schema(description = "Weather info", example = "Sunny")
    private String weather;

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setColor(String color) { this.color = color; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public void setWeather(String weather) { this.weather = weather; }

    public static NoteResponseDTO fromNote(Note note) {
        NoteResponseDTO dto = new NoteResponseDTO();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle() != null ? note.getTitle() : "");
        dto.setContent(note.getContent());
        dto.setColor(note.getColor() != null ? note.getColor() : "#ffffff");
        dto.setCreatedAt(note.getCreatedAt() != null ? note.getCreatedAt().toString() : null);
        dto.setUpdatedAt(note.getUpdatedAt() != null ? note.getUpdatedAt().toString() : null);
        dto.setUserId(note.getUser() != null ? note.getUser().getId() : null);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
        if (note.getCreatedAt() != null) {
            dto.setTimestamp(note.getCreatedAt().format(formatter));
        } else {
            dto.setTimestamp(null);
        }

        dto.setWeather(note.getWeather() != null ? note.getWeather() : "Sunny");

        return dto;
    }
}
