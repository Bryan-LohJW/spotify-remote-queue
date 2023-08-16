package com.bryan.spotifyremotequeue.config.security;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContext;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Principal {
    private String userId;
    private String roomId;
}
