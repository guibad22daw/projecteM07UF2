package edu.fje.daw2.projecteM07UF2.controladors;

import edu.fje.daw2.projecteM07UF2.model.Alumne;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

@Controller
public class AlumnesController {
    private static ArrayList<Alumne> alumnes = new ArrayList<>();

    @GetMapping("/afegirAlumne")
    public String creaAlumne(@RequestParam(required = true) String nom, @RequestParam(required = true) String cognom, @RequestParam(required = true) double nota, @RequestParam(required = true) String dataNaixement, Model model) {
        alumnes.add(new Alumne("Guillem","Badenas",8,"15/03/2002"));
        alumnes.add(new Alumne("Xavier","Aranda",4,"15/03/2001"));
        alumnes.add(new Alumne("Samuel","Rebollo",4,"15/03/2003"));
        alumnes.add(new Alumne(nom,cognom,nota,dataNaixement));
        alumnes.sort(Comparator.comparing(Alumne::getCognom));
        model.addAttribute("alumnes", alumnes);
        model.addAttribute("tipusOrdenacio", "per cognom");
        return "llistatAlumnes";
    }

    @GetMapping("/llistarPerCognom")
    public String llistaPerCognom(Model model) {
        alumnes.sort(Comparator.comparing(Alumne::getCognom));
        model.addAttribute("alumnes", alumnes);
        model.addAttribute("tipusOrdenacio", "per cognom");
        return "llistatAlumnes";
    }

    @GetMapping("/llistarPerNota")
    public String llistarPerNota(Model model) {
        alumnes.sort(Comparator.comparing(Alumne::getNota).reversed());
        model.addAttribute("alumnes", alumnes);
        model.addAttribute("tipusOrdenacio", "per nota");
        return "llistatAlumnes";
    }

    @GetMapping("/llistarPerData")
    public String llistarPerData(Model model) {
        alumnes.sort(Comparator.comparing(o -> new Date(o.getDataNaixement()).getTime()));
        model.addAttribute("alumnes", alumnes);
        model.addAttribute("tipusOrdenacio", "per data de naixement");
        return "llistatAlumnes";
    }


}