namespace micro_service.Models
{
    public class Email
    {
        public string? destinataire { get; set; }

        public string? objet { get; set; }

        public string? contenu { get; set; }

        public string? type { get; set; }


       

        public static EmailBuilder Builder()
        {
            return new EmailBuilder();
        }

        public class EmailBuilder
        {
            private Email email;
            public EmailBuilder() 
            {
                this.email = new Email();
            }
            

            public EmailBuilder SetDestination(string destinataire)
            {
                this.email.destinataire = destinataire;
                return this;
            }

            public EmailBuilder SetObjet(string objet)
            {
                this.email.objet = objet;
                return this;
            }

            public EmailBuilder SetContenu(string contenu)
            {
                this.email.contenu = contenu;
                return this;
            }

            public EmailBuilder SetType(string type) 
            {
                this.email.type = type;
                return this;
            }

            public Email Build()
            {
                return this.email;
            }
        }
    }
}
