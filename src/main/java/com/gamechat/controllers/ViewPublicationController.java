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
import com.gamechat.model.Publication;
import com.gamechat.model.User;
import com.gamechat.model.ViewPublication;
import com.gamechat.services.AuthenticatedTokenService;
import com.gamechat.services.PublicationService;
import com.gamechat.services.ViewPublicationService;

@Controller
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class ViewPublicationController {

	private final ViewPublicationService viewPublicationService;
	private final PublicationService publicationService;
	private final AuthenticatedTokenService authenticatedTokenService;

	// Criar visualização para uma publicação
	@RequestMapping(value = "/viewPublication", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ViewPublication> postViewPublication(@RequestHeader(value = "Authorization") String token,
			@RequestBody ViewPublication viewPublication) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().isBefore(LocalDateTime.now())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = authenticatedToken.getLogin().getUser();

		if (viewPublicationService.findByUserIdAndPublicationId(user.getId(),
				viewPublication.getPublication().getId()) != null) {
			return null;
		}

		viewPublication.setUser(user);

		viewPublication.setMoment(LocalDateTime.now());

		Publication publication = publicationService.findOne(viewPublication.getPublication().getId());
		publication.setAmountViews(publication.getAmountViews() + 1);
		publicationService.save(publication);

		return new ResponseEntity<>(viewPublicationService.save(viewPublication), HttpStatus.CREATED);
	}

	// Usuários que visualizaram uma publicação
	@RequestMapping(value = "/viewPublication/publication/{idPublication}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> getUsersViewPublicationsByPublication(@PathVariable Long idPublication) {

		List<User> users = viewPublicationService.findUsersViewPublicationByPublication(idPublication);

		return new ResponseEntity<>(users, HttpStatus.OK);
	}

}
