# BMS to Solar Inverter communication

Here's some nice way to read your BMS's data (e.g. Daly BMS as reference) via different protocols and read/write the battery data to the inverter.
The (reference) project uses a Raspberry Pi 4 with a Waveshare RS485/CAN hat or USB-CAN-A module getting the data from _multiple_ battery packs, aggregating them and sending the data to the SMA Sunny Island inverter.
Any BMS or inverter can be supported in a very short time by just mapping the manufacturers protocol specification in an own implementation of the [`PortProcessor`](https://github.com/ai-republic/bms-to-inverter/blob/main/core-api/src/main/java/com/airepublic/bmstoinverter/core/PortProcessor.java).

This way _you_ control what gets send to the inverter.
You can monitor each of your battery packs cells and view alarm states on the included webserver or hook up via the MQTT broker on your smart home.

Currently implemented BMS:
* Daly BMS (RS485 or CAN)

Currently implemented inverters:
* SMA Sunny Island (CAN)

----------

## Supported protocols:
* UART / RS485
* CAN

----------

## Supported architectures

The following architectures are supported:
* x86_32 
* x86_64
* armv6
* armv7
* armv7a
* armv7l 
* aarch64
* riscv32
* riscv64

**NOTE:** There are restrictions using CAN on Windows as SocketCAN library is *NOT* available on Windows OS.

----------

## How to use
The reference project [`bms-to-inverter-main`](https://github.com/ai-republic/bms-to-inverter/blob/main/bms-to-inverter-main) shows how to communicate with Daly BMS to a Sunny Island inverter. Please make sure you have the right ports/devices configured in [`config.properties`](https://github.com/ai-republic/bms-to-inverter/blob/main/bms-to-inverter-main/src/main/resources/config.properties).

#### Choose your BMS and inverter with the appropriate protocol
In the pom.xml you'll find the dependencies which BMS and which inverter to use. If you're not using Daly BMS to SMA Sunny Island both communicating via CAN protocol you'll have to change the following dependencies according to your BMS, inverter and protocol.

```

		<!--		choose BMS 		-->
		<dependency>
			<groupId>com.ai-republic.bms-to-inverter</groupId>
			<artifactId>bms-daly-can</artifactId>
			<version>${project.version}</version>
		</dependency>

		<!--		choose inverter			-->
		<dependency>
			<groupId>com.ai-republic.bms-to-inverter</groupId>
			<artifactId>inverter-sma-can</artifactId>
			<version>${project.version}</version>
		</dependency>


```

So if you like to use RS485 protocol to communicate with the Daly BMS you can just change the `bms-daly-can` to `bms-daly-rs485`.

#### Choose your target architecture (only if using CAN protocol)
In the `native` folder  [`libjavacan-core.so`](https://github.com/ai-republic/bms-to-inverter/blob/main/protocol-can/src/main/resources/native) you have sub-folders for all the supported architectures. Choose your target architecture and copy the libjavacan-core.so from the architecture folder to the `native` folder.

#### Configuration
Once you have your right dependencies and target architecture defined check the [`config.properties`](https://github.com/ai-republic/bms-to-inverter/blob/main/bms-to-inverter-main/src/main/resources/config.properties) to define the number of battery packs, port assignments and MQTT properties.

```
numBatteryPacks=8

# RS485 properties
RS485.baudrate=9600
RS485.startFlag=165
RS485.frameLength=13

# CAN properties
#bms.portname=com3				# RS485 on Windows for testing
#bms.portname=/dev/ttyS0		# RS485 on Raspberry
bms.portname=can0
inverter.portname=can1		# can1 for Waveshare 2CH-CAN-HAT-FD, otherwise can0 for e.g. Waveshare RS485/CAN hat


#MQTT properties
mqtt.locator=tcp://127.0.0.1:61616
mqtt.topic=energystorage
```

If you intend to use the `webserver` project to monitor your BMS you might want to review the `application.properties` to define the server port and make sure that the MQTT properties match those in the `config.properties` above.

```
# Webserver properties
server.port=8080

# MQTT properties
mqtt.locator=tcp://localhost:61616
mqtt.topic=energystorage
```


#### Building the project

Once your project is configured you can simple build it with `mvn clean package` to produce a `zip` file found under the `target` directory.
The `zip` file contains the main jar plus all dependencies in a lib folder.
Copy this to your target machine, e.g. a Raspberry, unpack it and start it with `java -jar bms-to-inverter-main-0.0.1-SNAPSHOT.jar`.

If you're using the `webserver` then you'll have to copy the `webserver-0.0.1-SNAPSHOT.jar` found in the `webserver/target` folder to your target machine and start it with `java -jar webserver-0.0.1-SNAPSHOT`.

----------

## Notes
I will be doing a lot of updates to the documentation and current code and structure but if you have questions or need support feel free to contact me or raise an issue.


*I do not take any responsiblity for any damages that might occur by using this software!*
