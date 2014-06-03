package controllers;

/*
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Plan;
*/

import org.bson.types.ObjectId;
import play.mvc.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pay extends Controller {

    public static void checkout(String authenticityToken, String email, String userId, String planId) {
        /*Float amount = getPriceByPlan(Integer.valueOf(planId));
        Float chargedAmount = 0f;
        if(null != authenticityToken){
            String token = session.get("token");
            if(token != null && token.equals(authenticityToken)){
                flash.error("You have apparently resubmitted the form. Please do not do that.");
                session.clear();
            }else{
                session.put("token", authenticityToken);
            }
            Stripe.apiKey = "sk_test_UiF9LJMxc8THUSvCOLLytaBz";
            //TODO we need to get the massage payment amount from previous page
            Plan plan = new Plan();
            Map<String, Object> chargeParams = new HashMap<String, Object>();
            if(amount == null){
                chargedAmount = 400f;
                chargeParams.put("amount", chargedAmount);
            }else{
                chargedAmount = amount;
            }
            chargeParams.put("currency", "usd");
            chargeParams.put("card", authenticityToken);
            try {
                Charge charge = Charge.create(chargeParams);
                Boolean paid = charge.getPaid();
                if(paid == true){
                    //TODO save the token to database and show success message
                    *//*Schedule schedule = new Schedule(null,null,null,null,null,null,null);
                    Massage massage = new Massage(schedule, new ObjectId(userId),new ObjectId(therapistId), ServiceType.DEEP_TISSUE_MASSAGE,Float.valueOf(chargedAmount), authenticityToken);
                    ObjectId massageId = massage.save().getId();
                    session.put("massageId", massageId);*//*
                    Application.index();
                }
            } catch (AuthenticationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InvalidRequestException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (APIConnectionException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (CardException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (APIException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
           Application.index();
        }else{
            session.clear();
            render();
        }*/
        render();
    }

    public static Float getPriceByPlan(int planId){
        switch(planId){
            case 1:
              return new Float(0.00f);
            case 2:
                return new Float(0.00f);
            case 3:
                return new Float(0.00f);
            case 4:
                return new Float(0.00f);
            default:
                return new Float(0.00f);
        }

    }
}