package br.com.projetoferias.service;

import br.com.projetoferias.model.Product;
import br.com.projetoferias.model.ProductCategory;
import br.com.projetoferias.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void filtersProductsByCategory() {
        when(productRepository.findAll()).thenReturn(products());

        assertThat(productService.findAll(null, ProductCategory.CHUTEIRAS))
                .allMatch(product -> product.category() == ProductCategory.CHUTEIRAS)
                .hasSize(2);
    }

    @Test
    void searchesProductsByText() {
        when(productRepository.findAll()).thenReturn(products());

        assertThat(productService.findAll("mercurial", null))
                .extracting("name")
                .containsExactly("Chuteira Nike Mercurial Rosa");
    }

    private List<Product> products() {
        return List.of(
                new Product(1L, "Camisa Brasil Casa 2026", "Nike", ProductCategory.CAMISAS,
                        "Camisa amarela da selecao brasileira.", new BigDecimal("349.90"), "/images/brasilcasa.jpg"),
                new Product(2L, "Chuteira Puma Future Coral", "Puma", ProductCategory.CHUTEIRAS,
                        "Chuteira de campo Puma com travas firmes.", new BigDecimal("459.90"), "/images/pumachuteira.jpg"),
                new Product(7L, "Chuteira Nike Mercurial Rosa", "Nike", ProductCategory.CHUTEIRAS,
                        "Chuteira Nike rosa para velocidade.", new BigDecimal("529.90"), "/images/nikechuteira.jpg")
        );
    }
}
