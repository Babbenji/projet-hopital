using micro_service.ConsulConfig;
using micro_service.Models;
using Microsoft.Extensions.Options;
using SelectPdf;
using System.Text;

namespace micro_service.Helpers
{
    public class PDFHelpers
    {

        private readonly HyperLinKHelpers hyperLink;

       public PDFHelpers(IOptions<HyperLinKHelpers> hyperLink)
        {
            this.hyperLink = hyperLink.Value;
        }
        private string GenerateFactureHTML(Facture facture)
        {
            string css = "<style>body{margin-top:20px;background-color: #f7f7ff;}#invoice {padding: 0px;}.invoice {position: relative;background-color: #FFF;min-height: 680px;padding: 15px}.invoice header {padding: 10px 0;margin-bottom: 20px;border-bottom: 1px solid #0d6efd}.invoice .company-details {text-align: right}.invoice .company-details .name {margin-top: 0;margin-bottom: 0}.invoice .contacts {margin-bottom: 20px}.invoice .invoice-to {text-align: left}.invoice .invoice-to .to {margin-top: 0;margin-bottom: 0}.invoice .invoice-details {text-align: right}.invoice .invoice-details .invoice-id {margin-top: 0;color: #0d6efd}.invoice main {padding-bottom: 50px}.invoice main .thanks {margin-top: -100px;font-size: 2em;margin-bottom: 50px}.invoice main .notices {padding-left: 6px;border-left: 6px solid #0d6efd;background: #e7f2ff;padding: 10px;}.invoice main .notices .notice {font-size: 1.2em}.invoice table {width: 100%;border-collapse: collapse;border-spacing: 0;margin-bottom: 20px}.invoice table td,.invoice table th {padding: 15px;background: #eee;border-bottom: 1px solid #fff}.invoice table th {white-space: nowrap;font-weight: 400;font-size: 16px}.invoice table td h3 {margin: 0;font-weight: 400;color: #0d6efd;font-size: 1.2em}.invoice table .qty,.invoice table .total,.invoice table .unit {text-align: right;font-size: 1.2em}.invoice table .no {color: #fff;font-size: 1.6em;background: #0d6efd}.invoice table .unit {background: #ddd}.invoice table .total {background: #0d6efd;color: #fff}.invoice table tbody tr:last-child td {border: none}.invoice table tfoot td {background: 0 0;border-bottom: none;white-space: nowrap;text-align: right;padding: 10px 20px;font-size: 1.2em;border-top: 1px solid #aaa}.invoice table tfoot tr:first-child td {border-top: none}.card {position: relative;display: flex;flex-direction: column;min-width: 0;word-wrap: break-word;background-color: #fff;background-clip: border-box;border: 0px solid rgba(0, 0, 0, 0);border-radius: .25rem;margin-bottom: 1.5rem;box-shadow: 0 2px 6px 0 rgb(218 218 253 / 65%), 0 2px 6px 0 rgb(206 206 238 / 54%);}.invoice table tfoot tr:last-child td {color: #0d6efd;font-size: 1.4em;border-top: 1px solid #0d6efd}.invoice table tfoot tr td:first-child {border: none}.invoice footer {width: 100%;text-align: center;color: #777;border-top: 1px solid #aaa;padding: 8px 0}@media print {.invoice {font-size: 11px !important;overflow: hidden !important}.invoice footer {position: absolute;bottom: 10px;page-break-after: always}.invoice>div:last-child {page-break-before: always}}.invoice main .notices {padding-left: 6px;border-left: 6px solid #0d6efd;background: #e7f2ff;padding: 10px;} </style>";

            StringBuilder sb = new StringBuilder();
            sb.Append("<!DOCTYPE html><html>");
            sb.Append(css);
            sb.Append("<body><div class=\"container\"><div class=\"card\"><div class=\"card-body\"><div id=\"invoice\"><div class=\"invoice overflow-auto\"><div style=\"min-width: 600px\">");
            sb.Append("<main><div class=\"row contacts\"><div class=\"col invoice-to\">");
            sb.Append("<h2 class=\"to\">" + facture.patient.prenom + " " + facture.patient.nom.ToUpper() + "</h2>");
            sb.Append("<div class=\"email\"><a href=\"mailto:" + facture.patient.email + "\">" + facture.patient.email + "</a></div></div>");
            sb.Append("<div class=\"col invoice-details\"><h1 class=\"invoice-id\">FACTURE " + facture.Id + "</h1><div class=\"date\">Date: " + facture.DateFature.ToShortDateString() + "</div></div></div>");
            sb.Append("<table><thead>");
            sb.Append("<tr><th>No</th><th class=\"text-left\">DESCRIPTION</th><th class=\"text-right\">QUANTITÉ</th></tr>");
            sb.Append("</thead><tbody>");
            sb.Append(GetProduit(facture));
            sb.Append("</tbody><tfoot>");
            sb.Append("<tr><td colspan=\"1\"></td><td colspan=\"1\">QUANTITÉ TOTAL</td><td>" + QuantiteTotal(facture) + "</td></tr>");
            sb.Append("<tr><td colspan=\"1\"></td><td colspan=\"1\">MONTANT</td><td>" + facture.coutDuPatient + " €</td></tr>");
            sb.Append("</tfoot></table>");
            sb.Append("</main><footer>Hopital de la source</footer></div>");
            sb.Append("<div></div></div></div></div></div></div></body></html>");

            return sb.ToString();


        }


        private string GetProduit(Facture facture)
        {
            string prod = "";
            int cpt = 1;
            if (facture.listeProduits != null)
            {
                foreach (KeyValuePair<string, int> produit in facture.listeProduits)
                {
                    prod += "<tr><td class=\"no\">" + cpt + "</td><td class=\"text-left\"><h3>" + produit.Key + "</h3><td class=\"unit\">" + produit.Value + "</td></tr>";
                    cpt++;
                }
            }
            return prod;
        }


        private int QuantiteTotal(Facture facture)
        {
            if (facture.listeProduits != null)
                return facture.listeProduits.Values.Sum();
            else
                return 0;
        }


        public string generatePDF(string chemain, string idfacture, Facture facture)
        {
            HtmlToPdf converter = new HtmlToPdf();


            converter.Options.PdfPageSize = PdfPageSize.A4;
            converter.Options.PdfPageOrientation = PdfPageOrientation.Portrait;

            // add the HTML content to the converter
            PdfDocument pdfDocument = converter.ConvertHtmlString(this.GenerateFactureHTML(facture));



            string fileName = "Facture-" + idfacture + ".pdf";
            string filePath = Path.Combine(chemain, fileName);

            pdfDocument.Save(filePath);
            return "http://" + hyperLink.Host + ":" + hyperLink.Port + "/api/v1/facture/pdf?facture=" + idfacture;
        }
    }
}
