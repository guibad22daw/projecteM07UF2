package edu.fje2.daw2.spring1.controladors;

import edu.fje2.daw2.spring1.model.Usuari;
import edu.fje2.daw2.spring1.repositoris.UsuariRepositori;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("usuaris")
public class UsuarisController {
    @Autowired
    private UsuariRepositori repositori;

    @ModelAttribute("usuaris")
    public List<Usuari> inicialitzar() {

        List<Usuari> usuaris = new ArrayList<>();
        for (Usuari c : repositori.findAll()) {
            usuaris.add(c);
        }
        return usuaris;
    }

    @RequestMapping(value={"/client", "/usuari"})
    String mostrarFormulari() {
        return("formulari");
    }

    @RequestMapping(value="/desarUsuari", method = RequestMethod.POST)
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
    String esborrarClient(@SessionAttribute("clients") List<Client> clients, @RequestParam (defaultValue = "") String id) {

        System.out.println(id);

        repositori.deleteById(id);
        Client t = new Client();
        t.setId(id);
        clients.remove(t);

        return("llistarClients");
    }

    @RequestMapping(value="/mostrarClients", method = RequestMethod.GET)
    void mostrarClients(){
        List <Client> client = new ArrayList<Client>();
        for (Client c : repositori.findByCognom("Grau")) {
            System.out.println(c);
        }
    }
}
