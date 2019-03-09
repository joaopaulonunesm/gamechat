package com.gamechat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamechat.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByNickNameIgnoreCase(String nickName);

}
