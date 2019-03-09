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
import com.gamechat.model.Publication;
import com.gamechat.model.Sharing;
import com.gamechat.model.User;
import com.gamechat.services.AuthenticatedTokenService;
import com.gamechat.services.PublicationService;
import com.gamechat.services.SharingService;

@Controller
@RequestMapping(value = "/v1")
public class SharingController {

	@Autowired
	private SharingService sharingService;

	@Autowired
	private PublicationService publicationService;

	@Autowired
	private AuthenticatedTokenService authenticatedTokenService;

	// Compartilhar publicação
	@RequestMapping(value = "/sharing", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sharing> toSharePublication(@RequestHeader(value = "Authorization") String token,
			@RequestBody Sharing sharing) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().before(new Date())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = authenticatedToken.getLogin().getUser();

		if (sharingService.findByUserIdAndPublicationId(user.getId(), sharing.getPublication().getId()) != null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		sharing.setUser(user);

		sharing.setMoment(new Date());

		Publication publication = publicationService.findOne(sharing.getPublication().getId());
		publication.setAmountShares(publication.getAmountShares() + 1);
		publicationService.save(publication);

		return new ResponseEntity<>(sharingService.save(sharing), HttpStatus.CREATED);
	}

	// Descomparilhar uma publicação
	@RequestMapping(value = "/unshare/{idPublication}", method = RequestMethod.DELETE)
	public ResponseEntity<Sharing> unsharePublication(@RequestHeader(value = "Authorization") String token,
			@PathVariable Long idPublication) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().before(new Date())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = authenticatedToken.getLogin().getUser();

		Sharing sharing = sharingService.findByUserIdAndPublicationId(user.getId(), idPublication);

		if (sharing == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Publication publication = publicationService.findOne(sharing.getPublication().getId());
		publication.setAmountShares(publication.getAmountShares() - 1);
		publicationService.save(publication);

		sharingService.delete(sharing);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Usuários que comparilharam uma publicação
	@RequestMapping(value = "/sharing/publication/{idPublication}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> getUsersSharingsByPublication(@PathVariable Long idPublication) {

		List<User> users = sharingService.findUsersSharingByPublication(idPublication);

		return new ResponseEntity<>(users, HttpStatus.OK);
	}

}
