package fr.univ.orleans.miage.servicenotification.repository;

import fr.univ.orleans.miage.servicenotification.modele.Push;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
@Repository
public interface PushRepository extends JpaRepository<Push, Long>, Serializable {
}
