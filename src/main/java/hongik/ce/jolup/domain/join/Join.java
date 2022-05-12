package hongik.ce.jolup.domain.join;

import hongik.ce.jolup.domain.Time;
import hongik.ce.jolup.domain.result.Result;
import hongik.ce.jolup.domain.room.Room;
import hongik.ce.jolup.domain.user.User;
import hongik.ce.jolup.dto.JoinDto;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "joins")
public class Join extends Time {
    /*
    drop table if exists join_room CASCADE;
    create table join_room(
    join_room_id bigint generated by default as identity,
    user_id bigint,
    room_id bigint,
    room_role enum('MASTER', 'MANGER', 'GUEST') not null,
    primary key(join_room_id),
    foreign key(user_id) references account(user_id),
    foreign key(room_id) references room(room_id)
    );
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "join_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Embedded
    private Result result;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JoinRole joinRole;

    @Builder
    public Join(Long id, User user, Room room, Result result, JoinRole joinRole) {
        this.id = id;
        this.user = user;
        this.room = room;
        this.result = result;
        this.joinRole = joinRole;
    }

    public JoinDto toDto () {
        return JoinDto.builder()
                .id(id)
                .userDto(user.toDto())
                .roomDto(room.toDto())
                .result(result)
                .joinRole(joinRole)
                .build();
    }

    public Join update(Result result) {
        this.result = result;
        return this;
    }

    public void setUser(User user) {
        if (this.user != null) {
            this.user.getJoins().remove(this);
        }
        this.user = user;
        user.getJoins().add(this);
    }

    public void setRoom(Room room) {
        if (this.room != null) {
            this.room.getJoins().remove(this);
        }
        this.room = room;
        room.getJoins().add(this);
    }
}
