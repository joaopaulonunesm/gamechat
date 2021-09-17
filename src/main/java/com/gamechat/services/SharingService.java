package com.gamechat.services;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamechat.model.Publication;
import com.gamechat.model.Sharing;
import com.gamechat.model.User;
import com.gamechat.repositories.SharingRepository;

@Service
@RequiredArgsConstructor
public class SharingService {

	private final SharingRepository sharingRepository;

	// Compartilhar
	public Sharing save(Sharing sharing) {
		return sharingRepository.save(sharing);
	}

	// Deixar de compartilhar
	public void delete(Sharing sharing) {
		sharingRepository.delete(sharing);
	}

	// Remover todas compartilhações de um user
	public void deleteAllByUser(Long id) {

		List<Sharing> sharings = findSharingsByUser(id);

		for (Sharing sharing : sharings) {
			sharingRepository.delete(sharing);
		}
	}

	// Compartilhações de uma publicação
	public List<Sharing> findAllByPublicationIdOrderByMomentDesc(Long idPublication) {
		return sharingRepository.findAllByPublicationIdOrderByMomentDesc(idPublication);
	}

	// Todas as Sharing de um user
	public List<Sharing> findSharingsByUser(Long id) {
		return sharingRepository.findAllByUserId(id);
	}

	// Usuarios que compartilhou uma publicação
	public List<User> findUsersSharingByPublication(Long idPublication) {

		List<Sharing> sharings = findAllByPublicationIdOrderByMomentDesc(idPublication);

		List<User> users = new ArrayList<>();

		for (Sharing sharing : sharings) {
			users.add(sharing.getUser());
		}

		return users;
	}

	public Sharing findByUserIdAndPublicationId(Long id, Long idPublication) {
		return sharingRepository.findByUserIdAndPublicationId(id, idPublication).orElseThrow(() -> new RuntimeException("Erro ao buscar Compartilhamento de um usuário"));
	}

	// Publicações que um usuário compartilhou
	public List<Publication> findPublicationsSharedByUser(Long idUser) {

		List<Sharing> sharings = sharingRepository.findAllByUserId(idUser);

		List<Publication> publications = new ArrayList<>();

		for (Sharing sharing : sharings) {
			publications.add(sharing.getPublication());
		}

		return publications;
	}

}
