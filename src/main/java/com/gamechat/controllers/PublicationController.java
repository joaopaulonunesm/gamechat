package com.gamechat.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gamechat.model.AuthenticatedToken;
import com.gamechat.model.Game;
import com.gamechat.model.Publication;
import com.gamechat.model.User;
import com.gamechat.services.AuthenticatedTokenService;
import com.gamechat.services.GameService;
import com.gamechat.services.PublicationService;
import com.gamechat.services.UserService;

@Controller
@RequestMapping(value = "/v1")
public class PublicationController {

	@Autowired
	private PublicationService publicationService;

	@Autowired
	private GameService gameService;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticatedTokenService authenticatedTokenService;

	// Criar publicação
	@RequestMapping(value = "/publication", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Publication> postPublication(@RequestHeader(value = "Authorization") String token,
			@RequestBody Publication publication) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().before(new Date())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = authenticatedToken.getLogin().getUser();

		publication.setUser(user);

		if (publication.getGame() != null) {

			Game game = gameService.findOne(publication.getGame().getId());

			if (game == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			publication.setGame(game);
		}

		publication.setCreateDate(new Date());

		user.setAmountPublications(user.getAmountPublications() + 1);
		publication.setAmountLikes(0);
		publication.setAmountShares(0);
		publication.setAmountViews(0);
		publication.setAmountComments(0);

		return new ResponseEntity<>(publicationService.save(publication), HttpStatus.CREATED);
	}

	// Alterar Publicação
	@RequestMapping(value = "/publication/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Publication> changePublication(@PathVariable Long id, @RequestBody Publication publication) {

		Publication searchPublication = publicationService.findOne(id);

		if (searchPublication == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (searchPublication.getUser().getId() != publication.getUser().getId()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		publication.setId(id);
		publication.setCreateDate(searchPublication.getCreateDate());

		return new ResponseEntity<>(publicationService.save(publication), HttpStatus.OK);
	}

	// Deletar uma publicação
	@RequestMapping(value = "/publication/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Publication> deletePublication(@PathVariable Long id) {

		Publication deletePublication = publicationService.findOne(id);

		if (deletePublication == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		publicationService.delete(deletePublication);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Buscar publicação por id
	@RequestMapping(value = "/publication/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Publication> getOnePublication(@PathVariable Long id) {

		Publication publication = publicationService.findOne(id);

		if (publication == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(publication, HttpStatus.OK);
	}

	// Buscar publicações de um usuário por id
	@RequestMapping(value = "/publication/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Publication>> getPublicationsById(@PathVariable Long id) {

		User user = userService.findOne(id);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<Publication> publications = publicationService.findAllByUserIdOrderByCreateDateDesc(user.getId());

		if (publications == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(publications, HttpStatus.OK);
	}

	// Buscar publicações de um usuário por nickname
	@RequestMapping(value = "/publication/user/nickname/{nickName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Publication>> getPublicationsByNickName(@PathVariable String nickName) {

		User user = userService.findByNickNameIgnoreCase(nickName);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<Publication> publications = publicationService.findAllByUserIdOrderByCreateDateDesc(user.getId());

		if (publications == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(publications, HttpStatus.OK);
	}

	// Buscar publicações de um usuário por token
	@RequestMapping(value = "/publication/me", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Publication>> getPublicationsByToken(
			@RequestHeader(value = "Authorization") String token) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		User user = authenticatedToken.getLogin().getUser();

		List<Publication> publications = publicationService.findAllByUserIdOrderByCreateDateDesc(user.getId());

		if (publications == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(publications, HttpStatus.OK);
	}

	// Buscar publicações da Timeline (Minhas publicações e publicações de quem
	// eu sigo ordenado por data de criação)
	@RequestMapping(value = "/publication/friends", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Publication>> publicationsForUser(@RequestHeader(value = "Authorization") String token) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().before(new Date())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = authenticatedToken.getLogin().getUser();

		List<Publication> friendsPublications = publicationService.findPublicationsByFollowings(user.getId());

		List<Publication> mePublications = publicationService.findAllByUserIdOrderByCreateDateDesc(user.getId());

		mePublications.addAll(friendsPublications);

		publicationService.orderByCreateDate(mePublications);

		return new ResponseEntity<>(mePublications, HttpStatus.OK);
	}

}
