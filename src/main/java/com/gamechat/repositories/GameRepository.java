package com.gamechat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamechat.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

	List<Game> findByNameIgnoreCase(String search);

	List<Game> findByGenreIgnoreCase(String search);

}
