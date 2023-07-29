package ca.fraseric.examscheduler.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ca.fraseric.examscheduler.api.entities.RoomEntity;
import ca.fraseric.examscheduler.api.services.RoomService;

@RestController
public class RoomController {
    
    @Autowired
    private RoomService service;

    @Secured("ROLE_ADMIN")
    @GetMapping("/room")
    public List<RoomEntity> getAllRooms() {
        return service.getAll();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/room", params = "roomType")
    public List<RoomEntity> getRoomsByType(@RequestParam String roomType) {
        return service.getRoomsByType(roomType);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/room/{id}")
    public RoomEntity getRoomById(@PathVariable String id) {
        return service.getRoomById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "request not found"));
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/room")
    public RoomEntity postRoom(@RequestBody RoomEntity newRoom) {
        return service.saveRoom(newRoom);
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/room/{id}")
    public void deleteRoomById(@PathVariable String id) {
        service.deleteRoomById(id);
    }
}
