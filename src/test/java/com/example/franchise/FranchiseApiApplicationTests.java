package com.example.franchise;

import com.example.franchise.domain.dto.HighestStockProductDTO;
import com.example.franchise.domain.model.Branch;
import com.example.franchise.domain.model.Franchise;
import com.example.franchise.domain.model.Product;
import com.example.franchise.repository.FranchiseRepository;
import com.example.franchise.service.FranchiseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FranchiseServiceTest {

	private FranchiseRepository repository;
	private FranchiseService service;

	@BeforeEach
	void setUp() {
		repository = mock(FranchiseRepository.class);
		service = new FranchiseService(repository);
	}


	@Test
	void shouldCreateFranchise() {
		Franchise franchise = Franchise.builder().name("Test").build();

		when(repository.existsByNameIgnoreCase("Test"))
				.thenReturn(Mono.just(false));
		when(repository.save(franchise))
				.thenReturn(Mono.just(franchise));

		StepVerifier.create(service.addFranchise(franchise))
				.expectNext(franchise)
				.verifyComplete();
	}

	@Test
	void shouldGetAllFranchises() {
		Franchise f1 = Franchise.builder().name("F1").build();
		Franchise f2 = Franchise.builder().name("F2").build();

		when(repository.findAll())
				.thenReturn(Flux.just(f1, f2));

		StepVerifier.create(service.getAllFranchises())
				.expectNext(f1)
				.expectNext(f2)
				.verifyComplete();
	}

	@Test
	void shouldUpdateFranchiseName() {
		Franchise franchise = Franchise.builder().id("123").name("Old").build();

		when(repository.findById("123"))
				.thenReturn(Mono.just(franchise));
		when(repository.existsByNameIgnoreCase("New"))
				.thenReturn(Mono.just(false));
		when(repository.save(any()))
				.thenReturn(Mono.just(franchise));

		StepVerifier.create(service.updateFranchiseName("123", "New"))
				.expectNextMatches(f -> f.getName().equals("New"))
				.verifyComplete();
	}


	@Test
	void shouldAddBranch() {
		Franchise franchise = Franchise.builder()
				.id("1")
				.branches(new ArrayList<>())
				.build();

		Branch branch = Branch.builder()
				.name("Sucursal 1")
				.products(new ArrayList<>())
				.build();

		when(repository.findById("1"))
				.thenReturn(Mono.just(franchise));
		when(repository.save(any()))
				.thenReturn(Mono.just(franchise));

		StepVerifier.create(service.addBranch("1", branch))
				.expectNextMatches(f ->
						f.getBranches().stream()
								.anyMatch(b -> b.getName().equals("Sucursal 1"))
				)
				.verifyComplete();
	}

	@Test
	void shouldUpdateBranchName() {
		Branch branch = Branch.builder().name("Old").build();
		Franchise franchise = Franchise.builder()
				.id("1")
				.branches(new ArrayList<>(List.of(branch)))
				.build();

		when(repository.findById("1"))
				.thenReturn(Mono.just(franchise));
		when(repository.save(any()))
				.thenReturn(Mono.just(franchise));

		StepVerifier.create(service.updateBranchName("1", "Old", "New"))
				.expectNextMatches(f ->
						f.getBranches().get(0).getName().equals("New")
				)
				.verifyComplete();
	}

	@Test
	void shouldAddProductToBranch() {
		Product product = Product.builder().name("Empanada").stock(10).build();
		Branch branch = Branch.builder()
				.name("Sucursal")
				.products(new ArrayList<>())
				.build();

		Franchise franchise = Franchise.builder()
				.id("1")
				.branches(new ArrayList<>(List.of(branch)))
				.build();

		when(repository.findById("1"))
				.thenReturn(Mono.just(franchise));
		when(repository.save(any()))
				.thenReturn(Mono.just(franchise));

		StepVerifier.create(service.addProduct("1", "Sucursal", product))
				.expectNextMatches(f ->
						f.getBranches().get(0).getProducts().contains(product)
				)
				.verifyComplete();
	}

	@Test
	void shouldRemoveProductFromBranch() {
		Product product = Product.builder().name("Empanada").stock(10).build();
		Branch branch = Branch.builder()
				.name("Sucursal")
				.products(new ArrayList<>(List.of(product)))
				.build();

		Franchise franchise = Franchise.builder()
				.id("1")
				.branches(new ArrayList<>(List.of(branch)))
				.build();

		when(repository.findById("1"))
				.thenReturn(Mono.just(franchise));
		when(repository.save(any()))
				.thenReturn(Mono.just(franchise));

		StepVerifier.create(service.removeProduct("1", "Sucursal", "Empanada"))
				.expectNextMatches(f ->
						f.getBranches().get(0).getProducts().isEmpty()
				)
				.verifyComplete();
	}

	@Test
	void shouldUpdateProductStock() {
		Product product = Product.builder().name("Empanada").stock(10).build();
		Branch branch = Branch.builder()
				.name("Sucursal")
				.products(new ArrayList<>(List.of(product)))
				.build();

		Franchise franchise = Franchise.builder()
				.id("1")
				.branches(new ArrayList<>(List.of(branch)))
				.build();

		when(repository.findById("1"))
				.thenReturn(Mono.just(franchise));
		when(repository.save(any()))
				.thenReturn(Mono.just(franchise));

		StepVerifier.create(service.updateProductStock("1", "Sucursal", "Empanada", 50))
				.expectNextMatches(f ->
						f.getBranches().get(0).getProducts().get(0).getStock() == 50
				)
				.verifyComplete();
	}

	@Test
	void shouldUpdateProductName() {
		Product product = Product.builder().name("Empanada").stock(10).build();
		Branch branch = Branch.builder()
				.name("Sucursal")
				.products(new ArrayList<>(List.of(product)))
				.build();

		Franchise franchise = Franchise.builder()
				.id("1")
				.branches(new ArrayList<>(List.of(branch)))
				.build();

		when(repository.findById("1"))
				.thenReturn(Mono.just(franchise));
		when(repository.save(any()))
				.thenReturn(Mono.just(franchise));

		StepVerifier.create(service.updateProductName("1", "Sucursal", "Empanada", "Arepa"))
				.expectNextMatches(f ->
						f.getBranches().get(0).getProducts().get(0).getName().equals("Arepa")
				)
				.verifyComplete();
	}

	@Test
	void shouldGetHighestStockProducts() {
		Product p1 = new Product("Empanada", 10);
		Product p2 = new Product("Chorizo", 30);
		Product p3 = new Product("Arepa", 25);

		Branch b1 = new Branch("Sucursal 1", List.of(p1, p2));
		Branch b2 = new Branch("Sucursal 2", List.of(p3));

		Franchise franchise = Franchise.builder()
				.id("1")
				.branches(List.of(b1, b2))
				.build();

		when(repository.findById("1"))
				.thenReturn(Mono.just(franchise));

		StepVerifier.create(service.getHighestStockProducts("1"))
				.expectNext(new HighestStockProductDTO("Sucursal 1", "Chorizo", 30))
				.expectNext(new HighestStockProductDTO("Sucursal 2", "Arepa", 25))
				.verifyComplete();
	}
}
