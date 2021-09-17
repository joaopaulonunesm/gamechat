package com.gamechat.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamechat.model.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

	Optional<Follow> findByUserIdAndFollowingId(Long idUser, Long idFollowing);

	List<Follow> findAllByUserIdOrderByMomentDesc(Long id);

	List<Follow> findAllByFollowingId(Long id);

}
