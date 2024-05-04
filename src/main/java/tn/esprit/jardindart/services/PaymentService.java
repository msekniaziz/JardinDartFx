package tn.esprit.jardindart.services;
import com.stripe.*;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import com.stripe.model.Source;
import com.stripe.param.ChargeCreateParams;
import tn.esprit.jardindart.models.CardDetails;


import java.util.HashMap;
import java.util.Map;

public class PaymentService {
    private static final String STRIPE_API_KEY = "sk_test_51OnpVUAug4Wf2Ud6LUFzNq3Y0GmMGjeEkQ0cEq01nNnJiUPQqwBtY2gVARNkpKgnQDqC4mTrzuNW9B5UJaM65dwU00qQ1pJn5h";
   // private Stripe stripe;
    public PaymentService() {
        Stripe.apiKey = STRIPE_API_KEY;
    }


    public String chargeCard(CardDetails details) throws StripeException {

        Map<String, Object> params = new HashMap<>();
        params.put("payment_method_types", new String[]{"card"});

        Map<String, Object> card = new HashMap<>();
        card.put("number", details.getNumber());
        card.put("exp_month", details.getExpMonth());
        card.put("exp_year", details.getExpYear());
        card.put("cvc", details.getCvc());

        Map<String, Object> item = new HashMap<>();
        item.put("price_data", Map.of(
                "currency", "usd",
                "product_data", Map.of("name", "Donation"),
                "unit_amount", 2000 * 100
        ));
        item.put("quantity", 1);

        params.put("line_items", new Object[]{item});

        Charge charge = Charge.create(params);

        return charge.getId();

    }


//    public void refundCharge(String chargeId) throws StripeException {
//
//        Refund.create(
//                chargeId,
//                null
//        );
//
//    }



}
