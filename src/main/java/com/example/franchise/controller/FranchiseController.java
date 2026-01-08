package com.example.franchise.controller;

import com.example.franchise.domain.model.Branch;
import com.example.franchise.domain.model.Franchise;
import com.example.franchise.domain.model.Product;
import com.example.franchise.domain.dto.HighestStockProductDTO;
import com.example.franchise.service.FranchiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franchise")
@RequiredArgsConstructor
public class FranchiseController {

    private final FranchiseService service;

    @PostMapping
    public Mono<Franchise> createFranchise(@RequestBody Franchise franchise) {
        return service.addFranchise(franchise);
    }

    @PostMapping("/{id}/branch")
    public Mono<Franchise> addBranch(@PathVariable String id, @RequestBody Branch branch) {
        return service.addBranch(id, branch);
    }

    @PostMapping("/{id}/branch/{branchName}/product")
    public Mono<Franchise> addProduct(@PathVariable String id,
                                      @PathVariable String branchName,
                                      @RequestBody Product product) {
        return service.addProduct(id, branchName, product);
    }

    @DeleteMapping("/{id}/branch/{branchName}/product/{productName}")
    public Mono<Franchise> removeProduct(@PathVariable String id,
                                         @PathVariable String branchName,
                                         @PathVariable String productName) {
        return service.removeProduct(id, branchName, productName);
    }

    @PutMapping("/{id}/branch/{branchName}/product/{productName}/stock/{newStock}")
    public Mono<Franchise> updateStock(@PathVariable String id,
                                       @PathVariable String branchName,
                                       @PathVariable String productName,
                                       @PathVariable int newStock) {
        return service.updateProductStock(id, branchName, productName, newStock);
    }

    @GetMapping("/{id}/highest-stock-products")
    public Flux<HighestStockProductDTO> getHighestStockProducts(@PathVariable String id) {
        return service.getHighestStockProducts(id);
    }

    @PutMapping("/{id}/name")
    public Mono<Franchise> updateFranchiseName(@PathVariable String id, @RequestBody String newName) {
        return service.updateFranchiseName(id, newName);
    }

    @PutMapping("/{id}/branch/name")
    public Mono<Franchise> updateBranchName(@PathVariable String id,
                                            @RequestParam String oldName,
                                            @RequestParam String newName) {
        return service.updateBranchName(id, oldName, newName);
    }

    @PutMapping("/{id}/branch/{branchName}/product/name")
    public Mono<Franchise> updateProductName(@PathVariable String id,
                                             @PathVariable String branchName,
                                             @RequestParam String oldName,
                                             @RequestParam String newName) {
        return service.updateProductName(id, branchName, oldName, newName);
    }

    @GetMapping
    public Flux<Franchise> getAllFranchises() {
        return service.getAllFranchises();
    }

}
