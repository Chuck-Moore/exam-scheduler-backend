package ca.fraseric.examscheduler.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.fraseric.examscheduler.api.entities.RoomEntity;

public interface RoomRepository extends JpaRepository<RoomEntity, String>{
    
    List<RoomEntity> findByRoomType(RoomEntity.RoomType roomType);

}
