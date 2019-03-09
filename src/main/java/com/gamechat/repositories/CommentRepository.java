package com.gamechat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamechat.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	Comment findByUserIdAndPublicationId(Long idUser, Long idPublication);

	List<Comment> findAllByPublicationIdOrderByMomentDesc(Long idPublication);

	List<Comment> findAllByUserId(Long id);

}
