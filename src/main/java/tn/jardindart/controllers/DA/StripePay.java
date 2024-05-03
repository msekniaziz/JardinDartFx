package tn.jardindart.controllers.DA;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.model.PaymentIntent;
import tn.jardindart.models.DonArgent;
import tn.jardindart.services.DonArgentService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Desktop;
import java.util.HashMap;
import java.util.Map;

public class StripePay {

    private static final String STRIPE_API_KEY = "sk_test_51OnpVUAug4Wf2Ud6LUFzNq3Y0GmMGjeEkQ0cEq01nNnJiUPQqwBtY2gVARNkpKgnQDqC4mTrzuNW9B5UJaM65dwU00qQ1pJn5h";
    private String paymentIntentId;
    public String createCheckoutSession(int id, double montant, int idAsso) throws StripeException {
        Stripe.apiKey = STRIPE_API_KEY;

        // Montant en centimes (USD)
        int montantEnCentimes = (int) (montant * 100);

        Map<String, Object> params = new HashMap<>();
        params.put("payment_method_types", new String[]{"card"});
        params.put("line_items", new Object[]{
                Map.of(
                        "price_data", Map.of(
                                "currency", "usd",
                                "product_data", Map.of(
                                        "name", "Donation"
                                ),
                                "unit_amount", montantEnCentimes
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
        paymentIntentId = session.getPaymentIntent();
        // Gérer les événements de paiement réussis
        //handlePaymentEvent(session.getPaymentIntent(),montantEnCentimes,id,idAsso);

        // Retourner l'URL de paiement seulement si la session est créée avec succès
        return session.getUrl();
    }
    public String getPaymentIntentId() {
        return paymentIntentId;
    }
    public Boolean handlePaymentEvent(String paymentIntentId, int montantEnCentimes, int id, int idA) {
        // Vérifier si le paiement a été effectué avec succès en fonction de l'ID du paiement
        try {
            // Attendre quelques secondes pour permettre le traitement du paiement
            Thread.sleep(20000); // Attendre 20 secondes (20000 millisecondes)

            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            System.out.println("Payment Intent Status: " + paymentIntent.getStatus());

            if (paymentIntent.getStatus().equals("succeeded")) {
                // Le paiement a été effectué avec succès

                // Mettre à jour la base de données ou effectuer d'autres actions nécessaires
                System.out.println("Payment succeeded!");

                return true;
            } else {
                // Le paiement a échoué ou est en attente

                // Effectuer des actions en conséquence, si nécessaire
                System.out.println("Payment failed or is pending!");
            }
        } catch (StripeException | InterruptedException e) {
            e.printStackTrace();

            // Gérer l'erreur
        }
        return false;
    }


   /* public Boolean handlePaymentEvent(String paymentIntentId,int montantEnCentimes,int id,int idA) {
        // Vérifier si le paiement a été effectué avec succès en fonction de l'ID du paiement
        try {
            // Attendre quelques secondes pour permettre le traitement du paiement
            Thread.sleep(20000); // Attendre 2 secondes (2000 millisecondes)

            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            AddDA cont=new AddDA(paymentIntentId);
            cont.pid(paymentIntentId);

            System.out.println("ahla  "+paymentIntent.getStatus());

            if (paymentIntent.getStatus().equals("succeeded")) {
                // Mettre à jour la base de données si le paiement est réussi

                String donId = paymentIntent.getMetadata().get("don_id");

               *//* DonArgentService donArgentService = new DonArgentService();
                DonArgent newDon = new DonArgent(id,montantEnCentimes, idA); // Remplacer 27 par l'ID de l'utilisateur actuellement connecté
                donArgentService.ajouter(newDon);*//*
                return true;
            }
        } catch (StripeException | InterruptedException e) {
            e.printStackTrace();

            // Gérer l'erreur
        }
        return false;

    }*/

    public void openBrowser(String url) throws IOException, URISyntaxException {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI(url));
        } else {
            System.out.println("Opening browser not supported on this platform");
        }
//        handlePaymentEvent(session.getPaymentIntent(),montantEnCentimes,id,idAsso);

    }
}
