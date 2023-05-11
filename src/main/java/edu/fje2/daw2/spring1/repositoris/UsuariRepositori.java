package edu.fje2.daw2.spring1.repositoris;

import edu.fje2.daw2.spring1.model.Usuari;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * Interficie de persistència amb Spring Data i MongoDB.
 * @author sergi.grau@fje.edu
 * @version  1.0 4.4.2019
 */
public interface UsuariRepositori extends MongoRepository<Usuari, String> {
    Usuari findByUsername(String username);
    List<Usuari> findByMail(String mail);
}
