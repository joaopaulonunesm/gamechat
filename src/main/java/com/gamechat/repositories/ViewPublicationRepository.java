package com.gamechat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamechat.model.ViewPublication;

public interface ViewPublicationRepository extends JpaRepository<ViewPublication, Long> {

	ViewPublication findByUserIdAndPublicationId(Long id, Long idPublication);

	List<ViewPublication> findAllByPublicationIdOrderByMomentDesc(Long idPublication);

	List<ViewPublication> findAllByUserId(Long id);

}
