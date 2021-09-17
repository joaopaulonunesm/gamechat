package com.gamechat.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamechat.model.Sharing;

@Repository
public interface SharingRepository extends JpaRepository<Sharing, Long> {

    Optional<Sharing> findByUserIdAndPublicationId(Long id, Long idPublication);

    List<Sharing> findAllByPublicationIdOrderByMomentDesc(Long idPublication);

    List<Sharing> findAllByUserId(Long id);

}
