package eus.klimu.security;

public class PasswordManager {

    private static final int MIN_CHARACTERS = 8;
    private static final int MAX_CHARACTERS = 32;

    public static String check(String password) {
        int upCount = 0, lowCount = 0, numCount = 0;

        if (password.length() < MIN_CHARACTERS) {
            return "La contraseña es demasiado corta.";
        } else if (password.length() > MAX_CHARACTERS) {
            return "La contraseña es muy larga";
        } else {
            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if (Character.isUpperCase(c)) {
                    upCount++;
                } else if (Character.isLowerCase(c)) {
                    lowCount++;
                } else if (Character.isDigit(c)) {
                    numCount++;
                }
            }
            if (upCount <= 0) {
                return "La contraseña debe tener minimo una letra mayuscula";
            } else if (lowCount <= 0) {
                return "La contraseña debe tener minimo una letra minuscula";
            } else if (numCount <= 0) {
                return "La contraseña debe tener minimo un numero";
            } else {
                return "OK";
            }
        }
    }

}
