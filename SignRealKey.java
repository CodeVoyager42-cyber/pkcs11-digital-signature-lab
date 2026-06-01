import java.security.*;
import java.util.*;

public class SignRealKey {

    public static void main(String[] args) throws Exception {

        Provider p = Security.getProvider("SunPKCS11")
                .configure("pkcs11.cfg");

        Security.addProvider(p);

        System.out.println("Provider loaded: " + p.getName());

        KeyStore ks = KeyStore.getInstance("PKCS11", p);
        ks.load(null, "1234".toCharArray());

        System.out.println("Aliases in token:");

        Enumeration<String> aliases = ks.aliases();
        while (aliases.hasMoreElements()) {
            String a = aliases.nextElement();
            System.out.println(" - " + a);
        }

        System.out.println("\nTrying signing...");

        Signature sig = Signature.getInstance("SHA256withRSA", p);

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", p);
        kpg.initialize(2048);

        KeyPair kp = kpg.generateKeyPair();

        sig.initSign(kp.getPrivate());
        sig.update("HELLO HSM".getBytes());

        byte[] result = sig.sign();

        System.out.println(java.util.Base64.getEncoder().encodeToString(result));
    }
}
