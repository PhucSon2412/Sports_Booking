package com.thcsdl.demothymeleaf.repository;

import com.thcsdl.demothymeleaf.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    List<Room> findByRoomType(String roomType);
}
