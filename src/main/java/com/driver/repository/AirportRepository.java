package com.driver.repository;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirportRepository {
    public Map<String, Airport> airportMap = new HashMap<>();
    public Map<Integer, Flight> flightMap = new HashMap<>();
    public Map<Integer , Passenger> passengerMap = new HashMap<>();

    public Map<Integer , List<Passenger>> ticketMap = new HashMap<>();
    public Map<Integer , Flight> passengerFlightMap = new HashMap<>();
    private int flightFare = 3000;

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

    public int getNumberOfPeopleOn(Date date, String airportName) {
        int count = 0;
        Airport airport = airportMap.get(airportName);

        for(int flight : flightMap.keySet()){
            if(flightMap.get(flight).getFromCity().equals(airport.getCity())){
                count++;
            }
        }
        return count;
    }


    public int calculateFlightFare(Integer flightId) {
        flightFare = ticketMap.get(flightId).size() * 50;
        return flightFare;
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        return "";
    }
}
