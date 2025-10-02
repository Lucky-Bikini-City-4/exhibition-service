# exhibition-service

## Dependencies
- Java 17
- Spring Boot 3.5.5
  - Spring Boot Starter Data JPA
  - Hibernate 6.6.26
- MYSQL 8.0.43
- QueryDSL 5.1.0
- Spring Cloud
  - Eureka Client 4.3.0
- Lombok 1.18.38

## API Specification

https://docs.google.com/spreadsheets/d/1erfJV-eh3TtUMcaTJLLNou-vhwnxBvB4MV5cJ72-hw8/edit?gid=416827453#gid=416827453

## 성능 개선
### 전시회 목록 검색 쿼리 성능 개선

1. 테스트 사양 M2 Air 16GB RAM, 256GB SSD
2. 전체 데이터 수 50만건
3. 테스트 시 DB 커넥션 풀에 대기하지 않기 위해 쓰레드 당 10초 Ramp-up

- Fulltext Index

CASE #1

- `Distinct` 제거 전
![1-2-1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2Fb3icPn%2FbtsQZ3q2Lg5%2FAAAAAAAAAAAAAAAAAAAAABdH7Ti6vWRTZ5UqoqZoaeUmfCAS9t_403BSjfxrFlMe%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3DqhqiMy%252FrlCzehnjsnq0KCAVi%252FF8%253D)
- `Distinct` 제거 후
![1-2-1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FerSb2Y%2FbtsQYGwEV4a%2FAAAAAAAAAAAAAAAAAAAAAGf7yN7OcZJREtxflPWyenP9yAznmyu8ZlvSCRp9OAEV%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3DYyu1JP8o8gGeykmGCJeGpDZQhPI%253D)
![1-2-2](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FvSycO%2FbtsQXPHFu5G%2FAAAAAAAAAAAAAAAAAAAAAJikZ5ggFAMwhDugAWOisEf8larByCeMgHfcq_QB_jgf%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3DXjS3EjDVn1U%252FzooDZvf7jk3OsQY%253D)
![1-2-3](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2Fbj6No2%2FbtsQZxMK6Fu%2FAAAAAAAAAAAAAAAAAAAAAIXTw4ka6JIwEgnFPJHHmG4ihwbkw4joZf2eUBx1F948%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3DvU1SDPOqIevTdcn%252FH3OYh%252FYa4mM%253D)
![1-2-4](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FVMpby%2FbtsQYHPQbWo%2FAAAAAAAAAAAAAAAAAAAAALGfUZ8Vq1ZCFUCy16OnWMEFksB8LnPj-SfRaSYw7WPT%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3D6ONNy6pizV6Tcu3WF2rZBOJV%252FQg%253D)

CASE #2

- `Distinct` 제거 전
![2-2-1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FdRyX59%2FbtsQXHiJ9Lp%2FAAAAAAAAAAAAAAAAAAAAAMYE6jA4fMMkh9XR5MSaYWICW6V0VcRoaQhJuErPw5tl%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3DYp8cePzALgvKJJHQbUvj16t7mp4%253D)
- `Distinct` 제거 후
![2-2-1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2Ft9Fi0%2FbtsQXIotTng%2FAAAAAAAAAAAAAAAAAAAAALC1S7xv8babxaSQa0Pwpi6lv-Giw17gILVQo1Buve_a%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3D7r%252Fc%252FthEsI4f2fWEajszfp%252FQji0%253D)
![2-2-2](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FyknOx%2FbtsQZkNEk41%2FAAAAAAAAAAAAAAAAAAAAAN_qxLjOqa2SD9t-nSnqSfAZatC7KAAswxcveP6Mny3t%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3DBZcd7uF7rcA27sVEgIosQ8XaKoY%253D)
![2-2-3](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2Fc9NCId%2FbtsQ0DeE6hY%2FAAAAAAAAAAAAAAAAAAAAAIkjbSwhE3pzpnxb2ToyIhIZaOLXRuFp0pKm_RTpSwhR%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3DIJOvghzieoFVzse5xlJeT7YxFZM%253D)
![2-2-4](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FX4rc2%2FbtsQZrl14q2%2FAAAAAAAAAAAAAAAAAAAAADfFtSM04Yad0vMxO7LAs_BofyR6ej4fjm9MDnr1iJLN%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3DCE%252BRzk9YjLdO5qKlpTINPUdjo2M%253D)

결과
- `DISTINCT` 사용 할 경우
    - 응답 평균 시간 75220ms → 14899ms ⇒ 약 80% 향상
    - TPS 6.4/sec -> 25.2/sec ⇒ 약 294% 향상
- + 아티스트 검색 시 중복 값들에 대한 처리를 위해 `DISTINCT`를 사용하였으나 전시회 이름이나 장소로 검색 시에는 사용하지 않도록 로직 변경
    - 응답 평균 시간 14899 ms → 6155 ms ⇒ 약 59% 향상
    - TPS 25.2/sec → 45.8/sec ⇒ 약 82% 향상
- 최종
    - 응답 평균 시간 75220ms → 6155ms ⇒ 약 92% 향상
    - TPS 6.4/sec → 45.8/sec ⇒ 약 616% 향상
- Fulltext Index 단점
    - 삽입 갱신 삭제 시 비용
      - 삽입 삭제는 빈번하게 발생하지 않을 것으로 예상
      - 갱신의 경우 조회보다는 빈번하게 발생하지 않을 것으로 예상
    - 전시회 데이터 50만건 기준, 토큰 사이즈 2 생성 시 약 9초 소요
- 남은 성능 개선
    - 아티스트 이름 검색
    - Using filesort 제거

- Composite Index

CASE #1
![3-1-1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2Fbmmqh9%2FbtsQZY4kVQz%2FAAAAAAAAAAAAAAAAAAAAAElQtCElmD75BC9ordN8dR4uajaDWLycs1UtqchVHTJm%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3DuolYvfLOyNX87LOsdv6ZKrhnUBw%253D)
![3-1-1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FkAp0l%2FbtsQ0FXSFnq%2FAAAAAAAAAAAAAAAAAAAAAPwg6t-ZVY6bq-u4C7k9XZJrewlyXXE_xlvulFUaJmwO%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3D31wM5LiF4XEWrPw%252FJLzC1ByPfbE%253D)
![3-1-1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2Fc9NCId%2FbtsQ0DeE6hY%2FAAAAAAAAAAAAAAAAAAAAAIkjbSwhE3pzpnxb2ToyIhIZaOLXRuFp0pKm_RTpSwhR%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3DIJOvghzieoFVzse5xlJeT7YxFZM%253D)
![3-1-1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FX4rc2%2FbtsQZrl14q2%2FAAAAAAAAAAAAAAAAAAAAADfFtSM04Yad0vMxO7LAs_BofyR6ej4fjm9MDnr1iJLN%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3DCE%252BRzk9YjLdO5qKlpTINPUdjo2M%253D)

- 풀 테이블 스캔, ORDER BY start_date에 의한 Using filesort

CASE #2
![4-1-1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FdHuIzt%2FbtsQ1l5MIBx%2FAAAAAAAAAAAAAAAAAAAAAJ6tHurPNiSjbGR5hlSP0RREy6T4lmqejpc8n48-BU7r%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3DPf9yYIVRY2iLqxYDUZ9YANM9dC4%253D)
![4-1-1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FLUAcZ%2FbtsQ1bIZam8%2FAAAAAAAAAAAAAAAAAAAAAM10kTVfEMZLHbGkdQftObRejjpB9lSDMJnTSUOWS3H8%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3D3ezjAnF1HaCgHzFZnNpzKE%252BUqY0%253D)
![4-1-1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FbNjHsI%2FbtsQ07s2W8Q%2FAAAAAAAAAAAAAAAAAAAAAKcxXtA-hkb_TbFIuhYKOKRQqRZXmPG3Uf2yZbbsfx75%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3DzyFd5jYClRr29bao76%252Bynq3KDnM%253D)
![4-1-1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FC2y9N%2FbtsQ1tbGIfR%2FAAAAAAAAAAAAAAAAAAAAALDHr-LFuyinLA5XbNRkxgbA07M9UxlGjqi8I0I7heZn%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3D30f0oGOBOXKGVLU%252Bj%252B3rJLjUF6M%253D)

- 인덱스 레인지 스캔, start_date 인덱스만 사용됨

CASE #3
![5-1-1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2F0qiS2%2FbtsQZo3AA3e%2FAAAAAAAAAAAAAAAAAAAAAKWjcnkDovlCnk7jTd5F_1n4vpcqFYRlhc5nXIKXf8dk%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3D7fh7Ynjn%252BG9DKOmquOhpdjXgdLo%253D)
![5-1-1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2Fooqvs%2FbtsQZRYrTPK%2FAAAAAAAAAAAAAAAAAAAAACn0kmqvA1KU0ub2IZ0mYdEL02G4jQuuRJ6rvWL7EbLT%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3DdWMkJh%252BC1QR2vItpGrNDZe%252Fuwjc%253D)
![5-1-1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FwlHmy%2FbtsQZb4RsTe%2FAAAAAAAAAAAAAAAAAAAAAL2f3fdoTPWCQa6vHBj-_yLZnCHy31zyK6d98Seo5GBD%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3DuNbx33zi83wdZpWZfnDMlENj97o%253D)
![5-1-1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FxNvsg%2FbtsQ1sjwox6%2FAAAAAAAAAAAAAAAAAAAAAGKX1pwvKbMosbBzM2l50XXiW4f2RaeuH9H0_xkQN3Yo%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1761922799%26allow_ip%3D%26allow_referer%3D%26signature%3DMfD6iiJPUkaIlvdN%252BLp%252FSSsdjxY%253D)

- 인덱스 레인지 스캔, 복합 인덱스 사용


- (시작 날짜)로 검색하는 경우

```sql
WHERE 
	startDate <= :startDate
	AND endDate >= :startDate
```

- (시작 날짜, 종료 날짜)로 검색하는 경우

```sql
WHERE
	startDate <= :endDate
	AND endDate >= :startDate
```
    
- 두 경우 모두 start_date와 end_date에 대해 쿼리를 하게 됨
- 복합 인덱스 사용 (start_date, end_date)

결과
- 응답 평균 시간 30513ms → 16441ms ⇒ 약 46% 향상
- TPS 13.4/sec → 25/sec  ⇒ 약 87% 향상