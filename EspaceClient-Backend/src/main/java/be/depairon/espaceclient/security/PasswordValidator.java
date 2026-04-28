package be.depairon.espaceclient.security;

import java.util.ArrayList;
import java.util.List;

public class PasswordValidator {

    private static final int MIN_LENGTH = 8;

    /* Vérif du mdp */
    public static List<String> validate(String password){
        List<String> errors = new ArrayList<>();

        if(password == null || password.length() < MIN_LENGTH){
            errors.add("Le mot de passe doit contenir au moins " + MIN_LENGTH + " caractères");
        }
        if(password == null || !password.matches(".*[A-Z].*")){
            errors.add("Au moins une majuscule requis");
        }
        if(password == null || !password.matches(".*[0-9].*")){
            errors.add("Au moins un chiffre requis");
        }
        if(password == null || !password.matches(".*[^A-Za-z0-9].*")){
            errors.add("Au moins un caractère spécial requis");
        }

        return errors;
    }

    public static boolean isValid(String password){
        return validate(password).isEmpty();
    }
}
