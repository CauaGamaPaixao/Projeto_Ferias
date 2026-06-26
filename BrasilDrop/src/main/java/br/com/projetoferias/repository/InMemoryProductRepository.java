package br.com.projetoferias.repository;

import br.com.projetoferias.model.Product;
import br.com.projetoferias.model.ProductCategory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> products = List.of(
            new Product(1L, "Camisa Brasil Casa 2026", "Nike", ProductCategory.CAMISAS,
                    "Camisa amarela da selecao brasileira para torcer em clima de copa.",
                    new BigDecimal("349.90"),
                    "/images/brasilcasa.jpg"),
            new Product(2L, "Chuteira Puma Future Coral", "Puma", ProductCategory.CHUTEIRAS,
                    "Chuteira de campo Puma com travas firmes e cabedal leve.",
                    new BigDecimal("459.90"),
                    "/images/pumachuteira.jpg"),
            new Product(3L, "Camisa Brasil Goleiro Verde", "Nike", ProductCategory.CAMISAS,
                    "Camisa verde de goleiro com grafismo da selecao brasileira.",
                    new BigDecimal("299.90"),
                    "/images/brasilgoleiro.jpg"),
            new Product(4L, "Camisa Brasil Fora Azul", "Jordan", ProductCategory.CAMISAS,
                    "Camisa azul da selecao para jogos fora de casa.",
                    new BigDecimal("369.90"),
                    "/images/brasilfora.jpg"),
            new Product(5L, "Bone Brasil Azul CBF", "Brasil Market", ProductCategory.BONES,
                    "Bone ajustavel azul com escudo do Brasil.",
                    new BigDecimal("119.90"),
                    "/images/brasilbone.jpg"),
            new Product(6L, "Blusa Treino Brasil Manga Longa", "Nike", ProductCategory.CAMISAS,
                    "Blusa azul de treino com manga longa e meio ziper.",
                    new BigDecimal("279.90"),
                    "/images/brasiltreinolonga.jpg"),
            new Product(7L, "Chuteira Nike Mercurial Rosa", "Nike", ProductCategory.CHUTEIRAS,
                    "Chuteira Nike rosa para campo, velocidade e controle.",
                    new BigDecimal("529.90"),
                    "/images/nikechuteira.jpg"),
            new Product(8L, "Tenis Nike Shox Brasil", "Nike", ProductCategory.TENIS,
                    "Tenis esportivo com detalhes em amarelo, azul e prata.",
                    new BigDecimal("699.90"),
                    "/images/brasiltenis.jpg")
    );

    private final Set<Long> promotionalProductIds = Set.of(3L, 5L, 6L);
    private final List<Long> mostSoldProductIds = List.of(1L, 7L, 2L, 4L);

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(product -> product.id().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> findTop10ByCategory(ProductCategory category) {
        return products.stream()
                .filter(product -> product.category() == category)
                .limit(10)
                .toList();
    }

    @Override
    public List<Product> findPromotionalProducts() {
        return products.stream()
                .filter(product -> promotionalProductIds.contains(product.id()))
                .toList();
    }

    @Override
    public List<Product> findMostSoldProducts() {
        return mostSoldProductIds.stream()
                .map(this::findById)
                .flatMap(Optional::stream)
                .toList();
    }
}
