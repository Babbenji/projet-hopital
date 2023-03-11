package fr.univ.orleans.miage.servicenotification.repository;

import fr.univ.orleans.miage.servicenotification.modele.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long>, Serializable {
//
//    Email findByDestinataire(String destinataire);
//
//    default void deleteByEmailDestinataire(String destinataire) {
//        this.deleteAllByEmailDestinataire(destinataire);
//    }
//
//    void deleteAllByEmailDestinataire(String destinataire);
//
//    List<Email> findAllByEmailDateBetween(String dateDebut, String dateFin);
//
//    List<Email> findAllByEmailDateBetweenAndDestinataire(String dateDebut, String dateFin, String destinataire);

}
