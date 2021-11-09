package jpabook.model.entity.ch6_2_일대다.단방향;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

class ToManyMemberTest {

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
    public void 일대일_단방향_매핑의_단점() {
        ToManyMember member1 = new ToManyMember("member1");
        ToManyMember member2 = new ToManyMember("member2");

        FromOneTeam team1 = new FromOneTeam("team1");
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);

//        em.persist(member1);    // INSERT
//        em.persist(member2);    // INSERT
        // 본인 테이블에 외래키가 있으면 엔티티의 저장과 연관관계 처리를 INSERT 한 번으로 끝낼 수 있지만
        // 매핑한 객체가 관리하는 외래 키가 다른 테이블에 있으므로
        // 연관관계 처리를 위한 UPDATE문을 추가로 실행해야 한다
        em.persist(team1);  // INSERT-team1, UPDATE-member1.fk, UPDATE-member2.fk
        // 일대다 단방향 매핑보다는 다대일 양방향 매핑을 사용하자
        // 다대일 양방향 매핑은 관리해야 하는 외래 키가 본인 테이블에 있어서 위와 같은 문제가 발생하지 않는다
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