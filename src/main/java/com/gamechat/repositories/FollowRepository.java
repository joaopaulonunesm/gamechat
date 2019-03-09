package com.gamechat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamechat.model.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

	Follow findByUserIdAndFollowingId(Long idUser, Long idFollowing);

	List<Follow> findAllByUserIdOrderByMomentDesc(Long id);

	List<Follow> findAllByFollowingId(Long id);

}
