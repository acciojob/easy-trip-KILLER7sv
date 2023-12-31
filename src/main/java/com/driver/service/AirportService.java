package com.driver.services;
import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import com.driver.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public class AirportService {

    private static AirportRepository airportRepository =new AirportRepository();

    public static void addAirport(Airport airport) {
        airportRepository.addAirport(airport);
    }

    public void addFlight(Flight flight) {
        airportRepository.addFlight(flight);
    }

    public void addPassenger(Passenger passenger) {
        airportRepository.addPassenger(passenger);
    }

    public String getLargestAirportName() {
        List<Airport> allAirport=airportRepository.getAllAirport();
        int max=0;
        String str=allAirport.get(0).getAirportName();
        for (Airport airport:allAirport){
            if(max<airport.getNoOfTerminals()){
                max=airport.getNoOfTerminals();
                str=airport.getAirportName();
            }
            if (max==airport.getNoOfTerminals()) {
                str=airport.getAirportName();
            }
        }
        return str;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        List<Flight> flightList=airportRepository.getAllFlight();
        double min=Double.MAX_VALUE;
        for(Flight flight:flightList){
            if(flight.getFromCity().equals(fromCity) && flight.getToCity().equals(toCity) && min>flight.getDuration()){
                min=flight.getDuration();
            }
        }
        if(min==Double.MAX_VALUE)return -1;
        return min;
    }

    public String bookTicket(Integer flightId, Integer passengerId) {
        return airportRepository.bookTicket(flightId,passengerId);
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        return airportRepository.cancelATicket(flightId,passengerId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        return airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        return airportRepository.getNumberOfPeopleOn(date,airportName);
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        return airportRepository.getAirportNameFromFlightId(flightId);
    }

    public int calculateFlightFare(Integer flightId) {
        return airportRepository.calculateFlightFare(flightId);
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        return airportRepository.calculateRevenueOfAFlight(flightId);
    }
}
