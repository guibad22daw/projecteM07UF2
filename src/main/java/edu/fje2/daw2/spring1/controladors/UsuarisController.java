package edu.fje2.daw2.spring1.controladors;

import edu.fje2.daw2.spring1.model.Usuari;
import edu.fje2.daw2.spring1.repositoris.UsuariRepositori;
import org.springframework.beans.factory.annotation.Autowired;
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
            Usuari nouUsuari = new Usuari(username, mail, null);
            repositori.save(nouUsuari);
            System.out.println("Nou usuari creat");
        }
        else {
            System.out.println("Usuari ja existent. Id = " + username);
        }

        return("home");
    }

    /* @RequestMapping(value="/desarUsuari", method = RequestMethod.POST)
    String desarClient(@SessionAttribute("clients") List<Usuari> clients,
                       @RequestParam(defaultValue = "") String nom,
                       @RequestParam (defaultValue = "") String cognom,
                       @RequestParam (defaultValue = "") int volumCompres,
                       ModelMap model){
        Usuari c = new Usuari(nom, cognom, volumCompres);
        repositori.save(c);

        if(!model.containsAttribute("clients")) {
            model.addAttribute("clients", clients);
        }
        clients.add(c);
        return("llistarClients");
    }

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
