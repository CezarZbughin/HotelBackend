package com.cezar.HotelBackend.configuration;

import com.cezar.HotelBackend.model.EndUser;
import com.cezar.HotelBackend.model.Hotel;
import com.cezar.HotelBackend.model.Room;
import com.cezar.HotelBackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@Configuration
public class MockDataCommandLineRunner implements CommandLineRunner {
    @Autowired
    EndUserRepository endUserRepository;
    @Autowired
    HotelRepository hotelRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start Mocking Data.");
        createUsers();
        createRamadaHotel();
        createGrandHotelItalia();
        createHamptonByHilton();
        System.out.println("Finish Mocking Data.");
    }

    private void createUsers() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();;
        EndUser user = new EndUser();
        user.setUsername("cezar");
        user.setPassword(passwordEncoder.encode("root"));
        endUserRepository.save(user);
    }

    private void createRamadaHotel() {
        Hotel ramadaHotel = new Hotel();
        ramadaHotel.setId(1L);
        ramadaHotel.setName("Hotel Ramada");
        ramadaHotel.setRooms(new ArrayList<>());
        ramadaHotel.setLatitude(46.764654252624204);
        ramadaHotel.setLongitude(23.598674125224626);
        {
            Room room210 = new Room();
            room210.setHotel(ramadaHotel);
            room210.setRoomNumber(210);
            room210.setType(2);
            room210.setPrice(200);
            room210.setAvailable(true);
            ramadaHotel.getRooms().add(room210);

            Room room125 = new Room();
            room125.setHotel(ramadaHotel);
            room125.setRoomNumber(125);
            room125.setType(1);
            room125.setPrice(350);
            room125.setAvailable(true);
            ramadaHotel.getRooms().add(room125);

            Room room87 = new Room();
            room87.setHotel(ramadaHotel);
            room87.setRoomNumber(87);
            room87.setType(1);
            room87.setPrice(300);
            room87.setAvailable(false);
            ramadaHotel.getRooms().add(room87);
        }

        hotelRepository.save(ramadaHotel);
    }

    private void createGrandHotelItalia() {
        Hotel grandHotelItalia = new Hotel();
        grandHotelItalia.setId(2L);
        grandHotelItalia.setName("Grand Hotel Italia");
        grandHotelItalia.setRooms(new ArrayList<>());
        grandHotelItalia.setLatitude(46.7522792440665);
        grandHotelItalia.setLongitude(23.605990381045697);
        {
            Room room41 = new Room();
            room41.setHotel(grandHotelItalia);
            room41.setRoomNumber(41);
            room41.setType(3);
            room41.setPrice(240);
            room41.setAvailable(true);
            grandHotelItalia.getRooms().add(room41);
        }
        hotelRepository.save(grandHotelItalia);
    }

    private void createHamptonByHilton() {
        Hotel hamptonByHilton = new Hotel();
        hamptonByHilton.setId(3L);
        hamptonByHilton.setName("Hampton by Hilton");
        hamptonByHilton.setRooms(new ArrayList<>());
        hamptonByHilton.setLatitude(46.77539900854998);
        hamptonByHilton.setLongitude(23.60182699638966);
        {
            Room room32 = new Room();
            room32.setHotel(hamptonByHilton);
            room32.setRoomNumber(32);
            room32.setType(2);
            room32.setPrice(410);
            room32.setAvailable(false);
            hamptonByHilton.getRooms().add(room32);

            Room room21 = new Room();
            room21.setHotel(hamptonByHilton);
            room21.setRoomNumber(21);
            room21.setType(2);
            room21.setPrice(350);
            room21.setAvailable(true);
            hamptonByHilton.getRooms().add(room21);

            Room room64 = new Room();
            room64.setHotel(hamptonByHilton);
            room64.setRoomNumber(64);
            room64.setType(3);
            room64.setPrice(300);
            room64.setAvailable(true);
            hamptonByHilton.getRooms().add(room64);
        }
        hotelRepository.save(hamptonByHilton);
    }


}

