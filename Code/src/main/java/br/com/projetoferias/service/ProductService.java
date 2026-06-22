package br.com.projetoferias.service;

import br.com.projetoferias.model.Product;
import br.com.projetoferias.model.ProductCategory;
import br.com.projetoferias.repository.InMemoryProductRepository;
import br.com.projetoferias.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService() {
        this(new InMemoryProductRepository());
    }

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll(String query, ProductCategory category) {
        return productRepository.findAll().stream()
                .filter(product -> category == null || product.category() == category)
                .filter(product -> query == null || query.isBlank() || matches(product, query))
                .toList();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<ProductCategory> categories() {
        return List.of(ProductCategory.values());
    }

    private boolean matches(Product product, String query) {
        String normalized = query.toLowerCase(Locale.ROOT);
        return product.name().toLowerCase(Locale.ROOT).contains(normalized)
                || product.brand().toLowerCase(Locale.ROOT).contains(normalized)
                || product.description().toLowerCase(Locale.ROOT).contains(normalized);
    }
}
