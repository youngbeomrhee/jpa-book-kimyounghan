package jpabook.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BulkTest {

    //엔티티 매니저 팩토리 생성
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
    static EntityManager em;
    static EntityTransaction tx;

    @BeforeAll
    public static void setup() {
        em = emf.createEntityManager(); //엔티티 매니저 생성
        tx = em.getTransaction(); //트랜잭션 기능 획득
        tx.begin();

        for (int i = 0; i < 20; i++) {
            em.persist(new Product(i, i));
        }
    }

    // 예제 10.123 UPDATE 벌크 연산
    @Test
    public void UPDATE_벌크_연산() {
        String sql =
            "update Product p " +
            "set p.price = p.price * 1.1 " +
            "where p.stockAmount < :stockAmount ";

        int resultCount = em.createQuery(sql)
            .setParameter("stockAmount", 10)
            .executeUpdate();

        System.out.println(resultCount);
    }

    // 예제 10.124 DELETE 벌크 연산
    @Test
    public void DELETE_벌크_연산() {
        String sql =
            "delete from Product p " +
            "where p.price < :price ";

        int resultCount = em.createQuery(sql)
            .setParameter("price", 10)
            .executeUpdate();

        System.out.println(resultCount);
    }

    @Test
    public void 벌크_연산_시_주의점() {

        em.persist(new Product("productA", 1000));

        // 상품 A 조회(상품A의 가격은 원이다)
        Product productA =
            em.createQuery("select p from Product p where p.name = :name", Product.class)
                .setParameter("name", "productA")
                .getSingleResult();

        // 출력결과 1000
        System.out.println("productA 수정 전 = " + productA.getPrice());

        // 벌크 연산 수행으로 모든 상품 가격 10% 상승
        em.createQuery("update Product p set p.price = p.price * 1.1")
            .executeUpdate();

        // 출력결과 1000
        System.out.println("productA 수정 후 = " + productA.getPrice());

        // 해결책
        em.refresh(productA);
        // or 벌크 연산 먼저 실행
        // or 벌크 연산 수행 후 영속성 컨텍스트 초기화

        // 출력결과 1000
        System.out.println("productA refresh 후 = " + productA.getPrice());

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