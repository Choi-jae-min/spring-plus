# SPRING PLUS
해당 프로젝트는 과제성 프로젝트로 AOP, spring security, aws, queryDSL, index과 같은 Spring을 하는데 있어서 필수적인 요소들을 학습하고
직접 작성해보는 프로젝트입니다.

상세과정 TIL 
https://long-holly-6cf.notion.site/Spring-plus-33457c6474998074b071d4ef9d512cd8

## 프로젝트 개요

| 구분 | 내용 |
|------|------|
| 프로젝트명 | SPRING PLUS |
| 한줄 소개 | Spring 핵심 기술을 직접 구현하며 학습하는 백엔드 프로젝트 |
| 진행 목적 | 실무에서 사용되는 Spring 필수 기술(AOP, Security 등) 이해 및 적용 |
| 적용 기술 | AOP, Spring Security, AWS, QueryDSL, Index |
| 특징 | 이론이 아닌 직접 구현을 통한 학습 중심 프로젝트 |

### 1. 코드 개선 퀴즈 - @Transactional의 이해
기존 서비스 레이어 클래스 최상단에 있던 @transactional을 클래스 단위가 아닌 메서드 단위로 부여하여 경계를 명확하게 설정 하였습니다.
<img width="709" height="874" alt="image (4)" src="https://github.com/user-attachments/assets/4b02fbea-8a93-4eed-b9ed-b2e1fe118996" />

### 2. 코드 추가 퀴즈 - JWT의 이해
jwt에서 nickname정보를 가져올 수 있도록 clams에 추가 해주었습니다. 그에 맞춰 엔티티, 서비스도 수정 해 주었습니다.
<img width="704" height="309" alt="image (5)" src="https://github.com/user-attachments/assets/1a054699-80fe-4d65-83c8-64eb8a016dbd" />

### 3. 코드 개선 퀴즈 - JPA의 이해
날씨와 기간검색을 위한 JPQL 작성을 해 주었습니다.
![JPQL](https://github.com/user-attachments/assets/360368dc-62f6-4d43-8e83-a635e10f86fe)

개선 방향으로는 파람들을 하나의 DTO로 묶어서 관리하는것, 점점 복잡해질 쿼림문을 QueryDSL등으로 변경하는 방향이 있을거같습니다.

### 4. 테스트 코드 퀴즈 - 컨트롤러 테스트의 이해
원래 요구 사항에 맞는 상태로 변경하여 수정 해 주었습니다.
![컨트롤러개ㅓㄴ](https://github.com/user-attachments/assets/04e65107-3011-48f8-a73a-3ba5fc71153c)

### 5. 코드 개선 퀴즈 - AOP의 이해
기존 @after 어노테이션을 변경해주고 다른경로에서 실행중이던 AOP를 변경 해주었다.
<img width="848" height="227" alt="image (6)" src="https://github.com/user-attachments/assets/233a08c7-1443-422b-9585-1f1d48acae6a" />

### 6. JPA Cascade 
요구 사항이 할일 생성시 매니저도 자동등록이므로 PERSIST 옵션을 사용하여 자동으로 저장 될 수 있도록 해주었습니다.
<img width="848" height="147" alt="image (4)" src="https://github.com/user-attachments/assets/340926c6-31a8-4710-b19c-cd8d5cfdb022" />

### 7. N+1
단건 조회이기 때문에 fetch join으로 간단하게 해결하였습니다, 
<img width="631" height="58" alt="image (5)" src="https://github.com/user-attachments/assets/d8ee6641-b12d-4092-bf93-b16e753a8ce7" />

### 8. QueryDSL
간단한 QueryDSL코드를 작성하여 타입세이프하게 코드를 작성하고 Qclass 기반으로 컬럼명, 조건등을 자동완성으로 작성하여 생산성을 향상 시켰습니다.
<img width="565" height="392" alt="image (6)" src="https://github.com/user-attachments/assets/e9d36a2e-e56d-481d-a86b-f65d37a67bdb" />

### 9. Spring Security
기존 filter 와 argument resolver 의 역할인 jwtFilter를 체인에 등록, authUser 데이터 할당 이 두가지 역할을 수행하기 위하여 
spring security에 filter 체인 등록을 해 주었고,"/admin" 경로에 대한 admin권한을 체크 해 주었습니다.
그리고 기존 argument resolver의 역할은 filter 에서 SecurityContextHolder 에 authUser를 등록 하는 방식으로 변경 해 주었습니다.
<img width="762" height="644" alt="image (4)" src="https://github.com/user-attachments/assets/501f592a-7581-4a10-a576-faf58c38b4e0" />
```
if (claims == null) {
    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 JWT 토큰입니다.");
    return;
}

UserRole userRole = UserRole.valueOf(claims.get("userRole", String.class));

AuthUser authUser = new AuthUser(
        Long.parseLong(claims.getSubject()),
        claims.get("email", String.class),
        userRole
);
Authentication authentication =
        new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());

SecurityContextHolder.getContext().setAuthentication(authentication);

filterChain.doFilter(request, response);
```

### 10. QueryDSL을 사용하여 검색 기능 만들기
파람들을 하나의 DTO로 관리 할 수 있도록 ModelAttribute 을 활용 해주었습니다.
<img width="696" height="185" alt="Image" src="https://github.com/user-attachments/assets/504a1fa3-f242-4a04-a09e-f35abc513975" />

<img width="690" height="159" alt="image (6)" src="https://github.com/user-attachments/assets/4a6c3b9b-2699-49ab-bc62-5584252b8513" />
BooleanExpression 을 사용하여 조건 들을 분리하여 관리 할 수 있도록 하였습니다, 

manager.count() → 해당 부분을 처음에는 JPAExpressions 라는 서브 쿼리 방식을찾아서 사용 했는데,
이방식은 안티패턴이라는 글이있고 성능적으로 leftJoin 이 훨씬 안정적이고 빠르다는 것을 확인하여 수정 하였습니다.
이유는 서브 쿼리는 SELECT 안에 SELECT 를 한번 더 하는 방식인데 left join은 알고있듯 한번에 처리하기때문입니다.
```
@Override
public Page<TodoPageResponse> findTodosByCondition(Pageable pageable, SearchCondition searchCondition) {
  List<TodoPageResponse> content = jpaQueryFactory
          .select(Projections.constructor(TodoPageResponse.class,
                  todo.id,
                  todo.title,
                  manager.count(),
                  comment.count(),
                  todo.createdAt,
                  todo.modifiedAt
          ))
          .from(todo)
          .join(todo.user, user)
          .leftJoin(manager).on(manager.todo.id.eq(todo.id))
          .leftJoin(comment).on(comment.todo.id.eq(todo.id))
          .where(
                  nicknameContains(searchCondition.nickName()),
                  titleContains(searchCondition.title()),
                  createdAtAfter(searchCondition.createStartAt()),
                  createdAtBefore(searchCondition.createEndAt())
          )
          .groupBy(todo.id)
          .orderBy(todo.createdAt.asc())
          .offset(pageable.getOffset())
          .limit(pageable.getPageSize())
          .fetch();

  Long total = jpaQueryFactory
          .select(todo.count())
          .from(todo)
          .where(nicknameContains(searchCondition.nickName()),
                  titleContains(searchCondition.title()),
                  createdAtAfter(searchCondition.createStartAt()),
                  createdAtBefore(searchCondition.createEndAt()))
          .fetchOne();

  if (total == null) {
      total = 0L;
  }

  return new PageImpl<>(content, pageable, total);
}

private BooleanExpression nicknameContains(String nickName) {
  return nickName != null ? user.nickname.contains(nickName) : null;
}

private BooleanExpression titleContains(String title) {
  return title != null ? todo.title.contains(title) : null;
}

private BooleanExpression createdAtAfter(LocalDateTime createStartAt) {
  return createStartAt != null ? todo.createdAt.goe(createStartAt) : null;
}

private BooleanExpression createdAtBefore(LocalDateTime createEndAt) {
  return createEndAt != null ? todo.createdAt.loe(createEndAt) : null;
}
```
### 11. Transaction 심화
엔티티 설계

<img width="797" height="718" alt="image (8)" src="https://github.com/user-attachments/assets/db31e056-d818-4b22-ad5b-b54d365c796d" />

@transactional REQUIRES_NEW 을 사용하여 기존에 있던 트랜잭션을 잠시 중단하고 새로운 트랜잭션을 생성하여 부모트랜잭션이 롤백 되더라도 정상적으로 커밋이 수행되도록 작성 하였습니다.

<img width="510" height="389" alt="image (7)" src="https://github.com/user-attachments/assets/80d91019-aeb5-4f5c-9716-60205863f229" />

등록 요청 시 로그를 찍는다 하여 before혹은 after를 사용할 수 있지 않을까 생각할 수 도있지만, 
그냥 요청이 들어오고 성공하면 성공 로그를 실패 하면 실패 로그를 찍으면 모든 시도에 대한 로그를 작성 할 수 있기때문에 Around로 성공과 실패 상황을 한번에 작성해 주었습니다.
after로 작성하면 proceed의 성공 여부를 AfterReturning AfterThrowing로 두개를 사용해야 하기때문에 특별한 별도 처리가 필요하지 않은 이상 한번에 처리하는게 깔끔하다고 생각했습니다.

<img width="794" height="728" alt="image (9)" src="https://github.com/user-attachments/assets/4b02ea80-95c4-4ef1-a28e-ec16c65e3875" />

### 12. AWS 활용
이번 프로젝트는 ALB, ASG, nginx 등등 다른 걸 해줄 필요가 없어서 ec2를 퍼블릭 서브넷에 열어두고 RDS만 ec2를 통해서만 접속 할 수 있도록 만들어 주었습니다.
EC2
<img width="1076" height="618" alt="image (10)" src="https://github.com/user-attachments/assets/83adae84-1651-4eae-90cf-5bb235e4e3cd" />
<img width="1653" height="597" alt="image (11)" src="https://github.com/user-attachments/assets/c0464e86-0f4c-4340-b313-e08f15f217b5" />
<img width="1644" height="561" alt="image (12)" src="https://github.com/user-attachments/assets/2ad8dda6-ba8a-43fa-8204-7644f3fef57c" />
RDS
<img width="2270" height="219" alt="image (14)" src="https://github.com/user-attachments/assets/9e81460b-74a4-4e9a-b92e-55142dacf983" />
<img width="1481" height="1128" alt="image (13)" src="https://github.com/user-attachments/assets/4b20f9be-81fc-42d7-9bdc-08db5b23bb3e" />
S3
<img width="1474" height="1060" alt="image (15)" src="https://github.com/user-attachments/assets/c0b45dfb-3c25-4c19-b541-e576227f763d" />


http://3.39.110.240:8080/actuator/health - 헬스체크

그리고 S3를 활용해 고객의 프로필 사진을 업로드 할 수 있게 하였습니다, 이때 엔티티는 따로 분리하였고 url이 아닌 키를 저장하여 조회 시 signedUrl 을 발급하고 
캐싱해두는 방식을 생각했습니다.


### 13. 대용량 데이터 처리
단일 인덱스로 쿼리 성능을 개선하였습니다.
500만건 데이터 조회 시 최대 약 30배이상 빨라진 개선 속도를 체감할 수 있었습니다
<img width="545" height="270" alt="image" src="https://github.com/user-attachments/assets/cca12c37-76c9-43fa-8e90-96ce8d2df830" />
<img width="655" height="282" alt="image" src="https://github.com/user-attachments/assets/7f3d002b-c796-41ca-b658-ba0414c4a978" />
추가 개선사항으로는 redis 같은 캐시서버를 도입하여 조회시 DB를 다녀가지 않도록 하는 방법도 있습니다. 
