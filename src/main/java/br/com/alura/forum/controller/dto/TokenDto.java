package br.com.alura.forum.controller.dto;

import lombok.Getter;

@Getter
public class TokenDto {
    private String token, type;

    public TokenDto(String token, String type) {
        this.token = token;
        this.type = type;
    }
}
