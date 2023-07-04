package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.*;

public class HotelManagementRepository {
    private Map<Integer, User> userDb = new HashMap<>();
    private Map<String, Hotel> hotelDb = new TreeMap<>();
    private Map<String, HashSet<Facility>> hotelFacilitiesDb = new TreeMap<>();
    private Map<String, Booking> bookingDb = new HashMap<>();

    public String addHotel(Hotel hotel) {
        if(hotelDb.containsKey(hotel.getHotelName())){
            return "FAILURE";
        }
        hotelDb.put(hotel.getHotelName(), hotel);
        HashSet<Facility> set = new HashSet<>();
        List<Facility> facilities = hotel.getFacilities();
        for(Facility s : facilities){
            set.add(s);
        }
        hotelFacilitiesDb.put(hotel.getHotelName(), set);
        return "SUCCESS";
    }
    public Optional getHotel(String hotelName){
        if(hotelDb.containsKey(hotelName)){
            return Optional.of(hotelDb.get(hotelName));
        }
        return Optional.empty();
    }
    public HashSet<Facility> getFacilities(String hotelName){
        if(hotelFacilitiesDb.containsKey(hotelName)){
            return hotelFacilitiesDb.get(hotelName);
        }
        return new HashSet<>();
    }
    public List<Hotel> getAllHotels(){
        return new ArrayList<>(hotelDb.values());
    }
    public Integer addUser(User user) {

        userDb.put(user.getaadharCardNo(), user);
        return user.getaadharCardNo();
    }

    public void bookARoom(Booking booking) {
        bookingDb.put(booking.getBookingId(), booking);
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookingDb.values());
    }
}
