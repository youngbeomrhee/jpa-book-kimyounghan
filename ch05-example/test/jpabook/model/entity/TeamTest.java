package jpabook.model.entity;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class TeamTest {

    //엔티티 매니저 팩토리 생성
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
    static EntityManager em;
    static EntityTransaction tx;

    @BeforeAll
    public static void setup() {
        em = emf.createEntityManager(); //엔티티 매니저 생성
        tx = em.getTransaction(); //트랜잭션 기능 획득
        tx.begin();
    }

    @Test
    public void testSave() {
        // 팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        // 회원1 저장
        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1); // 연관관계 설정 member1 -> team1. 회원 -> 팀 참조
        em.persist(member1);    // 저장

        // 회원2 저장
        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1); // 연관관계 설정 member2 -> team1
        em.persist(member2);
    }

    @Test
    public void testObjectGraph() {
        Member member1 = em.find(Member.class, "member1");
        System.out.println(member1.getTeam().getName());
    }

    @Test
    public void testJQPL() {
        String jpql = "select m from Member m join m.team t where " +
                "t.name=:teamName";

        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setParameter("teamName", "팀1")
                .getResultList();

        for (Member member : resultList) {
            System.out.println("[query] member.username=" + member.getUsername());
        }
    }

    @Test
    public void updateRelation() {
        // 새로운 팀2
        Team team2 = new Team("team2", "팀2");
        em.persist(team2);

        // 회원1에 새로운 팀2 설정
        Member member = em.find(Member.class, "member1");
        member.setTeam(team2);
    }

    @Test
    public void deleteRelation() {
        Member member1 = em.find(Member.class, "member1");
        member1.setTeam(null);
    }

    @Test
    public void biDerectionSelect() {
        Team team = em.find(Team.class, "team1");
        List<Member> members = team.getMembers();   // 팀 -> 회원. 객체 그래프 탐색
        for (Member member : members) {
            System.out.println("member.username = " + member.getUsername());
        }
    }

    @Test
    public void bidirectionSave() {
        // 팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        // 회원1 저장
        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1); // 연관관계 설정 member1 -> team1
        em.persist(member1);

        // 회원2 저장
        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1); // 연관관계 설정 member2 -> team1
        em.persist(member2);
    }

    @Test
    public void saveNonOwner() {
        // 회원1 저장
        Member member1 = new Member("member1", "회원1");
        em.persist(member1);

        // 회원2 저장
        Member member2 = new Member("member2", "회원2");
        em.persist(member2);

        Team team1 = new Team("team1", "팀1");
        // 주인이 아닌 곳에 연관관계 설정
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);

        em.persist(team1);
    }

    @Test
    public void test순수한객채_양방향_누락() {
        // 팀1
        Team team1 = new Team("team1", "팀1");
        Member member1 = new Member("member1", "회원1");
        Member member2 = new Member("member2", "회원2");

        member1.setTeam(team1); // 연관관계 설정 member1 -> team1
        member2.setTeam(team1); // 연관관계 설정 member2 -> team1

        List<Member> members = team1.getMembers();
        // FAIL
        // Assertions.assertEquals(members.size(), 2);
        System.out.println("members.size = " + members.size());
    }

    @Test
    public void test순수한객채_양방향() {
        // 팀1
        Team team1 = new Team("team1", "팀1");
        Member member1 = new Member("member1", "회원1");
        Member member2 = new Member("member2", "회원2");

        member1.setTeam(team1); // 연관관계 설정 member1 -> team1
        team1.getMembers().add(member1);    // 연관관계 설정 team1 -> member1
        member2.setTeam(team1); // 연관관계 설정 member2 -> team1
        team1.getMembers().add(member2);    // 연관관계 설정 team1 -> member2

        List<Member> members = team1.getMembers();
        // SUCCESS
        Assertions.assertEquals(members.size(), 2);
        System.out.println("members.size = " + members.size());
    }

    @Test
    public void testORM_양방향() {
        // 팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        Member member1 = new Member("member1", "회원1");

        // 양방향 연관관계 설정
        member1.setTeam(team1); // 연관관계 설정 member1 -> team1
        team1.getMembers().add(member1);    // 연관관계 설정 team1 -> member1
        em.persist(member1);

        Member member2 = new Member("member2", "회원2");

        // 양방향 연관관계 설정
        member2.setTeam(team1); // 연관관계 설정 member2 -> team1
        team1.getMembers().add(member2);    // 연관관계 설정 team1 -> member2
        em.persist(member2);
    }

    @AfterAll
    public static void close() {
        try {
            tx.commit();//트랜잭션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }
}