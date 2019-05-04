package com.parkinglot.vo;

import com.parkinglot.enums.VehicleType;

public class Bike extends Vehicle {

	public Bike(String vehicleNumber,VehicleType type) {
		super(vehicleNumber,VehicleType.BIKE);
	}

}
