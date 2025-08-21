package com.agent404.audiobook.ingestservice.repository;

import java.util.UUID;
import org.springframework.stereotype.Repository;

import com.agent404.audiobook.ingestservice.model.Books;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@Repository
public interface BooksRepository extends ReactiveCrudRepository<Books, UUID>{}
