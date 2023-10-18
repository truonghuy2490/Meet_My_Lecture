package com.springboot.meetMyLecturer.security;

import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class GoogleJwtConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    final
    UserRepository userRepository;

    public GoogleJwtConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        String email = jwt.getClaim("email");
        User user = userRepository.findUserByEmail(email);

        String roleName = user.getRole().getRoleName();

        GrantedAuthority roleAuthority = new SimpleGrantedAuthority(roleName);


        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(roleAuthority);

        return authorities;
    }
}
