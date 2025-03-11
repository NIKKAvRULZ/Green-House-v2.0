package humidityserviceconsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import humidityserviceproducer.HumidityService;

public class HumidityServiceConsumerActivator implements BundleActivator {

	private ServiceReference humidityReference;
	private HumidityService humidityService;

	private boolean running = true;
	private final Scanner scanner = new Scanner(System.in);

	private List<String> humidityDevices = new ArrayList<>();

	public void start(BundleContext context) throws Exception {
		System.out.println("Humidity Service Starting...");
		humidityReference = context.getServiceReference(HumidityServiceConsumerActivator.class.getName());
		humidityService = (HumidityService) context.getService(humidityReference);
		controlHumidityDevices();
	}

	public double getHumidityLevel() {
		return HumidityService.getHumidityLevel;
	}
	
	public void controlHumidityDevices() {
		boolean inHumidityMenu = true;
		while (inHumidityMenu) {
			System.out.println("\n"
				    + "╔════════════════════════════════════════╗\n"
				    + "║       💧 HUMIDITY CONTROL MENU         ║\n"
				    + "╠════════════════════════════════════════╣\n"
				    + "║ 1️⃣  Add Device                         ║\n"
				    + "║ 2️⃣  Remove Device                      ║\n"
				    + "║ 3️⃣  Change Device Mode                 ║\n"
				    + "║ 4️⃣  View All Devices Info              ║\n"
				    + "║ 5️⃣  Disable Devices                    ║\n"
				    + "║ 6️⃣  Back to Main Menu                  ║\n"
				    + "╚════════════════════════════════════════╝\n"
				    + "👉 Enter your choice: "
				);
			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume the newline character

			switch (choice) {
			case 1:
				addDevice("Humidity");
				break;
			case 2:
				removeDevice("Humidity");
				break;
			case 3:
				changeDeviceMode("Humidity");
				break;
			case 4:
				viewAllDevicesInfo("Humidity");
				break;
			case 5:
				disableDevices("Humidity");
				break;
			case 6:
				inHumidityMenu = false; // Back to Main Menu
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
		if (deviceType.equals("Humidity")) {
			humidityDevices.add(deviceName);
		}

		System.out.println("✅ " + deviceName + " added successfully to " + deviceType + " devices.");
	}

	private void removeDevice(String deviceType) {
		System.out.print("Enter the name of the " + deviceType + " device you want to remove: ");
		String deviceName = scanner.nextLine();

		// Determine which device list to check based on device type
		List<String> devices = new ArrayList<>();
		if (deviceType.equals("Humidity")) {
			devices = humidityDevices;
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

		double currentHumidity = HumidityService.getHumidityLevel;

		// Check if the device exists in the respective list
		boolean deviceExists = false;
		if (deviceType.equals("Humidity")) {
			deviceExists = humidityDevices.contains(deviceName);
		}

		if (!deviceExists) {
			System.out.println(deviceName + " not found.");
			return;
		}

		// Humidity Control
		else if (deviceType.equals("Humidity")) {
			if (currentHumidity < 40) {
				System.out.println("Humidity is low. " + deviceName + " (Humidifier) turned ON.");
			} else {
				System.out.println("Humidity is normal. " + deviceName + " (Humidifier) turned OFF.");
			}

			// Ask user if they want to change the device state
			System.out.print("Do you want to change it? (yes/no): ");
			Scanner scanner = new Scanner(System.in);
			String response = scanner.nextLine().trim().toLowerCase();

			if (response.equals("yes")) {
				// Toggle the state
				String newState = (currentHumidity < 40) ? "OFF" : "ON";
				System.out.println(deviceName + " is " + newState + ".");
			} else {
				System.out.println(deviceName + " unchanged!!!");

			}
		}
	}

	private void viewAllDevicesInfo(String deviceType) {
		System.out.println("📜 Displaying all " + deviceType + " devices info...");

		// Display all devices based on device type
		List<String> devices = new ArrayList<>();
		if (deviceType.equals("Humidity")) {
			devices = humidityDevices;
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
		if (deviceType.equals("Humidity")) {
			devices = humidityDevices;
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
			    + "║    ⛔ HUMIDITY SERVICE STOPPING...     ║\n"
			    + "╚════════════════════════════════════════╝"
			);	}

}
