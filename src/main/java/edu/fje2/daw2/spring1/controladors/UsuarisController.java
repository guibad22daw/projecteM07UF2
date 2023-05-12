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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("usuaris")
public class UsuarisController {
    @Autowired
    private UsuariRepositori repositori;

    /*
    @ModelAttribute("usuaris")
    public List<Usuari> inicialitzar() {

        List<Usuari> usuaris = new ArrayList<>();
        for (Usuari c : repositori.findAll()) {
            usuaris.add(c);
        }
        return usuaris;
    }

    @RequestMapping(value={"/home", "/usuari"})
    String mostrarFormulari() {
        return("formulari");
    }
    */


    @ModelAttribute("clients")
    @RequestMapping(value="/home", method = RequestMethod.GET)
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

    @PostMapping(value="/esborraCiutat")
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

    /*
    @RequestMapping(value="/esborrarClient", method = RequestMethod.GET)
    String esborrarClient(@SessionAttribute("clients") List<Usuari> clients, @RequestParam (defaultValue = "") String id) {

        System.out.println(id);

        repositori.deleteById(id);
        Usuari t = new Usuari();
        t.setId(id);
        clients.remove(t);

        return("llistarClients");
    }

    @RequestMapping(value="/mostrarClients", method = RequestMethod.GET)
    void mostrarClients(){
        List <Usuari> client = new ArrayList<Usuari>();
        for (Usuari c : repositori.findByCognom("Grau")) {
            System.out.println(c);
        }
    } */
}
