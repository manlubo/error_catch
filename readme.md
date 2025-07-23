# Notion 오류 로깅 시스템

Spring Boot 기반 애플리케이션에서 발생하는 예외 정보를 Notion 페이지로 전송해 기록하는 시스템입니다.  
개발 / 운영 중 발생하는 예외를 빠르게 파악하고 대응하기 위한 목적이며, 다음과 같은 특징을 가집니다:

- 예외 발생 시 root cause 및 전체 stack trace 자동 정리
- Notion API를 활용한 페이지 자동 생성

## 📌 기술 스택
- Java 21
- Spring Boot 3.5.3
- Notion Open API v1 (REST)
- Jackson (JSON 직렬화)
- Log4j2 (로그)

## 구현필요 목록
- 디스코드 알림
- 누구나 쉽게 적용할 수 있도록 템플릿 구성

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

---


