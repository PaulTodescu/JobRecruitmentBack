package com.wt.jrs.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityById(Long id);

    Optional<UserEntity> findUserEntityByEmail(String email);

}
