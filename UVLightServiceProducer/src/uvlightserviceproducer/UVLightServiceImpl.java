package uvlightserviceproducer;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class UVLightServiceImpl implements UVLightService {
    private int uvLightLevel;
    private int uvLightThreshold = 280; // Threshold for turning the purifier ON
    private final Random random = new Random();

    public UVLightServiceImpl() {
        // Initialize UV light level within the range of 100 to 280
        uvLightLevel = 100 + random.nextInt(181); // Random value between 100 and 280

        // Simulate UV light changes every 5 seconds (in the background)
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                simulateUVLightChange();
            }
        }, 0, 5000);
    }

    private void simulateUVLightChange() {
        uvLightLevel += random.nextInt(21) - 10; // Small fluctuations (-10 to +10)

        // Ensure the UV light level stays within the range of 100 to 280
        uvLightLevel = Math.max(100, Math.min(280, uvLightLevel)); // Keep within 100 to 280 range
    }


    @Override
    public void setUVLightThreshold(int threshold) {
        this.uvLightThreshold = threshold;
        System.out.println("✅ UV Light threshold set to " + uvLightThreshold + "%");
    }

    // User can request the current UV light status and reading
    public void displayUVLightStatus() {
        System.out.println("Current UV Light level: " + uvLightLevel + "%");

        if (uvLightLevel > uvLightThreshold) {
            System.out.println("UV Light level exceeded the threshold! UV Light Purifier turned ON.");
        } else {
            System.out.println("UV Light level is normal. UV Light Purifier is OFF.");
        }
    }
}
