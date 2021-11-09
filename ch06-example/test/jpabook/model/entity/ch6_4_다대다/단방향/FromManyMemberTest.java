package jpabook.model.entity.ch6_4_다대다.단방향;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FromManyMemberTest {

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
    public void 다대다_단방향_save() {
        ToManyProduct productA = new ToManyProduct();
        productA.setName("상품A");
        em.persist(productA);

        FromManyMember member1 = new FromManyMember();
        member1.setUsername("회원1");
        member1.getProducts().add(productA);    // 연관관계 설정
        em.persist(member1);
    }

    @Test
    public void 다대다_단방향_find() {
        FromManyMember member = em.find(FromManyMember.class, 2L);
        List<ToManyProduct> products = member.getProducts();    // 객체 그래프 탐색
        for (ToManyProduct product : products) {
            System.out.println("product.name = " + product.getName());
        }
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