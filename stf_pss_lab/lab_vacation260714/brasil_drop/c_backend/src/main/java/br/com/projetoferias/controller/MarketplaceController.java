package br.com.projetoferias.controller;

import br.com.projetoferias.model.CartItem;
import br.com.projetoferias.model.Product;
import br.com.projetoferias.model.ProductCategory;
import br.com.projetoferias.service.ProductService;
import br.com.projetoferias.service.ShoppingSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class MarketplaceController {

    private final ProductService productService;
    private final ShoppingSessionService shoppingSession;

    public MarketplaceController(ProductService productService, ShoppingSessionService shoppingSession) {
        this.productService = productService;
        this.shoppingSession = shoppingSession;
    }

    @GetMapping("/products")
public List<Product> products(@RequestParam(required = false) String q,
                              @RequestParam(required = false) String category) {
    ProductCategory cat = Arrays.stream(ProductCategory.values())
            .filter(c -> c.getLabel().equalsIgnoreCase(category) || c.name().equalsIgnoreCase(category))
            .findFirst()
            .orElse(null);
    return productService.findAll(q, cat);
}

    @GetMapping("/products/categories")
    public List<String> categories() {
        return Arrays.stream(ProductCategory.values())
                .map(ProductCategory::getLabel)
                .toList();
    }

    @GetMapping("/cart")
    public Map<String, Object> cart(HttpSession session) {
        synchronized (session) {
            List<CartItem> items = shoppingSession.cartItems(productService);
            BigDecimal total = shoppingSession.total(productService);
            return Map.of("items", items.stream().map(this::toCartDto).toList(), "total", total);
        }
    }

    @PostMapping("/cart/add")
    public ResponseEntity<Void> addToCart(@RequestBody Map<String, Long> body, HttpSession session) {
        synchronized (session) {
            if (body.get("productId") == null || productService.findById(body.get("productId")).isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            shoppingSession.addToCart(body.get("productId"));
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/cart/remove")
    public ResponseEntity<Void> removeFromCart(@RequestBody Map<String, Long> body, HttpSession session) {
        synchronized (session) {
            shoppingSession.removeFromCart(body.get("productId"));
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/wishlist")
    public List<Product> wishlist() {
        return shoppingSession.wishlistItems(productService);
    }

    @PostMapping("/wishlist/toggle")
    public Map<String, Object> toggleWishlist(@RequestBody Map<String, Long> body) {
        shoppingSession.toggleWishlist(body.get("productId"));
        Set<Long> ids = shoppingSession.wishlistIds();
        return Map.of("wishlistIds", ids);
    }

    private Map<String, Object> toCartDto(CartItem item) {
        return Map.of(
                "product", item.product(),
                "quantity", item.quantity(),
                "subtotal", item.subtotal()
        );
    }
}
