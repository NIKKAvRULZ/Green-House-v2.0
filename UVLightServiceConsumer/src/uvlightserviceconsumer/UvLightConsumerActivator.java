package uvlightserviceconsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import uvlightserviceproducer.*;

public class UvLightConsumerActivator implements BundleActivator {

    private ServiceReference<?> uvLightRef;
    
    private UVLightService uVLightService;
    private UVLightServiceImpl uvLightServiceImpl;
    private int uVLightThreshold = 400;

    
    private final Scanner scanner = new Scanner(System.in);
    
    private List<String> uVLightDevices = new ArrayList<>();

    
	public void start(BundleContext context) throws Exception {
		System.out.println("\n"
			    + "╔════════════════════════════════════════╗\n"
			    + "║      ☀ UV LIGHT SERVICE STARTING...   ║\n"
			    + "╚════════════════════════════════════════╝"
			);
        uvLightRef = context.getServiceReference(UVLightService.class.getName());
        uVLightService = (UVLightService) context.getService(uvLightRef);
        ServiceReference<UVLightService> ref = context.getServiceReference(UVLightService.class);
        
        if (ref != null) {
            uVLightService = context.getService(ref);
            System.out.println("✅ UV Light Service found and registered.");
        } else {
            System.out.println("⚠️ UV Light Service NOT FOUND!");
        }
        uvLightServiceImpl = (UVLightServiceImpl) context.getService(context.getServiceReference(UVLightService.class.getName()));
        
     
        if (uvLightServiceImpl != null) {
            System.out.println("✅ UV Light Service Initialized");
        } else {
            System.out.println("❌ UV Light Service not found");
        }
        
        // Start user interaction menu
        controlUVLightDevices();

	}
	
	public double getUVLightLevel() {
            return UVLightService.getUVLightLevel;
	}
	
    public void controlUVLightDevices() {
    	boolean inUvMenu = true;
    	while (inUvMenu) {
    		System.out.println("\n"
    			    + "╔════════════════════════════════════════╗\n"
    			    + "║      ☀️  UV LIGHT CONTROL OPTIONS      ║\n"
    			    + "╚════════════════════════════════════════╝\n"
    			    + "╔════════════════════════════════════════╗\n"
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
            scanner.nextLine();  // Consume the newline character
            
            switch (choice) {
            	case 1:
                	addDevice("UV Light");
                	break;
            	case 2:
                	removeDevice("UV Light");
                	break;
            	case 3:
                	changeDeviceMode("UV Light");
                	break;
            	case 4:
                	viewAllDevicesInfo("UV Light");
                	break;
            	case 5:
                	disableDevices("UV Light");
                	break;
            	case 6:
            		System.out.println("UV Light     : " + UVLightService.getUVLightLevel + "nm");
            		inUvMenu = false;  // Back to Main Menu
                	break;
            	default:
            		System.out.println("Invalid choice! Please try again.");
            }
    		
    	}
    }

    private void addDevice(String deviceType) {
    	System.out.print("➕ Enter device name to ADD: "); 
        String deviceName = scanner.nextLine();
        
        // Add the device to the appropriate list
        if (deviceType.equals("UV Light")) {
        	uVLightDevices.add(deviceName);
        }
        System.out.println("✅ " + deviceName + " added successfully to " + deviceType + " devices.");
    }
    
    private void removeDevice(String deviceType) {
        System.out.print("Enter the name of the " + deviceType + " device you want to remove: ");
        String deviceName = scanner.nextLine();
        
        // Determine which device list to check based on device type
        List<String> devices = new ArrayList<>();
        if (deviceType.equals("UV Light")) {
        	devices = uVLightDevices;
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
    	System.out.print("🔄 Enter device name to CHANGE MODE: ");  
        String deviceName = scanner.nextLine();

        double currentUVLight = UVLightService.getUVLightLevel;

        // Check if the device exists in the respective list
        boolean deviceExists = false;
        if (deviceType.equals("UV Light")) {
        	deviceExists = uVLightDevices.contains(deviceName);
        }

        if (!deviceExists) {
            System.out.println(deviceName + " not found.");
            return;
        }

	    if (deviceType.equals("UV Light")) {
		    boolean isUVPurifierOn = currentUVLight > 40;
		
		    if (isUVPurifierOn) {
		    	System.out.println("⚠️ UV Light level is HIGH. " + deviceName + " is now ON.");
		    } else {
		    	System.out.println("✅ " + deviceName + " mode updated successfully!");
		    }
		
		    // Ask user if they want to change the device state
		    System.out.print("Do you want to change it? (yes/no): ");
		    Scanner scanner = new Scanner(System.in);
		    String response = scanner.nextLine().trim().toLowerCase();
		
		    if (response.equals("yes")) {
		        // Toggle the state
		        isUVPurifierOn = !isUVPurifierOn;
		        String newState = isUVPurifierOn ? "ON" : "OFF";
		        System.out.println(deviceName + " is " + newState + ".");
		    } else {
		        System.out.println(deviceName + " unchanged!!!");
		    }
		 }
    }

    private void viewAllDevicesInfo(String deviceType) {
    	System.out.println("\n📜 Displaying all " + deviceType + " devices...");
        
        // Display all devices based on device type
        List<String> devices = new ArrayList<>(); 
        if (deviceType.equals("UV Light")) {
        	devices = uVLightDevices;
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
        if (deviceType.equals("UV Light")) {
        	devices = uVLightDevices;
        }

        // If there are no devices in the list, notify the user
        if (devices.isEmpty()) {
            System.out.println("❌ No devices available for " + deviceType + ".");
            return;
        }

        // Prompt the user to enter a device name or "All Devices"
        System.out.println("🚫 Enter the device name to disable (or type 'All Devices' to disable all):");
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
			    + "║      ⛔ UV LIGHT SERVICE STOPPING...   ║\n"
			    + "╚════════════════════════════════════════╝"
			);
	}

}
