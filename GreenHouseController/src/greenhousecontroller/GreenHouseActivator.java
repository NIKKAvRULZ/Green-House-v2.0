package greenhousecontroller;

import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import humidityserviceconsumer.*;
import soilmoistrureserviceconsumer.*;
import temperatureconsumer.*;
import uvlightserviceconsumer.*;

public class GreenHouseActivator implements BundleActivator {

	private TemperatureConsumerActivator temperatureConsumerActivator;
	private UvLightConsumerActivator uvLightConsumerActivator;
	private SoilMoistrureServiceConsumerActivator soilMoistrureServiceConsumerActivator;
	private HumidityServiceConsumerActivator humidityServiceConsumerActivator;

	private int tempThreshold = 30;
	private int humidityThreshold = 40;
	private int moistureThreshold = 50;
	private int uVLightThreshold = 400;

	private boolean running = true;

	private Scanner sc = new Scanner(System.in);

	public void start(BundleContext context) throws Exception {
		System.out.println("===========================================");
		System.out.println("\n" + ""
				+ "██████╗  ██████╗ ████████╗ █████╗ ███╗   ██╗ ██╗ ██╗  ██╗\n"
				+ "██╔══██╗██╔═══██╗╚══██╔══╝██╔══██╗████╗  ██║ ██║ ██║  ██║\n"
				+ "██║███  ██║   ██║   ██║   ███████║██╔██╗ ██║ ██║  █████║\n"
				+ "██║  ██║██║   ██║   ██║   ██╔══██║██║╚██╗██║ ██║ ██╔══██║\n"
				+ "██████╔╝╚██████╔╝   ██║   ██║  ██║██║ ╚████║ ██║ ██║  ██║\n"
				+ "╚═════╝  ╚═════╝    ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═╝ ╚═╝  ╚═╝\n"
				+ "----------------------------------------------------\n"
				+ "🌱 Welcome to BotaniX - Smart Greenhouse Controller 🌱\n"
				+ "----------------------------------------------------\n");
		System.out.println("===========================================");
		System.out.println("🚀 Initializing System Components...");
		System.out.println("\n🌱🌎🌞 Smart Greenhouse Controller is Starting... 🌞🌎🌱");
		System.out.println("\n🍃 Monitoring Sensors... Adjusting Climate... Ensuring Optimal Growth 🌱\n");

		temperatureConsumerActivator = new TemperatureConsumerActivator();
		uvLightConsumerActivator = new UvLightConsumerActivator();
		soilMoistrureServiceConsumerActivator = new SoilMoistrureServiceConsumerActivator();
		humidityServiceConsumerActivator = new HumidityServiceConsumerActivator();

		System.out.println("✅ Temperature Consumer Service Initialized.");
		System.out.println("✅ UV Light Consumer Service Initialized.");
		System.out.println("✅ Soil Moisture Consumer Service Initialized.");
		System.out.println("✅ Humidity Consumer Service Initialized.");

		System.out.println("\n🌿 Greenhouse System is Up and Running! 🌿\n");

		runMenu();
	}

	private void runMenu() {
		while (running) {
			System.out.println("\n" + "╔════════════════════════════════════════╗\n"
									+ "║            WELCOME TO BOTANIX          ║\n" 
									+ "╚════════════════════════════════════════╝\n" 
									+ "╔════════════════════════════════════════╗\n"
									+ "║            MAIN MENU OPTIONS           ║\n" 
									+ "╠════════════════════════════════════════╣\n"
									+ "║  1️⃣  View Sensor Data                  ║\n" 
									+ "║  2️⃣  Customize Sensor Settings         ║\n"
									+ "║  3️⃣  Control Temperature Devices       ║\n" 
									+ "║  4️⃣  Control Humidity Devices          ║\n"
									+ "║  5️⃣  Control Soil Moisture Devices     ║\n" 
									+ "║  6️⃣  Control UV Light Service Devices  ║\n"
									+ "║  7️⃣  Exit                              ║\n"
									+ "╚════════════════════════════════════════╝\n"
									+ "👉 Enter your choice: ");

			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1:
				viewSensorData();
				break;
			case 2:
				customizeSettings();
				break;
			case 3:
				temperatureConsumerActivator.controlTemperatureDevices();
				break;
			case 4:
				humidityServiceConsumerActivator.controlHumidityDevices();
				break;
			case 5:
				soilMoistrureServiceConsumerActivator.controlSoilMoistureDevices();
				break;
			case 6:
				uvLightConsumerActivator.controlUVLightDevices();
				break;
			case 7:
				running = false;
				System.out.println("Shutting down...");
				break;
			default:
				System.out.println("Invalid choice! Please try again.");
			}
		}
	}

	private void viewSensorData() {
		double temp = temperatureConsumerActivator.getTemperature();
		double humidity = humidityServiceConsumerActivator.getHumidityLevel();
		double moisture = soilMoistrureServiceConsumerActivator.getMoistureLevel();
		double uv = uvLightConsumerActivator.getUVLightLevel();

		System.out.println("\n" + "╔════════════════════════════════════════╗\n"
								+ "║         CURRENT SENSOR READINGS        ║\n" 
								+ "╚════════════════════════════════════════╝\n");

		System.out.println("Temperature  : " + temp + "°C");
		System.out.println("Humidity     : " + humidity + "%");
		System.out.println("Soil Moisture: " + moisture + "%");
		System.out.println("UV Light     : " + uv + "nm");

		System.out.println("\n╔════════════════════════════════════════╗");
		System.out.println("║           SYSTEM STATUS CHECK          ║");
		System.out.println("╚════════════════════════════════════════╝");

		if (temp > tempThreshold) {
			System.out.println("🔥 Cooling System:   [ ON ] (Temp > " + tempThreshold + "°C)");
		} else {
			System.out.println("❄ Cooling System:   [ OFF ]");
		}

		if (humidity < humidityThreshold) {
			System.out.println("💧 Humidifier:       [ ON ] (Humidity < " + humidityThreshold + "%)");
		} else {
			System.out.println("💨 Humidifier:       [ OFF ]");
		}

		if (moisture < moistureThreshold) {
			System.out.println("🚿 Irrigation:       [ ON ] (Moisture < " + moistureThreshold + "%)");
		} else {
			System.out.println("🌱 Irrigation:       [ OFF ]");
		}

		if (uv > uVLightThreshold) {
			System.out.println("☀ UV Light:         [ ON ] (UV > " + uVLightThreshold + "nm)");
		} else {
			System.out.println("🌑 UV Light:         [ OFF ]");
		}
			
	}

	private void customizeSettings() {
		// CUSTOMIZE SETTINGS MENU
		System.out.println("\n"
		    + "╔════════════════════════════════════════╗\n"
		    + "║     ⚙ CUSTOMIZE SENSOR SETTINGS       ║\n"
		    + "╚════════════════════════════════════════╝\n"
		    + "╔════════════════════════════════════════╗\n"
		    + "║ 1️⃣  Set Temperature Threshold          ║\n"
		    + "║ 2️⃣  Set Humidity Threshold             ║\n"
		    + "║ 3️⃣  Set Soil Moisture Threshold        ║\n"
		    + "║ 4️⃣  Set UV Light Threshold             ║\n"
		    + "║ 5️⃣  Back to Main Menu                  ║\n"
		    + "╚════════════════════════════════════════╝\n"
		    + "👉 Enter your choice: "
		);
		int choice = sc.nextInt();

		switch (choice) {
		case 1:
			System.out.print("📏 Enter new temperature threshold: ");
			tempThreshold = sc.nextInt();
			System.out.println("✅ Temperature threshold set to " + tempThreshold + "°C");
			break;
		case 2:
			System.out.print("📏 Enter new humidity threshold: ");
			humidityThreshold = sc.nextInt();
			System.out.println("✅ Humidity threshold set to " + humidityThreshold + "%");
			break;
		case 3:
			System.out.print("📏 Enter new soil moisture threshold: ");
			moistureThreshold = sc.nextInt();
			System.out.println("✅ Soil moisture threshold set to " + moistureThreshold + "%");
			break;
		case 4:
			System.out.print("📏 Enter new UV Light Threshold: ");
			uVLightThreshold = sc.nextInt();
			System.out.println("✅ UV Light threshold set to " + uVLightThreshold + "nm");
			break;
		case 5:
			return; // Go back to main menu
		default:
			System.out.println("❌ Invalid choice! Returning to menu...");
		}
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("Greenhouse Controller Stopping...");
	}
}
