package br.com.projetoferias.controller;

import br.com.projetoferias.model.ProductCategory;
import br.com.projetoferias.service.ProductService;
import br.com.projetoferias.service.ShoppingSessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MarketplaceController {

    private final ProductService productService;
    private final ShoppingSessionService shoppingSession;

    public MarketplaceController(ProductService productService, ShoppingSessionService shoppingSession) {
        this.productService = productService;
        this.shoppingSession = shoppingSession;
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String q,
                       @RequestParam(required = false) ProductCategory category,
                       HttpSession session,
                       Model model) {
        model.addAttribute("products", productService.findAll(q, category));
        model.addAttribute("categories", productService.categories());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("query", q);
        model.addAttribute("shopping", shoppingSession);
        model.addAttribute("user", session.getAttribute("user"));
        return "home";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId) {
        shoppingSession.addToCart(productId);
        return "redirect:/";
    }

    @PostMapping("/wishlist/toggle")
    public String toggleWishlist(@RequestParam Long productId) {
        shoppingSession.toggleWishlist(productId);
        return "redirect:/";
    }

    @GetMapping("/wishlist")
    public String wishlist(HttpSession session, Model model) {
        model.addAttribute("products", shoppingSession.wishlistItems(productService));
        model.addAttribute("shopping", shoppingSession);
        model.addAttribute("user", session.getAttribute("user"));
        return "wishlist";
    }

    @GetMapping("/cart")
    public String cart(HttpSession session, Model model) {
        model.addAttribute("items", shoppingSession.cartItems(productService));
        model.addAttribute("total", shoppingSession.total(productService));
        model.addAttribute("shopping", shoppingSession);
        model.addAttribute("user", session.getAttribute("user"));
        return "cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long productId) {
        shoppingSession.removeFromCart(productId);
        return "redirect:/cart";
    }
}
