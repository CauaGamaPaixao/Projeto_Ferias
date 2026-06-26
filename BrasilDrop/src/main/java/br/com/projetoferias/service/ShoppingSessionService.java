package br.com.projetoferias.service;

import br.com.projetoferias.model.CartItem;
import br.com.projetoferias.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@SessionScope
public class ShoppingSessionService {

    private final Map<Long, Integer> cart = new HashMap<>();
    private final Set<Long> wishlist = new HashSet<>();

    public void addToCart(Long productId) {
        cart.merge(productId, 1, Integer::sum);
    }

    public void removeFromCart(Long productId) {
        cart.remove(productId);
    }

    public void clearCart() {
        cart.clear();
    }

    public void toggleWishlist(Long productId) {
        if (!wishlist.add(productId)) {
            wishlist.remove(productId);
        }
    }

    public boolean isFavorite(Long productId) {
        return wishlist.contains(productId);
    }

    public int cartCount() {
        return cart.values().stream().mapToInt(Integer::intValue).sum();
    }

    public int wishlistCount() {
        return wishlist.size();
    }

    public List<CartItem> cartItems(ProductService productService) {
        List<CartItem> items = new ArrayList<>();
        cart.forEach((productId, quantity) -> productService.findById(productId)
                .ifPresent(product -> items.add(new CartItem(product, quantity))));
        return items;
    }

    public List<Product> wishlistItems(ProductService productService) {
        return wishlist.stream()
                .map(productService::findById)
                .flatMap(optional -> optional.stream())
                .toList();
    }

    public BigDecimal total(ProductService productService) {
        return cartItems(productService).stream()
                .map(CartItem::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
