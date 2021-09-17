package com.gamechat.services;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gamechat.model.User;
import com.gamechat.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	// Usada apenas para alterar usuario
	public User save(User user) {
		return userRepository.save(user);
	}

	// Remover usuario
	public void delete(User user) {
		userRepository.delete(user);
	}

	// Buscar usuário por id
	public User findOne(Long id) {

		Optional<User> user = userRepository.findById(id);

		if (user.isPresent()) {
			return user.get();
		}

		return null;
	}

	// Listar todos os usuários
	public List<User> findAll() {
		return userRepository.findAll();
	}

	// Buscar usuário por nickname
	public User findByNickNameIgnoreCase(String nickName) {
		return userRepository.findByNickNameIgnoreCase(nickName).orElseThrow(() -> new RuntimeException("Usuário por nickname não encontrado"));
	}
}
