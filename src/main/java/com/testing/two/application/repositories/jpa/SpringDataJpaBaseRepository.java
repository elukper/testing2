package com.testing.two.application.repositories.jpa;

import com.testing.two.application.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaBaseRepository extends JpaRepository<BaseEntity,Integer> {
}
