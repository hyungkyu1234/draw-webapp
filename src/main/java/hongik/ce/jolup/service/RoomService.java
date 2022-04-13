package hongik.ce.jolup.service;

import hongik.ce.jolup.domain.room.Room;
import hongik.ce.jolup.repository.RoomRepository;
import hongik.ce.jolup.dto.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;

    public Long save(RoomDto requestDto) {
        return roomRepository.save(Room.builder()
                .subject(requestDto.getSubject())
                .roomType(requestDto.getRoomType())
                .memNum(requestDto.getMemNum()).build()).getId();
    }

    public RoomDto getRoom(Long id) {
        Optional<Room> roomWrapper = roomRepository.findById(id);
        Room room = roomWrapper.get();

        RoomDto roomDto = RoomDto.builder()
                .id(room.getId())
                .subject(room.getSubject())
                .roomType(room.getRoomType())
                .memNum(room.getMemNum())
                .build();

        return roomDto;
    }
}
