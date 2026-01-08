package com.example.franchise.domain.dto;

public record HighestStockProductDTO(
        String branchName,
        String productName,
        int stock
) {
}

