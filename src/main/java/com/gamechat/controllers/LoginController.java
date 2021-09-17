package com.gamechat.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gamechat.model.Login;
import com.gamechat.services.LoginService;

@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	// Criar login validande se já existe um com o mesmo Nome, Email ou Nickname
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Login> postLogin(@RequestBody Login login) {

		boolean loginExist = loginService.loginValidationByUsernameOrEmailOrNickName(login);

		if (!loginExist) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		login.setCreateDate(LocalDateTime.now());
		login.setActive(true);
		login.getUser().setAmountFollowings(0);
		login.getUser().setAmountFollowers(0);
		login.getUser().setAmountPublications(0);

		return new ResponseEntity<>(loginService.save(login), HttpStatus.CREATED);
	}

	// Alterar senha do Login
	@RequestMapping(value = "/login/change/password/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Login> changeLogin(@PathVariable Long id, @RequestBody Login login) {

		Login searchLogin = loginService.findOne(id);

		if (searchLogin == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		searchLogin.setPassword(login.getPassword());

		return new ResponseEntity<>(loginService.save(searchLogin), HttpStatus.OK);
	}

	// Deletar um Login
	@RequestMapping(value = "/login/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Login> deleteLogin(@PathVariable Long id) {

		Login deleteLogin = loginService.findOne(id);

		if (deleteLogin == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		loginService.delete(deleteLogin);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Buscar dodas de um Login
	@RequestMapping(value = "/login/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Login> getLoginById(@PathVariable Long id) {

		Login login = loginService.findOne(id);

		if (login == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(login, HttpStatus.OK);
	}

}
