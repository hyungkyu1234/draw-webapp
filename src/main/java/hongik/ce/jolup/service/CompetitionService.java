package hongik.ce.jolup.service;

import hongik.ce.jolup.domain.competition.Competition;
import hongik.ce.jolup.domain.competition.CompetitionType;
import hongik.ce.jolup.domain.room.Room;
import hongik.ce.jolup.repository.CompetitionRepository;
import hongik.ce.jolup.dto.CompetitionDto;
import hongik.ce.jolup.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public Long save(String title, CompetitionType competitionType, Long roomId) {
        Room room = roomRepository.findById(roomId).orElse(null);
        Competition competition = Competition.builder().title(title).competitionType(competitionType).room(room).build();
        return competitionRepository.save(competition).getId();
    }

    @Transactional
    public void deleteCompetition(Long id) {
        competitionRepository.deleteById(id);
    }

    @Transactional
    public Long updateCompetition(Long id, String title) {
        Competition competition = competitionRepository.findById(id).orElse(null);
        if (competition == null) {
            return null;
        }
        competition.updateTitle(title);
        return competition.getId();
    }

    public List<Competition> findAll() {
        return competitionRepository.findAll();
    }

    public Competition findOne(Long competitionId) {
        return competitionRepository.findById(competitionId).orElse(null);
    }

    public List<Competition> findCompetitions(Long roomId) {
        return competitionRepository.findByRoomId(roomId);
    }

    public Competition findOne(Long competitionId, Long roomId) {
        return competitionRepository.findByIdAndRoomId(competitionId, roomId).orElse(null);
    }
}
