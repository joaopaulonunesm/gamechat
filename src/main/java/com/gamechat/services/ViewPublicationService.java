package com.gamechat.services;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamechat.model.Publication;
import com.gamechat.model.User;
import com.gamechat.model.ViewPublication;
import com.gamechat.repositories.ViewPublicationRepository;

@Service
@RequiredArgsConstructor
public class ViewPublicationService {

	private final ViewPublicationRepository viewPublicationRepository;

	// Salvar visualização
	public ViewPublication save(ViewPublication view) {
		return viewPublicationRepository.save(view);
	}

	// Deleta uma visualização
	public void delete(ViewPublication viewPublication) {
		viewPublicationRepository.delete(viewPublication);
	}

	// Remover todas as visualiações de um user
	public void deleteAllByUser(Long id) {
		List<ViewPublication> viewPublications = findAllByUserId(id);

		for (ViewPublication viewPublication : viewPublications) {
			viewPublicationRepository.delete(viewPublication);
		}
	}

	// Visualizações de uma publicação
	public List<ViewPublication> findAllByPublicationIdOrderByMomentDesc(Long idPublication) {
		return viewPublicationRepository.findAllByPublicationIdOrderByMomentDesc(idPublication);
	}

	// View Publication especifico de um usuario e de uma publicação
	public ViewPublication findByUserIdAndPublicationId(Long id, Long idPublication) {
		return viewPublicationRepository.findByUserIdAndPublicationId(id, idPublication).orElseThrow(() -> new RuntimeException("Erro ao buscar views da publicação de um usuário"));
	}

	// Todas as views de um usuario
	public List<ViewPublication> findAllByUserId(Long id) {
		return viewPublicationRepository.findAllByUserId(id);
	}

	// Usuários que visualizaram uma publicação
	public List<User> findUsersViewPublicationByPublication(Long idPublication) {

		List<ViewPublication> views = findAllByPublicationIdOrderByMomentDesc(idPublication);

		List<User> users = new ArrayList<>();

		for (ViewPublication view : views) {
			users.add(view.getUser());
		}

		return users;
	}

	// Publicações que um usuário visualizou
	public List<Publication> findPublicationsViewedByUser(Long idUser) {

		List<ViewPublication> views = viewPublicationRepository.findAllByUserId(idUser);

		List<Publication> publications = new ArrayList<>();

		for (ViewPublication view : views) {
			publications.add(view.getPublication());
		}

		return publications;
	}

}
