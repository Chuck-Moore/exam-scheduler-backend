package ca.fraseric.examscheduler.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.fraseric.examscheduler.api.entities.RoomEntity;
import ca.fraseric.examscheduler.api.repositories.RoomRepository;

@Service
public class RoomService {
    
    @Autowired
    private RoomRepository repo;

    public List<RoomEntity> getAll() {
        return repo.findAll();
    }

    public List<RoomEntity> getRoomsByType(String type) {
        return repo.findByRoomType(RoomEntity.RoomType.valueOf(type));
    }

    public List<RoomEntity> getRoomsByType(RoomEntity.RoomType type) {
        return repo.findByRoomType(type);
    }

    public Optional<RoomEntity> getRoomById(String id) {
        return repo.findById(id);
    }

    public RoomEntity saveRoom(RoomEntity newEntity) {
        return repo.save(newEntity);
    }

    public void deleteRoomById(String id) {
        repo.deleteById(id);
    }
}
