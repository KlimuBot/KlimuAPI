package eus.klimu.security;

/**
 * Manage the AppUser passwords, making sure they are secure.
 */
public class PasswordManager {

    /**
     * The minimum amount of characters a password must have.
     */
    private static final int MIN_CHARACTERS = 8;

    private PasswordManager() {}

    /**
     * Check if a password is strong enough.
     * @param password The password that is being checked.
     * @return OK if the password is strong, or a message explaining why it's not strong enough.
     */
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

    /**
     * Check the type of character.
     * @param c The character that is being checked.
     * @return The character type.
     */
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
