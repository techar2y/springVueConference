package com.arty.springVueConference.repos;

import com.arty.springVueConference.domains.ERole;
import com.arty.springVueConference.domains.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long>
{
    Optional<Role> findByName(ERole name);
}
