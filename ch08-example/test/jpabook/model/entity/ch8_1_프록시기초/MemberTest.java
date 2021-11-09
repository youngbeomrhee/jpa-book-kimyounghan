package jpabook.model.entity.ch8_1_프록시기초;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MemberTest {

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
    void proxyTest() {
        Member member = em.getReference(Member.class, "id1");
        System.out.println("memberProxy = " + member.getClass().getName());

        member.getName();

        System.out.println("memberProxy = " + member.getClass().getName());

        // 프록시 인스턴스의 초기화 여부 확인
        boolean isLoaded = em.getEntityManagerFactory()
            .getPersistenceUnitUtil().isLoaded(member);
        // boolean isLoaded = emf.getPersistenceUnitUtil().isLoaded(member);
        System.out.println(isLoaded);

        System.out.println("memberProxy = " + member.getClass().getName());
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