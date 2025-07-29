# Notion 오류 로깅 시스템

Spring Boot 기반 애플리케이션에서 발생하는 예외 정보를 Notion 페이지와 디스코드 웹훅으로 전송해 기록하는 시스템입니다.  
개발 / 운영 중 발생하는 예외를 빠르게 파악하고 대응하기 위한 목적이며, 다음과 같은 특징을 가집니다:

### 목표
- 예외 발생 시 root cause 및 전체 stack trace 자동 정리해 노션 및 디스코드 웹훅으로 기록
- build.gradle 등에 의존성으로 추가해서 사용자가 이용할 수 있도록 하기

### 📌 기술 스택
- Java 21
- Spring Boot 3.5.3
- Notion Open API v1 (REST)
- Jackson (JSON 직렬화)
- Log4j2 (로그)
- Discord Rest API

### 구현완료 목록
- Notion API 요청용 Builder DSL (BlockBuilder, PropertiesBuilder 등)
- Discord embed 메시지 구성 및 전송 기능
- 자동 예외 감지 AOP + 예외 요약 유틸 제공
- application.yml 기반 설정 바인딩 (@ConfigurationProperties)
- JSON 직렬화를 위한 jackson-databind 포함

### 구현필요 목록
- 의존성 추가할 수 있도록 세팅

---

## 📆 작업 내역

### 2025-07-23
- 예외 발생 시 `@ControllerAdvice` 기반 전역 처리 구현
- `getRootCause()`로 최상위 예외 추출하는 유틸 작성
- `getStackTrace()`로 전체 stack trace 문자열화
- Notion API 요청용 Map 구조 설계 시작

### 2025-07-24
- `LinkedHashMap`을 활용하여 JSON key 순서 보장
- Notion 페이지 속성 값 (`문서 이름`, `카테고리`, `생성 일시`) 매핑
- children 블록으로 heading + code 블록 구성
- `null`, `""`로 end값 처리 테스트 (Notion API 오류 회피)
- StackTrace 길이 2000자 제한 인식 → 30줄만 추출하도록 개선
- Postman 통해 Notion API 직접 호출하여 테스트 검증 완료

### 2025-07-29
- Notion 연동 DSL 구조 설계
    - `PropertiesBuilder`, `BlockBuilder`, `NotionPageRequestBuilder` 분리 설계
    - 메서드 체이닝 방식으로 JSON 구조 생성 가능하도록 설계

- 예외 로깅 시스템 구성
    - `ExceptionLoggingAspect`로 예외 감지
    - `NotionLogger`에서 Notion API 호출
    - 스택트레이스 30줄 제한 및 1900자 제한 설정

- 예외 메시지 유틸 분리
    - `getStackTrace()`, `getRootCauseMessage()` 유틸 분리
    - 핵심 예외 메시지만 추출하여 제목(title) 구성

- Block 구성 개선
    - `code()` 블록 language를 enum(`CodeLanguage`)으로 관리
    - `@JsonValue`로 enum 문자열 자동 직렬화 (`"JAVA"` → `"java"`)

- 테스트 코드 추가
    - Notion DSL 조립 테스트 작성 (SpringBootTest)

### 2025-07-29

- **Notion Block 확장**
  - `Block` 클래스에 다양한 블록 타입 추가:
    - `heading1`, `heading3`, `bulletedListItem`, `numberedListItem`, `quote`, `divider`
  - `BlockBuilder` 체이닝 빌더 패턴으로 위 블록들 지원

- **Discord Webhook 연동 개선**
  - `DiscordUploadService`에서 Discord Webhook으로 메시지 전송 시 `embed` 스타일 적용
  - 제목, 설명, 서비스명, 클래스 경로 등 시각적으로 구분 가능하게 구성
  - HEX 색상 코드를 int 값으로 변환해 컬러 지정 가능 (`#FF0000` → `16711680`)
  - 예외 발생 위치 및 예외 종류 표시:
    - `ExceptionFormater.getRootExceptionClassPath(Throwable)`
    - `ExceptionFormater.getRootExceptionType(Throwable)`

---


