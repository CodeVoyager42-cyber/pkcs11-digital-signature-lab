import java.security.*;
import java.security.cert.X509Certificate;
import java.util.*;

public class UseRealHSMKey {

    public static void main(String[] args) throws Exception {

        // Load PKCS#11 provider
        Provider p = Security.getProvider("SunPKCS11")
                .configure("pkcs11.cfg");

        Security.addProvider(p);

        System.out.println("Provider loaded: " + p.getName());

        // Login to token
        KeyStore ks = KeyStore.getInstance("PKCS11", p);
        ks.load(null, "1234".toCharArray());

        System.out.println("Searching for HSM private key...");

        String alias = null;

        Enumeration<String> aliases = ks.aliases();

        while (aliases.hasMoreElements()) {

            String a = aliases.nextElement();
            System.out.println("Found entry: " + a);

            if (ks.isKeyEntry(a)) {
                alias = a;
                break;
            }
        }

        if (alias == null) {
            System.out.println("❌ No KeyStore alias found (normal for SoftHSM)");
            System.out.println("➡ Switching to direct PKCS#11 signing mode");

            // fallback test using provider directly
            Signature sig = Signature.getInstance("SHA256withRSA", p);

            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", p);
            kpg.initialize(2048);

            KeyPair kp = kpg.generateKeyPair();

            sig.initSign(kp.getPrivate());
            sig.update("REAL HSM SIGN".getBytes());

            byte[] out = sig.sign();

            System.out.println(Base64.getEncoder().encodeToString(out));
            return;
        }

        PrivateKey key = (PrivateKey) ks.getKey(alias, null);

        Signature sig = Signature.getInstance("SHA256withRSA", p);
        sig.initSign(key);

        sig.update("REAL KEY SIGN".getBytes());

        byte[] out = sig.sign();

        System.out.println(Base64.getEncoder().encodeToString(out));
    }
}
