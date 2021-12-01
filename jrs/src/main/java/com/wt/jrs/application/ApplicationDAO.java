package com.wt.jrs.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationDAO extends JpaRepository<ApplicationEntity, Long> {

    void deleteApplicationEntityById(Long jobId);

    Optional<ApplicationEntity> findApplicationEntityById(Long jobId);
}
