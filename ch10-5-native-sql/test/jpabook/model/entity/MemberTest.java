package jpabook.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.apple.laf.AquaButtonBorder.Named;
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

class MemberTest {

    //엔티티 매니저 팩토리 생성
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
    static EntityManager em;
    static EntityTransaction tx;

    static String memberId = "member1";

    private static Member member1 = new Member(memberId, "회원1", 39);

    @BeforeAll
    public static void setup() {
        em = emf.createEntityManager(); //엔티티 매니저 생성
        tx = em.getTransaction(); //트랜잭션 기능 획득
        tx.begin();

        em.persist(member1);
    }

    // 예제 10.106 엔티티 조회 코드
    // JPA가 지원하는 영속성 컨텍스트의 기능을 그대로 사용할 수 있다
    @Test
    public void 엔티티조회() {
        // SQL 정의
        String sql = "SELECT ID, AGE, NAME, TEAM_ID " +
            "FROM MEMBER WHERE AGE > ?";
        Query nativeQuery = em.createNativeQuery(sql, Member.class)
            .setParameter(1, 20);

        List<Member> resultList = nativeQuery.getResultList();

        for (Member member : resultList) {
            System.out.println(member);
        }
    }

    @Test
    public void 엔티티조회_파라미터이름() {
        // SQL 정의
        String sql = "SELECT ID, AGE, NAME, TEAM_ID " +
            "FROM MEMBER WHERE AGE > :age";
        Query nativeQuery = em.createNativeQuery(sql, Member.class)
            .setParameter("age", 20);

        List<Member> resultList = nativeQuery.getResultList();

        for (Member member : resultList) {
            System.out.println(member);
        }
    }

    // 예제 10.107 값 조회
    // 단순히 스칼라 값만을 조회했을 뿐이므로 결과를 영속성 컨텍스트가 관리하지 않는다
    @Test
    public void 값조회() {
        // SQL 정의
        String sql = "SELECT ID, AGE, NAME, TEAM_ID " +
            "FROM MEMBER WHERE AGE > ?";

        // 엔티티로 조회하지 않고 단순히 값으로 조회하려면 em.createNativeQuery의 두 번째 파라미터를 사용하지 않으면 된다
        Query nativeQuery = em.createNativeQuery(sql)
            .setParameter(1, 20);

        List<Object[]> resultList = nativeQuery.getResultList();

        for (Object[] row : resultList) {
            System.out.println("id = " + row[0]);
            System.out.println("age = " + row[1]);
            System.out.println("name = " + row[2]);
            System.out.println("team_id = " + row[3]);
        }
    }

    // 예제 10.108 결과 매핑 사용
    // 엔티티와 스칼라 값을 함께 조회하는 등 매핑이 복잡해지면 @SqlResultSetMapping을 정의해서 결과 매핑을 해야 한다.
    @Test
    public void 결과_매핑_사용() {
        Order order = new Order(member1);

        em.persist(order);
        // SQL 정의
        String sql = "SELECT M.ID, NAME, AGE, TEAM_ID, I.ORDER_COUNT " +
            "FROM MEMBER M " +
            "LEFT JOIN " +
            "   (SELECT IM.ID, COUNT(*) AS ORDER_COUNT " +
            "   FROM ORDERS O, MEMBER IM " +
            "   WHERE O.MEMBER_ID = IM.ID " +
            "   GROUP BY IM.ID) I " +
            "ON M.ID = I.ID ";

        Query nativeQuery = em.createNativeQuery(sql, "memberWithOrderCount");

        List<Object[]> resultList = nativeQuery.getResultList();

        for (Object[] row : resultList) {
            Member member = (Member) row[0];
            BigInteger orderCount = (BigInteger) row[1];

            System.out.println("member = " + member);
            System.out.println("orderCount = " + orderCount);
        }
    }

    // 예제 10.109 표준 명세 예제
    // 엔티티와 스칼라 값을 함께 조회하는 등 매핑이 복잡해지면 @SqlResultSetMapping을 정의해서 결과 매핑을 해야 한다.
    @Test
    public void 표준_명세_예제() {
        Order order = new Order(member1);
        Item item = new Item(memberId);
        em.persist(order);

        Query nativeQuery = em.createNativeQuery(
            "SELECT o.order_id AS order_id, " +
                    "o.quantity AS order_quantity, " +
                    "o.item AS order_item, " +
                    "i.name AS item_name " +
                "FROM Orders o, Item i " +
                "WHERE (order_quantity > 25) " +
                "   AND (order_item = i.id)",
            "OrderResults"
        );

        List<Object[]> resultList = nativeQuery.getResultList();

        for (Object[] row : resultList) {
            Order order1 = (Order) row[0];

            System.out.println("order1 = " + order1);
        }
    }


    // Named Query
    @Test
    public void Named_Query_사용() {
        Order order = new Order(member1);
        em.persist(order);

        TypedQuery<Member> nativeQuery = em.createNamedQuery("Member.memberSQL", Member.class)
            .setParameter(1, 20);

        List<Member> resultList = nativeQuery.getResultList();

        for (Member member : resultList) {
            System.out.println("member = " + member);
        }
    }

    // 예제 10.113 Named Query 결과 매핑 사용
    @Test
    public void Named_Query_결과매핑_사용() {
        Order order = new Order(member1);
        em.persist(order);

        List<Object[]> resultList = em.createNamedQuery("Member.memberWithOrderCount").getResultList();

        for (Object[] row : resultList) {
            Member member = (Member) row[0];
            BigInteger orderCount = (BigInteger) row[1];

            System.out.println("member = " + member);
            System.out.println("orderCount = " + orderCount);
        }
    }

    // xml Named Query 결과 매핑 사용
    @Test
    public void xml_Named_Query_결과매핑_사용() {
        Order order = new Order(member1);
        em.persist(order);

        List<Object[]> resultList = em.createNamedQuery("Member.memberWithOrderCountXml").getResultList();

        for (Object[] row : resultList) {
            Member member = (Member) row[0];
            BigInteger orderCount = (BigInteger) row[1];

            System.out.println("member = " + member);
            System.out.println("orderCount = " + orderCount);
        }
    }

    // 예제 10.115 네이티브 SQL과 페이징 처리
    @Test
    public void 네이티브_SQL과_페이징_처리() {
        // SQL 정의
        String sql = "SELECT ID, AGE, NAME, TEAM_ID FROM MEMBER";
        Query nativeQuery = em.createNativeQuery(sql, Member.class)
            .setFirstResult(10)
            .setMaxResults(20);

        List<Member> resultList = nativeQuery.getResultList();

        for (Member member : resultList) {
            System.out.println(member);
        }
    }

    // 예제 10.117 순서 기반 파라미터 호출
    @Test
    public void 순서기반_파라미터_호출() {
        StoredProcedureQuery spq = em.createStoredProcedureQuery("proc_multiply");
        spq.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        spq.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);

        spq.setParameter(1, 100);
        spq.execute();

        Integer out = (Integer) spq.getOutputParameterValue(2);
        assertEquals(200, out);
    }

    // 예제 10.117 이름 기반 파라미터 호출
    @Test
    public void 이름기반_파라미터_호출() {
        StoredProcedureQuery spq = em.createStoredProcedureQuery("proc_multiply");
        spq.registerStoredProcedureParameter("inParam", Integer.class, ParameterMode.IN);
        spq.registerStoredProcedureParameter("outParam", Integer.class, ParameterMode.OUT);

        spq.setParameter("inParam", 100);
        spq.execute();

        Integer out = (Integer) spq.getOutputParameterValue("outParam");
        assertEquals(200, out);
    }

    // 예제 10.122 xml Named 스토어드 프로시저 사용
    @Test
    public void xml_Named_스토어드_프로시저_사용() {
        StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("Proc.multiply");

        spq.setParameter("inParam", 100);
        spq.execute();

        Integer out = (Integer) spq.getOutputParameterValue("outParam");
        assertEquals(200, out);
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