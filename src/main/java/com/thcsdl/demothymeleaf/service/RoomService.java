package com.thcsdl.demothymeleaf.service;

import com.thcsdl.demothymeleaf.dto.request.RoomCreateRequest;
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

    public Room createRoom(RoomCreateRequest request){
        Room room = new Room();
        room.setRoomType(request.getRoomType());
        room.setPrice(request.getPrice());
        return roomRepository.save(room);
    }

    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    public List<Room> getRoomByRoomType(String roomType){
        return roomRepository.findByRoomType(roomType);
    }

    public Room updateRoom(Room room){
        Room room1 = roomRepository.findById(room.getId()).orElseThrow(RuntimeException::new);
        room1.setRoomType(room.getRoomType());
        room1.setPrice(room.getPrice());
        return roomRepository.save(room1);
    }

    public void deleteRoom(String Id){
        roomRepository.deleteById(Id);
    }

    public List<Room> getRoomsByIdOrRoomType(String roomId, String roomType){
        List<Room> rooms = roomRepository.findAll();
        if(roomId!=""){
            rooms = roomRepository.findAll().stream().filter(room -> room.getId().equals(roomId)).toList();
        }
        List<Room> rooms1 = rooms;
        if(roomType!=""){
            rooms1 = rooms.stream().filter(room -> room.getRoomType().equals(roomType)).toList();
        }
        return rooms1;
    }

    public List<String> getRoomIdByRoomType(String roomType){
        List<Room> rooms = roomRepository.findAll();
        List<Room> rooms1 = rooms.stream().filter(room -> room.toString3().equals(roomType)).toList();
        return rooms1.stream().map(Room::toString2).toList();
    }

}
