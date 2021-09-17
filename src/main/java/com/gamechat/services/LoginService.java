package com.gamechat.services;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamechat.model.AuthenticatedToken;
import com.gamechat.model.Login;
import com.gamechat.model.User;
import com.gamechat.repositories.LoginRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final LoginRepository loginRepository;
	private final AuthenticatedTokenService authenticatedTokenService;
	private final CommentService commentService;
	private final FollowService followService;
	private final LikeService likeService;
	private final SharingService sharingService;
	private final ViewPublicationService viewPublicationService;
	private final PublicationService publicationService;

	// Criar login
	public Login save(Login login) {
		return loginRepository.save(login);
	}

	// Remover login (remove também todas as dependencias do usuario no Gamechat)
	public void delete(Login deleteLogin) {

		User deleteUser = deleteLogin.getUser();

		AuthenticatedToken authenticatedToken = authenticatedTokenService.findByLoginId(deleteLogin.getId());

		authenticatedTokenService.delete(authenticatedToken);

		commentService.deleteAllByUser(deleteUser.getId());

		followService.deleteAllByUser(deleteUser.getId());

		likeService.deleteAllByUser(deleteUser.getId());

		publicationService.deleteAllByUser(deleteUser.getId());

		sharingService.deleteAllByUser(deleteUser.getId());

		viewPublicationService.deleteAllByUser(deleteUser.getId());

		loginRepository.delete(deleteLogin);
	}

	// Buscar login por id
	public Login findOne(Long id) {

		Optional<Login> login = loginRepository.findById(id);

		if (login.isPresent()) {
			return login.get();
		}

		return null;
	}

	// Buscar login por username
	public Login findByUsername(String username) {
		return loginRepository.findByUsername(username).orElseThrow(() -> new RuntimeException(""));
	}

	// Buscar login por username
	public Login findByUserId(Long id) {
		return loginRepository.findByUserId(id).orElseThrow(() -> new RuntimeException(""));
	}

	// Validar campos para criação de login
	public boolean loginValidationByUsernameOrEmailOrNickName(Login login) {

		Login userNickname = loginRepository.findByUserNickName(login.getUser().getNickName()).get();

		Login userUsername = loginRepository.findByUsername(login.getUsername()).get();

		Login userEmail = loginRepository.findByEmail(login.getEmail()).get();

		return userNickname != null || userUsername != null || userEmail != null;
	}

}
