package edu.fje.daw2.projecteM07UF2.controladors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OperacionsController {
    @GetMapping("/operar")
    public String operar(@RequestParam(required = true) double n1, @RequestParam(required = true) double n2, @RequestParam(required = true) String operacio, Model model) {
        double resultatOperacio = 0;
        switch (operacio) {
            case "suma":
                resultatOperacio = n1 + n2;
                break;

            case "resta":
                resultatOperacio = n1 - n2;
                break;
        }
        model.addAttribute("n1", n1);
        model.addAttribute("n2", n2);
        model.addAttribute("resultat", resultatOperacio);
        model.addAttribute("operacio", operacio);
        return "resultat";
    }
}