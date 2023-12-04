package com.example.model.board;

import lombok.Data;


import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

@Data
public class BoardUpdateForm {
    private Long board_id;
    @NotBlank
    private String board_title;
    @NotBlank
    private String board_content;
    private String board_member;
    private Long board_hit;
    private LocalDateTime board_create_time;

}
