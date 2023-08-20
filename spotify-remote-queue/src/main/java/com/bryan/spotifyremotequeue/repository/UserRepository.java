package com.bryan.spotifyremotequeue.repository;

import com.bryan.spotifyremotequeue.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT U FROM User U WHERE U.userId = :userId AND U.room.roomId = :roomId")
    Optional<User> findByUserIdAndRoomId(@Param("userId") String userId, @Param("roomId") String roomId);
}
