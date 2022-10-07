package hongik.ce.jolup;

import hongik.ce.jolup.module.competition.application.LeagueTableService;
import hongik.ce.jolup.module.competition.domain.entity.CompetitionType;
import hongik.ce.jolup.module.competition.application.CompetitionService;
import hongik.ce.jolup.module.match.application.LeagueService;
import hongik.ce.jolup.module.match.application.TournamentService;
import hongik.ce.jolup.module.room.domain.entity.Grade;
import hongik.ce.jolup.module.member.application.MemberService;
import hongik.ce.jolup.module.member.domain.entity.Member;
import hongik.ce.jolup.module.room.application.JoinService;
import hongik.ce.jolup.module.room.domain.entity.Room;
import hongik.ce.jolup.module.member.endpoint.form.SignupForm;
import hongik.ce.jolup.module.room.application.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
//        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

//        private final EntityManager em;
//
//        public void dbInit() {
//
//            List<Member> members = new ArrayList<>();
//            for (int i = 0; i < 20; i++) {
//                Member member = createMember(i);
//                members.add(member);
//                em.persist(member);
//            }
//
//            Room room1 = createRoom("private", RoomSetting.PRIVATE);
//            em.persist(room1);
//
//            Belong belong1 = Belong.builder().member(members.get(1)).room(room1).belongType(BelongType.MASTER).build();
//            em.persist(belong1);
//            for (int i = 2; i < 11; i++) {
//                Belong belong = Belong.builder().member(members.get(i)).room(room1).belongType(BelongType.USER).build();
//                em.persist(belong);
//            }
//
//            Room room2 = createRoom("public", RoomSetting.PUBLIC);
//            em.persist(room2);
//
//            Belong belong2 = Belong.builder().member(members.get(1)).room(room2).belongType(BelongType.MASTER).build();
//            em.persist(belong2);
//            for (int i = 11; i < 20; i++) {
//                Belong belong = Belong.builder().member(members.get(i)).room(room2).belongType(BelongType.USER).build();
//                em.persist(belong);
//            }
//
//            Room room3 = createRoom("test123", RoomSetting.PUBLIC);
//            em.persist(room3);
//
//            Belong belong3 = Belong.builder().member(members.get(1)).room(room3).belongType(BelongType.MASTER).build();
//            em.persist(belong3);
//            for (int i = 7; i < 14; i++) {
//                Belong belong = Belong.builder().member(members.get(i)).room(room3).belongType(BelongType.USER).build();
//                em.persist(belong);
//            }

        private final MemberService memberService;
        private final JoinService joinService;
        private final RoomService roomService;
        private final CompetitionService competitionService;
        private final LeagueTableService leagueTableService;
        private final LeagueService leagueService;
        private final TournamentService tournamentService;

        public void dbInit() {

            /**
             * 회원 DB 생성
             */
            List<Member> members = new ArrayList<>();
            for (int i = 0; i <= 100; i++) {
                members.add(memberService.signup(createMember(i)));
            }
            Member member = members.get(1);

            Room room1 = createRoom("1", false);
            Long room1Id = roomService.saveRoom(room1);

            joinService.save(members.get(1).getId(), room1Id, Grade.ADMIN);
            for (int i = 2; i <= 100; i++) {
                joinService.save(members.get(i).getId(), room1Id, Grade.USER);
            }

            for (int i = 2; i <= 50; i++) {
                Room room = createRoom(Integer.toString(i), true);
                Long roomId = roomService.saveRoom(room);

                joinService.save(members.get(1).getId(), roomId, Grade.ADMIN);
            }

            /**
             * 대회, 경기 테스트 DB 생성
             */

            /*Long worldCupId = competitionService.save("zxcv", CompetitionType.TOURNAMENT, room1Id).getId();
            List<Long> memberId32 = new ArrayList<>();
            for (int i = 1; i <= 32; i++) {
                memberId32.add(members.get(i).getId());
            }
            tournamentService.save(memberId32, worldCupId);

            Long eplId = competitionService.save("asdf", CompetitionType.LEAGUE, room1Id).getId();
            List<Long> memberId20 = new ArrayList<>();
            for (int i = 1; i <= 20; i++) {
                memberId20.add(members.get(i).getId());
            }
            leagueTableService.save(memberId20, eplId);
            leagueService.save(memberId20, eplId);*/
        }

        private Room createRoom(String title, boolean access) {
            return Room.builder().title(title).access(access).build();
        }

        private SignupForm createMember(int i) {
            SignupForm signupForm = new SignupForm();
            signupForm.setEmail(Integer.toString(i));
            signupForm.setPassword("1");
            signupForm.setName("user" + i);
            return signupForm;
        }
    }
}
