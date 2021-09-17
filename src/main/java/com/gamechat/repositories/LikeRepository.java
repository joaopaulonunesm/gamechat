package com.gamechat.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamechat.model.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

	Optional<Like> findByUserIdAndPublicationId(Long idUser, Long idPublication);

	List<Like> findAllByPublicationIdOrderByMomentDesc(Long idPublication);

	List<Like> findAllByUserId(Long id);

}
