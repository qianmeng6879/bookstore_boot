package top.mxzero.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Base64;

public class PasswordUtils {
    private static final String SALT = "mxzero.dev";

    public static boolean checkPassword(String password, String securityPassword){
        if(password == null || "".equals(password)){
            return false;
        }

        if(securityPassword == null || securityPassword.length() != 32){
            return false;
        }

        return securityPassword.equals(getSecurityPassword(password));
    }

    public static String getSecurityPassword(String password) {
        String data = password;
        for (int i = 0; i < 3; i++) {
            data = new String(Base64.getEncoder().encode((SALT + data + SALT).getBytes()));
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(SALT.getBytes());
            md5.update(password.getBytes());
            md5.update(data.getBytes());
            md5.update(SALT.getBytes());
            return new BigInteger(1, md5.digest()).toString(16);
        }catch (Exception e){
            System.err.println(e.getMessage());;
            return password;
        }

    }
}
