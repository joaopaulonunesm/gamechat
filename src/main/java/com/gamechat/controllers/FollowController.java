package com.gamechat.controllers;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
import com.gamechat.model.Follow;
import com.gamechat.model.User;
import com.gamechat.services.AuthenticatedTokenService;
import com.gamechat.services.FollowService;
import com.gamechat.services.UserService;

@Controller
@RequestMapping(value = "/v1")
public class FollowController {

	@Autowired
	private FollowService followService;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticatedTokenService authenticatedTokenService;

	// Post para seguir uma pessoa
	@RequestMapping(value = "/follow", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Follow> toFollow(@RequestHeader(value = "Authorization") String token,
			@RequestBody Follow follow) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().before(new Date())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = authenticatedToken.getLogin().getUser();

		follow.setUser(user);

		User following = userService.findOne(follow.getFollowing().getId());

		if (following.getId() == user.getId()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (followService.findAllFollowings(user.getId()).contains(following)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		follow.setMoment(new Date());

		user.setAmountFollowings(user.getAmountFollowings() + 1);
		userService.save(user);

		following.setAmountFollowers(following.getAmountFollowers() + 1);
		userService.save(following);

		return new ResponseEntity<>(followService.save(follow), HttpStatus.CREATED);
	}

	// Delete para dar unfollow em uma pessoa
	@RequestMapping(value = "/unfollow/{idExFollowing}", method = RequestMethod.DELETE)
	public ResponseEntity<Follow> unfollow(@RequestHeader(value = "Authorization") String token,
			@PathVariable Long idExFollowing) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().before(new Date())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User userLogged = authenticatedToken.getLogin().getUser();

		User exFollowing = userService.findOne(idExFollowing);

		if (exFollowing == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Follow follow = followService.findByUserIdAndFollowingId(userLogged.getId(), exFollowing.getId());

		if (follow == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		userLogged.setAmountFollowings(userLogged.getAmountFollowings() - 1);
		userService.save(userLogged);

		exFollowing.setAmountFollowers(exFollowing.getAmountFollowers() - 1);
		userService.save(exFollowing);

		followService.delete(follow);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Buscar todas as pessoas que o usuário segue por token
	@RequestMapping(value = "/follows", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> followsUserByToken(@RequestHeader(value = "Authorization") String token) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().before(new Date())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = authenticatedToken.getLogin().getUser();

		List<User> follows = followService.findAllFollowings(user.getId());

		if (follows == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(follows, HttpStatus.OK);
	}

	// Buscar todas as pessoas que o usuário segue por nickname
	@RequestMapping(value = "/follows/nickname/{nickName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> userFollowingsByNickName(@PathVariable String nickName) {

		User user = userService.findByNickNameIgnoreCase(nickName);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<User> follows = followService.findAllFollowings(user.getId());

		if (follows == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(follows, HttpStatus.OK);
	}

	// Buscar todas as pessoas que segue o usuário por token
	@RequestMapping(value = "/followers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> followersUserByToken(@RequestHeader(value = "Authorization") String token) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().before(new Date())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = authenticatedToken.getLogin().getUser();

		List<User> followers = followService.findAllFollowers(user.getId());

		if (followers == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(followers, HttpStatus.OK);
	}

	// Buscar todas as pessoas que segue o usuário por nickname
	@RequestMapping(value = "/followers/nickname/{nickName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> followersUserById(@PathVariable String nickName) {

		User user = userService.findByNickNameIgnoreCase(nickName);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<User> followers = followService.findAllFollowers(user.getId());

		if (followers == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(followers, HttpStatus.OK);
	}

	// Retorna lista de usuarios seguidos de quem você segue
	@RequestMapping(value = "/suggestion/followings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<User>> suggestionsByFollowings(@RequestHeader(value = "Authorization") String token) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().before(new Date())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User userLogged = authenticatedToken.getLogin().getUser();

		List<User> following = followService.findAllFollowings(userLogged.getId());

		if (following == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Set<User> suggestions = new LinkedHashSet<>();

		for (User userSuggestion : following) {
			suggestions.addAll(followService.findAllFollowings(userSuggestion.getId()));
		}

		suggestions.removeAll(following);

		while (suggestions.contains(userLogged)) {
			suggestions.remove(userLogged);
		}

		return new ResponseEntity<>(suggestions, HttpStatus.OK);
	}

	// Retorna lista de usuarios que o usuario segue
	@RequestMapping(value = "/suggestion/following/{nickName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> suggestionsByFollowing(@RequestHeader(value = "Authorization") String token,
			@PathVariable String nickName) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().before(new Date())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User userLogged = authenticatedToken.getLogin().getUser();

		User following = userService.findByNickNameIgnoreCase(nickName);

		if (following == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<User> suggestions = followService.findAllFollowings(following.getId());

		List<User> iFollow = followService.findAllFollowings(userLogged.getId());

		suggestions.removeAll(iFollow);

		suggestions.remove(userLogged);

		Collections.shuffle(suggestions);

		return new ResponseEntity<>(suggestions, HttpStatus.OK);
	}

	// Verificar se sigo o usuario do nickname
	@RequestMapping(value = "/follows/verify/ifolow/{nickName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Follow> verifyRelationIFollow(@RequestHeader(value = "Authorization") String token,
			@PathVariable String nickName) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().before(new Date())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User userLogged = authenticatedToken.getLogin().getUser();

		User following = userService.findByNickNameIgnoreCase(nickName);

		if (following.getNickName().equals(userLogged.getNickName())) {
			return new ResponseEntity<>(HttpStatus.OK);
		}

		Follow follow = followService.findByUserIdAndFollowingId(userLogged.getId(), following.getId());

		if (follow == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(follow, HttpStatus.OK);
	}

	// Verificar se sou seguido pelo usuario do nickname
	@RequestMapping(value = "/follows/verify/imfollowed/{nickName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Follow> verifyRelationImFollowed(@RequestHeader(value = "Authorization") String token,
			@PathVariable String nickName) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().before(new Date())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User userLogged = authenticatedToken.getLogin().getUser();

		User following = userService.findByNickNameIgnoreCase(nickName);

		if (following.getNickName().equals(userLogged.getNickName())) {
			return new ResponseEntity<>(HttpStatus.OK);
		}

		Follow follow = followService.findByUserIdAndFollowingId(following.getId(), userLogged.getId());

		if (follow == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(follow, HttpStatus.OK);
	}
}
