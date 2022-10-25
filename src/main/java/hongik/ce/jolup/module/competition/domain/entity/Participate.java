package hongik.ce.jolup.module.competition.domain.entity;

import hongik.ce.jolup.BaseTimeEntity;
import hongik.ce.jolup.module.member.domain.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Participate extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Competition competition;

    @Column(nullable = false)
    private Integer win = 0;

    @Column(nullable = false)
    private Integer draw = 0;

    @Column(nullable = false)
    private Integer lose = 0;

    @Column(nullable = false)
    private Integer goalFor = 0;

    @Column(nullable = false)
    private Integer goalAgainst = 0;

    @Builder
    public Participate(Long id, Member member, Competition competition, Integer win, Integer draw, Integer lose, Integer goalFor, Integer goalAgainst) {
        this.id = id;
        this.member = member;
        this.competition = competition;
        this.win = win;
        this.draw = draw;
        this.lose = lose;
        this.goalFor = goalFor;
        this.goalAgainst = goalAgainst;
    }

    public static Participate from(Member member, Competition competition) {
        Participate participate = new Participate();
        participate.member = member;
        participate.competition = competition;
        return participate;
    }

    public void updateMember(Member member) {
        this.member = member;
    }

    // 편의 메서드
    public void addWin(Integer win) {
        this.win += win;
    }

    public void subWin(Integer win) {
        this.win -= win;
    }

    public void addDraw(Integer draw) {
        this.draw += draw;
    }

    public void subDraw(Integer draw) {
        this.draw -= draw;
    }

    public void addLose(Integer lose) {
        this.lose += lose;
    }

    public void subLose(Integer lose) {
        this.lose -= lose;
    }

    public void addGoalFor(Integer goalFor) {
        this.goalFor += goalFor;
    }

    public void subGoalFor(Integer goalFor) {
        this.goalFor -= goalFor;
    }

    public void addGoalAgainst(Integer goalAgainst) {
        this.goalAgainst += goalAgainst;
    }

    public void subGoalAgainst(Integer goalAgainst) {
        this.goalAgainst -= goalAgainst;
    }
}
