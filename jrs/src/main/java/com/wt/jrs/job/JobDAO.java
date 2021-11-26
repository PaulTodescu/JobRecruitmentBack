package com.wt.jrs.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobDAO extends JpaRepository<JobEntity, Long> {

    Optional<JobEntity> findJobById(Long id);

    Optional<JobEntity> findJobByTitle(String title);

    @Modifying
    @Query("DELETE FROM JobEntity jb WHERE jb.id = :jobId")
    void deleteJobEntityById(@Param("jobId") Long jobId);

}
