package com.gamechat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamechat.model.AuthenticatedToken;

import java.util.Optional;

public interface AuthenticatedTokenRepository extends JpaRepository<AuthenticatedToken, Long> {

	Optional<AuthenticatedToken> findByToken(String token);

	Optional<AuthenticatedToken> findByLoginId(Long id);

}
