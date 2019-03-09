package com.gamechat.services;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamechat.model.AuthenticatedToken;
import com.gamechat.model.Login;
import com.gamechat.repositories.AuthenticatedTokenRepository;

@Service
public class AuthenticatedTokenService {

	@Autowired
	private AuthenticatedTokenRepository authenticatedTokenRepository;

	@Autowired
	private LoginService loginService;

	// Criar Token
	public AuthenticatedToken save(AuthenticatedToken authenticatedToken) {
		return authenticatedTokenRepository.save(authenticatedToken);
	}

	// Deletar um token
	public void delete(AuthenticatedToken authenticatedToken) {
		authenticatedTokenRepository.delete(authenticatedToken);
	}

	// Buscar Autenticação por Token
	public AuthenticatedToken findByToken(String token) {
		return authenticatedTokenRepository.findByToken(token);
	}

	// Buscar Autenticação por Login
	public AuthenticatedToken findByLoginId(Long id) {
		return authenticatedTokenRepository.findByLoginId(id);
	}

	// Validar Login para gerar um token
	public Login validateLogin(Login login) throws ServletException {

		if (login.getUsername() == null || login.getPassword() == null) {
			throw new ServletException("Username e Senha é obrigatório.");
		}

		Login loginAuthenticated = loginService.findByUsername(login.getUsername());

		if (loginAuthenticated == null) {
			throw new ServletException("User não encontrado.");
		}

		if (!login.getPassword().equals(loginAuthenticated.getPassword())) {
			throw new ServletException("User ou Password inválido.");
		}
		return loginAuthenticated;
	}
}
