일대일 관계 중 대상 테이블에 외래 키가 있는 단방향 관계는 JPA에서 지원하지 않고 이런 모양으로 매핑할 수 있는 방법도 없다.
단방향 관계를 Locker에서 Main으로 수정하거나, 양방향 관계로 만들고 Locker를 연관관계의 주인으로 설정해야 한다
참고로 JPA2.0부터 일대다 단방향 관계에서 대상 테이블에 외래 키가 있는 매핑을 허용