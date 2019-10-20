package com.inflatemymind.utility;

import com.inflatemymind.models.User;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import javax.json.Json;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MailgunVerification {

    private static String privateApiKey;

    private final static String DOMAIN = "sandboxf431de81278647a39a9278212ea21f92.mailgun.org";

    private final static String LINK_TO_WEBSITE = "localhost";

    static {
        Scanner scanner = null;
        String pathToKey = "src/main/resources/mailgun_private_api_key";
        try {
            scanner = new Scanner(new File(pathToKey));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        privateApiKey = scanner.nextLine();
    }

    public static void main(String[] args) {
        User u = new User();
        u.setEmail("dimakid123@gmail.com");
        verifyEmail(u);
    }

    public static void verifyEmail(User user) {
        JsonNode j = Unirest.post("https://api.mailgun.net/v3/" + DOMAIN + "/messages").basicAuth("api", privateApiKey)
                .queryString("from", "InflateMyMind <hivemind@inflate-my-mind.com>")
                .queryString("to", user.getEmail())
                .queryString("subject", "Hey, verify your account")
                .queryString("text", "verification link: " + LINK_TO_WEBSITE + "/login")
                .asJson().getBody();
    }

}


