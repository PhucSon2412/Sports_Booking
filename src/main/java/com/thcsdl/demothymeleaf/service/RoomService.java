package com.thcsdl.demothymeleaf.service;

import com.thcsdl.demothymeleaf.entity.Room;
import com.thcsdl.demothymeleaf.repository.RoomRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomService {
    RoomRepository roomRepository;

    public List<Room> getAllRooms(){
        return roomRepository.findAllRoom();
    }

    public List<Room> findByRoomIdOrRoomType(String roomId, String roomType){
        List<Room> rooms = new ArrayList<>();
        if(roomId.isEmpty()){
            if(roomType.equals("UNKNOWN")){
                rooms = roomRepository.findAllRoom();
            }
            else rooms = roomRepository.findByRoomType(roomType);
        }
        else {
            if(roomType.equals("UNKNOWN")){
                rooms = roomRepository.findByRoomType(roomId);
            }
            else rooms = roomRepository.findByRoomIdOrRoomType(roomId, roomType);
        }
        return rooms;
    }


    public void updateRoomType(String id, String roomType){
        Room room =  roomRepository.findByRoomId(id);
        room.setRoomType(roomType);
        roomRepository.save(room);
    }

    public void updateRoomPrice(String id, Double price){
        Room room = roomRepository.findByRoomId(id);
        room.setPrice(price);
        roomRepository.save(room);
    }

    public Room findByRoomId(String roomId){
        return roomRepository.findByRoomId(roomId);
    }

    public List<String> getRoomIdByRoomType(String roomType){
        List<Room> rooms = roomRepository.findAll();
        List<Room> rooms1 = rooms.stream().filter(room -> room.toString3().equals(roomType)).toList();
        return rooms1.stream().map(Room::toString2).toList();
    }

}
