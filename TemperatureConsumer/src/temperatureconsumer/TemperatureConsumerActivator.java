package temperatureconsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle.Control;
import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import temperaturesensorproducer.TemperatureService;
import temperaturesensorproducer.TemperatureServiceImpl;

public class TemperatureConsumerActivator implements BundleActivator {

	private ServiceReference<?> tempReference;
	private TemperatureServiceImpl temperatureServiceImpl;
    private int tempThreshold = 30;

	
	private TemperatureService temperatureService;

	private boolean running = true;
	private final Scanner scanner = new Scanner(System.in);

	private List<String> temperatureDevices = new ArrayList<>();
	
	public void start(BundleContext context) throws Exception {
		System.out.println("\n"
			    + "╔════════════════════════════════════════╗\n"
			    + "║    🌡 TEMPERATURE SERVICE STARTING...  ║\n"
			    + "╚════════════════════════════════════════╝"
			);
		controlTemperatureDevices();
	}
	
	public double getTemperature() {
	    return TemperatureService.getTemperature;
	}

	
	public void controlTemperatureDevices() {
		boolean inTempMenu = true;
		while (inTempMenu) {
			System.out.println("\n"
				    + "╔════════════════════════════════════════╗\n"
				    + "║      🌡 TEMPERATURE CONTROL MENU       ║\n"
				    + "╠════════════════════════════════════════╣\n"
				    + "║ 1️⃣  Add Device                         ║\n"
				    + "║ 2️⃣  Remove Device                      ║\n"
				    + "║ 3️⃣  Change Device Mode                 ║\n"
				    + "║ 4️⃣  View All Devices Info              ║\n"
				    + "║ 5️⃣  Disable Devices                    ║\n"
				    + "║ 6️⃣  Back to Main Menu                  ║\n"
				    + "╚════════════════════════════════════════╝\n"
				    + "👉 Enter your choice: "
				);			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume the newline character

			switch (choice) {
			case 1:
				addDevice("Temperature");
				break;
			case 2:
				removeDevice("Temperature");
				break;
			case 3:
				changeDeviceMode("Temperature");
				break;
			case 4:
				viewAllDevicesInfo("Temperature");
				break;
			case 5:
				disableDevices("Temperature");
				break;
			
			case 7:
				System.out.println("Temperature  : " + TemperatureService.getTemperature + "°C");

				inTempMenu = false; // Back to Main Menu
				break;
			default:
				System.out.println("Invalid choice! Please try again.");
			}
		}
	}
	private void addDevice(String deviceType) {
		System.out.print("➕ Enter the name of the device to ADD: ");  
		String deviceName = scanner.nextLine();

		// Add the device to the appropriate list
		if (deviceType.equals("Temperature")) {
			temperatureDevices.add(deviceName);
		}

		System.out.println("✅ " + deviceName + " added successfully to " + deviceType + " devices.");
	}

	private void removeDevice(String deviceType) {
		System.out.print("Enter the name of the " + deviceType + " device you want to remove: ");
		String deviceName = scanner.nextLine();

		// Determine which device list to check based on device type
		List<String> devices = new ArrayList<>();
		if (deviceType.equals("Temperature")) {
			devices = temperatureDevices;
		}

		// Check if the device exists in the list
		if (devices.contains(deviceName)) {
			devices.remove(deviceName);
			System.out.println("✅ " + deviceName + " removed successfully from " + deviceType + " devices.");
		} else {
			System.out.println("❌ " + deviceName + " not found in " + deviceType + " devices.");
		}
	}

	private void changeDeviceMode(String deviceType) {
		System.out.print("Enter the device name you want to change: ");
		String deviceName = scanner.nextLine();

		double currentTemperature = TemperatureService.getTemperature;

		// Check if the device exists in the respective list
		boolean deviceExists = false;
		if (deviceType.equals("Temperature")) {
			deviceExists = temperatureDevices.contains(deviceName);
		}
		if (!deviceExists) {
			System.out.println(deviceName + " not found.");
			return;
		}

		// Temperature Control Logic (Unchanged)
		if (deviceType.equals("Temperature")) {
			System.out.print("Enter the device type (Air Conditioner/Heater): ");
			String selectedDevice = scanner.nextLine();

			if (currentTemperature > 25 && selectedDevice.equalsIgnoreCase("Air Conditioner")) {
			    System.out.println("🔥 " + deviceName + " is ON. Do you want to turn it OFF? (yes/no)");
			} else if (currentTemperature < 18 && selectedDevice.equalsIgnoreCase("Heater")) {
			    System.out.println("❄️ " + deviceName + " is ON. Do you want to turn it OFF? (yes/no)");
			} else if (currentTemperature > 25 && selectedDevice.equalsIgnoreCase("Heater")) {
			    System.out.println("🚨 " + deviceName + " is OFF. Warning!!! Temperature is high ☀️.");
			} else if (currentTemperature < 18 && selectedDevice.equalsIgnoreCase("Air Conditioner")) {
			    System.out.println("🚨 " + deviceName + " is OFF. Warning!!! Temperature is low ❄️.");
			} else {
			    System.out.println("🌡 Temperature is normal (18°C - 25°C). No need to change.");
			}

			String response = scanner.nextLine().toLowerCase();
			if (response.equals("yes")) {
				if ((currentTemperature > 25 && selectedDevice.equalsIgnoreCase("Air Conditioner"))
						|| (currentTemperature < 18 && selectedDevice.equalsIgnoreCase("Heater"))) {
					System.out.println(deviceName + " turned OFF.");
				} else if ((currentTemperature > 25 && selectedDevice.equalsIgnoreCase("Heater"))
						|| (currentTemperature < 18 && selectedDevice.equalsIgnoreCase("Air Conditioner"))) {
					System.out.println(deviceName + " turned ON. Warning!!! This is not good.");
				} else {
					System.out.println(deviceName + " switched " + response + ", but it is not needed!");
				}
			} else {
				System.out.println(deviceName + " mode unchanged!");
			}
		}
	}

	private void viewAllDevicesInfo(String deviceType) {
		System.out.println("📜 Displaying all " + deviceType + " devices info...");

		// Display all devices based on device type
		List<String> devices = new ArrayList<>();
		if (deviceType.equals("Temperature")) {
			devices = temperatureDevices;
		}

		// Print all devices
		if (devices.isEmpty()) {
			System.out.println("No " + deviceType + " devices found.");
		} else {
			devices.forEach(device -> System.out.println(device));
		}
	}

	private void disableDevices(String deviceType) {
		// Initialize the scanner to take user input
		Scanner scanner = new Scanner(System.in);
		List<String> devices = new ArrayList<>();

		// Determine which list of devices to operate on
		if (deviceType.equals("Temperature")) {
			devices = temperatureDevices;
		}

		// If there are no devices in the list, notify the user
		if (devices.isEmpty()) {
			System.out.println("❌ No devices available for " + deviceType + ".");
			return;
		}

		// Prompt the user to enter a device name or "All Devices"
		System.out.println("Enter the device name to disable (or type 'All Devices' to disable all):");
		String input = scanner.nextLine().trim();

		// Case 1: User entered "All Devices"
		if (input.equalsIgnoreCase("All Devices")) {
			System.out.println("Disabling all " + deviceType + " devices...");
			for (String device : devices) {
				System.out.println("🚫 " + device + " has been disabled successfully!");
			}
			devices.clear();
			System.out.println("✅ All " + deviceType + " devices have been disabled.");
		}
		// Case 2: User entered a specific device name
		else if (devices.contains(input)) {
			devices.remove(input);
			System.out.println("🚫 " + input + " has been disabled successfully!");
		}
		// Case 3: Device name not found
		else {
			System.out.println("❌ " + input + " not found!");
		}
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("\n"
			    + "╔════════════════════════════════════════╗\n"
			    + "║    ⛔ TEMPERATURE SERVICE STOPPING...  ║\n"
			    + "╚════════════════════════════════════════╝"
			);
	}

}
