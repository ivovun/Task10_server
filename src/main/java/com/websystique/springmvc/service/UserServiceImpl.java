package com.websystique.springmvc.service;

import java.util.List;
import java.util.Optional;

import com.websystique.springmvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.model.User;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	private PasswordEncoder passwordEncoder;

	private UserRepository userRepository;

	@Autowired
	@Lazy
	public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

	public User findById(long id) {
		return userRepository.findById(id);
	}

	public User findBySsoId(String ssoId) {
		return userRepository.findBySsoId(ssoId);
	}

	public void save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public void deleteUserBySsoId(String sso) {
		userRepository.deleteBySsoId(sso);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public boolean isUserSSOUnique(Long id, String sso) {
		User user = findBySsoId(sso);
		return ( user == null || ((id != null) && (user.getId().equals(id))));
	}

	@Override
	public UserDetails loadUserByUsername(String ssoId) throws UsernameNotFoundException {
		return userRepository.findBySsoId(ssoId);
	}
}
