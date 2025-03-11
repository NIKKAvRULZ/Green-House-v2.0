package humidityserviceproducer;

public class HumidityServiceImpl implements HumidityService {
	private double humidityLevel;
    private int humidityThreshold = 40; // Default threshold
   

    public HumidityServiceImpl() {
       this.humidityLevel = Math.random() * 10;
    }


    @Override
    public void setHumidityThreshold(int threshold) {
        this.humidityThreshold = threshold;
        System.out.println("✅ Humidity threshold set to " + humidityThreshold + "%");
    }
    public void displayUVLightStatus() {
        System.out.println("Current UV Light level: " + humidityLevel + "%");

        if (humidityLevel < humidityThreshold) {
			System.out.println("💧 Humidifier:       [ ON ] (Humidity < " + humidityThreshold + "%)");
		} else {
			System.out.println("💨 Humidifier:       [ OFF ]");
		}
   }
	
}
