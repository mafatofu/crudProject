# 🌳 멋나무숲
익명 커뮤니티 프로젝트

## User 정보
 > 익명 게시판으로 운영되지만 update/delete, MyPage, 가입, 탈퇴등의 관리를 위해 필요할 것으로 보임  
 > 닉네임 `🌳` 통일 등으로 구성
 - id
 - username (익명이기 때문에 자동 생성등으로 구현해도 될 것 같음)
 - email
 - password (password 암호화 등 보안 부분 고려 필요)


## Board
 > 게시판 자체를 단일로 두어 말머리로 article type 나누기
 - id
 - category
 - `@OneToMany` Article
 

## Article
 > *조회수, 추천, 글 작성 시간, 사진 첨부 등*  
 > 말머리는 주어진 주제 중에서 선택하여 게시할 수 있도록 
 - id
 - title
 - content
 - `@ManyToOne` Board
 - timeStamp (글 작성 시간)
  - ...

## Comment
 > `리댓 기능 구현 (보류)` - 테이블 분리 등 고려할 부분이 있음  
 > 추천, 댓글 작성 시간
 - id
 - comment
 - timeStamp (글 작성 시간)
 - ...

--- 
## URL 
- [URL 구성 GUIDE](RestUrl.md)

### Feature 

- 수정과 삭제는 작성한 user 정보만 진행할 수 있도록 구현하기
- `log-in` / `log-out` 구현하기
- 전체 게시글 페이징
- 카테고리(말머리)별 모아보기
- 제목, 내용, 댓글 검색
- 공지사항 게시글 추가 (메인 상단 노출)
- 추천수 많은 글 상위 노출 (주마다 업데이트할 수 있으면 좋음)
