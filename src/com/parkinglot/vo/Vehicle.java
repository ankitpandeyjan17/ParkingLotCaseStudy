package com.parkinglot.vo;

import com.parkinglot.enums.VehicleType;

public abstract class Vehicle {
	  private String vehicleNumber;
	  private final VehicleType type;
	public Vehicle(String vehicleNumber, VehicleType type) {
		super();
		this.vehicleNumber = vehicleNumber;
		this.type = type;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public VehicleType getType() {
		return type;
	}
	 
}
