using Microsoft.IdentityModel.Tokens;
using Org.BouncyCastle.Crypto.Parameters;
using Org.BouncyCastle.OpenSsl;
using System.Security.Cryptography;

namespace micro_service.Security
{
    public static class RSAConfiguration
    {
        public static RsaSecurityKey RSASignature()
        {
            string publicKeyText = File.ReadAllText("publickey.pem");
            StringReader reader = new StringReader(publicKeyText);
            PemReader pemReader = new PemReader(reader);
            RsaKeyParameters publicKey = (RsaKeyParameters)pemReader.ReadObject();
            return new RsaSecurityKey(new RSAParameters
            {
                Modulus = publicKey.Modulus.ToByteArrayUnsigned(),
                Exponent = publicKey.Exponent.ToByteArrayUnsigned()
            });
        }



    }
}
