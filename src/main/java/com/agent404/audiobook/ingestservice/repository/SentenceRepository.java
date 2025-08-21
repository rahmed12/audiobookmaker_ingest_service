package com.agent404.audiobook.ingestservice.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.agent404.audiobook.ingestservice.model.Sentences;

@Repository
public interface SentenceRepository extends ReactiveCrudRepository<Sentences, UUID>{}
