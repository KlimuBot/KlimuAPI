package eus.klimu.security;

public class PasswordManager {

    private static final int MIN_CHARACTERS = 8;

    private PasswordManager() {}

    public static String check(String password) {
        int upCount = 0;
        int lowCount = 0;
        int numCount = 0;

        if (password.length() < MIN_CHARACTERS) {
            return "La contraseña es demasiado corta.";
        } else {
            for (int i = 0; i < password.length(); i++) {
                switch (checkType(password.charAt(i))) {
                    case "upper":
                        upCount++; break;
                    case "lower":
                        lowCount++; break;
                    case "digit":
                        numCount++; break;
                    default: break;
                }
            }
            if (upCount == 0) {
                return "La contraseña debe tener minimo una letra mayuscula";
            }
            if (lowCount == 0) {
                return "La contraseña debe tener minimo una letra minuscula";
            }
            if (numCount == 0) {
                return "La contraseña debe tener minimo un numero";
            }
            return "OK";
        }
    }

    private static String checkType(char c) {
        if (Character.isUpperCase(c)) {
            return "upper";
        }
        if (Character.isLowerCase(c)) {
            return "lower";
        }
        if (Character.isDigit(c)) {
            return "digit";
        }
        return "none";
    }
}
