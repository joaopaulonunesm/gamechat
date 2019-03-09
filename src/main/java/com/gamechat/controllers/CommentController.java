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
import com.gamechat.model.Comment;
import com.gamechat.model.Publication;
import com.gamechat.model.User;
import com.gamechat.services.AuthenticatedTokenService;
import com.gamechat.services.CommentService;
import com.gamechat.services.PublicationService;

@Controller
@RequestMapping(value = "/v1")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private PublicationService publicationService;

	@Autowired
	private AuthenticatedTokenService authenticatedTokenService;

	// Comentar em uma publicação
	@RequestMapping(value = "/comment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Comment> postCommentPublication(@RequestHeader(value = "Authorization") String token,
			@RequestBody Comment comment) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().before(new Date())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = authenticatedToken.getLogin().getUser();

		comment.setUser(user);
		comment.setMoment(new Date());

		Publication publication = publicationService.findOne(comment.getPublication().getId());
		publication.setAmountComments(publication.getAmountComments() + 1);
		publicationService.save(publication);

		return new ResponseEntity<>(commentService.save(comment), HttpStatus.CREATED);
	}

	// Editar comentario de uma publicação
	@RequestMapping(value = "/comment", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Comment> putCommentPublication(@RequestHeader(value = "Authorization") String token,
			@RequestBody Comment comment) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().before(new Date())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = authenticatedToken.getLogin().getUser();

		Comment commentExistence = commentService.findByUserIdAndPublicationId(user.getId(),
				comment.getPublication().getId());

		if (commentExistence == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		commentExistence.setText(comment.getText());

		return new ResponseEntity<>(commentService.save(commentExistence), HttpStatus.OK);
	}

	// Excluir comentário de uma publicação
	@RequestMapping(value = "/uncomment/{idPublication}", method = RequestMethod.DELETE)
	public ResponseEntity<Comment> deleteComment(@RequestHeader(value = "Authorization") String token,
			@PathVariable Long idPublication) {

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByToken(token.substring(7));

		if (authenticatedToken == null || authenticatedToken.getExpirationDate().before(new Date())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = authenticatedToken.getLogin().getUser();

		Comment comment = commentService.findByUserIdAndPublicationId(user.getId(), idPublication);

		if (comment == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Publication publication = publicationService.findOne(comment.getPublication().getId());
		publication.setAmountComments(publication.getAmountComments() - 1);
		publicationService.save(publication);

		commentService.delete(comment);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Buscar Usuários que comentaram uma publicação
	@RequestMapping(value = "/comment/user/publication/{idPublication}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> getUsersCommentsByPublication(@PathVariable Long idPublication) {

		List<User> users = commentService.findUsersCommentByPublication(idPublication);

		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	// Buscar comentários de uma publicação
	@RequestMapping(value = "/comment/publication/{idPublication}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Comment>> getCommentsByPublication(@PathVariable Long idPublication) {

		List<Comment> comments = commentService.findAllByPublicationIdOrderByMomentDesc(idPublication);

		return new ResponseEntity<>(comments, HttpStatus.OK);
	}
}
