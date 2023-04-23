package edu.fje2.daw2.spring1.controladors;

import edu.fje2.daw2.spring1.Alumne;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Controller
public class AlumnesController {
    private static ArrayList<Alumne> llistaAlumnes= new ArrayList<>();

    @GetMapping("/llistarPerCognom")
    public String llistarPerCognom(Model model){

        Collections.sort(llistaAlumnes, new Comparator<Alumne>() {
            @Override
            public int compare(Alumne o1, Alumne o2) {
                return o1.getCognom().compareTo(o2.getCognom());
            }
        });
        model.addAttribute("alumnes", llistaAlumnes);
        return "llistatAlumnes";
    }
    @GetMapping("/llistarPerNota")
    public String llistarPerNota(Model model){

        Collections.sort(llistaAlumnes, new Comparator<Alumne>() {
            @Override
            public int compare(Alumne o1, Alumne o2) {
                return o1.getNota()-o2.getNota();
            }
        });
        model.addAttribute("alumnes", llistaAlumnes);
        return "llistatAlumnes";
    }
    @GetMapping("/afegirAlumne")
    public String afegirAlumne(
            @RequestParam() String nom,
            @RequestParam() String cognom,
            @RequestParam() int nota,
            Model model) {

        llistaAlumnes.add(new Alumne(nom, cognom, nota));
        model.addAttribute("alumnes", llistaAlumnes);
        System.out.println(llistaAlumnes);
        return "llistatAlumnes";
    }
}
