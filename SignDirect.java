import java.security.*;

public class SignDirect {

    public static void main(String[] args) throws Exception {

        Provider p = Security.getProvider("SunPKCS11")
                .configure("pkcs11.cfg");

        Security.addProvider(p);

        System.out.println("Provider loaded: " + p.getName());

        // IMPORTANT: force KeyStore login (this logs into PKCS#11 session)
        KeyStore ks = KeyStore.getInstance("PKCS11", p);
        ks.load(null, "1234".toCharArray());

        System.out.println("Login successful");

        // Now PKCS11 session is authenticated

        Signature sig = Signature.getInstance("SHA256withRSA", p);

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", p);

        kpg.initialize(2048);

        KeyPair kp = kpg.generateKeyPair();

        sig.initSign(kp.getPrivate());

        sig.update("Hello SoftHSM".getBytes());

        byte[] signature = sig.sign();

        System.out.println(java.util.Base64.getEncoder().encodeToString(signature));
    }
}
