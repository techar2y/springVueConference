package com.arty.springVueConference.repos;

import com.arty.springVueConference.domains.RefreshToken;
import com.arty.springVueConference.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long>
{

    @Override
    Optional<RefreshToken> findById(Long id);

    Optional<RefreshToken> findByToken(String token);

    Integer deleteByUser(User user);

}
