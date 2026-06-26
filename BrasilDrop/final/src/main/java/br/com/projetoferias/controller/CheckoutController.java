package br.com.projetoferias.controller;

import br.com.projetoferias.model.CheckoutAddress;
import br.com.projetoferias.model.OrderSummary;
import br.com.projetoferias.service.OrderService;
import br.com.projetoferias.service.ProductService;
import br.com.projetoferias.service.ShoppingSessionService;
import br.com.projetoferias.service.ViaCepService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CheckoutController {

    private final ProductService productService;
    private final ShoppingSessionService shoppingSession;
    private final ViaCepService viaCepService;
    private final OrderService orderService;

    public CheckoutController(ProductService productService,
                              ShoppingSessionService shoppingSession,
                              ViaCepService viaCepService,
                              OrderService orderService) {
        this.productService = productService;
        this.shoppingSession = shoppingSession;
        this.viaCepService = viaCepService;
        this.orderService = orderService;
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        if (shoppingSession.cartCount() == 0) {
            return "redirect:/cart";
        }
        addCheckoutModel(model, session, null, null);
        return "checkout";
    }

    @PostMapping("/checkout")
    public String pay(@RequestParam String cep,
                      @RequestParam String paymentMethod,
                      HttpSession session,
                      Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        return viaCepService.findAddress(cep)
                .map(address -> {
                    OrderSummary order = orderService.createOrder(
                            shoppingSession,
                            productService,
                            address,
                            paymentMethod
                    );
                    shoppingSession.clearCart();
                    model.addAttribute("order", order);
                    model.addAttribute("shopping", shoppingSession);
                    model.addAttribute("user", session.getAttribute("user"));
                    return "success";
                })
                .orElseGet(() -> {
                    addCheckoutModel(model, session, "CEP invalido ou nao encontrado no ViaCEP.", cep);
                    return "checkout";
                });
    }

    private void addCheckoutModel(Model model, HttpSession session, String error, String cep) {
        model.addAttribute("items", shoppingSession.cartItems(productService));
        model.addAttribute("total", shoppingSession.total(productService));
        model.addAttribute("shopping", shoppingSession);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("error", error);
        model.addAttribute("cep", cep);
    }
}
