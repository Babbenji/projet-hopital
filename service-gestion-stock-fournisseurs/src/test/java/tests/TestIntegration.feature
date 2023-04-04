Featur: Tests Integration gestion stock fournisseur
  Background:
  * url 'http://localhost:8084/gestionnaire'
  * header Accept = 'application/json'

  Scenario: Test d'ajout d'un utilisateur
    Given path '/utilisateurs'
    And request {"prenom": "Megan", "nom": "Killer", "email": "megan.killer@etu.univ-orleans.fr" }
    When method post
    Then status 201
    And print response

  Scenario Test d'ajout d'un utilisateur déjà existant
    Given path '/utilisateurs'
    And request {"prenom": "Megan", "nom": "Killer", "email": "megan.killer@etu.univ-orleans.fr" }
    When method post
    Then status 409
    And print response

  Scenario Test d'ajout d'un produit
    Given path '/produitMedical'
    And request {"nomProduitMedical": "Doliprane", "prixProduitMedical": "3.0"", "descriptionProduitMedical": "Réduitla fièvre et les douleurs" }
    When method post
    Then status 201
    And print response

  Scenario Test d'ajout d'un fournisseur
    Given path '/fournisseurs'
    And request {"nomFournisseur": "Bayers", "adresseFournisseur": "3 rue des plantes", "telephoneFournisseur": "0676546546" }
    When method post
    Then status 201
    And print response

  Scenario: Test d'ajout d'un produit dans le catalogue fournisseur
    Given path 'fournisseurs/2/catalogue?idProduit=3'
    When method patch
    Then status 200
    And print response

  Scenario: Test d'ajout d'un produit dans un panier
    Given path 'utilisateurs/2/panier?idProduit=3&quantite=12'
    When method patch
    Then status 200
    And print response

  Scenario Test passer commande
    Given path '/utilisateurs/2/passerCommande'
    When method patch
    Then status 200
    And print response

  Scenario: Test de suppression d'un produit dans un panier
    Given path 'utilisateurs/2/panier/6'
    When method delete
    Then status 200'
    And print response

  Scenario: Test d'annulation d'une commande
    Given path 'commandes/2'
    When method delete
    Then status 200
    And print response

  Scenario: Test de suppression d'un fournisseur
    Given path 'fournisseurs/2'
    When method delete
    Then status 200
    And print response

  Scenario: Test d'annulation d'un produit
    Given path 'produitsMedical/4'
    When method delete
    Then status 200
    And print response

  Scenario: Test de suppression d'un catalogue fournisseur
    Given path 'fournisseurs/8/catalogue/6'
    When method delete
    Then status 200
    And print response