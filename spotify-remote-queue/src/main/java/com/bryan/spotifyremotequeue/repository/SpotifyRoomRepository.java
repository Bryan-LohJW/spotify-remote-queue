package com.bryan.spotifyremotequeue.repository;

import com.bryan.spotifyremotequeue.model.SpotifyRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotifyRoomRepository extends JpaRepository<SpotifyRoom, String> {
}
