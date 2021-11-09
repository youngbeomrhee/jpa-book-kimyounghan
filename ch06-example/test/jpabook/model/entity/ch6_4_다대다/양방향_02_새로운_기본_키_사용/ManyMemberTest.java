package jpabook.model.entity.ch6_4_다대다.양방향_02_새로운_기본_키_사용;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManyMemberTest {

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
    public void save() {

        // 회원 저장
        ManyMember member1 = new ManyMember();
        member1.setUsername("회원3");
        em.persist(member1);

        // 상품 저장
        ManyProduct productA = new ManyProduct();
        productA.setName("상품3");
        em.persist(productA);

        // 주문 저장
        Order order = new Order();
        order.setMember(member1);   // 주문회원 - 연관관계 설정
        order.setProduct(productA);   // 주문상품 - 연관관계 설정
        order.setOrderAmount(2);    // 주문 수량
        em.persist(order);
    }

    // 양방향 연관관계로 만들었으므로 product.getMembers()를 사용해서 역방향으로 객체 그래프를 탐색할 수 있다
    @Test
    public void findInverse() {
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