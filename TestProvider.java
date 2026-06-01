import java.security.Provider;
import java.security.Security;

public class TestProvider {
    public static void main(String[] args) throws Exception {

        Provider p = Security.getProvider("SunPKCS11");

        System.out.println("Base provider: " + p);

        Provider configured = p.configure("pkcs11.cfg");

        System.out.println("Configured provider: " + configured);

        Security.addProvider(configured);

        System.out.println("Added successfully.");
    }
}
