# Greenhouse Monitoring and Control System

This project simulates a **Greenhouse Monitoring and Control System** that uses the **OSGi framework** to manage and control various devices and services. The system is designed to monitor and control the environment within a greenhouse, managing factors like temperature, humidity, soil moisture, and UV light levels.

## Features

### Main Menu
1. **Get Sensor Data**: Displays sensor data and adjusts devices based on threshold values.
2. **Customize Sensor Data**: Allows the user to modify the threshold values for temperature, humidity, soil moisture, and UV light.
3. **Control Temperature Devices**: Manage temperature control devices such as heaters and air conditioners.
4. **Control Humidity Devices**: Manage devices like humidifiers to maintain desired humidity levels.
5. **Control Soil Moisture Devices**: Control irrigation devices based on soil moisture levels.
6. **Control UV Light Devices**: Manage UV light systems to maintain optimal light conditions for plants.
7. **Exit**: Exit the system.

### Device Management Menus

For each device type (Temperature, Humidity, Soil Moisture, UV Light), users can:

1. **Add Device**: Add a new device to the system.
2. **Remove Device**: Remove an existing device from the system.
3. **Change Device Mode**: Toggle the device mode between ON/OFF and adjust based on sensor data.
4. **View All Devices Info**: List all available devices and their current status.
5. **Disable Devices**: Disable specific devices or all devices at once.

### Detailed Device Control Logic

- **Temperature**: The system controls heating or cooling devices based on the temperature threshold. If the temperature exceeds the threshold, cooling devices (like air conditioners) are activated. If it’s below the threshold, heating devices (like heaters) are activated.
- **Humidity**: The system monitors the humidity level and activates a humidifier when the level drops below the threshold.
- **Soil Moisture**: The system manages irrigation devices, turning them on when soil moisture is below the threshold.
- **UV Light**: The system ensures UV light devices are activated when the light level is below the desired threshold.

### System Architecture

The system uses the **OSGi framework**, which allows for dynamic module management and communication between various components:

- **GreenHouseController**: The main controller that interacts with the devices and presents menus for the user.
- **Service Consumers (HumidityServiceConsumer, SoilMoistureServiceConsumer, TemperatureConsumer, UVLightServiceConsumer)**: These classes consume data from the respective service producers.
- **Service Producers (HumidityServiceProducer, SoilMoistureServiceProducer, TemperatureSensorProducer, UVLightServiceProducer)**: These classes provide the data and manage thresholds for the devices.

## Getting Started

### Prerequisites

- JDK 8 or later
- Apache Maven
- OSGi Framework (e.g., Apache Felix, Eclipse Equinox)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/greenhouse-system.git
