package com.springboot.meetMyLecturer.security;

import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.repository.RoleRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        String userName = jwt.getClaim("name");
        String give_name = jwt.getClaim("given_name");
        User userExisted = userRepository.findUserByEmail(email);
        String regexStudent = ".*@fpt\\.edu\\.vn$";
        String regexLecturer = ".*@fe\\.edu\\.vn$";

        if(userExisted == null){
            String[] parts = give_name.split(" ");
            String lastName = parts[parts.length - 1];
            StringBuilder result = new StringBuilder();
            result.append(lastName.toLowerCase());
            for (int i = 0; i < parts.length - 1; i++) {
                result.append(parts[i].substring(0, 1).toUpperCase());
            }

            List<User> userList = userRepository.findUserByUniqueContains(result.toString());
            if(userList != null){
                int i = userList.size();
                if(i != 0){
                    result.append(i);
                }
            }

            User user = new User();
            user.setEmail(email);
            user.setUserName(userName);
            user.setUnique(result.toString());
            user.setStatus(Constant.OPEN);


            if(email.matches(regexStudent)){
                user.setRole(roleRepository.findRoleByRoleName("STUDENT"));
            }else if(email.matches(regexLecturer)){
                user.setRole(roleRepository.findRoleByRoleName("LECTURER"));
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
