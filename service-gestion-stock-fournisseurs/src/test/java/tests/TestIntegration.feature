Feature: Tests Integration gestion stock fournisseur
  Background:
  * url 'http://localhost:8084/api/v1/gestionnaire'
  * header Accept = 'application/json'

  Scenario: Test d'ajout d'un utilisateur
    Given path '/utilisateurs'
    And request {"nomUtilisateur": "nom1", "prenomUtilisateur": "prenom1", "emailUtilisateur": "nom1.prenom1@etu.univ-orleans.fr" }
    When method post
    Then status 201
    And print response

  Scenario: Test d'ajout d'un utilisateur déjà existant
    Given path '/utilisateurs'
    And request {"nomUtilisateur": "nom1", "prenomUtilisateur": "prenom1", "emailUtilisateur": "nom1.prenom1@etu.univ-orleans.fr" }
    When method post
    Then status 400
    And print response

  Scenario: Test d'ajout d'un produit
    Given path '/produitsMedical'
    And request {"nomProduitMedical": "produit1", "prixProduitMedical": 3.0, "descriptionProduitMedical": "Réduit la fièvre et les douleurs" }
    When method post
    Then status 201
    And print response

  Scenario: Test d'ajout d'un fournisseur
    Given path '/fournisseurs'
    And request {"nomFournisseur": "fournisseur1", "adresseFournisseur": "3 rue des plantes", "telephoneFournisseur": "0676546546" }
    When method post
    Then status 201
    And print response

  Scenario: Test d'ajout d'un produit dans le catalogue fournisseur
    Given url "http://localhost:8084/api/v1/gestionnaire/fournisseurs/10/catalogue?idProduit=14"
    When method post
    Then status 201
    And print response

  Scenario: Test d'ajout d'un produit dans un panier
    Given url 'http://localhost:8084/api/v1/gestionnaire/utilisateurs/13/panier?idProduit=14&quantite=12'
    When method post
    Then status 200
    And print response


  Scenario: Test passer commande
    Given path '/utilisateurs/13/passerCommande'
    When method post
    Then status 200
    And print response


  Scenario: Test de récupération des informations d'un fournisseur
    Given path 'fournisseurs/10'
    When method get
    Then status 200
    And print response

  Scenario: Test de récupération d'un stock de produit
    Given path '/produitsMedical/14/stock'
    When method get
    Then status 200
    And print response

  Scenario: Test de récupération d'un catalogue d'un fournisseur
    Given path '/fournisseurs/10/catalogue'
    When method get
    Then status 200
    And print response

  Scenario: Test de récupération d'une commande
    Given path '/commandes/8'
    When method get
    Then status 200
    And print response

  Scenario: Test de récupération de toutes les commandes
    Given path '/commandes'
    When method get
    Then status 200
    And print response

  Scenario: Test de récupération de tous les produits d'un panier d'un utilisateur
    Given path '/utilisateurs/13/panier'
    When method get
    Then status 200
    And print response

  Scenario: Test de suppression d'un produit dans un panier
    Given path 'utilisateurs/18/panier/1'
    When method delete
    Then status 200'
    And print response

  Scenario: Test d'annulation d'une commande
    Given path 'commandes/8'
    When method delete
    Then status 200
    And print response

  Scenario: Test de suppression d'un produit d'un catalogue fournisseur
    Given path 'fournisseurs/10/produits/0'
    When method delete
    Then status 200
    And print response

  Scenario: Test de suppression d'un fournisseur
    Given path 'fournisseurs/10'
    When method delete
    Then status 200
    And print response

