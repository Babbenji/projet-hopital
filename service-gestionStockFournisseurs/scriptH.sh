sudo docker stop springboot-mongodb-stockfournisseurs
sudo docker rm springboot-mongodb-stockfournisseurs
sudo docker stop service-gestionstockfournisseurs_mongo_db_1
sudo docker rm service-gestionstockfournisseurs_mongo_db_1
sudo docker system prune -a -f
sudo docker volume rm $(docker volume ls -q)
sudo docker pull mongo:latest
sudo mvn clean install
sudo docker build -t springboot-mongodb-stockfournisseurs:1.0 .
sudo docker-compose up -d






sudo docker exec -it service-gestionstockfournisseurs_mongo_db_1 mongosh

#A entrer a la main
use gestionStock

db.commande.insert({ id : Int32(), date : String(), prix : Double()})

db.fournisseur.insert({id : Int32(), nom : String(), adresse : String(), telephone : String(), catalogue : {0 : String() }})

db.utilisateur.insert({id : Int32(), nom : String(), prenom : String(), email : String(), panierProduits : {0 : Int32()}})

db.produitMedical.insert({id : Int32(), prix : Double(), nom : String(), description : String(), stock : Int32() })

exit