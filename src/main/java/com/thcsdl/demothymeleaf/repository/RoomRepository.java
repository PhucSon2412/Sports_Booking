package com.thcsdl.demothymeleaf.repository;

import com.thcsdl.demothymeleaf.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    @Query("SELECT r FROM Room r")
    List<Room> findAllRoom();

    @Query("SELECT r FROM Room r WHERE r.id = :id AND r.roomType = :roomType")
    List<Room> findByRoomIdOrRoomType(@Param("id") String id, @Param("roomType") String roomtype);

    @Query("SELECT r FROM Room r WHERE r.roomType = :roomType")
    List<Room> findByRoomType(@Param("roomType") String roomtype);

    @Query("SELECT r FROM Room r WHERE r.id = :id")
    Room findByRoomId(@Param("id") String id);

    @Procedure(name = "UpdateRoomType")
    void updateRoomType(@Param("id") String id, @Param("roomType") String username );

    @Procedure(name = "UpdateRoomPrice")
    void updateRoomPrice(@Param("id") String id, @Param("price") Double price );


}
