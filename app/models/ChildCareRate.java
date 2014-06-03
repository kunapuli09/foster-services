package models;

import com.google.code.morphia.annotations.Embedded;

/**
 * Care Service Rate
 */
@Embedded
public class ChildCareRate {
    public Float childRate;
    public Float adminRate;
    public int numberOfDays;


    private Float combinedTotal;
    private Float adminTotal;
    private Float providerTotal;
    private Float grandTotal;

    //TODO smart domain object

    public Float calculateGrandTotal(){
        /*if(numberOfDays == 0){
            throw IllegalArgumentException();
        }*/
        return numberOfDays * childRate + numberOfDays * adminRate;
    }

}
