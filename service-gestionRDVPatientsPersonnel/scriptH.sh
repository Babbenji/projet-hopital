sudo docker stop springboot-mongodb
sudo docker stop service-gestionrdvpatientspersonnel_mongo_db_1
sudo docker rm service-gestionrdvpatientspersonnel_mongo_db_1
sudo docker rm springboot-mongodb

mvn clean install

sudo docker-compose up -d
sudo docker exec -it service-gestionrdvpatientspersonnel_mongo_db_1 mongosh

#A entrer a la main :
use rdvpatients
db.consultation.insert({id : Int32(), creneau : Object(), type : String(), motif : String(), compteRendu : String(), ordonnance : String(), confirmation : Boolean(), idMedecin : Int32(), idPatient : Int32(), dateCreation : String(), dateModification : String(), dateAnnulation : String() })

db.creneau.insert({date : String(), heure : String(), disponibilite : Boolean()})

db.patient.insert({id : Int32(), prenom : String(), nom : String(), email : String(), numSecu : String(), numTel : String(), dateNaissance : String(), genre : String(), idMedecinTraitant : Int32(), antecedents : String()})

db.medecin.insert({id : Int32(), prenom : String(), nom : String(), email : String(), listeConsultations : Array(), listePatients : Array() })
exit
