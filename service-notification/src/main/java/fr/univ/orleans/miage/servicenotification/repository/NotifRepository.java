package fr.univ.orleans.miage.servicenotification.repository;

import fr.univ.orleans.miage.servicenotification.modele.Notif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
@Repository
public interface NotifRepository extends JpaRepository<Notif, Long>, Serializable {
}
