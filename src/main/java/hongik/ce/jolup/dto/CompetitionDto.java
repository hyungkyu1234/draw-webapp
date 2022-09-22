package hongik.ce.jolup.dto;

import hongik.ce.jolup.domain.competition.Competition;
import hongik.ce.jolup.domain.competition.CompetitionType;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionDto {
    private Long id;
    private String name;
    private CompetitionType type;
    private RoomDto roomDto;

    public Competition toEntity() {
        return Competition.builder()
                .id(id)
                .name(name)
                .type(type)
                .room(roomDto.toEntity())
                .build();
    }
}
