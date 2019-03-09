package com.gamechat.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamechat.model.Game;
import com.gamechat.repositories.GameRepository;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;

	// Criar um game
	public Game save(Game game) {
		return gameRepository.save(game);
	}

	// Remover um game
	public void delete(Game game) {
		gameRepository.delete(game);
	}

	// Buscar game por id
	public Game findOne(Long id) {

		Optional<Game> game = gameRepository.findById(id);

		if (game.isPresent()) {
			return game.get();
		}

		return null;
	}

	// Listar todos os games
	public List<Game> findAll() {
		return gameRepository.findAll();
	}

	// Buscar Game por nome
	public List<Game> findByNameIgnoreCase(String search) {
		return gameRepository.findByNameIgnoreCase(search);
	}

	// Buscar Game por gênero
	public List<Game> findByGenreIgnoreCase(String search) {
		return gameRepository.findByGenreIgnoreCase(search);
	}

}
