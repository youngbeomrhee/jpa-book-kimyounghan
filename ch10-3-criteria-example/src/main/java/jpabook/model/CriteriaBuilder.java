package jpabook.model;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Selection;

// Critera를 사용하려면 CriteriaBuilder.createQuery() 메소드로 CriteriaQuery를 생성하면 된다
public interface CriteriaBuilder {

    CriteriaQuery<Object> createQuery(); // 조회값 반환 타입: Object

    // 조회값 반환 타입: 엔티티, 임베디드 타입, 기타
    <T> CriteriaQuery<T> criteriaQuery(Class<T> resultClass);

    CriteriaQuery<Tuple> createTuplequery();    // 조회값 반환 타입: Tuple

}
