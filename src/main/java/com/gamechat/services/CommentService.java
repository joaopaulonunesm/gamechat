package com.gamechat.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamechat.model.Comment;
import com.gamechat.model.Publication;
import com.gamechat.model.User;
import com.gamechat.repositories.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	// Comentar
	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}

	// Excluir comentário
	public void delete(Comment comment) {
		commentRepository.delete(comment);
	}

	// Excluir todos comentários de um user
	public void deleteAllByUser(Long id) {

		List<Comment> comments = findAllByUserId(id);

		for (Comment comment : comments) {
			commentRepository.delete(comment);
		}
	}

	// Comentários de uma publicação
	public List<Comment> findAllByPublicationIdOrderByMomentDesc(Long idPublication) {
		return commentRepository.findAllByPublicationIdOrderByMomentDesc(idPublication);
	}

	// Comentarios de um user
	public List<Comment> findAllByUserId(Long id) {
		return commentRepository.findAllByUserId(id);
	}

	// Usuarios que comentou em uma publicação
	public List<User> findUsersCommentByPublication(Long idPublication) {

		List<Comment> comments = findAllByPublicationIdOrderByMomentDesc(idPublication);

		List<User> users = new ArrayList<>();

		for (Comment comment : comments) {
			users.add(comment.getUser());
		}

		return users;
	}

	// Comentario de um usuario para uma publicação especifica
	public Comment findByUserIdAndPublicationId(Long idUser, Long idPublication) {

		return commentRepository.findByUserIdAndPublicationId(idUser, idPublication);
	}

	// Publicações que um usuário comentou
	public List<Publication> findPublicationsCommentedByUser(Long idUser) {

		List<Comment> comments = commentRepository.findAllByUserId(idUser);

		List<Publication> publications = new ArrayList<>();

		for (Comment comment : comments) {
			publications.add(comment.getPublication());
		}

		return publications;
	}

}
