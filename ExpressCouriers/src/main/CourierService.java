package main;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by idnasa on 12-08-2014.
 */
public class CourierService {
    private Map<String,Double> destinations = new HashMap<String,Double>();
    private Map<String, Double> totalParcelWeightByCity = new HashMap<String,Double>();
    private Map<String, Integer> totalNumberOfParcelsByCity = new HashMap<String,Integer>();

    public CourierService(){
        populateDestination();
    }

    public Map<String,Double> getDestinations(){
        return destinations;
    }
    public boolean checkParcelLimit(String destinationCity, int numberOfParcels, double parcel1Weight, double parcel2Weight, double parcel3Weight) {
        return checkParcelLimitByParcelNumber(destinationCity,numberOfParcels) &&
               checkParcelLimitByParcelWeight(destinationCity,parcel1Weight,parcel2Weight,parcel3Weight);
    }

    private boolean checkParcelLimitByParcelWeight(String destinationCity, double parcel1Weight, double parcel2Weight, double parcel3Weight) {
        if(parcel1Weight > Constants.MAX_ALLOWED_WEIGHT_PER_PARCEL || parcel2Weight > Constants.MAX_ALLOWED_WEIGHT_PER_PARCEL || parcel3Weight > Constants.MAX_ALLOWED_WEIGHT_PER_PARCEL) return false;

        double totalWeight = parcel1Weight + parcel2Weight + parcel3Weight;
        if(totalParcelWeightByCity.containsKey(destinationCity)) {
            Double currentWeight = totalParcelWeightByCity.get(destinationCity);
            double value = currentWeight + totalWeight;
            if(value < Constants.TOTAL_SHIPMENT_WEIGHT_IN_A_DAY) {
                totalParcelWeightByCity.put(destinationCity, value);
                return true;
            }
            else
            {
                return false;
            }
        }
        totalParcelWeightByCity.put(destinationCity,totalWeight);
        return true;
    }

    private boolean checkParcelLimitByParcelNumber(String destinationCity, int numberOfParcels) {
        if(totalNumberOfParcelsByCity.containsKey(destinationCity)){
            Integer totalParcelsByCity = totalNumberOfParcelsByCity.get(destinationCity);
            totalParcelsByCity = totalParcelsByCity + numberOfParcels;
            if(totalParcelsByCity > Constants.NO_OF_PARCELS_IN_A_DAY){
                return false;
            }
            else
            {
                return true;
            }
        }
        totalNumberOfParcelsByCity.put(destinationCity,numberOfParcels);
        return true;
    }

    public double calculateCharge(String destinationCity, int numberOfParcels, double parcel1Weight, double parcel2Weight, double parcel3Weight, int time, boolean fragile) {

        boolean extraCharge=false;
        if(checkParcelLimit(destinationCity,numberOfParcels,parcel1Weight,parcel2Weight,parcel3Weight)) {
            if (totalParcelWeightByCity.get(destinationCity) > 5000 || totalNumberOfParcelsByCity.get(destinationCity) > 50 || time > 1500) {
                extraCharge = true;
            }
            return calculateChargePerParcel(parcel1Weight, destinationCity, extraCharge) + calculateChargePerParcel(parcel2Weight, destinationCity, extraCharge) + calculateChargePerParcel(parcel3Weight, destinationCity, extraCharge);
        }
        return 0;
    }

    private double calculateChargePerParcel(double parcelWeight,String destinationCity, boolean extraCharge) {
        double unit = parcelWeight/100;
        if(extraCharge)
            return Math.ceil(unit) * destinations.get(destinationCity)*1.2;
        else
            return Math.ceil(unit) * destinations.get(destinationCity);
    }

    private void populateDestination() {
        destinations.put("Banglore", (double) 30);
        destinations.put("Delhi", (double) 35);
        destinations.put("Goa", (double) 40);
        destinations.put("J & K", (double) 45);
        destinations.put("Chennai", (double) 50);
        destinations.put("Punjab", (double) 55);
        destinations.put("Chandigadh", (double) 60);
        destinations.put("Kolkata", (double) 65);
        destinations.put("Hyderabad", (double) 70);
        destinations.put("Gandhinagar", (double) 75);
    }
}
