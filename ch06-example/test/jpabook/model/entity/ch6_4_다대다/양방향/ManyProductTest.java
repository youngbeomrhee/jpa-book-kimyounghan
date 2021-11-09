package jpabook.model.entity.ch6_4_다대다.양방향;

import jpabook.model.entity.ch6_4_다대다.단방향.FromManyMember;
import jpabook.model.entity.ch6_4_다대다.단방향.ToManyProduct;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManyProductTest {

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
        ManyProduct productA = new ManyProduct();
        productA.setName("상품A");
        em.persist(productA);

        ManyMember member1 = new ManyMember();
        member1.setUsername("회원1");
        member1.getProducts().add(productA);    // 연관관계 설정
        em.persist(member1);
    }

    // 양방향 연관관계로 만들었으므로 product.getMembers()를 사용해서 역방향으로 객체 그래프를 탐색할 수 있다
    @Test
    public void findInverse() {
        ManyProduct product = em.find(ManyProduct.class, 1L);
        List<ManyMember> members = product.getMembers();
        for (ManyMember member : members) {
            System.out.println("member = " + member.getUsername());
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