package com.gamechat.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamechat.model.Publication;
import com.gamechat.model.User;
import com.gamechat.repositories.PublicationRepository;

@Service
@RequiredArgsConstructor
public class PublicationService {

	private final PublicationRepository publicationRepository;
	private final FollowService friendshipService;

	// Criar publicação
	public Publication save(Publication publication) {
		return publicationRepository.save(publication);
	}

	// Remover publicação
	public void delete(Publication publication) {
		publicationRepository.delete(publication);
	}

	// Remover todas publicações de um user
	public void deleteAllByUser(Long id) {

		List<Publication> publications = findAllByUserIdOrderByCreateDateDesc(id);

		for (Publication publication : publications) {
			publicationRepository.delete(publication);
		}

	}

	// Buscar publicação por Id
	public Publication findOne(Long id) {

		Optional<Publication> publication = publicationRepository.findById(id);

		if (publication.isPresent()) {
			return publication.get();
		}

		return null;
	}

	// Buscar publicações de um usuario
	public List<Publication> findAllByUserIdOrderByCreateDateDesc(Long idUser) {
		return publicationRepository.findAllByUserIdOrderByCreateDateDesc(idUser);
	}

	// Buscar publicações de quem eu sigo
	public List<Publication> findPublicationsByFollowings(Long id) {

		List<User> follows = friendshipService.findAllFollowings(id);

		List<Publication> followsPublications = new ArrayList<>();

		for (User follow : follows) {

			List<Publication> publicationByOneFriend = findAllByUserIdOrderByCreateDateDesc(follow.getId());
			followsPublications.addAll(publicationByOneFriend);

		}

		return followsPublications;
	}

	// Ordenar publicações por data. Do mais recente ao mais antigo
	public void orderByCreateDate(List<Publication> myPublications) {

		Comparator<Publication> comparator = (publication1, publication2) -> {

			if (publication1.getCreateDate().isBefore(publication2.getCreateDate())) {
				return 1;
			}

			if (publication1.getCreateDate().isAfter(publication2.getCreateDate())) {
				return -1;
			}

			return 0;
		};

		myPublications.sort(comparator);
	}

}
