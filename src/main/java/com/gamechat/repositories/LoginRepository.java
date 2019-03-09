package com.gamechat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamechat.model.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

	public Login findByUserNickName(String nickName);

	public Login findByUsername(String username);

	public Login findByEmail(String email);

	public Login findByUserId(Long id);

}
