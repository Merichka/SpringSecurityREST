package ru.merichka.appspringboot.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.merichka.appspringboot.repository.UserRepository;

import java.security.Principal;

@Controller
@AllArgsConstructor
@PreAuthorize("hasRole('USER')")
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    @RequestMapping()
    public String userPage(Principal principal, Model model) {
        model.addAttribute("title", "User information-page");
        model.addAttribute("user", userRepository.findFirstByEmail(principal.getName()));
        return "users/get";
    }

}
