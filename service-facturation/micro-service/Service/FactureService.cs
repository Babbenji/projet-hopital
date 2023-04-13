using micro_service.EventBus;
using micro_service.Helpers;
using micro_service.Models;
using micro_service.Models.DTO;
using micro_service.Repository;
using micro_service.Service.Exceptions;

namespace micro_service.Service
{
    public class FactureService : IFactureService
    {
        private readonly IFactureRepository factureRepository;

        private readonly IRabbitMQPublisher rabbitMQPublisher;

        private readonly PDFHelpers pdfHelpers;
        public FactureService(IFactureRepository factureRepository, IRabbitMQPublisher rabbitMQPublisher, PDFHelpers pdfHelpers) 
        {
            this.factureRepository = factureRepository;
            this.rabbitMQPublisher = rabbitMQPublisher;
            this.pdfHelpers = pdfHelpers;
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

        public Facture? GetFacturePatient(string idfacture, string username)
        {
            return (from f in this.GetAll()
                    where f.patient.email == username && f.Id == idfacture
                    select f).ToList().SingleOrDefault();
        }

        public void SendFactureEmailToPatient(string idfacture, string username, string chemain)
        {
            Facture? facture = this.GetFacturePatient(idfacture, username);
            if(facture != null)
            {


                Email email = Email.Builder()
                  .SetDestination(username)
                  .SetObjet("Facture " + idfacture)
                  .SetContenu("<html><body><p>Bonjour " + facture.patient.prenom + " " + facture.patient.nom.ToUpper() + ",</p>" +
                  "<p>Vous trouverez votre facture <a href =\""+this.pdfHelpers.generatePDF(chemain, idfacture, facture) +"\">ici</a></p>" +
                  "<body><html>")
                  .SetType("html")
                  .Build();


                this.rabbitMQPublisher.Publish<Email>(email, "email.exchange", "email.routingKey");
            }
            else
            {
                throw new FactureNotFoundException("Facture n'existe pas !!!");
            }
                
        }

        

        public Facture GetById(string id)
        {
            return this.factureRepository.GetById(id) ?? throw new FactureNotFoundException("la facture " + id + " n'existe pas dans la base de données");

        }

        public void Update(string id, Facture entity)
        {
            this.factureRepository.Update(id, entity);

            
        }

        public List<ChiffreAffaireAnnuelleModel> GetChiffreAffaireModel()
        {
            if (this.GetAll().Count > 0)
                return this.GetAll().GroupBy(f => f.DateFature.Year).Select(f => new ChiffreAffaireAnnuelleModel() { anne = f.Key, chiffreAffireAnnnuel = f.ToList().Sum(f => f.coutDuPatient) }).ToList();
            else
                throw new FactureNotFoundException("Aucun facture n'existe pas dans la base de données!!!");
        }


        public ChiffreAffaireDetailsModel GetAllChiffireAffaireByMonthOfYear(int year)
        {

            if (this.GetAll().Count > 0)
            {
                List<Facture> factureYear = (from f in this.GetAll()
                                             where f.DateFature.Year == year
                                             select f).ToList();
                if (factureYear.Count > 0)
                {
                    return new()
                    {
                        anne = year,
                        chiffreAffireAnnnuel = factureYear.Sum(c => c.coutDuPatient),
                        chiffreAffireMensuelModels = factureYear.GroupBy(f => f.DateFature.Month).Select(g => new ChiffreAffireMensuelModel { mois = g.Key, chiffreAffireMensuel = g.ToList().Sum(f => f.coutDuPatient) }).ToList()
                    };
                }
                else
                {
                    throw new FactureNotFoundException("Aucun facture n'existe pas dans la base de données!!!");
                }

            }
            else
            {
                throw new FactureNotFoundException("Aucun facture n'existe pas dans la base de données!!!");
            }
        }


        public List<Facture> GetAllFacturePatient(string username)
        {
            return (from f in this.GetAll()
                    where f.patient.email == username
                    select f).ToList();
        }

        public void ConfirmationFactureGenere(Facture facture, string secretionLogin, string chemain)
        {
            if (facture != null)
            {


                Email email = Email.Builder()
                  .SetDestination(secretionLogin)
                  .SetObjet("Confirmation de la Facture-" + facture.Id +"  ")
                  .SetContenu("<html><body><p>Bonjour,</p>" +
                  "<p> La facture "+facture.Id+ " de "+facture.patient.prenom +" "+ facture.patient.nom.ToUpper() +" a été bien générer" +
                  "<p>Vous trouverez la facture <a href =\"" + this.pdfHelpers.generatePDF(chemain, facture.Id, facture) + "\">ici</a></p>" +
                  "<body><html>")
                  .SetType("html")
                  .Build();


                this.rabbitMQPublisher.Publish<Email>(email, "facture.exchange", "facturenotif.routingKey");
            }
            else
            {
                throw new FactureNotFoundException("Facture n'existe pas !!!");
            }
        }
    }
}
