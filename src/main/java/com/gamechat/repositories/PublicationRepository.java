package com.gamechat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamechat.model.Publication;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    List<Publication> findAllByUserIdOrderByCreateDateDesc(Long idUser);
}
