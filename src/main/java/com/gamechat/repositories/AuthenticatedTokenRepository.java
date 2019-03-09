package com.gamechat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamechat.model.AuthenticatedToken;

public interface AuthenticatedTokenRepository extends JpaRepository<AuthenticatedToken, Long> {

	public AuthenticatedToken findByToken(String token);
	
	public AuthenticatedToken findByLoginId(Long id);

}
