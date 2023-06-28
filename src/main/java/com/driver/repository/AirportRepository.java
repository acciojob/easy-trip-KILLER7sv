package com.driver.repository;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AirportRepository {
    Map<String, Airport> airportMap = new HashMap<>();
    Map<Integer, Flight> flightMap = new HashMap<>();
    Map<Integer , Passenger> passengerMap = new HashMap<>();

    Map<Integer , List<Passenger>> ticketMap = new HashMap<>();
    Map<Integer , Flight> passengerFlightMap = new HashMap<>();


    public void addAirport(Airport airport) {
        airportMap.put(airport.getAirportName() , airport);
    }

    public String getLargestAirportName() {
        String currans = "";
        int curr = Integer.MIN_VALUE;
        for(String airport : airportMap.keySet()) {
            if (airportMap.get(airport).getNoOfTerminals() > curr) {
                curr = airportMap.get(airport).getNoOfTerminals();
                currans = airport;
            }
        }
        return currans;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        double ans = -1;
        double currDuration = Double.MAX_VALUE;
        for(int flights : flightMap.keySet()){
            if(flightMap.get(flights).getFromCity().equals(fromCity) && flightMap.get(flights).getToCity().equals(toCity)){
                if (flightMap.get(flights).getDuration() < currDuration){
                    ans = flightMap.get(flights).getDuration();
                    currDuration = flightMap.get(flights).getDuration();
                }
            }
        }
        return ans;
    }

    public String addPassenger(Passenger passenger) {
        passengerMap.put(passenger.getPassengerId() , passenger);
        return "SUCCESS";
    }

    public String getAirportNameFromFlightId(Integer flightId) {

        City city = flightMap.get(flightId).getFromCity();
        for(String airport : airportMap.keySet()){
            if(airportMap.get(airport).getCity().equals(city)){
                return airport;
            }
        }

        return null;
    }

    public String addFlight(Flight flight) {
        flightMap.put(flight.getFlightId(), flight);
        return "SUCCESS";
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        if(ticketMap.containsKey(flightId)){
            if(ticketMap.get(flightId).size() > flightMap.get(flightId).getMaxCapacity() || passengerFlightMap.containsKey(passengerId)){
                return "FAILURE";
            }
        }
        List<Passenger> currpassengers = ticketMap.get(flightId);
        currpassengers.add(passengerMap.get(passengerId));
        ticketMap.put(flightId , currpassengers);
        passengerFlightMap.put(passengerId , flightMap.get(flightId));
        return "SUCCESS";
    }
}
