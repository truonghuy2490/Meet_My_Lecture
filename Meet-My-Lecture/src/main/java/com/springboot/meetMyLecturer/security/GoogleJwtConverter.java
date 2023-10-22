package com.springboot.meetMyLecturer.security;

import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.repository.RoleRepository;
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

    public GoogleJwtConverter(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    final
    RoleRepository roleRepository;


    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        String email = jwt.getClaim("email");
        User userExisted = userRepository.findUserByEmail(email);
        String regexStudent = ".*@fpt\\.edu\\.vn$";
        String regexLecturer = ".*@fe\\.edu\\.vn$";

        if(userExisted == null){
            User user = new User();
            user.setEmail(email);

            if(email.matches(regexStudent)){
                user.setRole(roleRepository.findRoleByRoleName("Student"));
            }else if(email.matches(regexLecturer)){
                user.setRole(roleRepository.findRoleByRoleName("Lecturer"));
            }

            String roleName = user.getRole().getRoleName();
            GrantedAuthority roleAuthority = new SimpleGrantedAuthority(roleName);
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            userRepository.save(user);
            authorities.add(roleAuthority);
            return authorities;
        }

        String roleName = userExisted.getRole().getRoleName();

        GrantedAuthority roleAuthority = new SimpleGrantedAuthority(roleName);

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(roleAuthority);

        return authorities;
    }
}
