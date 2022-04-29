package hongik.ce.jolup.service;

import hongik.ce.jolup.domain.room.Room;
import hongik.ce.jolup.repository.RoomRepository;
import hongik.ce.jolup.dto.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;

    public Long saveRoom(RoomDto roomDto) {
        return roomRepository.save(Room.builder()
                .title(roomDto.getTitle())
                .roomType(roomDto.getRoomType())
                .memNum(roomDto.getMemNum()).build()).getId();
    }

    public List<RoomDto> findAll() {
        List<Room> rooms = roomRepository.findAll();
        List<RoomDto> roomDtos = rooms.stream()
                .map(Room::toDto)
                .collect(Collectors.toList());
        return roomDtos;
    }

    public RoomDto getRoom(Long roomId) {
        Optional<Room> roomWrapper = roomRepository.findById(roomId);
        if (roomWrapper.isEmpty()) {
            return null;
        }
        Room room = roomWrapper.get();
        RoomDto roomDto = room.toDto();
        return roomDto;
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
