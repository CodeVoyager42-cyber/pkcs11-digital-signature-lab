import java.security.*;
import java.util.Enumeration;

public class SignTest {

    public static void main(String[] args) throws Exception {

        // 1. Load PKCS#11 provider
        Provider p = Security.getProvider("SunPKCS11");
        p = p.configure("pkcs11.cfg");
        Security.addProvider(p);

        System.out.println("Provider loaded: " + p.getName());

        // 2. Load KeyStore (just to trigger login/session)
        KeyStore ks = KeyStore.getInstance("PKCS11", p);
        ks.load(null, "1234".toCharArray());

        // 3. Find a private key entry
        String alias = null;
        Enumeration<String> aliases = ks.aliases();

        while (aliases.hasMoreElements()) {
            String a = aliases.nextElement();
            if (ks.isKeyEntry(a)) {
                alias = a;
                break;
            }
        }

        if (alias == null) {
            System.out.println("No private key found in PKCS11 token");
            return;
        }

        System.out.println("Using key alias: " + alias);

        // 4. Get private key
        PrivateKey privateKey = (PrivateKey) ks.getKey(alias, null);

        // 5. Create signature instance
        Signature sig = Signature.getInstance("SHA256withRSA", p);

        sig.initSign(privateKey);

        // 6. Data to sign
        byte[] data = "Hello SoftHSM Digital Signature".getBytes();

        sig.update(data);

        byte[] signature = sig.sign();

        System.out.println("Signature generated!");
        System.out.println(java.util.Base64.getEncoder().encodeToString(signature));
    }
}
