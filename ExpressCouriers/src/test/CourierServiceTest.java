package test;

import main.CourierService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Map;

/**
 * Created by idnasa on 12-08-2014.
 */
public class CourierServiceTest {

    Map<String, Double> populatedDestination;
    @Before
    public void setup(){
        CourierService courierService = new CourierService();
        populatedDestination = courierService.getDestinations();
    }

    @Test
    public void shouldAcceptValidCity(){
        String[] input = "XYZ:PAR1:100:PAR2:150:PAR3:360:12PM:true".split(":");
           Assert.assertTrue(populatedDestination.get(input[0]) == null);
    }

    @Test
    public void shouldAcceptTheParcelWithinLimit(){
        String[] input = "Banglore:3:100:150:360:12PM:true".split(":");
        String destinationCity = input[0];
        int numberOfParcels = Integer.parseInt(input[1]);

        double parcel1Weight = Double.parseDouble(input[2]);
        double parcel2Weight = Double.parseDouble(input[3]);
        double parcel3Weight = Double.parseDouble(input[4]);

        CourierService courierService = new CourierService();
        Assert.assertTrue(courierService.checkParcelLimit(destinationCity, numberOfParcels,parcel1Weight, parcel2Weight, parcel3Weight));
    }

    @Test
    public void shouldNotAcceptParcelMoreThan1000gms(){
        String[] input = "Banglore:3:100:2000:360:12PM:true".split(":");
        String destinationCity = input[0];
        int numberOfParcels = Integer.parseInt(input[1]);

        double parcel1Weight = Double.parseDouble(input[2]);
        double parcel2Weight = Double.parseDouble(input[3]);
        double parcel3Weight = Double.parseDouble(input[4]);

        CourierService courierService = new CourierService();
        Assert.assertFalse(courierService.checkParcelLimit(destinationCity, numberOfParcels,parcel1Weight, parcel2Weight, parcel3Weight));
    }

    @Test
    public void shouldCalculateCharge(){
        String[] input = "Banglore:3:100:100:100:1200:true".split(":");
        String destinationCity = input[0];
        int numberOfParcels = Integer.parseInt(input[1]);
        double parcel1Weight = Double.parseDouble(input[2]);
        double parcel2Weight = Double.parseDouble(input[3]);
        double parcel3Weight = Double.parseDouble(input[4]);
        int time = Integer.parseInt(input[5]);

        CourierService courierService = new CourierService();
        double calculatedCharge = courierService.calculateCharge(destinationCity, numberOfParcels,parcel1Weight, parcel2Weight, parcel3Weight, time, Boolean.parseBoolean(input[6]));
        Assert.assertTrue(90.00==calculatedCharge);
    }
}
