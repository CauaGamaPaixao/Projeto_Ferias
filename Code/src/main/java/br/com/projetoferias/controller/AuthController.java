package br.com.projetoferias.controller;

import br.com.projetoferias.model.UserAccount;
import br.com.projetoferias.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("mode", "login");
        return "auth";
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam String email,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {
        return userService.authenticate(email, password)
                .map(user -> {
                    session.setAttribute("user", user);
                    return "redirect:/";
                })
                .orElseGet(() -> {
                    model.addAttribute("mode", "login");
                    model.addAttribute("error", "E-mail ou senha invalidos.");
                    return "auth";
                });
    }

    @GetMapping("/cadastro")
    public String register(Model model) {
        model.addAttribute("mode", "register");
        return "auth";
    }

    @PostMapping("/cadastro")
    public String createAccount(@RequestParam String name,
                                @RequestParam String email,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {
        if (!userService.register(name, email, password)) {
            model.addAttribute("mode", "register");
            model.addAttribute("error", "Ja existe uma conta com este e-mail.");
            return "auth";
        }
        session.setAttribute("user", new UserAccount(name, email.trim().toLowerCase(), password));
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
