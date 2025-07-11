package com.example.franchise;

import com.example.franchise.model.*;
import com.example.franchise.repository.FranchiseRepository;
import com.example.franchise.service.FranchiseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

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
		when(repository.save(franchise)).thenReturn(Mono.just(franchise));

		StepVerifier.create(service.addFranchise(franchise))
				.expectNext(franchise)
				.verifyComplete();
	}

	@Test
	void shouldGetAllFranchises() {
		Franchise f1 = Franchise.builder().name("F1").build();
		Franchise f2 = Franchise.builder().name("F2").build();

		when(repository.findAll()).thenReturn(Flux.just(f1, f2));

		StepVerifier.create(service.getAllFranchises())
				.expectNext(f1)
				.expectNext(f2)
				.verifyComplete();
	}

	@Test
	void shouldAddBranch() {
		String franchiseId = "abc123";
		Franchise franchise = Franchise.builder()
				.id(franchiseId)
				.name("F")
				.branches(new ArrayList<>())
				.build();

		Branch newBranch = Branch.builder().name("Sucursal 1").products(new ArrayList<>()).build();

		when(repository.findById(franchiseId)).thenReturn(Mono.just(franchise));
		when(repository.save(any())).thenReturn(Mono.just(franchise));

		StepVerifier.create(service.addBranch(franchiseId, newBranch))
				.expectNextMatches(f -> f.getBranches().contains(newBranch))
				.verifyComplete();
	}

	@Test
	void shouldAddProductToBranch() {
		String franchiseId = "abc123";
		Product product = Product.builder().name("Empanada").stock(10).build();

		Branch branch = Branch.builder().name("Sucursal 1").products(new ArrayList<>()).build();
		List<Branch> branches = new ArrayList<>();
		branches.add(branch);

		Franchise franchise = Franchise.builder().id(franchiseId).name("F").branches(branches).build();

		when(repository.findById(franchiseId)).thenReturn(Mono.just(franchise));
		when(repository.save(any())).thenReturn(Mono.just(franchise));

		StepVerifier.create(service.addProduct(franchiseId, "Sucursal 1", product))
				.expectNextMatches(f -> f.getBranches().get(0).getProducts().contains(product))
				.verifyComplete();
	}

	@Test
	void shouldRemoveProductFromBranch() {
		String franchiseId = "abc123";
		Product product = Product.builder().name("Empanada").stock(10).build();
		Branch branch = Branch.builder().name("Sucursal 1").products(new ArrayList<>(List.of(product))).build();
		Franchise franchise = Franchise.builder().id(franchiseId).branches(new ArrayList<>(List.of(branch))).build();

		when(repository.findById(franchiseId)).thenReturn(Mono.just(franchise));
		when(repository.save(any())).thenReturn(Mono.just(franchise));

		StepVerifier.create(service.removeProduct(franchiseId, "Sucursal 1", "Empanada"))
				.expectNextMatches(f -> f.getBranches().get(0).getProducts().isEmpty())
				.verifyComplete();
	}

	@Test
	void shouldUpdateProductStock() {
		String franchiseId = "abc123";
		Product product = Product.builder().name("Empanada").stock(10).build();
		Branch branch = Branch.builder().name("Sucursal 1").products(new ArrayList<>(List.of(product))).build();
		Franchise franchise = Franchise.builder().id(franchiseId).branches(List.of(branch)).build();

		when(repository.findById(franchiseId)).thenReturn(Mono.just(franchise));
		when(repository.save(any())).thenReturn(Mono.just(franchise));

		StepVerifier.create(service.updateProductStock(franchiseId, "Sucursal 1", "Empanada", 50))
				.expectNextMatches(f -> f.getBranches().get(0).getProducts().get(0).getStock() == 50)
				.verifyComplete();
	}

	@Test
	void shouldGetHighestStockProducts() {
		String franchiseId = "abc123";
		Product p1 = new Product("Empanada", 10);
		Product p2 = new Product("Chorizo", 30);
		Product p3 = new Product("Arepa", 25);

		Branch b1 = new Branch("Sucursal 1", List.of(p1, p2));
		Branch b2 = new Branch("Sucursal 2", List.of(p3));

		Franchise franchise = Franchise.builder().id(franchiseId).branches(List.of(b1, b2)).build();

		when(repository.findById(franchiseId)).thenReturn(Mono.just(franchise));

		StepVerifier.create(service.getHighestStockProducts(franchiseId))
				.expectNext(p2)
				.expectNext(p3)
				.verifyComplete();
	}

	@Test
	void shouldUpdateFranchiseName() {
		Franchise franchise = Franchise.builder().id("123").name("Old Name").build();

		when(repository.findById("123")).thenReturn(Mono.just(franchise));
		when(repository.save(any())).thenReturn(Mono.just(franchise));

		StepVerifier.create(service.updateFranchiseName("123", "New Name"))
				.expectNextMatches(f -> f.getName().equals("New Name"))
				.verifyComplete();
	}

	@Test
	void shouldUpdateBranchName() {
		Branch branch = Branch.builder().name("Sucursal 1").build();
		Franchise franchise = Franchise.builder()
				.id("123")
				.branches(new ArrayList<>(List.of(branch)))
				.build();

		when(repository.findById("123")).thenReturn(Mono.just(franchise));
		when(repository.save(any())).thenReturn(Mono.just(franchise));

		StepVerifier.create(service.updateBranchName("123", "Sucursal 1", "Sucursal Nueva"))
				.expectNextMatches(f -> f.getBranches().get(0).getName().equals("Sucursal Nueva"))
				.verifyComplete();
	}

	@Test
	void shouldUpdateProductName() {
		Product product = Product.builder().name("Empanada").stock(10).build();
		Branch branch = Branch.builder().name("Sucursal 1").products(new ArrayList<>(List.of(product))).build();
		Franchise franchise = Franchise.builder().id("123").branches(new ArrayList<>(List.of(branch))).build();

		when(repository.findById("123")).thenReturn(Mono.just(franchise));
		when(repository.save(any())).thenReturn(Mono.just(franchise));

		StepVerifier.create(service.updateProductName("123", "Sucursal 1", "Empanada", "Arepa"))
				.expectNextMatches(f -> f.getBranches().get(0).getProducts().get(0).getName().equals("Arepa"))
				.verifyComplete();
	}
}
