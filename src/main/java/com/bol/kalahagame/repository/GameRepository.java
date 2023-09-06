package com.bol.kalahagame.repository;

import com.bol.kalahagame.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {
    Optional<Game> findByGameId(String id);
}