# jwt-auth-demo
JWT 인증/인가 관련 학습하는 공간입니다.

---

## Spring Security

<img src="/images/스크린샷 2026-03-18 오후 2.24.32.png">

- 기본적으로 스프링 시큐리티는 로그인 시스템이 내장되어 있다.
- 위 모듈들의 일부를 커스텀 필터로 사용해 JWT 방식의 인증을 사용할 수 있다.

### FilterChain
- 사용자의 Request를 가로채 일련의 절차를 처리한다.

### UsernamePasswordAuthenticatioToken
- 사용자가 제출한 인증정보가 포함되어 있다.

### AuthenticationManager
- 실제로 인증을 수행하고, 여러 `AuthenticationProvider`들을 이용한다.

### AuthentcationProvider
- 특정 유형의 인증을 처리한다.

### PasswordEncoder
- 패스워드의 인코딩 방식을 지정한다.

### UserDetailsService
- 사용자의 아이디를 받아 해당 사용자의 `UserDetails`를 반환한다.

### UserDetails
- 사용자의 아이디, 비밀번호, 권한 등 정보가 들어있다.

### Authentication
- 인증이 성공하면, `Authentication` 객체를 생성해 `AutnticationManager`에게 반환한다.

### SecurityContextHolder
- 현재 실행 중인 스레드에 대한 `SecurityContext`를 제공한다.

### SecurityContext
- 현재 사용자의 `Authentication`이 저장되어 있고 애플리케이션은 `SecurityContextHolder`를 통해 현재 사용자의 권한 확인 및 인가를 결정한다.