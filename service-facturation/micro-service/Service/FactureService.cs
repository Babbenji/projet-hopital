using micro_service.EventBus;
using micro_service.Models;
using micro_service.Repository;
using micro_service.Service.Exceptions;

namespace micro_service.Service
{
    public class FactureService : IFactureService
    {
        private readonly IFactureRepository factureRepository;

        private readonly IRabbitMQPublisher rabbitMQPublisher;
        public FactureService(IFactureRepository factureRepository, IRabbitMQPublisher rabbitMQPublisher) 
        {
            this.factureRepository = factureRepository;
            this.rabbitMQPublisher = rabbitMQPublisher;
        }
        public Facture Create(Facture entity)
        {
            return this.factureRepository.Create(entity);
        }

        public void Delete(string id)
        {
            this.factureRepository.Delete(id);
        }

        public List<Facture> GetAll()
        {
            return this.factureRepository.GetAll();
        }


        public List<Facture> GetAllFacturePatient(string username)
        {
            return (from p in this.GetAll()
                    where p.NomPatient == username
                    select p).ToList();
        }

        public Facture? GetFacturePatient(string idfacture, string username)
        {
             return (from p in this.GetAll()
                    where p.NomPatient == username && p.Id == idfacture
                    select p).ToList().SingleOrDefault();
        }

        public void SendFactureEmail(string idfacture, string username)
        {
            Facture? facture = this.GetFacturePatient(idfacture, username);
            if(facture != null)
            {
                Email email = Email.Builder()
                    .SetDestination(username)
                    .SetObjet("Facture " + idfacture)
                    .SetContenu("<html><body><p>Bonjour," +
                    "</p><p>Voici votre facture :</p>" +
                    "<p>Voici votre facture :</p>" +
                    "<p> Désignation: <br/> " + this.GetProduit(facture) + "</p>" )
                    .SetType("html")
                    .Build();
                this.rabbitMQPublisher.Publish<Email>(email, "facture.exchange", "facture.routingKey.notif");
            }
            else
            {
                throw new FactureNotFoundException("Facture n'existe pas !!!");
            }
                
        }

        private string GetProduit(Facture facture) 
        {
            string prod = "";
            if(facture.Produits != null) 
            {
                foreach (Produit produit in facture.Produits)
                {
                    prod += "Nom: " + produit.Nom + " - Marque: " + produit.Marque + " - Quantité: " + produit.Quantite + " <br/>";
                }
            }
            return prod;
        }

        public Facture GetById(string id)
        {
            return this.factureRepository.GetById(id) ?? throw new FactureNotFoundException("Facture n'existe pas !!!");

        }

        public void Update(string id, Facture entity)
        {
            this.factureRepository.Update(id, entity);
        }
    }
}
