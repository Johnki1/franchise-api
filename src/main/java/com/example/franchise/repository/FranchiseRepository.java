package com.example.franchise.repository;

import com.example.franchise.domain.model.Franchise;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface FranchiseRepository extends ReactiveMongoRepository<Franchise, String> {
    Mono<Boolean> existsByNameIgnoreCase(String name);
}