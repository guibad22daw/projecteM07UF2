package edu.fje.daw2.projecteM07UF2.controladors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PrimerController {
    @GetMapping("/salutacio")
    public String salutar(@RequestParam(defaultValue = "sergi", required = false) String nom, Model model) {
        model.addAttribute("usuari", nom);
        return "salutacio";
    }
}
