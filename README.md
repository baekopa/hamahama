# 🦝 HAMA - HAMA 🦛

## 📌 개요
### 📝 서비스 설명
**대화 형식 스터디로 함께!**
- 원하는 스터디 생성
- 팀원 추가, 미팅 일정 지정, 주제 설정

**발화 기록을 수월하게!**
- 녹음 및 화자 분리를 적용한 STT 지원

**많은 내용을 간단하게!**
- 추출한 텍스트를 바탕으로 요약 생성

**공부한 내용을 더 풍부하게!**
- 공부한 노트에 대한 추가 내용 및 키워드 제공
- 스터디 내용과 개인 내용의 차이 제공
- 리마인드 퀴즈 제공

**일정도 잊지 않게!**
- 리마인드 퀴즈 및 미팅 일정 알림 발송

## 💻 개발 환경
### ⚙ 기술 스택
**Backend**
---

<img  src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white"/> 
<img  src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat-square&logo=springsecurity&logoColor=white"/> 
<img  src="https://img.shields.io/badge/Spring JPA-6DB33F?style=flat-square&logo=spring&logoColor=white"/> <img  src="https://img.shields.io/badge/REDIS-DC382D?style=flat-square&logo=REDIS&logoColor=white"/> <img src="https://img.shields.io/badge/mariadb-003545?style=flat-square&logo=mariadb&logoColor=white"/> <img  src="https://img.shields.io/badge/amazons3-569A31?style=flat-square&logo=amazons3&logoColor=white"/> <img  src="https://img.shields.io/badge/framer-0055FF?style=flat-square&logo=framer&logoColor=white"/> <img src="https://img.shields.io/badge/fastapi-009688?style=flat-square&logo=fastapi&logoColor=white"/> <img  src="https://img.shields.io/badge/swagger-85EA2D?style=flat-square&logo=swagger&logoColor=white"/> <img  src="https://img.shields.io/badge/gradle-02303A?style=flat-square&logo=gradle&logoColor=white"/> 

**Frontend**
---

<img  src="https://img.shields.io/badge/javascript-F7DF1E?style=flat-square&logo=javascript&logoColor=black"/> 
<img  src="https://img.shields.io/badge/vue.js-4FC08D?style=flat-square&logo=vuedotjs&logoColor=white"/> 
<img  src="https://img.shields.io/badge/vuetify-1867C0?style=flat-square&logo=vuetify&logoColor=white"/>
<img  src="https://img.shields.io/badge/tailwindcss-06B6D4?style=flat-square&logo=React&logoColor=white"/> 
<img  src="https://img.shields.io/badge/Vite-646CFF?style=flat-square&logo=Vite&logoColor=white"/> <img  src="https://img.shields.io/badge/axios-5A29E4?style=flat-square&logo=axios&logoColor=white"/> <img  src="https://img.shields.io/badge/mui-007FFF?style=flat-square&logo=mui&logoColor=white"/> <img  src="https://img.shields.io/badge/reactrouter-CA4245?style=flat-square&logo=reactrouter&logoColor=white"/> <img  src="https://img.shields.io/badge/framer-0055FF?style=flat-square&logo=framer&logoColor=white"/>

**AI**
---
🤣<img  src="https://img.shields.io/badge/KoBART-FFFF66?style=flat-square&logo=KoBART&logoColor=white"/> <img  src="https://img.shields.io/badge/whisper-000000?style=flat-square&logo=openai&logoColor=white"/> <img  src="https://img.shields.io/badge/chatGPT-74aa9c?style=flat-square&logo=openai&logoColor=white"/>
 
**Infra**
---

<img  src="https://img.shields.io/badge/amazonec2-FF9900?style=flat-square&logo=amazonec2&logoColor=black"/> <img  src="https://img.shields.io/badge/jenkins-D24939?style=flat-square&logo=jenkins&logoColor=black"/> <img  src="https://img.shields.io/badge/docker-2496ED?style=flat-square&logo=docker&logoColor=white"/> <img  src="https://img.shields.io/badge/nginx-009639?style=flat-square&logo=nginx&logoColor=white"/> 

**Cooperation**
---

 <img  src="https://img.shields.io/badge/notion-000000?style=flat-square&logo=notion&logoColor=white"/>  <img  src="https://img.shields.io/badge/figma-F24E1E?style=flat-square&logo=figma&logoColor=white"/> <img  src="https://img.shields.io/badge/jirasoftware-0052CC?style=flat-square&logo=jirasoftware&logoColor=white"/> <img  src="https://img.shields.io/badge/gitlab-FC6D26?style=flat-square&logo=gitlab&logoColor=white"/>  <img  src="https://img.shields.io/badge/mattermost-0058CC?style=flat-square&logo=mattermost&logoColor=black"/>  <img  src="https://img.shields.io/badge/discord-5865F2?style=flat-square&logo=discord&logoColor=black"/>


### 프로젝트 기간
**`2023.02.26~2024.04.04`**

## 📺 서비스 화면
### 로그인
||
|--|
|**네이버 카카오 구글 소셜 로그인 기능**|
|소셜 서비스를 통한 로그인을 지원합니다.|

## 😺 기술 소개
**OAuth2**
- 네이버 카카오 구글 로그인을 통한 인증

**Json Web Token**
- Access Token & Refresh Token 방식을 통한 인가
- Refresh Token Rotation, header-cookie 방식을 이용한 보안 강화

**Server-Sent-Event**
- SSE를 활용한 알림 구현

**Aspect-Oriented Programming**
- aop를 활용한 코드 실행 시간 측정

**Common Response**
- success response와 Presentation, Business layer에서 발생하는 error response 정형화

**Auditing**
- 생성, 수정에 대한 일시, 대상 자동 등록

**Scheduling**
- 주기적, 특정 시간에 동작하는 코드 실행

**Soft Delete**
- 데이터 직접 제거 대신, 제거 요청 일시 등록
- Auditing을 이용한 관리

**Mapstruct**
- 편리한 객체 간 매핑

**Springdoc-openapi**
- 자동 Api 문서화

**Whisper**
- Speech-to-Text

**pyannote**
- 화자 분할


## 🗺️ 설계 문서
### ERD


### 아키텍처
(아키텍서 들어갈 곳)


## 🖋️ 요구사항 명세서



## 🧑‍🧒 팀원 소개
| 이 름 |이수민 |배성규 |윤정영 |김수민 |성영준 |여아정 |
| -- |-- |-- |-- |-- |-- |-- |  
|    | FE,BE,Infra	| FE	| FE,AI	| BE,Infra	| BE,Security	| BE,AI	|
|	 | 팀장, 프론트(CSS, 알림), 백(스터디 관리, 미팅 관리)	| 프론트(로그인, 메인화면, 초기화면, 채워주새우)	| AI(STT, 화자 분리), 프론트(스터디 화면)	 | 백(로그인, 개인 노트 관리) 	| 백(유저 인가, 알림, 산출물 전체 요약, 꼬리질문)	| AI 및 백(산출물 요약, 키워드, 퀴즈 생성)	|


## 📧 협업:Notion
