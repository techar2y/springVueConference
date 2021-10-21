package com.arty.springVueConference.controllers;

import com.arty.springVueConference.domains.Room;
import com.arty.springVueConference.repos.IRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/rooms")
public class RoomController
{
    @Autowired
    private IRoomRepository roomRepository;

    @PreAuthorize("hasAuthority('room:read')")
    @GetMapping("/getAllRooms")
    public ResponseEntity<List<Room>> getAllRooms() {
        try {
            List<Room> rooms = roomRepository.findAll();

            if(rooms.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(rooms, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
