package com.gamechat.controllers;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import lombok.RequiredArgsConstructor;
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
import com.gamechat.model.Like;
import com.gamechat.model.Publication;
import com.gamechat.model.User;
import com.gamechat.services.AuthenticatedTokenService;
import com.gamechat.services.LikeService;
import com.gamechat.services.PublicationService;

@Controller
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class LikeController {

	private final LikeService likeService;
	private final PublicationService publicationService;
	private final AuthenticatedTokenService authenticatedTokenService;

	// Curtir publicação
	@RequestMapping(value = "/like", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Like> postLikePublication(@RequestHeader(value = "Authorization") String token,
			@RequestBody Like like) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().isBefore(LocalDateTime.now())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = authenticatedToken.getLogin().getUser();

		if (likeService.findByUserIdAndPublicationId(user.getId(), like.getPublication().getId()) != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		like.setUser(user);
		like.setMoment(LocalDateTime.now());

		Publication publication = publicationService.findOne(like.getPublication().getId());
		publication.setAmountLikes(publication.getAmountLikes() + 1);
		publicationService.save(publication);

		return new ResponseEntity<>(likeService.save(like), HttpStatus.CREATED);
	}

	// Descurtir uma publicação
	@RequestMapping(value = "/unlike/{idPublication}", method = RequestMethod.DELETE)
	public ResponseEntity<Like> deleteLike(@RequestHeader(value = "Authorization") String token,
			@PathVariable Long idPublication) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().isBefore(LocalDateTime.now())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = authenticatedToken.getLogin().getUser();

		Like like = likeService.findByUserIdAndPublicationId(user.getId(), idPublication);

		if (like == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Publication publication = publicationService.findOne(like.getPublication().getId());
		publication.setAmountLikes(publication.getAmountLikes() - 1);
		publicationService.save(publication);

		likeService.delete(like);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Buscar Usuários que curtiram uma publicação
	@RequestMapping(value = "/like/publication/{idPublication}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> getUsersLikesByPublication(@PathVariable Long idPublication) {

		List<User> users = likeService.findUsersLikeByPublication(idPublication);

		return new ResponseEntity<>(users, HttpStatus.OK);
	}
}
