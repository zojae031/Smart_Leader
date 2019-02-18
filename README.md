# < Smart Leader ver2.0 (구 DoorOpenService) > 

## 개요 
##### 학교 출입문, 회사 출입문 등 결제기능없이 사용자 인증이 필요한 공간에 사용 할 수 있는 비콘 기반 사용자 인증 서비스

## System flow
#### 1.Login -> 2.Entrance into Beacon Range -> 3.ShakeAlgorithm -> 4.Server -> 5.OpenDoor -> 6.Server -> 7.Andorid

## Framework
1. 안드로이드
2. JAVA Socket DB Server (Maria DB)
3. 아두이노 (인증이 필요한 기기)


## 내용


### 1.Android
- 1Depth 의 사용자 인증 백그라운드 위주 서비스  
`` 로그인 이후 사용자의 직접적인 Application 교류 X``
- Estimate API 를 통해 비콘 핸들링  
``Bluetooth Low Energy(4.0) Beacon mode 사용``
- 비콘기반 위치 인증  
``비콘 UUID고정, Major, Minor 값으로 어떤 인증서비스인지 판별 후 서버로 전송, 사용자와 가장 가까이 있는 비콘정보 전송(RSSI)``
- SensorManager 을 이용하여 ShakeAlgorithm 을 적용한 사용자 인증   
 ``(좌우로 일정 세기 이상 흔들 경우 서버로 비콘정보 전송)``
- 상속관계를 이용한 서버통신의 다형성(polymorphism) 구현
- 비동기 작업을 위한 스레드 관리
- Handler를 통한 스레드 통신 관리  
``Callback구조를 극복하고자 Android.os.Handler로 하나의 클래스에서 모든 동작을 처리함``
  
### 2.Server
- Java Socket Database Server 구축
- Json을 이용한 통신 프토로콜 구축
- MariaDB를 이용한 데이터베이스 스키마, 테이블간의 연관관계 구축
- 로그인, 회원가입 모듈 제작
- Strategy Pattern을 이용하여 코드 간결화
- Thread Queue를 만들어 모듈에 따른 동작을 서버에서 synchronize 시킴  
``같은 모듈에 다수의 사용자가 접근 가능하기 때문에 모듈에 따른 동기화를 적용``
  
### 3.Module
- 타이머를 이용하여 스탭모터와 LED 조작
- 와이파이 센서를 통해 와이파이로 Server와 Socket 통신  
`` 클라이언트에서 동작을 지시받는게 아니라 서버를 통해 동작을 직접 지시받고 서버로 응답함``
- 모듈에 따른 동작 정의  
`` 각 모듈은 해당하는 비콘의 Major 값을 가지고 있고, Minor값으로 사용자인지 운영자인지 구분``
- 적외선 센서를 이용하여 사용자 이동을 감지
  
### 4.그 이외 기술
- SingleTon, Strategy, Viewholder 등 Design Pattern적용
- SHA-256 알고리즘을 사용하여 사용자 비밀번호 암호화
- Beacon Rssi값 계산 알고리즘  
`` - 값을 들어오는 rssi를 보기쉽게 +로 변환``  

### 아두이노 모듈
 - Bluetooth HM-10 (Bluetooth 4.0)
 - 전동모터
 - LED
 - UNO
 - 적외선 센서
 - 미니빵판
 - 와이파이모듈
 
 ### 확장성
  - 출입문, 도서대출, 출석체크, 스터디룸, 책 대여 등 다양한 사용자 인증이 필요한 모듈로 이식이 가능
