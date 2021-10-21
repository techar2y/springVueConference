package com.arty.springVueConference.security.services;

import com.arty.springVueConference.domains.User;
import com.arty.springVueConference.repos.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/*
* UserDetailsServiceImpl реализует интерфейс UserDetailsService
* у которого есть метод загрузки пользователя по username и возвращает
* объект UserDetails Спрингу Секурити для аутентификации и валидации.
*
* UserDetails содержит необходимую информацию (username, password, authorities)
* чтобы создать Authentication объект
* */


@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    IUserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }
}
