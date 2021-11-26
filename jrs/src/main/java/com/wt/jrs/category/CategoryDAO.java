package com.wt.jrs.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryDAO extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findCategoryEntityById(Long id);
    Optional<CategoryEntity> findCategoryEntityByName(String name);

}
