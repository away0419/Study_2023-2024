## TDD란?

테스트 주도 개발 프로그래밍.
반복 테스트를 이용한 소프트웨어 방법론.
작은 단위의 테스트 케이스를 작성하고 이를 통과하는 코드를 작성. 이후 반복.
애자일 방법론 중 하나인 XP의 Test-First에 기반을 둔 단순한 설계를 중요시하는 방법론.

<br/>

## 일반 설계와의 TDD 차이

![일반 설계](image/tdd1.png)

일반 설계

- 요구 사항이 명확하지 않을 경우 완벽한 설계 어려움.
- 자체 버그 검출 능력이 낮음.
- 자체 테스트 비용 증가.

<br/>

![TDD](image/tdd2.png)

TDD

- 유지 보수 용이함.
  - 코드 수정 및 기능 추가 시 빠르게 검증 가능.
  - 리팩토링 시 안정성 확보.
  - 디버깅 시간 단축.
  - 설계 수정 시간 단축.
- 테스트 문서 대체 가능.
  - 정확한 테스트 근거 자동 산출 가능.
- 객체 지향 코드 개발 가능.
  - 테스트 코드 먼저 작성하여 명확한 기능과 구조 설계 가능.
  - 코드의 재사용성 보장.
- 생산성 저하.
  - 테스트 코드를 작성하기 때문에 개발 시간 증가.

<br/>

## TDD 방법 및 순서

1. 실패할 경우의 테스트 케이스 작성 후 테스트 (red)
2. 성공할 경우의 테스트 케이스 작성 후 테스트 (green)
3. 리팩토링
4. 1~3 반복