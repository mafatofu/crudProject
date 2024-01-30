## URL

### 메인 화면 `'/home'` 구성 필요
- `GET` `readAllAtrticle` 필요
- 접근 권한 `permitAll` `전체 공개`
- 글 목록은 확인할 수 있지만 비로그인 상태에서는 눌러서 읽을 수는 없도록 설정하는 거 어떠신가요
- html에서는 로그인/회원가입 창으로 이동할 수 있는 버튼(혹은 링크) 추가 필요
- 로그인 후에는 `/home`으로 리다이렉트 필요
- 회원가입 후에는 로그인 상태에서 `/home`으로 리다이렉트 필요

### Article 관련
 #### 글 작성하기 `POST`
  - `/article/create`
  - 접근 권한: `authenticated` 로그인한 회원만
 #### 글 읽기 `GET`
  - `/artcie/{articleId}`
  - 접근 권한: `authenticated`로 할지 `permitAll`로 하고 댓글 쓰기를 막을지 의견 부탁드립니다
 #### 글 수정하기 `PUT`
  - `/article/{articleId}/update`
  - 접근 권한: `authenticated` / 글 작성 user 동일 검증 필요
 #### 글 삭제하기 `DELETE`
  - 삭제 같은 경우 별도 view 없이 글 읽기 페이지에서 버튼(혹은 링크)로 구현 후 
삭제가 완료되면 글 목록으로 리다이렉트 되도록 하는 게 좋을 것 같은데 어떠신지 의견 주세요!

### Comment 관련
 #### 댓글 작성하기 `POST`
 - `/artcie/{articleId}`
 - 접근 권한: `authenticated` 로그인한 회원만
 - 삭제 성공하면 `/artcie/{articleId}` view로 리다이렉트
 #### 댓글 수정하기 `PUT`
 - `/artcie/{articleId}`
 - 접근 권한: `authenticated` / 댓글 작성 user 동일 검증 필요
 - 수정 성공하면 `/artcie/{articleId}` view로 리다이렉트
 - 매핑 경로 중복 이슈 없을지 확인은 해 보아야 할 것 같습니다
 #### 댓글 삭제하기 `DELETE`
 - 댓글 삭제도 마찬가지로 `/artcie/{articleId}` 화면에서 바로 진행되는 것이 나을 것 같은데 
 테스트 필요할 것 같습니다.. 저는 view 따로 만들기는 했었는데 다른 방법 아시는 것 있으면 공유 부탁드립니다..
 - 접근 권한: `authenticated` / 댓글 작성 user 동일 검증 필요

### USER 관련
 - 로그인과 회원가입, 탈퇴 폼은 security 도입 후 구성해야 할 것 같아 보류하겠습니다
(현재 회원가입`/user`는 임시로 있는 상태입니다)
 #### 마이 페이지 `/user/myPage` 구성 필요
 - user 정보 (가입 이메일) 등 확인 
 - 비밀번호 변경 링크 이동 버튼
 - 회원 탈퇴 링크 이동 버튼
 - 내가 쓴 글 목록 (findArticleByUserId) 누르면 해당 글로 이동
 - 내가 쓴 댓글 목록 (findCommentByUserId) 누르면 해당 글로 이동
 - 접근 권한: `authenticated` / 로그인 user 동일 검증 필요
 - 가입일, 방문일 등 추가 정보도 있으면 좋겠네요 추후 의견 부탁드립니당
 #### 비밀번호 변경 `/{userId}/changePw`
 - `email`, `password` 입력 form 필요
 - 비밀번호 변경 완료 후 비로그인 상태에서 로그인 화면으로 리다이렉트 필요


