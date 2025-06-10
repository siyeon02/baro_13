# baro_13

## 프로젝트 소개

> 프로젝트 baro_13은 jwt 토큰을 이용한 회원가입, 로그인, 관리자 권한 부여의 기능을 가지고 있습니다.
>
> Swagger를 통해 API를 문서화하였고 EC2를 통해 배포까지 완료하였습니다.

## 📱 Tech Stack
<br>

<div align="center">
  <!-- Language -->
  <img src="https://img.shields.io/badge/java-white?style=for-the-badge&logo=java&logoColor=white">

  <!-- Backend -->
  <p>
    <img src="https://img.shields.io/badge/spring boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> 
    <img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white">
    <img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white">
  </p>
</div>

<br>

### Swagger UI
http://35.172.128.58:8080/swagger-ui/index.html

## API 명세서

<br>

### 회원가입 API

| Method | Endpoint                   | Description   |
|:------:|:---------------------------|:--------------|
|  POST  | `http://35.172.128.58:8080/signup`    | 회원가입   |

<br>

### 로그인 API

| Method | Endpoint                           | Description         |
|:------:|:-----------------------------------|:--------------------|
|  POST  | `http://35.172.128.58:8080/login`  | 로그인하기         |

<br>

### 관리자 권한 부여 API

| Method | Endpoint                      | Description        |
|:------:|:------------------------------|:-------------------|
|  PATCH   | `http://35.172.128.58:8080/admin/users/{userId}/roles` | 사용자에게 관리자 권한 부여 |

<br>

<br><br><br><br><br><br><br><br><br><br>
---
© 2025 Staccato. All rights reserved.
