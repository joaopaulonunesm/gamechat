package com.gamechat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamechat.model.Login;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

    Optional<Login> findByUserNickName(String nickName);

    Optional<Login> findByUsername(String username);

    Optional<Login> findByEmail(String email);

    Optional<Login> findByUserId(Long id);

}
