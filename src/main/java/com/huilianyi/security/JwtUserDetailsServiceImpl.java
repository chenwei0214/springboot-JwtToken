package com.huilianyi.security;

import com.huilianyi.domain.User;
import com.huilianyi.mapper.UserMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Primary
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private UserMapper userMapper;

    public JwtUserDetailsServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        user.setRoles(Collections.singletonList("admin"));
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        } else {
            return new JwtUser(user.getUsername(), user.getPassword(), user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        }
    }


}
