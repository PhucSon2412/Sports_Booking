package com.thcsdl.demothymeleaf.controller;

import com.thcsdl.demothymeleaf.dto.request.RoomCreateRequest;
import com.thcsdl.demothymeleaf.entity.Member;
import com.thcsdl.demothymeleaf.entity.Room;
import com.thcsdl.demothymeleaf.repository.MemberRepository;
import com.thcsdl.demothymeleaf.repository.RoomRepository;
import com.thcsdl.demothymeleaf.service.MemberService;
import com.thcsdl.demothymeleaf.service.RoomService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/rooms")
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomController {
    RoomService roomService;
    RoomRepository roomRepository;

    @GetMapping
    public String getAllRooms(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if ( member == null ) {
            return "redirect:/loginOrRegister";
        }
        if ( !member.getRole().equals("ADMIN"))
            return "redirect:/";


        List<Room> rooms = roomRepository.findAll();
        model.addAttribute("rooms", rooms);
        model.addAttribute("roomCreate", new RoomCreateRequest());
        model.addAttribute("roomSearch", new Room());
        return "rooms";
    }

    @PostMapping("/create")
    public String createRoom(@ModelAttribute("roomCreate") RoomCreateRequest request) {
        Room room = new Room();
        room.setRoomType(request.getRoomType());
        room.setPrice(request.getPrice());
        roomRepository.save(room);
        return "redirect:/admin/rooms";
    }

    @PostMapping("/search")
    public String searchRoomByRoomType(@ModelAttribute(name = "roomSearch") Room room, Model model , HttpSession session ) {

        Member member = (Member) session.getAttribute("member");
        if ( member == null ) {
            return "redirect:/loginOrRegister";
        }
        if ( !member.getRole().equals("ADMIN"))
            return "redirect:/";


        model.addAttribute("rooms", roomService.getRoomsByIdOrRoomType(room.getId(),room.getRoomType()));
        model.addAttribute("roomCreate", new RoomCreateRequest());
        return "rooms";
    }

    @PostMapping("/update")
    public String updateRoom(@RequestParam(name = "id") String id, Model model, HttpSession session) {
        Room room = roomRepository.findById(id).orElse(null);
        model.addAttribute("updateRoom", room);
        session.setAttribute("updateRoom",room);
        return "updateRoom";
    }

    @PostMapping("/realupdate")
    public String realUpdateRoom(@ModelAttribute("updateRoom") Room room, HttpSession session) {
        Room room1 = (Room) session.getAttribute("updateRoom");
        room1.setRoomType(room.getRoomType());
        room1.setPrice(room.getPrice());
        roomRepository.save(room1);
        return "redirect:/admin/rooms";
    }

    @PostMapping("/delete")
    public String deleteRoom(@RequestParam(name = "id") String id){
        roomRepository.deleteById(id);
        return "redirect:/admin/rooms";
    }
}
