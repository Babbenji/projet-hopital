using Microsoft.IdentityModel.Tokens;
using Org.BouncyCastle.Crypto.Parameters;
using Org.BouncyCastle.OpenSsl;
using System.Security.Cryptography;

namespace micro_service.Security
{
    public class RSAConfiguration
    {
        
        public static RsaSecurityKey RSASignature(string publicKeyString)
        {
            StringReader reader = new StringReader(publicKeyString);
            PemReader pemReader = new PemReader(reader);
            RsaKeyParameters publicKey = (RsaKeyParameters)pemReader.ReadObject();
            return new RsaSecurityKey(new RSAParameters
            {
                Modulus = publicKey.Modulus.ToByteArrayUnsigned(),
                Exponent = publicKey.Exponent.ToByteArrayUnsigned()
            });
        }
    }

    public static class RsaKey
    {
        public static string publicKey = "";
    } 
}
