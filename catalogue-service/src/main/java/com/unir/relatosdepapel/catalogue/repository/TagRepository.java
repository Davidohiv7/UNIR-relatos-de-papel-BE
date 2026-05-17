package com.unir.relatosdepapel.catalogue.repository;

import com.unir.relatosdepapel.catalogue.repository.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
}

