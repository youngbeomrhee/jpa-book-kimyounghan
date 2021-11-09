package jpabook.start;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.List;

/**
 * @author holyeye
 */
public class JpaMain {

    private static Logger logger = LoggerFactory.getLogger(JpaMain.class);

    public static void main(String[] args) {

        // Entity manager factory 생성. 비용이 아주 많이 든다
        // 여러 스레드가 동시에 접근해도 안전 => 스레드 간에 공유해도 된다
        // JPA 구현체들은 EntityManagerFactory를 생성할 때 커넥션풀도 만든다
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

        // EMF에서 Entity manager 생성. 비용이 거의 안 든다
        // 여러 스레드가 동시에 접근하면 동시성 문제가 발생. 스레드 간에 절대 공유하면 안 된다
        EntityManager em = emf.createEntityManager();

        // EM은 데이터베이스 연결이 꼭 필요한 시점까지 커넥션을 얻지 않는다
        // 트랜잭션 기능 획득 => 커넥션 획득
        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin(); //트랜잭션 시작
            logic(em);  //비즈니스 로직
            tx.commit();//트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static void logic(EntityManager em) {

        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("지한");
        member.setAge(2);

        System.out.print("############### member is managed : ");
        System.out.println((em.contains(member)));


        //등록
        em.persist(member);

        System.out.print("############### member is managed : ");
        System.out.println((em.contains(member)));

        //수정
        member.setAge(20);

        //한 건 조회
        Member findMember = em.find(Member.class, id);
        Member findMember2 = em.find(Member.class, id);

        // flush가 발생하기 전에 clear
        // 실제 DB 반영되지 않는다
//        em.clear();
        em.close();

        System.out.print("# findMember == findMember2 : ");
        System.out.println(findMember == findMember2);
        System.out.println("# findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

//        em.setFlushMode(FlushModeType.COMMIT);

        //목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("# members.size=" + members.size());


        // detach
//        em.detach(member);
//        System.out.print("############### member is managed : ");
//        System.out.println((em.contains(member)));

        // 삭제
//        em.remove(member);

        System.out.print("############### member is managed : ");
        System.out.println((em.contains(member)));

    }
}
