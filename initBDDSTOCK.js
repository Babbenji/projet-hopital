db.fournisseur.insert({_id : 10, nomFournisseur : "Solomon", adresseFournisseur : "5 rue des fleurs", telephoneFournisseur : "7635762789", catalogueFournisseur : {7 : "Eferalgan" }})

db.produitMedical.insert({_id : 14, prixProduitMedical : 5.0, nomProduitMedical  : "Pansement", descriptionProduitMedical  : "utile", stockProduitMedical : 45 })

db.produitMedical.insert({_id : 1, prixProduitMedical : 8.0, nomProduitMedical  : "Lisopaîne", descriptionProduitMedical  : "ça soigne", stockProduitMedical : 23 })

db.utilisateur.insert({_id : 13, nomUtilisateur : "Filok", prenomUtilisateur : "Julie", emailUtilisateur : "filok.julie@gmail.com", panierUtilisateur : {1 : 5, 14 : 2}})

db.commande.insert({ _id : 8, dateCommande: Date(), prixCommande : 148.0})