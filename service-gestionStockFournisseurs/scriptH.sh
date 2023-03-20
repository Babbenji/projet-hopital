docker stop springboot-mongodb-stockfournisseurs
docker rm springboot-mongodb-stockfournisseurs
docker stop service-gestionstockfournisseurs_mongo_db_1
docker rm service-gestionstockfournisseurs_mongo_db_1
#docker system prune -a -f
#docker volume rm $(docker volume ls -q)
#docker pull mongo:latest
mvn clean install
docker build -t springboot-mongodb-stockfournisseurs:1.0 .
docker-compose up


#docker exec -it service-gestionstockfournisseurs_mongo_db_1 mongosh

#A entrer a la main
#use gestionStock
#
#db.commande.insert({ id : Int32(), date : String(), prix : Double()})
#
#db.fournisseur.insert({id : Int32(), nom : String(), adresse : String(), telephone : String(), catalogue : {0 : String() }})
#
#db.utilisateur.insert({id : Int32(), nom : String(), prenom : String(), email : String(), panierProduits : {0 : Int32()}})
#
#db.produitMedical.insert({id : Int32(), prix : Double(), nom : String(), description : String(), stock : Int32() })
#
#exit