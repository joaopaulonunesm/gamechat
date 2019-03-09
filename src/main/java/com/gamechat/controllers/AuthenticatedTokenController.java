package com.gamechat.controllers;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gamechat.model.AuthenticatedToken;
import com.gamechat.model.Login;
import com.gamechat.services.AuthenticatedTokenService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Controller
public class AuthenticatedTokenController {

	@Autowired
	private AuthenticatedTokenService authenticatedTokenService;

	// Post de autenticação de Token
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthenticatedToken> authenticatedToken(@RequestBody Login login) throws ServletException {

		Login loginAuthenticated = authenticatedTokenService.validateLogin(login);

		AuthenticatedToken existenceToken = authenticatedTokenService.findByLoginId(loginAuthenticated.getId());

		AuthenticatedToken newAuthenticatedToken = new AuthenticatedToken();

		Date expirationDate = new Date(System.currentTimeMillis() + 240 * 60 * 1000);

		String token = Jwts.builder().setSubject(loginAuthenticated.getUsername())
				.signWith(SignatureAlgorithm.HS512, "autenticando").setExpiration(expirationDate).compact();

		if (existenceToken != null && existenceToken.getExpirationDate().after(new Date())) {

			return new ResponseEntity<>(existenceToken, HttpStatus.OK);

		} else if (existenceToken != null && existenceToken.getExpirationDate().before(new Date())) {

			newAuthenticatedToken.setId(existenceToken.getId());
		}

		newAuthenticatedToken.setLogin(loginAuthenticated);
		newAuthenticatedToken.setToken(token);
		newAuthenticatedToken.setExpirationDate(expirationDate);

		return new ResponseEntity<>(authenticatedTokenService.save(newAuthenticatedToken), HttpStatus.OK);
	}

	// Verifica se o token é existente
	@RequestMapping(value = "/authenticate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthenticatedToken> getUserByToken(@RequestHeader(value = "Authorization") String token) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().before(new Date())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<>(authenticatedToken, HttpStatus.OK);
	}

}
