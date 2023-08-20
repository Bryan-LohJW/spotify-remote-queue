package com.bryan.spotifyremotequeue.repository;

import com.bryan.spotifyremotequeue.model.SpotifyRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpotifyRoomRepository extends JpaRepository<SpotifyRoom, String> {
    public SpotifyRoom findByOwner(String owner);

    public boolean existsByOwner(String owner);

    public List<SpotifyRoom> deleteByOwner(String owner);
}
