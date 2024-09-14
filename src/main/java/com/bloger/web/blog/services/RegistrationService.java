package com.bloger.web.blog.services;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

@Data
@Service
public class RegistrationService {

    public List<JsonObject> credentialsList = new ArrayList<>();

    public void credentialObj(String login, String password) {
        JsonObject credentials = new JsonObject();
        credentials.addProperty("login", login);
        credentials.addProperty("password", password);
        credentials.addProperty("isAuthentication", false);

        credentialsList.add(credentials);
        System.out.println(getAllCredentials());
    }

    public List<JsonObject> getAllCredentials() {
        return credentialsList;
    }

    public void setUnActiveFlag() {
        if (!credentialsList.isEmpty()) {
            for (var obj : credentialsList) {
                obj.addProperty("isAuthentication", false);
            }
        }
    }

    public boolean isAuthorized(String login, String password) {
        for (var obj : credentialsList) {
            var objLogin = obj.getAsJsonObject().get("login").getAsString();
            var objPassword = obj.getAsJsonObject().get("password").getAsString();
            if (objLogin.equals(login) && objPassword.equals(password)) {
                setUnActiveFlag();
                obj.getAsJsonObject().addProperty("isAuthentication", true);
                return true;
            }
        }
        return false;
    }
}