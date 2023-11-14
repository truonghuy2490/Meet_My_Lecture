package com.springboot.meetMyLecturer.security;

import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.Role;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.repository.RoleRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class GoogleJwtConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    final UserRepository userRepository;
    final RoleRepository roleRepository;


    public GoogleJwtConverter(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        String email = jwt.getClaim("email");
        String userName = jwt.getClaim("name");
        String givenName = jwt.getClaim("given_name");
        User userExisted = userRepository.findUserByEmail(email);

        if (userExisted == null) {
            String unique = generateUnique(givenName);
            User user = new User();
            user.setEmail(email);
            user.setUserName(userName);
            user.setUnique(unique);
            user.setStatus(Constant.OPEN);
            user.setRole(assignRole(email));
            Constant.EMAIL = jwt.getClaim("email");

            userRepository.save(user);


            return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+user.getRole().getRoleName()));
        }



        if (userExisted.getAbsentCount() >= 3) {
            userExisted.setStatus(Constant.BANNED);
            Constant.EMAIL = jwt.getClaim("email");
            userRepository.save(userExisted);
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+userExisted.getStatus()));
        }

        Constant.EMAIL = jwt.getClaim("email");

        return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+userExisted.getRole().getRoleName()));
    }

    private Role assignRole(String email) {
        if (email.endsWith("@fpt.edu.vn")) {
            return roleRepository.findRoleByRoleName(Constant.STUDENT);
        } else if (email.endsWith("@fe.edu.vn")) {
            return roleRepository.findRoleByRoleName(Constant.LECTURER);
        }
        return null;
    }

    private String generateUnique(String givenName) {
        String[] parts = givenName.split(" ");
        String lastName = parts[parts.length - 1];
        StringBuilder result = new StringBuilder(lastName.toLowerCase());
        for (int i = 0; i < parts.length - 1; i++) {
            result.append(parts[i].substring(0, 1).toUpperCase());
        }
        List<Long> userList = userRepository.findUserByUniqueContains(result.toString());
        if(!userList.isEmpty()){
            int i = userList.size();
            if(i != 0){
                result.append(userList.size());
            }
        }

        return result.toString();
    }
}
