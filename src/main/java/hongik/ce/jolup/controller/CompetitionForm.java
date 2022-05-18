package hongik.ce.jolup.controller;

import hongik.ce.jolup.domain.competition.CompetitionType;
import lombok.*;

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
public class CompetitionForm {
    @NotBlank(message = "대회 이름은 필수 입력 값입니다!")
    @Size(min = 3, max = 30, message = "최소 3글자 최대 30글자로 입력해주세요!")
//    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "대회 이름 형식이 올바르지 않습니다.")
    private String title;

    @NotNull(message = "대회 방식은 필수 입력 값입니다!")
    private CompetitionType competitionType;

    @NotNull(message = "대회 참여 인원 수는 필수 입력 값입니다!")
    private Long headCount;

    @Size(min = 2, max = 20, message = "최소 인원 오류")
    @NotNull(message = "null 오류")
    private List<@NotBlank(message = "참가자 아이디는 필수 입력 값입니다!") String> emails = new ArrayList<>();

    public void addEmail(String email) {
        this.emails.add(email);
    }
}
