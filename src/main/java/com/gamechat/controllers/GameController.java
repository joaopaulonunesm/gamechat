package com.gamechat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gamechat.model.Game;
import com.gamechat.services.GameService;

@Controller
@RequestMapping(value = "/v1")
public class GameController {

	@Autowired
	private GameService gameService;

	//Criação de Game
	@RequestMapping(value = "/game", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Game> postGame(@RequestBody Game game) {

		return new ResponseEntity<>(gameService.save(game), HttpStatus.CREATED);
	}

	//Alterar um Game
	@RequestMapping(value = "/game/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Game> changePublication(@PathVariable Long id, @RequestBody Game game) {

		if (gameService.findOne(id) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		game.setId(id);

		return new ResponseEntity<>(gameService.save(game), HttpStatus.OK);
	}

	//Deletar um Game
	@RequestMapping(value = "/game/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Game> deleteGame(@PathVariable Long id) {

		Game game = gameService.findOne(id);

		if (game == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		gameService.delete(game);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	//Buscar todos os Games
	@RequestMapping(value = "/game", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Game>> getGames() {

		List<Game> games = gameService.findAll();

		return new ResponseEntity<>(games, HttpStatus.OK);
	}

	//Buscar apenas um Game
	@RequestMapping(value = "/game/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Game> getOneGame(@PathVariable Long id) {

		if (gameService.findOne(id) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(gameService.findOne(id), HttpStatus.OK);
	}

	//Buscar Game por nome
	@RequestMapping(value = "/game/name/{game}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Game>> getGameByName(@PathVariable String game) {

		List<Game> games = gameService.findByNameIgnoreCase(game);

		if (games == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(games, HttpStatus.OK);
	}

	//Buscar Game por gênero
	@RequestMapping(value = "/game/genre/{genre}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Game>> getGameByGenre(@PathVariable String genre) {
		List<Game> genres = gameService.findByGenreIgnoreCase(genre);
		if (genres == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(genres, HttpStatus.OK);
	}

}
