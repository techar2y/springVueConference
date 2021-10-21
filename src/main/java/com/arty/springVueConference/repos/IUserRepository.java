package com.arty.springVueConference.repos;

import com.arty.springVueConference.domains.ERole;
import com.arty.springVueConference.domains.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>
{
    Page<User> findUserByUsernameContaining(String username, Pageable paging);

    User getUserById(Long id);

    List<User> findUsersByUsernameAndIdNot(String username, Long id);

    List<User> findUsersByEmailAndIdNot(String email, Long id);

    /*@Query(value = "SELECT * FROM users u JOIN roles r ON u.role_id = r.id WHERE r.status = :status",
            nativeQuery = true)
    List<User> findUsersByStatusRoleNamedParamsNative(@Param("status") String status);*/

    List<User> findUsersByRolesName(ERole name);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
