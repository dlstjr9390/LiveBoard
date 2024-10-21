package com.example.liveboard.global.userdetail;

import com.example.liveboard.domain.user.entity.User;
import com.example.liveboard.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new BisException(ErrorCode.NOT_FOUND_USER)
    );

    return new UserDetailsImpl(user);
  }
}