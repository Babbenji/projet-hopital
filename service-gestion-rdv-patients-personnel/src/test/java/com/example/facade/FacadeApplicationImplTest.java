package com.example.facade;

import com.example.exceptions.AdresseMailDejaUtiliseeException;
import com.example.exceptions.NumeroSecuDejaAttribueException;
import com.example.modele.Consultation;
import com.example.modele.Medecin;
import com.example.modele.Patient;
import com.example.modele.Creneau;
import com.example.repository.ConsultationRepository;
import com.example.repository.CreneauRepository;
import com.example.repository.MedecinRepository;
import com.example.repository.PatientRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class FacadeApplicationImplTest{
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private MedecinRepository medecinRepository;
    @Mock
    private CreneauRepository creneauRepository;
    @Mock
    private ConsultationRepository consultationRepository;
    @InjectMocks
    private FacadeApplicationImpl facadeApplication;
    private Medecin medecin;
    private Patient patient;
    private Consultation consultation;
    private Creneau creneau;

//    @BeforeEach
//    public void init(){
//        medecin = new Medecin("Aziz", "BENJAZIA","aziz.benjazia@etu.univ-orleans.fr");
//        patient = new Patient("Rachida","ELOUARIACHI","rachida45@gmail.com","12345","0123456789", "14-02-1995","Femme");
//    }
    @Test
    public void enregistrementMedecinOK() throws AdresseMailDejaUtiliseeException {
        String prenom = "Aziz";
        String nom = "BENJAZIA";
        String email = "aziz.benjazia@etu.univ-orleans.fr";

        Medecin medecin = new Medecin("Aziz", "BENJAZIA","aziz.benjazia@etu.univ-orleans.fr");
        when(medecinRepository.save(Mockito.any(Medecin.class))).thenReturn(medecin);

        Medecin nouveauMedecin = facadeApplication.ajouterMedecin(prenom, nom, email);

        Assertions.assertThat(nouveauMedecin).isNotNull();
        Assertions.assertThat(nouveauMedecin.getId()).isGreaterThan(0);
    }

    @Test
    public void enregistrementPatientOK() throws NumeroSecuDejaAttribueException, AdresseMailDejaUtiliseeException {
        String prenom = "Rachida";
        String nom = "ELOUARIACHI";
        String email = "rachida45@gmail.com";
        String numSecu = "12345";
        String numTel = "0123456789";
        String dateNaissance = "14-02-1995";
        String genre = "Femme";

        Patient patient = new Patient(prenom,nom,email,numSecu,numTel,dateNaissance,genre);
        when(patientRepository.save(Mockito.any(Patient.class))).thenReturn(patient);

        Patient nouveauPatient = facadeApplication.ajouterPatient(prenom,nom,email,numSecu,numTel,dateNaissance,genre);

        Assertions.assertThat(nouveauPatient).isNotNull();
        Assertions.assertThat(nouveauPatient.getId()).isGreaterThan(0);
        Assertions.assertThat(nouveauPatient.getIdMedecinTraitant()).isEqualTo(0);
    }
//    @Test
//    public void getPatientByNumSecuOK() throws PatientInexistantException {
//        String prenom = "Rachida";
//        String nom = "ELOUARIACHI";
//        String email = "rachida45@gmail.com";
//        String numSecu = "12345";
//        String numTel = "0123456789";
//        String dateNaissance = "14-02-1995";
//        String genre = "Femme";
//        Patient patient = new Patient(prenom,nom,email,numSecu,numTel,dateNaissance,genre);
//        when(patientRepository.save(Mockito.any(Patient.class))).thenReturn(patient);
//        when(patientRepository.findPatientByNumSecu("12345")).thenReturn(patient);
//        Patient patientRecupere = facadeApplication.getPatientByNumSecu("12345");
//        Assertions.assertThat(patientRecupere).isNotNull();
//    }
    /*

    private MedecinRepository medecinRepository;
    private ConsultationRepository consultationRepository;
    private CreneauRepository creneauRepository;
    @Override
    public FacadeApplication getInstance() throws ClientNotFoundException {
        this.patientRepository = EasyMock.createMock(PatientRepository.class);
        this.medecinRepository = EasyMock.createMock(MedecinRepository.class);
        this.consultationRepository = EasyMock.createMock(ConsultationRepository.class);
        this.creneauRepository = EasyMock.createMock(CreneauRepository.class);
        return new FacadeApplicationImpl(patientRepository,medecinRepository,consultationRepository,creneauRepository);
    }

    @Override
    public void initialisationTestEnregistrementMedecin1(String prenom, String nom, String email) {
        Medecin medecin = EasyMock.createMock(Medecin.class);
        EasyMock.expect(medecin.getId()).andReturn(2);
        EasyMock.expect(this.medecinRepository.save(medecin));
        EasyMock.expect(this.medecinRepository.findMedecinById(2)).andReturn(medecin);
        EasyMock.replay(this.medecinRepository,medecin);
    }
    */
}