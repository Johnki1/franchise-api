package com.example.franchise.domain.model;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {

    @Indexed(unique = true)
    private String name;
    private List<Product> products = new ArrayList<>();
}