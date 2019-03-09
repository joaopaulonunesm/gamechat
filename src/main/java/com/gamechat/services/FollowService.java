package com.gamechat.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamechat.model.Follow;
import com.gamechat.model.User;
import com.gamechat.repositories.FollowRepository;

@Service
public class FollowService {

	@Autowired
	private FollowRepository followRepository;

	@Autowired
	private UserService userService;

	// Seguir
	public Follow save(Follow follow) {
		return followRepository.save(follow);
	}

	// Deixar de seguir
	public void delete(Follow follow) {
		followRepository.delete(follow);
	}

	// Deixar de seguir todos quem um usuario segue
	public void deleteAllByUser(Long id) {

		List<Follow> follows = findAllByUserIdOrderByMomentDesc(id);

		for (Follow follow : follows) {
			followRepository.delete(follow);
		}
	}

	// Buscar um follow
	public Follow findOne(Long id) {

		Optional<Follow> follow = followRepository.findById(id);

		if (follow.isPresent()) {
			return follow.get();
		}

		return null;
	}

	// Buscar todos os followings do user
	public List<Follow> findAllByUserIdOrderByMomentDesc(Long id) {
		return followRepository.findAllByUserIdOrderByMomentDesc(id);
	}

	// Buscar relacionamento de um determinado usuario e alguem que ele segue
	public Follow findByUserIdAndFollowingId(Long idUser, Long idFollowing) {
		return followRepository.findByUserIdAndFollowingId(idUser, idFollowing);
	}

	// Buscar todos os usuarios que sigo
	public List<User> findAllFollowings(Long id) {

		List<Follow> byUser = followRepository.findAllByUserIdOrderByMomentDesc(id);

		List<User> follows = new ArrayList<>();

		for (Follow follow : byUser) {
			follows.add(userService.findOne(follow.getFollowing().getId()));
		}

		return follows;
	}

	// Buscar todos os usuarios que me segue
	public List<User> findAllFollowers(Long id) {

		List<Follow> byUser = followRepository.findAllByFollowingId(id);

		List<User> followers = new ArrayList<>();

		for (Follow follow : byUser) {
			followers.add(userService.findOne(follow.getUser().getId()));
		}

		return followers;
	}

}
