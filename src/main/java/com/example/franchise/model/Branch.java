package com.example.franchise.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {
    private String name;
    private List<Product> products = new ArrayList<>();
}