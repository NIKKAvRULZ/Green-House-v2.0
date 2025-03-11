package temperaturesensorproducer;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TemperatureServiceImpl implements TemperatureService {
    private int temperature ; // Default room temperature
    private int temperatureThreshold = 30;
    private final Random random = new Random();

    public TemperatureServiceImpl() {
    	
    	temperature = 15 + random.nextInt(25); // Between 15°C and 40°C
        // Simulate temperature changes every 5 seconds
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                simulateTemperatureChange();
            }
        }, 0, 5000);
    }

    private void simulateTemperatureChange() {
        temperature += random.nextInt(3) - 1; // Small gradual change
        temperature = Math.max(15, Math.min(40, temperature)); // Keep within limits
    }

    @Override
    public void setTemperatureThreshold(int threshold) {
        this.temperatureThreshold = threshold;
        System.out.println("✅ Temperature threshold set to " + temperatureThreshold + "°C");
    }
}