package com.example.franchise.service;

import com.example.franchise.model.*;
import com.example.franchise.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FranchiseService {

    private final FranchiseRepository repository;

    public Mono<Franchise> addFranchise(Franchise franchise) {
        return repository.save(franchise);
    }

    public Mono<Franchise> addBranch(String franchiseId, Branch branch) {
        return repository.findById(franchiseId)
                .flatMap(franchise -> {
                    franchise.getBranches().add(branch);
                    return repository.save(franchise);
                });
    }

    public Mono<Franchise> addProduct(String franchiseId, String branchName, Product product) {
        return repository.findById(franchiseId)
                .flatMap(franchise -> {
                    franchise.getBranches().forEach(branch -> {
                        if (branch.getName().equalsIgnoreCase(branchName)) {
                            branch.getProducts().add(product);
                        }
                    });
                    return repository.save(franchise);
                });
    }

    public Mono<Franchise> removeProduct(String franchiseId, String branchName, String productName) {
        return repository.findById(franchiseId)
                .flatMap(franchise -> {
                    franchise.getBranches().forEach(branch -> {
                        if (branch.getName().equalsIgnoreCase(branchName)) {
                            branch.getProducts().removeIf(p -> p.getName().equalsIgnoreCase(productName));
                        }
                    });
                    return repository.save(franchise);
                });
    }

    public Mono<Franchise> updateProductStock(String franchiseId, String branchName, String productName, int newStock) {
        return repository.findById(franchiseId)
                .flatMap(franchise -> {
                    franchise.getBranches().forEach(branch -> {
                        if (branch.getName().equalsIgnoreCase(branchName)) {
                            branch.getProducts().forEach(product -> {
                                if (product.getName().equalsIgnoreCase(productName)) {
                                    product.setStock(newStock);
                                }
                            });
                        }
                    });
                    return repository.save(franchise);
                });
    }

    public Flux<Product> getHighestStockProducts(String franchiseId) {
        return repository.findById(franchiseId)
                .flatMapMany(franchise -> Flux.fromIterable(franchise.getBranches()))
                .flatMap(branch -> {
                    Optional<Product> maxProduct = branch.getProducts().stream()
                            .max(Comparator.comparingInt(Product::getStock));
                    return maxProduct.map(Flux::just).orElseGet(Flux::empty);
                });
    }

    public Mono<Franchise> updateFranchiseName(String id, String newName) {
        return repository.findById(id)
                .flatMap(franchise -> {
                    franchise.setName(newName);
                    return repository.save(franchise);
                });
    }

    public Mono<Franchise> updateBranchName(String franchiseId, String oldName, String newName) {
        return repository.findById(franchiseId)
                .flatMap(franchise -> {
                    franchise.getBranches().forEach(branch -> {
                        if (branch.getName().equalsIgnoreCase(oldName)) {
                            branch.setName(newName);
                        }
                    });
                    return repository.save(franchise);
                });
    }

    public Mono<Franchise> updateProductName(String franchiseId, String branchName, String oldProductName, String newProductName) {
        return repository.findById(franchiseId)
                .flatMap(franchise -> {
                    franchise.getBranches().forEach(branch -> {
                        if (branch.getName().equalsIgnoreCase(branchName)) {
                            branch.getProducts().forEach(product -> {
                                if (product.getName().equalsIgnoreCase(oldProductName)) {
                                    product.setName(newProductName);
                                }
                            });
                        }
                    });
                    return repository.save(franchise);
                });
    }

    public Flux<Franchise> getAllFranchises() {
        return repository.findAll();
    }
}