package com.testing.two.application.repositories.jpa;

import com.testing.two.application.model.AnotherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaAnotherRepository extends JpaRepository<AnotherEntity, Integer> {
}
