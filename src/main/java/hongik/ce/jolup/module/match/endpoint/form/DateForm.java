package hongik.ce.jolup.module.match.endpoint.form;

import hongik.ce.jolup.module.match.domain.entity.Match;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DateForm {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDateTime;

    public static DateForm from(Match match) {
        DateForm dateForm = new DateForm();
        dateForm.startDateTime = match.getStartDateTime();
        return dateForm;
    }
}
