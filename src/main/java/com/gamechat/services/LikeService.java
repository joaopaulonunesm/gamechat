package com.gamechat.services;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamechat.model.Like;
import com.gamechat.model.Publication;
import com.gamechat.model.User;
import com.gamechat.repositories.LikeRepository;

@Service
@RequiredArgsConstructor
public class LikeService {

	private final LikeRepository likeRepository;

	// Curtir
	public Like save(Like like) {
		return likeRepository.save(like);
	}

	// Deixar de curtir
	public void delete(Like like) {
		likeRepository.delete(like);
	}

	// Deixar de curtir todos curtir que um user deu
	public void deleteAllByUser(Long id) {

		List<Like> likes = findAllByUserId(id);

		for (Like like : likes) {
			likeRepository.delete(like);
		}

	}

	// Curtidas de uma publicação
	public List<Like> findAllByPublicationIdOrderByMomentDesc(Long idPublication) {
		return likeRepository.findAllByPublicationIdOrderByMomentDesc(idPublication);
	}

	// Curtida de um usuario para uma publicação especifica
	public Like findByUserIdAndPublicationId(Long idUser, Long idPublication) {
		return likeRepository.findByUserIdAndPublicationId(idUser, idPublication).orElseThrow(() -> new RuntimeException("Erro ao buscar uma curtida do usuario para uma publicação especifica"));
	}

	// Todos os Likes de um user
	public List<Like> findAllByUserId(Long id) {
		return likeRepository.findAllByUserId(id);
	}

	// Usuarios que curtiu uma publicação
	public List<User> findUsersLikeByPublication(Long idPublication) {

		List<Like> likes = findAllByPublicationIdOrderByMomentDesc(idPublication);

		List<User> users = new ArrayList<>();

		for (Like like : likes) {
			users.add(like.getUser());
		}

		return users;
	}

	// Publicações que um usuário curtiu
	public List<Publication> findPublicationsLikedByUser(Long idUser) {

		List<Like> likes = likeRepository.findAllByUserId(idUser);

		List<Publication> publications = new ArrayList<>();

		for (Like like : likes) {
			publications.add(like.getPublication());
		}

		return publications;
	}

}
