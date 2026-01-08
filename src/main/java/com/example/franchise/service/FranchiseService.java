package com.example.franchise.service;

import com.example.franchise.domain.model.Branch;
import com.example.franchise.domain.model.Franchise;
import com.example.franchise.domain.model.Product;
import com.example.franchise.domain.dto.HighestStockProductDTO;
import com.example.franchise.exception.BusinessException;
import com.example.franchise.exception.ResourceNotFoundException;
import com.example.franchise.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class FranchiseService {

    private final FranchiseRepository repository;

    public Mono<Franchise> addFranchise(Franchise franchise) {
        return repository.existsByNameIgnoreCase(franchise.getName())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(
                                new BusinessException("La franquicia ya existe")
                        );
                    }
                    return repository.save(franchise);
                });
    }

    public Mono<Franchise> addBranch(String franchiseId, Branch branch) {
        return repository.findById(franchiseId)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("Franquicia no encontrada")
                ))
                .flatMap(franchise -> {

                    boolean exists = franchise.getBranches().stream()
                            .anyMatch(b -> b.getName().equalsIgnoreCase(branch.getName()));

                    if (exists) {
                        return Mono.error(
                                new BusinessException("La sucursal ya existe")
                        );
                    }

                    franchise.getBranches().add(branch);
                    return repository.save(franchise);
                });
    }

    public Mono<Franchise> addProduct(String franchiseId, String branchName, Product product) {
        return repository.findById(franchiseId)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("Franquicia no encontrada")
                ))
                .flatMap(franchise -> {

                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getName().equalsIgnoreCase(branchName))
                            .findFirst()
                            .orElseThrow(() ->
                                    new ResourceNotFoundException("Sucursal no encontrada")
                            );

                    boolean exists = branch.getProducts().stream()
                            .anyMatch(p -> p.getName().equalsIgnoreCase(product.getName()));

                    if (exists) {
                        return Mono.error(
                                new BusinessException("El producto ya existe")
                        );
                    }

                    branch.getProducts().add(product);
                    return repository.save(franchise);
                });
    }


    public Mono<Franchise> removeProduct(
            String franchiseId,
            String branchName,
            String productName
    ) {
        return repository.findById(franchiseId)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("Franquicia no encontrada")
                ))
                .flatMap(franchise -> {

                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getName().equalsIgnoreCase(branchName))
                            .findFirst()
                            .orElseThrow(() ->
                                    new ResourceNotFoundException("Sucursal no encontrada")
                            );

                    boolean removed = branch.getProducts()
                            .removeIf(p -> p.getName().equalsIgnoreCase(productName));

                    if (!removed) {
                        return Mono.error(
                                new ResourceNotFoundException("Producto no encontrado")
                        );
                    }

                    return repository.save(franchise);
                });
    }

    public Mono<Franchise> updateProductStock(
            String franchiseId,
            String branchName,
            String productName,
            int newStock
    ) {
        return repository.findById(franchiseId)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("Franquicia no encontrada")
                ))
                .flatMap(franchise -> {

                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getName().equalsIgnoreCase(branchName))
                            .findFirst()
                            .orElseThrow(() ->
                                    new ResourceNotFoundException("Sucursal no encontrada")
                            );

                    Product product = branch.getProducts().stream()
                            .filter(p -> p.getName().equalsIgnoreCase(productName))
                            .findFirst()
                            .orElseThrow(() ->
                                    new ResourceNotFoundException("Producto no encontrado")
                            );

                    product.setStock(newStock);
                    return repository.save(franchise);
                });
    }

    public Flux<HighestStockProductDTO> getHighestStockProducts(String franchiseId) {
        return repository.findById(franchiseId)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("Franquicia no encontrada")
                ))
                .flatMapMany(franchise -> Flux.fromIterable(franchise.getBranches()))
                .flatMap(branch ->
                        Flux.fromStream(
                                branch.getProducts().stream()
                                        .max(Comparator.comparingInt(Product::getStock))
                                        .stream()
                                        .map(product ->
                                                new HighestStockProductDTO(
                                                        branch.getName(),
                                                        product.getName(),
                                                        product.getStock()
                                                )
                                        )
                        )
                );
    }

    public Mono<Franchise> updateFranchiseName(String id, String newName) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("Franquicia no encontrada")
                ))
                .flatMap(franchise -> {
                    franchise.setName(newName);
                    return repository.save(franchise);
                });
    }

    public Mono<Franchise> updateBranchName(String franchiseId, String oldName, String newName) {
        return repository.findById(franchiseId)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("Franquicia no encontrada")
                ))
                .flatMap(franchise -> {

                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getName().equalsIgnoreCase(oldName))
                            .findFirst()
                            .orElseThrow(() ->
                                    new ResourceNotFoundException("Sucursal no encontrada")
                            );

                    branch.setName(newName);
                    return repository.save(franchise);
                });
    }

    public Mono<Franchise> updateProductName(
            String franchiseId,
            String branchName,
            String oldName,
            String newName
    ) {
        return repository.findById(franchiseId)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("Franquicia no encontrada")
                ))
                .flatMap(franchise -> {

                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getName().equalsIgnoreCase(branchName))
                            .findFirst()
                            .orElseThrow(() ->
                                    new ResourceNotFoundException("Sucursal no encontrada")
                            );

                    Product product = branch.getProducts().stream()
                            .filter(p -> p.getName().equalsIgnoreCase(oldName))
                            .findFirst()
                            .orElseThrow(() ->
                                    new ResourceNotFoundException("Producto no encontrado")
                            );

                    product.setName(newName);
                    return repository.save(franchise);
                });
    }

    public Flux<Franchise> getAllFranchises() {
        return repository.findAll();
    }
}