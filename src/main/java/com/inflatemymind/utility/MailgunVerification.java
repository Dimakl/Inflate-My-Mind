package com.inflatemymind.utility;

import com.inflatemymind.models.Email;
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

    public static void verifyEmail(User user, Email email) {
        String code = email.getId() * 587 + 2953 + "";
        JsonNode j = Unirest.post("https://api.mailgun.net/v3/" + DOMAIN + "/messages").basicAuth("api", privateApiKey)
                .queryString("from", "InflateMyMind <hivemind@inflate-my-mind.com>")
                .queryString("to", user.getEmail())
                .queryString("subject", "Verify your account, please")
                .queryString("text", user.getFirstName() + " " + user.getSecondName() + ", your verification link is: " +
                        "http://127.0.0.1:8080/verification?email=" + user.getEmail() + "&code=" + code)
                .asJson().getBody();
    }

}


