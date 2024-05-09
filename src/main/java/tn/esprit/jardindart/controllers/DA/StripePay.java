package tn.esprit.jardindart.controllers.DA;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.LineItem;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import tn.esprit.jardindart.models.DonArgent;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StripePay {

    private static final String STRIPE_API_KEY = "sk_test_51OnpVUAug4Wf2Ud6LUFzNq3Y0GmMGjeEkQ0cEq01nNnJiUPQqwBtY2gVARNkpKgnQDqC4mTrzuNW9B5UJaM65dwU00qQ1pJn5h";

    @FXML
    private Button payButton;

    @FXML
    private void handlePayment(ActionEvent event) {
        try {

            String paymentUrl = createCheckoutSession();
           // String paymentUrl = createCheckoutSession();
            openBrowser(paymentUrl);
        } catch (StripeException | URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    public String createCheckoutSession() throws StripeException {
        Stripe.apiKey = STRIPE_API_KEY;

        Map<String, Object> params = new HashMap<>();
        params.put("payment_method_types", new String[]{"card"});
        params.put("line_items", new Object[]{
                Map.of(
                        "price_data", Map.of(
                                "currency", "usd",
                                "product_data", Map.of(
                                        "name", "Donation"
                                ),
                                "unit_amount", 2000 * 100 // Amount in cents
                        ),
                        "quantity", 1
                )
        });
        params.put("mode", "payment");
        params.put("success_url", "https://yourwebsite.com/success");
        params.put("cancel_url", "https://yourwebsite.com/cancel");

        Session session = Session.create(params);
        System.out.println("Session ID: " + session.getId());
        System.out.println("Payment URL: " + session.getUrl());

        return session.getUrl();
//        stripe
    }

    public void openBrowser(String url) throws IOException, URISyntaxException {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI(url));
        } else {
            System.out.println("Opening browser not supported on this platform");
        }
    }
}
