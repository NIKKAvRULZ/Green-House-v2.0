package soilmoistureserviceProducer;

public class SoilMoistureServiceImpl implements SoilMoistureService {

	private double moistureLevel;
	private int moistureThreshold = 30;
	
	public SoilMoistureServiceImpl() {
		this.moistureLevel = Math.random() * 10;
	}
	
	@Override
	public void setMoistureThreshold(int threshold) {
		this.moistureThreshold = threshold;
        System.out.println("✅ Soil moisture threshold set to " + moistureThreshold + "%");
		
	}
	public void displayUVLightStatus() {
        System.out.println("Current UV Light level: " + moistureLevel + "%");

        if (moistureLevel < moistureThreshold) {
			System.out.println("🚿 Irrigation:       [ ON ] (Moisture < " + moistureThreshold + "%)");
		} else {
			System.out.println("🌱 Irrigation:       [ OFF ]");
		}
   }
}
