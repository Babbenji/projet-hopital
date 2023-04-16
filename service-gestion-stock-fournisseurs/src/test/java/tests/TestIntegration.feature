Feature: Tests Integration gestion stock fournisseur
  Background:
    * url 'http://localhost:8084/api/v1/gestionnaire'
    * header Accept = 'application/json'
    * def signIn = call read('TestConnexionSecretaire.feature')
    * configure headers = { Authorization: '#(signIn.token)' },

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
    Given path '/produits'
    And request {"nomProduitMedical": "produit1", "prixProduitMedical": 3.0, "descriptionProduitMedical": "Réduit la fièvre et les douleurs" }
    When method post
    Then status 201
    And print response

  Scenario: Test d'ajout d'un produit déjà existant
    Given path '/produits'
    And request {"nomProduitMedical": "produit1", "prixProduitMedical": 3.0, "descriptionProduitMedical": "Réduit la fièvre et les douleurs" }
    When method post
    Then status 400
    And print response


  Scenario: Test d'ajout d'un fournisseur
    Given path '/fournisseurs'
    And request {"nomFournisseur": "fournisseur1", "adresseFournisseur": "3 rue des plantes", "telephoneFournisseur": "0676546546" }
    When method post
    Then status 201
    And print response

  Scenario: Test d'ajout d'un fournisseur deja existant
    Given path '/fournisseurs'
    And request {"nomFournisseur": "fournisseur1", "adresseFournisseur": "3 rue des plantes", "telephoneFournisseur": "0676546546" }
    When method post
    Then status 400
    And print response

  Scenario: Test d'ajout d'un produit dans le catalogue fournisseur
    Given url 'http://localhost:8084/api/v1/gestionnaire/fournisseurs/10/catalogue?idProduit=14'
    When method post
    Then status 201
    And print response

  Scenario: Test d'ajout d'un produit dans un catalogue de fournisseur inexistant
    Given url 'http://localhost:8084/api/v1/gestionnaire/fournisseurs/99/catalogue?idProduit=14'
    When method post
    Then status 404
    And print response

  Scenario: Test d'ajout d'un produit déjà dans le catalogue fournisseur
    Given url 'http://localhost:8084/api/v1/gestionnaire/fournisseurs/10/catalogue?idProduit=14'
    When method post
    Then status 400
    And print response

  Scenario: Test d'ajout d'un produit inexistant dans le catalogue fournisseur
    Given url 'http://localhost:8084/api/v1/gestionnaire/fournisseurs/10/catalogue?idProduit=99'
    When method post
    Then status 500
    And print response

  Scenario: Test d'ajout d'un produit dans un panier
    Given url 'http://localhost:8084/api/v1/gestionnaire/utilisateurs/13/panier?idProduit=14&quantite=12'
    When method post
    Then status 200
    And print response

  Scenario: Test d'ajout d'un produit dans un panier d'un utilisateur inexistant
    Given url 'http://localhost:8084/api/v1/gestionnaire/utilisateurs/99/panier?idProduit=14&quantite=12'
    When method post
    Then status 404
    And print response

  Scenario: Test d'ajout d'un produit inexistant dans un panier
    Given url 'http://localhost:8084/api/v1/gestionnaire/utilisateurs/13/panier?idProduit=99&quantite=12'
    When method post
    Then status 400
    And print response

  Scenario: Test de récupération des informations d'un fournisseur
    Given path 'fournisseurs/10'
    When method get
    Then status 200
    And print response

  Scenario: Test de récupération des informations d'un fournisseur inexistant
    Given path 'fournisseurs/99'
    When method get
    Then status 404
    And print response

  Scenario: Test de récupération d'un stock de produit
    Given path '/produitsMedical/14/stock'
    When method get
    Then status 200
    And print response

  Scenario: Test de récupération d'un stock de produit inexistant
    Given path '/produitsMedical/99/stock'
    When method get
    Then status 404
    And print response

  Scenario: Test de récupération d'un catalogue d'un fournisseur
    Given path '/fournisseurs/10/produits'
    When method get
    Then status 200
    And print response

  Scenario: Test de récupération d'un catalogue d'un fournisseur inexistant
    Given path '/fournisseurs/99/produits'
    When method get
    Then status 404
    And print response

  Scenario: Test de récupération d'une commande
    Given path '/commandes/8'
    When method get
    Then status 200
    And print response

  Scenario: Test de récupération d'une commande inexistante
    Given path '/commandes/99'
    When method get
    Then status 404
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

  Scenario: Test de récupération de tous les produits d'un panier d'un utilisateur inexistant
    Given path '/utilisateurs/99/panier'
    When method get
    Then status 404
    And print response

    Scenario: Test de modification d'une seule information d'un fournisseur
      Given path '/fournisseurs/10'
      And request { "adresseFournisseur": "3 rue des tulipes"}
      When method patch
      Then status 202
      And print response

  Scenario: Test de modification des informations d'un fournisseur
    Given path '/fournisseurs/10'
    And request {"nomFournisseur": "Fujhi", "adresseFournisseur": "3 rue des brassards", "telephoneFournisseur": "6218485465" }
    When method patch
    Then status 202
    And print response

  Scenario: Test de modification des informations d'un fournisseur inexistant
    Given path '/fournisseurs/99'
    And request {"nomFournisseur": "Fujhi", "adresseFournisseur": "3 rue des brassards", "telephoneFournisseur": "6218485465" }
    When method patch
    Then status 404
    And print response

  Scenario: Test de modification des informations d'un produit
    Given path '/produits/1'
    And request {"nomProduitMedical": "produit1", "prixProduitMedical": 3.0, "descriptionProduitMedical": "Réduit la fièvre et les douleurs" }
    When method patch
    Then status 202
    And print response

  Scenario: Test de modification d'une seule information d'un produit
    Given path '/produits/1'
    And request {"prixProduitMedical": 4.5}
    When method patch
    Then status 202
    And print response

  Scenario: Test de modification des informations d'un produit inexistant
    Given path '/produits/99'
    And request {"nomProduitMedical": "Medicaments"}
    When method patch
    Then status 404
    And print response

  Scenario: Test de suppression d'un produit dans un panier
    Given path 'utilisateurs/13/panier/1'
    When method delete
    Then status 202
    And print response

  Scenario: Test de suppression d'un produit inexistant dans un panier
    Given path 'utilisateurs/13/panier/99'
    When method delete
    Then status 400
    And print response

  Scenario: Test passer commande
    Given path '/utilisateurs/13/passerCommande'
    When method post
    Then status 200
    And print response

  Scenario: Test passer commande d'un utilisateur inexistant
    Given path '/utilisateurs/99/passerCommande'
    When method post
    Then status 400
    And print response

  Scenario: Test de recuperation des produits d'une commande
    Given path '/commandes/8/panier'
    When method get
    Then status 200
    And print response

  Scenario: Test d'annulation d'une commande
    Given path 'commandes/8'
    When method delete
    Then status 202
    And print response

  Scenario: Test d'annulation d'une commande inexistante
    Given path 'commandes/99'
    When method delete
    Then status 400
    And print response

  Scenario: Test de suppression d'un produit d'un catalogue fournisseur
    Given path 'fournisseurs/10/catalogue/7'
    When method delete
    Then status 202
    And print response

  Scenario: Test de suppression d'un produit inexistant d'un catalogue fournisseur
    Given path 'fournisseurs/99/catalogue/7'
    When method delete
    Then status 400
    And print response

  Scenario: Test de suppression d'un fournisseur
    Given path 'fournisseurs/10'
    When method delete
    Then status 202
    And print response

  Scenario: Test de suppression d'un fournisseur inexistant
    Given path 'fournisseurs/99'
    When method delete
    Then status 400
    And print response

