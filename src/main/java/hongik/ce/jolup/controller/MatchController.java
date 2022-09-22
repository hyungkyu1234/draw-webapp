package hongik.ce.jolup.controller;

import hongik.ce.jolup.domain.belong.Belong;
import hongik.ce.jolup.domain.belong.BelongType;
import hongik.ce.jolup.domain.competition.Competition;
import hongik.ce.jolup.domain.match.Match;
import hongik.ce.jolup.domain.match.MatchStatus;
import hongik.ce.jolup.domain.member.Member;
import hongik.ce.jolup.service.BelongService;
import hongik.ce.jolup.service.CompetitionService;
import hongik.ce.jolup.service.JoinService;
import hongik.ce.jolup.service.MatchService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/rooms/{roomId}/competitions/{competitionId}/matches")
public class MatchController {

    private final MatchService matchService;
    private final JoinService joinService;
    private final BelongService belongService;
    private final CompetitionService competitionService;

    @GetMapping("/{matchId}")
    public String matchDetail(@PathVariable("roomId") Long roomId,
                              @PathVariable("competitionId") Long competitionId,
                              @PathVariable("matchId") Long matchId,
                              @AuthenticationPrincipal Member member,
                              Model model) {

        log.info("GET MatchDetail : roomId = {}, competitionId = {}, matchId = {}", roomId, competitionId, matchId);
        Belong myBelong = belongService.findOne(member.getId(), roomId);
        if (myBelong == null || !myBelong.getBelongType().equals(BelongType.ADMIN)) {
            return "error";
        }

        Competition competition = competitionService.findOne(competitionId, roomId);
        if (competition == null) {
            return "error";
        }

        Match match = matchService.findByIdAndCompetitionId(matchId, competitionId);
        if (match == null) {
            return "error";
        }
        MatchDto matchDto = MatchDto.builder().id(match.getId()).homeScore(match.getHomeScore()).awayScore(match.getAwayScore()).build();
        if (match.getHome() != null) {
            matchDto.setHomeId(match.getHome().getId());
            matchDto.setHome(match.getHome().getEmail() + "(" + match.getHome().getName() + ")");
        }
        if (match.getAway() != null) {
            matchDto.setAwayId(match.getAway().getId());
            matchDto.setAway(match.getAway().getEmail() + "(" + match.getAway().getName() + ")");
        }
        model.addAttribute("competition", competition);
        model.addAttribute("match", matchDto);
        log.info("matchDto={}", matchDto);
        return "matches/matchDetail";
    }

    @GetMapping("/{matchId}/update")
    public String updateForm(@PathVariable("roomId") Long roomId,
                             @PathVariable("competitionId") Long competitionId,
                             @PathVariable("matchId") Long matchId,
                             @AuthenticationPrincipal Member member,
                             Model model) {

        log.info("GET updateMatch : roomId = {}, competitionId = {}, matchId = {}", roomId, competitionId, matchId);
        Belong myBelong = belongService.findOne(member.getId(), roomId);
        if (myBelong == null || !myBelong.getBelongType().equals(BelongType.ADMIN)) {
            return "error";
        }

        Competition competition = competitionService.findOne(competitionId, roomId);
        if (competition == null) {
            return "error";
        }

        Match match = matchService.findByIdAndCompetitionId(matchId, competitionId);
        if (match == null) {
            return "error";
        }

        /*UpdateMatchForm form = UpdateMatchForm.builder().matchId(match.getId())
                .homeScore(match.getScore().getHomeScore()).awayScore(match.getScore().getAwayScore()).build();*/
        UpdateMatchForm form = UpdateMatchForm.builder().matchId(match.getId())
                .homeScore(match.getHomeScore()).awayScore(match.getAwayScore()).build();
        if (match.getHome() != null) {
            form.setHomeId(match.getHome().getId());
            form.setHome(match.getHome().getEmail() + "(" + match.getHome().getName() + ")");
        }
        if (match.getAway() != null) {
            form.setAwayId(match.getAway().getId());
            form.setAway(match.getAway().getEmail() + "(" + match.getAway().getName() + ")");
        }
        log.info("matchUpdateForm = {}", form);
        model.addAttribute("form", form);
        return "matches/update";
    }

    @PostMapping("/{matchId}/update")
    public String update(@PathVariable("roomId") Long roomId,
                         @PathVariable("competitionId") Long competitionId,
                         @PathVariable("matchId") Long matchId, @AuthenticationPrincipal Member member,
                         @ModelAttribute("form") @Valid UpdateMatchForm form,
                         BindingResult bindingResult) {

        log.info("POST updateMatch : roomId = {}, competitionId = {}, matchId = {}", roomId, competitionId, matchId);
        Belong myBelong = belongService.findOne(member.getId(), roomId);
        if (myBelong == null || !myBelong.getBelongType().equals(BelongType.ADMIN)) {
            return "error";
        }
        Competition competition = competitionService.findOne(competitionId, roomId);
        if (competition == null) {
            return "error";
        }

        Match match = matchService.findByIdAndCompetitionId(matchId, competitionId);
        if (match == null) {
            return "error";
        }

        if (bindingResult.hasErrors()) {
            return "matches/update";
        }

        log.info("matchUpdateForm = {}", form);

        joinService.update(form.getHomeId(), form.getAwayId(), competitionId,
                form.getMatchId(), form.getHomeScore(), form.getAwayScore(), MatchStatus.END);
        return "redirect:/rooms/{roomId}/competitions/{competitionId}";
    }

    @PostMapping("/{matchId}/init")
    public String init(@PathVariable("roomId") Long roomId,
                       @PathVariable("competitionId") Long competitionId,
                       @PathVariable("matchId") Long matchId,
                       @AuthenticationPrincipal Member member) {

        log.info("POST initMatch : roomId = {}, competitionId = {}, matchId = {}", roomId, competitionId, matchId);

        Belong myBelong = belongService.findOne(member.getId(), roomId);
        if (myBelong == null || !myBelong.getBelongType().equals(BelongType.ADMIN)) {
            return "error";
        }
        Competition competition = competitionService.findOne(competitionId, roomId);
        if (competition == null) {
            return "error";
        }

        Match match = matchService.findByIdAndCompetitionId(matchId, competitionId);
        if (match == null) {
            return "error";
        }
        Long homeId = match.getHome() != null ? match.getHome().getId() : null;
        Long awayId = match.getAway() != null ? match.getAway().getId() : null;
        joinService.update(homeId, awayId, competitionId,
                matchId, 0, 0, MatchStatus.READY);
        return "redirect:/rooms/{roomId}/competitions/{competitionId}";
    }

    @ToString
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class MatchDto {
        private Long id;

        private Long homeId;
        private String home;
        private String away;
        private Long awayId;

        private Integer homeScore;
        private Integer awayScore;
    }

    @ToString @Getter @Setter @Builder
    @NoArgsConstructor @AllArgsConstructor
    private static class UpdateMatchForm {

        @NotNull
        private Long matchId;

        private Long homeId;
        private String home;
        private String away;
        private Long awayId;

        @Min(value = 0, message = "최소 0점이어야 합니다.")
        @Max(value = 100, message = "최대 100점이어야 합니다.")
        private Integer homeScore;

        @Min(value = 0, message = "최소 0점이어야 합니다.")
        @Max(value = 100, message = "최대 100점이어야 합니다.")
        private Integer awayScore;
    }
}
