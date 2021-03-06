package com.parkinglot;

import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

import com.parkinglot.enums.VehicleType;
import com.parkinglot.tools.BookingToolsImpl;
import com.parkinglot.tools.ParkingLotTools;
import com.parkinglot.tools.ParkingLotToolsImpl;
import com.parkinglot.tools.ToolsFactory;
import com.parkinglot.vo.Booking;
import com.parkinglot.vo.Customer;
import com.parkinglot.vo.ParkingFloor;
import com.parkinglot.vo.ParkingLot;
import com.parkinglot.vo.ParkingSpot;

public class ParkingLotApplication {
	static ParkingLot parkingLot = null;
	static ParkingLotTools parkingLotTools = null;
	static BookingToolsImpl bookingTools = null;

	public static void main(String[] args) {
		System.out.println("\t\t\t\t******Welcome To Gondor Parking Area******\t\t\t\t\t");
		System.out.println("Please enter the number of floor:::\n");
		Scanner scanner = new Scanner(System.in);
		int numberOfFloor = scanner.nextInt();
		Scanner scan = new Scanner(System.in);
		init(numberOfFloor);
		parkingLotTools = (ParkingLotToolsImpl) ToolsFactory.getTools("ParkingLotTools");
		bookingTools = (BookingToolsImpl) ToolsFactory.getTools("BookingTools");
		
		while (true) {
			System.out.println();
			System.out.println("Enter the the options in number to perform below mentoned task::");
			System.out.println("1 : PARK");
			System.out.println("2 : UNPARK");
			System.out.println("3 : PARKING SUMMARY");
			System.out.println("4 : MAIN MENU");
			
			int choice = scanner.nextInt();
			
			switch (choice) {
			
			
			case 1:
				VehicleType type = null;
				boolean seniorCitizen = false;
				boolean royalMember = false;
				boolean carPooledCustomer=false;

				System.out.println("Enter Your Name::");
				String personName = scan.nextLine();

				System.out.println("Enter Your Phone Number::");
				String phoneNumber = scan.nextLine();
				
				System.out.println("Enter Vehicle Number::");
				String vehicleNumber = scan.nextLine();

				System.out.println("Please choose vehicle type::\n Bike \n Car");
				String vehicleType = new Scanner(System.in).nextLine();
				
				

				if ("Bike".equalsIgnoreCase(vehicleType)) {
					type = VehicleType.BIKE;
				} else if ("Car".equalsIgnoreCase(vehicleType)) {
					type = VehicleType.CAR;
				} else {
					System.out.println("Please re-enter your vehicle type");
					break;
				}

				System.out.println("Are you senior citizen::\n Yes \n No");
				String senior = new Scanner(System.in).nextLine();
				if ("yes".equalsIgnoreCase(senior)) {
					seniorCitizen = true;
				} else if ("no".equalsIgnoreCase(senior)) {
					seniorCitizen = false;
				} else {
					System.out.println("Please re-enter your input");
					break;
				}

				System.out.println("Are you royal member::\n Yes \n No");
				String royal = new Scanner(System.in).nextLine();
				if ("yes".equalsIgnoreCase(royal)) {
					royalMember = true;
				} else if ("no".equalsIgnoreCase(royal)) {
					royalMember = false;
				} else {
					System.out.println("Please re-enter your input");
					break;
				}
				

				System.out.println("Are you using car pooled ::\n Yes \n No");
				String carPooled = new Scanner(System.in).nextLine();

				if ("yes".equalsIgnoreCase(carPooled)) {
					carPooledCustomer = true;
				} else if ("no".equalsIgnoreCase(carPooled)) {
					carPooledCustomer = false;
				} else {
					System.out.println("Please re-enter your input");
					break;
				}
				ParkingSpot parkingSpot = parkingLotTools.allocateParkingFloor(parkingLot, royalMember, type,
						seniorCitizen);
				if (parkingSpot == null) {
					System.out.println("Sorry Parking Is Full Please Try After Sometime");

				} else {
					Customer customer = new Customer();
					customer.setName(personName);
					customer.setPhone(phoneNumber);
					customer.setSeniorCititzen(seniorCitizen);
					Booking booking = bookingTools.confirmBooking(customer, parkingSpot, type, vehicleNumber,
							royalMember,carPooledCustomer);
					System.out.println(
							"Parking is Allocated Successfully With Booking Number::" + booking.getBookingId());
				}
				System.out.println();
				break;
				
				
			case 2:
				
				System.out.println("Enter the booking id::");
				String bookingId = scan.nextLine();
				boolean realeasedSuccessfully = false;
				Booking booking = bookingTools.getAllocatedBooking().get(bookingId);
				if (booking != null) {
					realeasedSuccessfully = parkingLotTools.releaseFromParkingFloor(parkingLot,
							booking.getFloorNumber(), booking.getSpotNumber());
				} else {
					System.out.println("Oops There is no vehicle in parking..");
					break;
				}
				if (realeasedSuccessfully) {
					System.out.println("Vehicle with reg Number " + " isSuccessfully With Booking Number::"
							+ booking.getBookingId());
					bookingTools.vacateParking(bookingId);
				} else {
					System.out.println("Oops There is no vehicle in parking with booking id:::" + bookingId);
				}

				break;
				
			case 3:
				

				if(bookingTools.getAllocatedBooking().size()==0) {
					System.out.println("Parking is empty");
					break;
				}
				Set allocatedMapDataSet = bookingTools.getAllocatedBooking().entrySet();
				Iterator it = allocatedMapDataSet.iterator();

				System.out.println("Parking details of all customer alongwith vehicle number nd where it is parked.");
				Booking bookingObj = null;
				while (it.hasNext()) {
					Map.Entry<String, Booking> allocatedParkingMapDataEntry = (Entry) it.next();
					bookingObj = allocatedParkingMapDataEntry.getValue();
					System.out.println("Customer Name:\t"+bookingObj.getCustomer().getName());
					System.out.println("Customer Phone:\t"+bookingObj.getCustomer().getPhone());
					System.out.println("Senior Citizen:\t"+bookingObj.getCustomer().isSeniorCititzen());
					System.out.println("Booking Id:\t"+bookingObj.getBookingId());
					System.out.println("Booking Status:\t"+bookingObj.getBookingStatus());
					System.out.println("Booking Cost:\t"+bookingObj.getBookingCost());
					System.out.println("Vehicle Number:\t"+bookingObj.getVehicleNumber());
					System.out.println("Vehicle Type:\t"+bookingObj.getVehicleType());
					System.out.println("Floor Number:\t"+bookingObj.getFloorNumber());
					System.out.println("Spot on Floor:\t"+bookingObj.getSpotNumber());
					System.out.println();
					

				}
				break;
				
			case 4:  break;
			 default:System.out.println("Pease enter correct option");
				 break;
			}
		}

	}

	static void init(int numberOfFloor) {
		parkingLot = new ParkingLot(numberOfFloor);
		for (int i = 1; i <= numberOfFloor; i++) {
			ParkingFloor parkingFloor = new ParkingFloor();
			parkingFloor.setFloorNumber(i);
			for (int j = 1; j <= 20; j++) {
				ParkingSpot parkingSpot = new ParkingSpot();
				parkingSpot.setSpotId(j);
				parkingSpot.setFloorNumber(i);
				parkingFloor.getAvailableParkingSpotsMap().put(j, parkingSpot);

			}

			parkingLot.getAvailableParkingFloorMap().put(i, parkingFloor);
		}
	}

}
