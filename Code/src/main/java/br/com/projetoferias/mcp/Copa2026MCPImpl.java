package br.com.projetoferias.mcp;

import br.com.projetoferias.football.FootballDataProvider;
import br.com.projetoferias.model.Product;
import br.com.projetoferias.model.ProductCategory;
import br.com.projetoferias.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Copa2026MCPImpl implements Copa2026MCP {

    private final FootballDataProvider footballDataProvider;
    private final ProductRepository productRepository;

    public Copa2026MCPImpl(FootballDataProvider footballDataProvider, ProductRepository productRepository) {
        this.footballDataProvider = footballDataProvider;
        this.productRepository = productRepository;
    }

    @Override
    public String getWorldCupContext() {
        return "Noticias da Copa: " + String.join("; ", footballDataProvider.getWorldCupNews())
                + ". Proximos jogos do Brasil: " + String.join("; ", footballDataProvider.getNextBrazilMatches()) + ".";
    }

    @Override
    public String getBrazilTeamContext() {
        return footballDataProvider.getBrazilTeamInfo();
    }

    @Override
    public String getMarketplaceContext() {
        return "Camisas: " + formatProducts(productRepository.findTop10ByCategory(ProductCategory.CAMISAS))
                + ". Chuteiras: " + formatProducts(productRepository.findTop10ByCategory(ProductCategory.CHUTEIRAS))
                + ". Promocoes: " + formatProducts(productRepository.findPromotionalProducts())
                + ". Mais vendidos: " + formatProducts(productRepository.findMostSoldProducts()) + ".";
    }

    private String formatProducts(List<Product> products) {
        if (products.isEmpty()) {
            return "nenhum produto encontrado";
        }
        return products.stream()
                .map(product -> product.name() + " (" + product.category().getLabel() + ", R$ " + product.price() + ")")
                .toList()
                .toString();
    }
}
