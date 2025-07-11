package com.example.franchise.repository;

import com.example.franchise.model.Franchise;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FranchiseRepository extends ReactiveMongoRepository<Franchise, String> {
}