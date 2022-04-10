package hongik.ce.jolup.web.controller;

import hongik.ce.jolup.domain.accounts.Account;
import hongik.ce.jolup.domain.rooms.JoinRoom;
import hongik.ce.jolup.domain.rooms.RoomType;
import hongik.ce.jolup.service.JoinRoomService;
import hongik.ce.jolup.service.RoomService;
import hongik.ce.jolup.web.dto.RoomSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class RoomController {

    private final RoomService roomService;
    private final JoinRoomService joinRoomService;

    @GetMapping("/createRoom")
    public String makeroom(Model model) {
        model.addAttribute("roomTypes", RoomType.values());
        return "createRoom";
    }

    @PostMapping("/createRoom")
    public String createRoom(RoomSaveRequestDto requestDto, @AuthenticationPrincipal Account account) {
        joinRoomService.createJoinRoom(account, roomService.createRoom(requestDto));
        return "redirect:/myRoom";
    }

    @GetMapping("/myRoom")
    public String myRoomList(Model model, @AuthenticationPrincipal Account account) {
        List<JoinRoom> joinRooms = joinRoomService.findMyJoinRooms(account);
        model.addAttribute("joinRooms", joinRooms);
        return "myRoom";
    }
}
