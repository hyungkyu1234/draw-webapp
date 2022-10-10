/*
package hongik.ce.jolup.module.match.application;

import hongik.ce.jolup.module.competition.domain.entity.Competition;
import hongik.ce.jolup.module.competition.domain.entity.Participate;
import hongik.ce.jolup.module.match.domain.entity.Status;
import hongik.ce.jolup.module.match.domain.entity.Match;
import hongik.ce.jolup.module.match.event.MatchUpdatedEvent;
import hongik.ce.jolup.module.member.domain.entity.Member;
import hongik.ce.jolup.module.competition.infra.repository.CompetitionRepository;
import hongik.ce.jolup.module.match.infra.repository.MatchRepository;
import hongik.ce.jolup.module.competition.infra.repository.ParticipateRepository;
import hongik.ce.jolup.module.member.infra.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class LeagueService {

    private final MatchRepository matchRepository;
    private final ParticipateRepository participateRepository;
    private final ApplicationEventPublisher eventPublisher;


    public void createMatches(List<Member> memberList, Competition competition) {
        Collections.shuffle(memberList);
        Set<Match> matches = new HashSet<>();
        int count = memberList.size();
        if (count % 2 == 1) {
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < count/2; j++) {
                    Match match = Match.builder().competition(competition)
                            .home(i % 2 == 0 ? memberList.get((i + j) % count) : memberList.get((i + count - j - 2) % count))
                            .away(i % 2 == 0 ? memberList.get((i + count - j - 2) % count) : memberList.get((i + j) % count))
                            .status(Status.BEFORE)
                            .round(i)
                            .number(j)
                            .homeScore(0).awayScore(0)
                            .build();
                    matches.add(match);
                }
            }
            */
/*if (option.equals(CompetitionOption.DOUBLE)) {
                for (int i = 0; i < count; i++) {
                    for (int j = 0; j < count/2; j++) {
                        Match Match = Match.builder().league(league)
                                .home(i % 2 == 0 ? memberList.get((i + count - j - 2) % count) : memberList.get((i + j) % count))
                                .away(i % 2 == 0 ? memberList.get((i + j) % count) : memberList.get((i + count - j - 2) % count))
                                .status(Status.BEFORE)
                                .round(count + i)
                                .homeScore(0).awayScore(0)
                                .build();
                        MatchRepository.save(Match);
                    }
                }
            }*//*

        }
        else {
            int j;
            Random random = new Random();
            Member fixed = memberList.remove(random.nextInt(count));
            for (int i = 0; i < count - 1; i++) {
                for (j = 0; j < count/2 - 1; j++) {
                    Match match = Match.builder().competition(competition)
                            .home(memberList.get(i % 2 == 0 ? (i + j) % (count - 1) : (i + count - j - 2) % (count - 1)))
                            .away(memberList.get(i % 2 == 0 ? (i + count - j - 2) % (count - 1) : (i + j) % (count - 1)))
                            .status(Status.BEFORE)
                            .round(i)
                            .number(j)
                            .homeScore(0).awayScore(0)
                            .build();
                    matches.add(match);
                }
                Match match = Match.builder().competition(competition)
                        .home(i % 2 == 0 ? memberList.get((i + j) % (count - 1)) : fixed)
                        .away(i % 2 == 0 ? fixed : memberList.get((i + j) % (count - 1)))
                        .status(Status.BEFORE)
                        .round(i)
                        .number(j)
                        .homeScore(0).awayScore(0)
                        .build();
                matches.add(match);
            }
            */
/*if (option.equals(CompetitionOption.DOUBLE)) {
                for (int i = 0; i < count - 1; i++) {
                    for (j = 0; j < count/2 - 1; j++) {
                        Match match = Match.builder().league(league)
                                .home(memberList.get(i % 2 == 0 ? (i + count - j - 2) % (count - 1) : (i + j) % (count - 1)))
                                .away(memberList.get(i % 2 == 0 ? (i + j) % (count - 1) : (i + count - j - 2) % (count - 1)))
                                .status(Status.BEFORE)
                                .round(count - 1 + i)
                                .homeScore(0).awayScore(0)
                                .build();
                        MatchRepository.save(match);
                    }
                    Match match = Match.builder().league(league)
                            .home(i % 2 == 0 ? fixed : memberList.get((i + j) % (count - 1)))
                            .away(i % 2 == 0 ? memberList.get((i + j) % (count - 1)) : fixed)
                            .status(Status.BEFORE)
                            .round(count - 1 + i)
                            .homeScore(0).awayScore(0)
                            .build();
                    MatchRepository.save(match);
                }
            }*//*

        }
        matchRepository.saveAll(matches);
    }

    public void update(Long matchId, Long competitionId, Status status, Integer homeScore, Integer awayScore, Long homeId, Long awayId) {
        Match match = matchRepository.findById(matchId).orElse(null);
        Participate home = participateRepository.findByMemberIdAndCompetitionId(homeId, competitionId).orElse(null);
        Participate away = participateRepository.findByMemberIdAndCompetitionId(awayId, competitionId).orElse(null);
        if (match == null || home == null || away == null) {
            return;
        }

        if (match.getStatus().equals(Status.AFTER)) {
            if (match.getHomeScore() > match.getAwayScore()) {
                home.subWin(1);
                away.subLose(1);
            } else if (match.getHomeScore() < match.getAwayScore()) {
                home.subLose(1);
                away.subWin(1);
            } else {
                home.subDraw(1);
                away.subDraw(1);
            }
            home.subGoalFor(match.getHomeScore());
            home.subGoalAgainst(match.getAwayScore());
            away.subGoalFor(match.getAwayScore());
            away.subGoalAgainst(match.getHomeScore());
        }

        if (status.equals(Status.AFTER)) {
            if (homeScore > awayScore) {
                home.addWin(1);
                away.addLose(1);
            } else if (homeScore < awayScore) {
                home.addLose(1);
                away.addWin(1);
            } else {
                home.addDraw(1);
                away.addDraw(1);
            }
            home.addGoalFor(homeScore);
            home.addGoalAgainst(awayScore);
            away.addGoalFor(awayScore);
            away.addGoalAgainst(homeScore);
        }

        match.updateStatus(status);
        match.updateScore(homeScore, awayScore);
    }

    public void sendAlarm(Match match, Long roomId, Set<Member> members) {
        eventPublisher.publishEvent(new MatchUpdatedEvent(match, roomId, "경기 결과가 수정되었습니다.", members));
    }

    public List<Match> findByCompetitionId(Long competitionId) {
        return matchRepository.findByCompetitionId(competitionId);
    }

    public Match findByIdAndCompetitionId(Long id, Long competitionId) {
        return matchRepository.findByIdAndCompetitionId(id, competitionId).orElse(null);
    }
}*/
