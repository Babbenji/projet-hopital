using Consul;
using Microsoft.IdentityModel.Tokens;
using Org.BouncyCastle.Crypto.Parameters;
using Org.BouncyCastle.OpenSsl;
using System.Net;
using System.Security.Cryptography;
using System.Text;

namespace micro_service.Security
{
    public static class RSAConfiguration
    {
        
        public static RsaSecurityKey RSASignature()
        {
            StringReader reader = new StringReader(RsaKey.publicKey);
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
