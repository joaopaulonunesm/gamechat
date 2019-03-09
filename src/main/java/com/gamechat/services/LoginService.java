package com.gamechat.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamechat.model.AuthenticatedToken;
import com.gamechat.model.Login;
import com.gamechat.model.User;
import com.gamechat.repositories.LoginRepository;

@Service
public class LoginService {

	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private AuthenticatedTokenService authenticatedTokenService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private FollowService followService;

	@Autowired
	private LikeService likeService;

	@Autowired
	private SharingService sharingService;

	@Autowired
	private ViewPublicationService viewPublicationService;

	@Autowired
	private PublicationService publicationService;

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
		return loginRepository.findByUsername(username);
	}

	// Buscar login por username
	public Login findByUserId(Long id) {
		return loginRepository.findByUserId(id);
	}

	// Validar campos para criação de login
	public boolean loginValidationByUsernameOrEmailOrNickName(Login login) {

		Login userNickname = loginRepository.findByUserNickName(login.getUser().getNickName());

		Login userUsername = loginRepository.findByUsername(login.getUsername());

		Login userEmail = loginRepository.findByEmail(login.getEmail());

		return userNickname != null || userUsername != null || userEmail != null;
	}

}
