package com.bryan.spotifyremotequeue.repository;

import com.bryan.spotifyremotequeue.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
