package br.com.projetoferias.repository;

import br.com.projetoferias.model.Product;
import br.com.projetoferias.model.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<Product> findTop10ByCategory(ProductCategory category);

    List<Product> findPromotionalProducts();

    List<Product> findMostSoldProducts();
}
