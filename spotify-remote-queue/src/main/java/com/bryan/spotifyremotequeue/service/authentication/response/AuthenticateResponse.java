package com.bryan.spotifyremotequeue.service.authentication.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateResponse {
    private String access_token;
    private String token_type;
    private Long expires_in;
    private String refresh_token;
    private String state;
}
