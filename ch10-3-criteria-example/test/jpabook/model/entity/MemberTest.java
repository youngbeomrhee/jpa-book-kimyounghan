package jpabook.model.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import jpabook.model.domain.MemberDTO;
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

    // 10.3.1 Criteria 기초
    @Test
    void 일반적인_조회() {
        /*
            select
                member0_.MEMBER_ID as MEMBER_I1_0_,
                member0_.age as age2_0_,
                member0_.username as username3_0_
            from
                Member member0_
        */
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // Criteria 생성, 반환 타입 지정
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        Root<Member> m = cq.from(Member.class); // from 절
        cq.select(m);   // select 절

        TypedQuery<Member> query = em.createQuery(cq);
        List<Member> members = query.getResultList();

        System.out.println(members);
    }

    @Test
    void 검색_조건_추가() {
        /*
            select
                member0_.MEMBER_ID as MEMBER_I1_0_,
                member0_.age as age2_0_,
                member0_.username as username3_0_
            from
                Member member0_
            where
                member0_.username=?
            order by
                member0_.age desc
        * */

        // 검색 조건부터 정렬까지 CriteriaBuilder를 사용해서 코드를 완성한다
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // Criteria 생성, 반환 타입 지정
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        // m이 "쿼리 루트" (조회의 시작점). 별칭은 엔티티에만 부여할 수 있다
        Root<Member> m = cq.from(Member.class); // from 절

        // 검색 조건 정의
        Predicate usernameEqual = cb.equal(m.get("username"), "회원1");   // JPQL의 m.username과 같다

        // 정렬 조건 정의
        Order ageDesc = cb.desc(m.get("age"));

        // 쿼리 생성
        cq.select(m)
            .where(usernameEqual)   // where 절 생성
            .orderBy(ageDesc);   // order by 절 생성

        List<Member> members = em.createQuery(cq).getResultList();

        System.out.println(members);
    }

    @Test
    void 숫자_타입_검색() {
        /*
            select
                member0_.MEMBER_ID as MEMBER_I1_0_,
                member0_.age as age2_0_,
                member0_.username as username3_0_
            from
                Member member0_
            where
                member0_.age>10
            order by
                member0_.age desc
        */

        // 검색 조건부터 정렬까지 CriteriaBuilder를 사용해서 코드를 완성한다
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // Criteria 생성, 반환 타입 지정
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        // m이 "쿼리 루트" (조회의 시작점). 별칭은 엔티티에만 부여할 수 있다
        Root<Member> m = cq.from(Member.class); // from 절

        // 타입 정보 필요
//        Predicate ageGt = cb.greaterThan(m.<Integer>get("age"), 10);
        Predicate ageGt = cb.gt(m.<Integer>get("age"), 10);
        // age의 타입 정보를 알지 못하므로 제네릭으로 반환 타입 정보를 명시해야 한다
        // String 같은 문자 타입은 지정하지 않아도 된다

        // 쿼리 생성
        cq.select(m)
            .where(ageGt)
            .orderBy(cb.desc(m.get("age")));

        List<Member> members = em.createQuery(cq).getResultList();

        System.out.println(members);
    }

    // 10.3.2 Criteria 쿼리 생성
    @Test
    void 반환_타입_지정() {
        /*

        */
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // Member를 반환 타입으로 지정
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        Root<Member> m = cq.from(Member.class); // from 절
        cq.select(m);   // select 절

        TypedQuery<Member> query = em.createQuery(cq);

        // 위에서 Member를 타입으로 지정했으므로 따로 지정하지 않아도 Member 타입을 반환
        List<Member> members = query.getResultList();

        System.out.println(members);
    }

    // 예제 10.62
    @Test
    void 완성된_코드() {
        /*
            select
                distinct member0_.username as col_0_0_,
                member0_.age as col_1_0_
            from
                Member member0_
        */
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

        Root<Member> m = cq.from(Member.class); // from 절
        cq.multiselect(m.get("username"), m.get("age")).distinct(true);   // select 절
//        cq.select(cb.array(m.get("username"), m.get("age"))).distinct(true);  // 와 같다

        TypedQuery<Object[]> query = em.createQuery(cq);

        List<Object[]> members = query.getResultList();

        System.out.println(members);
    }

    // 예제 10.63
    @Test
    void NEW_construct() {
        /*
            JPQL : select new jpabook.model.MemberDTO(m.username, m.age)
        */
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<MemberDTO> cq = cb.createQuery(MemberDTO.class);

        Root<Member> m = cq.from(Member.class); // from 절

        cq.select(cb.construct(MemberDTO.class, m.get("username"), m.get("age")));

        TypedQuery<MemberDTO> query = em.createQuery(cq);

        List<MemberDTO> members = query.getResultList();

        System.out.println(members);
    }

    // 예제 10.64
    @Test
    void 튜플() {
        /*
        
        */
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
//        CriteriaQuery<Tuple> cq = cb.createTupleQuery(Tuple.class);   // 위와 같다
        
        Root<Member> m = cq.from(Member.class); // from 절
        cq.multiselect(
            m.get("username").alias("username"),    // 튜플에서 사용할 튜플 별칭
            m.get("age").alias("age")
        );

        TypedQuery <Tuple> query = em.createQuery(cq);
        List<Tuple> members = query.getResultList();
        for (Tuple tuple : members) {
            // 튜플 별칭으로 조회
            String username = tuple.get("username", String.class);
            Integer age = tuple.get("age", Integer.class);
        }

        System.out.println(members);
    }

    // 예제 10.65
    @Test
    void 튜플과_엔티티_조회() {
        /*

        */
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
//        CriteriaQuery<Tuple> cq = cb.createTupleQuery(Tuple.class);   // 위와 같다

        Root<Member> m = cq.from(Member.class); // from 절
        cq.select(cb.tuple(
            m.alias("m"), // 회원 엔티티, 별칭 m
            m.get("username").alias("username")
        ));

        TypedQuery <Tuple> query = em.createQuery(cq);
        List<Tuple> members = query.getResultList();
        for (Tuple tuple : members) {
            // 튜플 별칭으로 조회
            Member member = tuple.get("m", Member.class);
            String username = tuple.get("username", String.class);
        }

        System.out.println(members);
    }

    // 10.3.4 집합
    @Test
    void 집합_예() {
        /*
        JPQL:
        select m.team.name, max(m.age), min(m.age)
        from Member m
        group by m.team.name
         */
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // Criteria 생성, 반환 타입 지정
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

        Root<Member> m = cq.from(Member.class); // from 절

        Expression maxAge = cb.max(m.<Integer>get("age"));
        Expression minAge = cb.min(m.<Integer>get("age"));

        cq.multiselect(m.get("team").get("name"), maxAge, minAge)   // select 절
            .groupBy(m.get("team").get("name"))  // group by
            .having(cb.gt(minAge, 10)) // having (팀에 가장 나이 어린 사람이 10살을 초과하는 팀을 조회
                                                // having min(m.age) > 10과 같다
            .orderBy(cb.desc(m.get("age")))     // JPQL: order by m.age desc
        ;
        TypedQuery<Object[]> query = em.createQuery(cq);
        List<Object[]> members = query.getResultList();

        System.out.println(members);
    }


    // 10.3.6 조인
    @Test
    void 조인_예() {
        /*
         */
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // Criteria 생성, 반환 타입 지정
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

        Root<Member> m = cq.from(Member.class); // from 절

        Join<Member, Team> t = m.join("team", JoinType.INNER);  // 내부조인
        // JoinType.LEFT : 외부조인

        // FETCH JOIN
        // m.fetch("team", JoinType.LEFT);
        // cq.select(m);

        cq.multiselect(m, t)   // select 절
            .where(cb.equal(t.get("name"), "팀A"))
        ;
        TypedQuery<Object[]> query = em.createQuery(cq);
        List<Object[]> members = query.getResultList();

        System.out.println(members);
    }

    // 10.3.7 서브 쿼리
    @Test
    void 서브쿼리_예() {
        /*
         */
        CriteriaBuilder cb = em.getCriteriaBuilder();
        // 메인쿼리
        CriteriaQuery<Member> mainQuery = cb.createQuery(Member.class);
        // 서브쿼리
        Subquery<Double> subQuery = mainQuery.subquery(Double.class);

        Root<Member> m2 = subQuery.from(Member.class); // from 절
        subQuery.select(cb.avg(m2.<Integer>get("age")));

        // 메인 쿼리 생성
        Root<Member> m = mainQuery.from(Member.class);
        mainQuery.select(m)
            .where(cb.ge(m.<Integer>get("age"), subQuery));
    }


    // 예제 10.69 상호 관련 서브 쿼리
    @Test
    void 상호관련_서브쿼리_예() {
        /*
         */
        CriteriaBuilder cb = em.getCriteriaBuilder();
        // 메인쿼리
        CriteriaQuery<Member> mainQuery = cb.createQuery(Member.class);
        // 서브 쿼리에서 사용되는 메인 쿼리의 m
        Root<Member> m = mainQuery.from(Member.class);

        // 서브쿼리 생성
        Subquery<Team> subQuery = mainQuery.subquery(Team.class);
        Root<Member> subM = subQuery.correlate(m);  // 메인 쿼리의 별칭을 가져옴

        Join<Member, Team> t = subM.join("team");
        subQuery.select(t)
            .where(cb.equal(t.get("name"), "팀A"));

        // 메인 쿼리 생성
        mainQuery.select(m)
            .where(cb.exists(subQuery));

        List<Member> members = em.createQuery(mainQuery).getResultList();

        System.out.println(members);
        /*
        JPQL
            select m from Member m
            where m.username in ("회원1", "회원2")
        */
    }

    // 예제 10.61 CASE 문 사용 예
    @Test
    void Case문_예() {
        /*
         */
        CriteriaBuilder cb = em.getCriteriaBuilder();
        // 메인쿼리
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> m = cq.from(Member.class);

        cq.multiselect(
            m.get("username"),
            cb.selectCase()
                .when(cb.ge(m.<Integer>get("age"), 60), 600)
                .when(cb.le(m.<Integer>get("age"), 15), 500)
                .otherwise(1000)
        );
        /*
        JPQL
            select m.username,
                case when m.age>=60 then 600
                    when m.age<=15 then 500
                    else 1000
                end
            from Member m
        */
    }

    // 10.3.10 파라미터 정의
    @Test
    void 파라미터정의_예() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> m = cq.from(Member.class);

        // 정의
        cq.select(m)
            .where(cb.equal(m.get("username"), cb.parameter(String.class, "usernameParam")));

        List<Member> resultList = em.createQuery(cq)
            .setParameter("usernameParam", "회원1")   // 바인딩
            .getResultList();
        /*
        JPQL
            select m from Member m
            where m.username = :usernameParam
        */
    }

    // 10.3.11 네이티브 함수 호출
    @Test
    void 네이티브_함수_호출_예() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Member> m = cq.from(Member.class);
        // 사용하려는 함수 지정
        // 하이버네이트 구현체는 방언에 사용자정의 SQL 함수를 등록해야 호출할 수 있다
        Expression<Long> function = cb.function("SUM", Long.class, m.get("age"));
        cq.select(function);

    }

    // 10.3.12 동적 쿼리
    @Test
    void JPQL_동적_쿼리_예() {

        // 검색 조건
        Integer age = 10;
        String userName = null;
        String teamName = "팀A";

        // JPQL 동적 쿼리 생성
        StringBuilder jpql = new StringBuilder("select m from Member m join m.team t ");
        List<String> criteria = new ArrayList<String>();

        if(age != null) criteria.add(" m.age = :age ");
        if(userName != null) criteria.add(" m.userName = :userName ");
        if(teamName != null) criteria.add(" m.teamName = :teamName ");

        if(criteria.size() > 0) jpql.append(" where ");

        for (int i = 0; i < criteria.size(); i++) {
            if(i > 0) jpql.append(" and ");
            jpql.append(criteria.get(i));
        }
        TypedQuery<Member> query = em.createQuery(jpql.toString(), Member.class);

        if(age != null) query.setParameter("age", age);
        if(userName != null) query.setParameter("userName", userName);
        if(teamName != null) query.setParameter("teamName", teamName);

        List<Member> resultList = query.getResultList();
    }

    // 예제 10.74 Criteria 동적 쿼리
    @Test
    void Criteria_동적_쿼리_예() {

        // 검색 조건
        Integer age = 10;
        String userName = null;
        String teamName = "팀A";

        // Criteria 동적 쿼리 생성
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        Root<Member> m = cq.from(Member.class);
        Join<Member, Team> t = m.join("team");

        List<Predicate> criteria = new ArrayList<Predicate>();

        if(age != null) criteria.add(cb.equal(m.<Integer>get("age"), cb.parameter(Integer.class, "age")));
        if(userName != null) criteria.add(cb.equal(m.get("username"), cb.parameter(String.class, "username")));
        if(teamName != null) criteria.add(cb.equal(m.get("teamName"), cb.parameter(String.class, "teamName")));

        cq.where(cb.and(criteria.toArray(new Predicate[0])));

        TypedQuery<Member> query = em.createQuery(cq);

        if(age != null) query.setParameter("age", age);
        if(userName != null) query.setParameter("userName", userName);
        if(teamName != null) query.setParameter("teamName", teamName);

        List<Member> resultList = query.getResultList();
    }

}