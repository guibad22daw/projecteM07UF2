package edu.fje2.daw2.spring1.controladors;

import edu.fje2.daw2.spring1.model.Ciutat;
import edu.fje2.daw2.spring1.model.Usuari;
import edu.fje2.daw2.spring1.repositoris.UsuariRepositori;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UsuarisController {
    @Autowired
    private UsuariRepositori repositori;

    /**
     * Desa un nou usuari a la base de dades si no existeix.
     * @return La vista "home".
     */
    @GetMapping(value="/home")
    String desaUsuari() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getPrincipal().toString().substring(authentication.getPrincipal().toString().indexOf("username=") + 9);
        String mail = authentication.getPrincipal().toString().substring(authentication.getPrincipal().toString().indexOf("email=") + 6);

        boolean existeix = repositori.existsByUsername(username);
        if (!existeix) {
            Usuari nouUsuari = new Usuari(username, mail, new ArrayList<>());
            repositori.save(nouUsuari);
            System.out.println("Nou usuari creat");
        }
        else {
            System.out.println("Usuari ja existent. Id = " + username);
        }

        return("home");
    }

    /**
     * Obté les ciutats favorites de l'usuari i les mostra en la vista "favorits".
     * @param model Model de la vista.
     * @return La vista "favorits".
     */
    @GetMapping(value="/favorits")
    String getFavorits(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getPrincipal().toString().substring(authentication.getPrincipal().toString().indexOf("username=") + 9);
        String mail = authentication.getPrincipal().toString().substring(authentication.getPrincipal().toString().indexOf("email=") + 6);

        boolean existeix = repositori.existsByUsername(username);
        if (!existeix) {
            Usuari nouUsuari = new Usuari(username, mail, new ArrayList<>());
            repositori.save(nouUsuari);
            System.out.println("Nou usuari creat");
        }
        else {
            System.out.println("Usuari ja existent. Id = " + username);
        }
        Usuari usuari = repositori.findByUsername(username);
        List<Ciutat> ciutats = usuari.getCiutats();

        model.addAttribute("ciutats", ciutats);
        return("favorits");
    }

    /**
     * Gestiona la petició POST per desar una nova ciutat als favorits de l'usuari.
     * @param ciutat Objecte que representa la ciutat que s'ha de desar.
     * @return Retorna una resposta HTTP.
     */
    @PostMapping(value="/desaCiutat")
    public ResponseEntity<?> desaCiutat(@RequestBody Ciutat ciutat) {
        System.out.println(ciutat);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getPrincipal().toString().substring(authentication.getPrincipal().toString().indexOf("username=") + 9);

        Usuari usuari = repositori.findByUsername(username);

        if (usuari == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aquest usuari no existeix.");
        }

        List<Ciutat> ciutats = usuari.getCiutats();
        if (ciutats == null) {
            ciutats = new ArrayList<>();
        }

        Ciutat novaCiutat = new Ciutat(ciutat.getNom(), ciutat.getLatitud(), ciutat.getLongitud());
        ciutats.add(novaCiutat);

        usuari.setCiutats(ciutats);
        repositori.save(usuari);

        return ResponseEntity.ok().build();
    }

    /**
     * Esborra una ciutat de les favorites de l'usuari amb la recepció d'una petició DELETE.
     * @param ciutat La ciutat a esborrar.
     * @return ResponseEntity amb el codi d'estat HTTP corresponent.
     */
    @DeleteMapping(value="/esborraCiutat")
    public ResponseEntity<?> esborraCiutat(@RequestBody Ciutat ciutat) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getPrincipal().toString().substring(authentication.getPrincipal().toString().indexOf("username=") + 9);

        Usuari usuari = repositori.findByUsername(username);
        if (usuari == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aquest usuari no existeix.");
        }

        List<Ciutat> ciutats = usuari.getCiutats();
        if (ciutats == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hi ha cap ciutat a esborrar.");
        }

        for (Ciutat ciutatUsuari : ciutats) {
            if (ciutatUsuari.getNom().equals(ciutat.getNom())) {
                System.out.println("Ciutat a esborrar: " + ciutatUsuari);
                ciutats.remove(ciutatUsuari);
                break;
            }
        }

        usuari.setCiutats(ciutats);
        repositori.save(usuari);

        return ResponseEntity.ok().build();
    }

    /**
     * Retorna una llista amb les ciutats preferides d'un usuari.
     * @return Llista de Ciutats que representa les ciutats preferides de l'usuari
     */
    @GetMapping("/getCiutats")
    @ResponseBody
    public List<Ciutat> getCiutats() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getPrincipal().toString().substring(authentication.getPrincipal().toString().indexOf("username=") + 9);

        Usuari usuari = repositori.findByUsername(username);
        if (usuari == null) {
            return null;
        }

        List<Ciutat> ciutats = usuari.getCiutats();
        if (ciutats == null) {
            return null;
        }
        System.out.println(ciutats);
        return ciutats;
    }
}
