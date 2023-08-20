package com.bryan.spotifyremotequeue.service.spotify;

import com.bryan.spotifyremotequeue.controller.request.RegisterRoomRequest;
import com.bryan.spotifyremotequeue.controller.request.RegisterUserRequest;
import com.bryan.spotifyremotequeue.enums.SpotifyConstants;
import com.bryan.spotifyremotequeue.exception.RegistrationException;
import com.bryan.spotifyremotequeue.exception.SpotifyApiException;
import com.bryan.spotifyremotequeue.model.SpotifyRoom;
import com.bryan.spotifyremotequeue.model.User;
import com.bryan.spotifyremotequeue.repository.SpotifyRoomRepository;
import com.bryan.spotifyremotequeue.repository.UserRepository;
import com.bryan.spotifyremotequeue.service.authentication.AuthenticationService;
import com.bryan.spotifyremotequeue.service.authentication.response.AuthenticateResponse;
import com.bryan.spotifyremotequeue.service.spotify.response.CurrentUserProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Optional;

@Service
public class SpotifyRegisterService {

    @Autowired
    private SpotifyRoomRepository spotifyRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Value("${spotify.authorizationHeader}")
    private String authorizationHeader;

    @Value("${spotify.redirectUri}")
    private String redirectUri;

    @Transactional
    public User registerRoom(RegisterRoomRequest request) throws SpotifyApiException {
        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
        bodyValues.add(SpotifyConstants.CODE, request.getCode());
        bodyValues.add(SpotifyConstants.REDIRECT_URI, redirectUri);
        bodyValues.add(SpotifyConstants.GRANT_TYPE, SpotifyConstants.AUTHORIZATION_CODE);

        WebClient.Builder builder = WebClient.builder();
        AuthenticateResponse authenticateResponse = null;
        try {
            authenticateResponse = builder.build()
                    .post()
                    .uri(SpotifyConstants.ACCESS_TOKEN_API)
                    .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(authorizationHeader.getBytes()))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body(BodyInserters.fromFormData(bodyValues))
                    .retrieve()
                    .bodyToMono(AuthenticateResponse.class)
                    .block();
        } catch (WebClientResponseException exception) {
            throw new SpotifyApiException(exception.getStatusCode().value(), "Exception while getting Spotify access token");
        }
        CurrentUserProfileResponse currentUserProfileResponse = null;
        try {
            currentUserProfileResponse = builder.build()
                    .get()
                    .uri(SpotifyConstants.BASE_API + SpotifyConstants.ME)
                    .header("Authorization", "Bearer " + authenticateResponse.getAccess_token())
                    .retrieve()
                    .bodyToMono(CurrentUserProfileResponse.class)
                    .block();
        } catch (WebClientResponseException exception) {
            throw new SpotifyApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Exception while getting current user profile");
        }
        if (!currentUserProfileResponse.getProduct().equals(SpotifyConstants.PREMIUM)) {
            throw new SpotifyApiException(HttpStatus.FORBIDDEN.value(), "Requires premium account");
        }
        if (spotifyRoomRepository.existsByOwner(currentUserProfileResponse.getId())) {
            //https://thorben-janssen.com/avoid-cascadetype-delete-many-assocations/
            SpotifyRoom room = spotifyRoomRepository.findByOwner(currentUserProfileResponse.getId());
            spotifyRoomRepository.deleteByOwner(currentUserProfileResponse.getId());
        }
        SpotifyRoom spotifyRoom = spotifyRoomRepository.save(new SpotifyRoom(authenticateResponse, currentUserProfileResponse.getId()));
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(Arrays.asList("ROLE_OWNER", "ROLE_USER"));
        userRepository.save(new User(currentUserProfileResponse.getDisplay_name(), authorities, spotifyRoom));
        return userRepository.save(new User(currentUserProfileResponse.getDisplay_name(), authorities, spotifyRoom));
    }

    public User registerUser(RegisterUserRequest request) throws SpotifyApiException {
        SpotifyRoom room = authenticationService.authenticateRoomJoining(request.getRoomId(), request.getPin(), request.getUserId());
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(Arrays.asList("ROLE_USER"));
        return userRepository.save(new User(request.getUserId(), authorities, room));
    }
}
