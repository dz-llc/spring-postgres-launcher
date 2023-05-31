package com.dz.postgrescrud.repository;

import com.dz.postgrescrud.domain.Journal;
import org.springframework.data.repository.CrudRepository;

public interface JournalRepository extends CrudRepository<Journal, Integer> {
}
