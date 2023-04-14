package fr.univ.servicegestionstockfournisseurs.controleur;

import fr.univ.servicegestionstockfournisseurs.modele.Fournisseur;
import fr.univ.servicegestionstockfournisseurs.modele.ProduitMedical;
import fr.univ.servicegestionstockfournisseurs.modele.Utilisateur;
import fr.univ.servicegestionstockfournisseurs.service.FacadeServiceGestionStock;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.FournisseurDejaExistantException;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.UtilisateurDejaExistantException;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.UtilisateurInexistantException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ControlleurServiceTest {

    @InjectMocks
    ControlleurService controlleurService;

    MockMvc mockMvc;
    @Mock
    FacadeServiceGestionStock facadeServiceGestionStock;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controlleurService).build();
    }

    @Test
    public void testPasserCommande_success() throws UtilisateurInexistantException {
        int idUtilisateur = 1;
        Mockito.doNothing().when(facadeServiceGestionStock).passerCommande(idUtilisateur);
        ResponseEntity<String> response = controlleurService.passerCommande(idUtilisateur);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Commande passée", response.getBody());
    }
    @Test
    public void testPasserCommande_UtilisateurInexistant() throws UtilisateurInexistantException {
        int idUtilisateur = 1;
        Mockito.doThrow(UtilisateurInexistantException.class).when(facadeServiceGestionStock).passerCommande(idUtilisateur);
        ResponseEntity<String> response = controlleurService.passerCommande(idUtilisateur);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Utilisateur inexistant", response.getBody());
    }



//    @Test
//    public void testAddNewUtilisateur() throws Exception {
//        Utilisateur utilisateur = new Utilisateur("Aziz", "BEN JAZIA", "aziz.benjazia@etu.univ-orleans.fr");
//
//        Mockito.when(facadeServiceGestionStock.ajouterUtilisateur(Mockito.any(Utilisateur.class))).thenReturn(utilisateur);
//
//        // Send course as body to /students/Student1/courses
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/api/v1/gestionnaire/utilisateurs")
//                .accept(MediaType.APPLICATION_JSON).content("{")
//                .contentType(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
//
//        assertEquals("http://localhost:8084/api/v1/gestionnaire/utilisateurs",
//                response.getHeader(HttpHeaders.LOCATION));
//    }


    @Test
    public void testAddNewUtilisateur_UtilisateurDejaExistant() throws UtilisateurDejaExistantException {
        Utilisateur utilisateur = new Utilisateur("John", "Doe", "john.doe@example.com");
        Mockito.doThrow(UtilisateurDejaExistantException.class).when(facadeServiceGestionStock).ajouterUtilisateur(Mockito.any(Utilisateur.class));
        ResponseEntity<Object> response = controlleurService.addNewUtilisateur(utilisateur);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Utilisateur déjà existant", response.getBody());
    }

    @Test
    public void testAddNewProduit_Success() throws Exception {

        ProduitMedical produitMedical = new ProduitMedical("Ibuprofen", 5.99, "Anti-douleur");
        Mockito.doNothing().when(facadeServiceGestionStock).ajouterProduit(Mockito.anyString(), Mockito.anyDouble(), Mockito.anyString());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/gestionnaire/produits")
                .accept(MediaType.APPLICATION_JSON).content("{\n" +
                        "    \"nomProduitMedical\": \"Ibuprofen\",\n" +
                        "    \"prixProduitMedical\": 5.99,\n" +
                        "    \"descriptionProduitMedical\": \"Anti-douleur\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void testAddNewFournisseur_Success() throws Exception {
        Fournisseur fournisseur = new Fournisseur("Aziz", "86 Rue des Tests", "0606060606");
        Mockito.doNothing().when(facadeServiceGestionStock).ajouterFournisseur(fournisseur);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/gestionnaire/fournisseurs")
                .accept(MediaType.APPLICATION_JSON).content("{\n" +
                        "    \"nomFournisseur\": \"Aziz\",\n" +
                        "    \"adresseFournisseur\": \"86 Rue des Tests\",\n" +
                        "    \"telephoneFournisseur\": \"0606060606\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andReturn();

    }
    @Test
    public void testAddNewFournisseur_FournisseurDejaExistantException() throws FournisseurDejaExistantException {
        Fournisseur fournisseur = new Fournisseur("Aziz", "86 Rue des Tests", "0606060606");
        Mockito.doThrow(FournisseurDejaExistantException.class).when(facadeServiceGestionStock).ajouterFournisseur(Mockito.any(Fournisseur.class));
        ResponseEntity<Object> response = controlleurService.addNewFournisseur(fournisseur);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Fournisseur déjà existant", response.getBody());
    }




}
