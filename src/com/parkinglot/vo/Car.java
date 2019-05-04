package com.parkinglot.vo;

import com.parkinglot.enums.VehicleType;

public class Car extends Vehicle {

	public Car(String vehicleNumber,VehicleType type) {
		super(vehicleNumber,VehicleType.CAR);
	}

}
