package hongik.ce.jolup.controller;

import hongik.ce.jolup.domain.room.RoomType;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString @Builder
public class RoomForm {
    @NotBlank(message = "대회 이름은 필수 입력 값입니다!")
    private String title;

    @NotNull(message = "대회 방식은 필수 입력 값입니다!")
    private RoomType roomType;

    @NotNull(message = "대회 참여 인원 수는 필수 입력 값입니다!")
    private Long memNum;

    @Size(min = 2, message = "최소 인원 오류")
    @NotNull(message = "null 오류")
    private List<@NotBlank(message = "참가자 아이디는 필수 입력 값입니다!") String> emails = new ArrayList<>();

    public void addEmail(String email) {
        this.emails.add(email);
    }
}
