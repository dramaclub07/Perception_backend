package com.perception.backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request DTO for creating/updating a note")
public class NoteRequestDTO {
    @Schema(description = "Title of the note", example = "Meeting Notes")
    private String title;
    @Schema(description = "Content of the note", example = "Discuss project milestones.")
    private String content;
    @Schema(description = "Color of the note", example = "yellow")
    private String color;
}
