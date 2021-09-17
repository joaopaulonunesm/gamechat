package com.gamechat.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamechat.model.ViewPublication;

public interface ViewPublicationRepository extends JpaRepository<ViewPublication, Long> {

    Optional<ViewPublication> findByUserIdAndPublicationId(Long id, Long idPublication);

    List<ViewPublication> findAllByPublicationIdOrderByMomentDesc(Long idPublication);

    List<ViewPublication> findAllByUserId(Long id);

}
