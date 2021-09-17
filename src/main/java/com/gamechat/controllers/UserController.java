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
import com.gamechat.model.User;
import com.gamechat.services.AuthenticatedTokenService;
import com.gamechat.services.UserService;

@Controller
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final AuthenticatedTokenService authenticatedTokenService;

	// Alterar informações do usuário
	@RequestMapping(value = "/user", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> changeUserByToken(@RequestHeader(value = "Authorization") String token,
			@RequestBody User user) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().isBefore(LocalDateTime.now())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User changeUser = authenticatedToken.getLogin().getUser();

		User userByNickName = userService.findByNickNameIgnoreCase(user.getNickName());

		if (userByNickName != null && !changeUser.getNickName().equalsIgnoreCase(user.getNickName())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		user.setId(changeUser.getId());
		if (user.getNickName().isEmpty()) {
			user.setNickName(changeUser.getNickName());
		}

		return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
	}

	// Buscar somente um usuário por id
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserById(@PathVariable Long id) {

		User user = userService.findOne(id);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	// Buscar somente um usuário por nickname
	@RequestMapping(value = "/user/nickname/{nickName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserByNickName(@PathVariable String nickName) {

		User user = userService.findByNickNameIgnoreCase(nickName);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	// Buscar somente um usuário por token
	@RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserByToken(@RequestHeader(value = "Authorization") String token) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().isBefore(LocalDateTime.now())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = authenticatedToken.getLogin().getUser();

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	// Buscar todos usuários
	@RequestMapping(value = "/user/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> getAllUser() {

		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}
}
