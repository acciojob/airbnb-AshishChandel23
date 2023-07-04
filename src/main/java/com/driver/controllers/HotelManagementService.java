package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.*;

public class HotelManagementService {

    private HotelManagementRepository repository = new HotelManagementRepository();

    public String addHotel(Hotel hotel) {
        return repository.addHotel(hotel);
    }

    public Integer addUser(User user) {
        return repository.addUser(user);
    }

    public String getHotelWithMostFacilities() {
        List<Hotel> hotels = repository.getAllHotels();
        String hotelName = "";
        int max = Integer.MIN_VALUE;
        for(Hotel hotel : hotels){
            if(max < hotel.getFacilities().size()){
                max = hotel.getFacilities().size();
                hotelName = hotel.getHotelName();
            }
        }
        return hotelName;
    }

    public int bookARoom(Booking booking) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        booking.setBookingId(uuidAsString);
        Optional<Hotel> responsehotel = repository.getHotel(booking.getHotelName());
        if(booking.getNoOfRooms()<=responsehotel.get().getAvailableRooms()){
            booking.setAmountToBePaid(booking.getNoOfRooms()*responsehotel.get().getPricePerNight());
            responsehotel.get().setAvailableRooms(responsehotel.get().getAvailableRooms()-booking.getNoOfRooms());
            repository.bookARoom(booking);
            return booking.getAmountToBePaid();
        }
        return -1;
    }

    public int getBookings(Integer aadharCard) {
        List<Booking> bookings = repository.getAllBookings();
        int countBooking = 0;
        for(Booking booking : bookings){
            if(booking.getBookingAadharCard()==aadharCard){
                countBooking+=1;
            }
        }
        return countBooking;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Optional<Hotel> response = repository.getHotel(hotelName);
        Hotel hotel = response.get();
        HashSet<Facility> set = repository.getFacilities(hotelName);
        for(Facility f : newFacilities){
            if(!set.contains(f)) set.add(f);
        }
        newFacilities = new ArrayList<>(set);
        hotel.setFacilities(newFacilities);
        repository.addHotel(hotel);
        return hotel;
    }
}
