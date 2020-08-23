<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
  <meta charset="utf-8">
  <title></title>
  <!-- 합쳐지고 최소화된 최신 CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

  <!-- 부가적인 테마 -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}//resources/css/style.css" />
  
  <c:if test="${ sessionScope.user ne null }">
	<style>
		body{display:none;}
	</style>
  </c:if>  
  
  <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
  <!-- 합쳐지고 최소화된 최신 자바스크립트 -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js" type="text/javascript"></script>
  <script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/index.js"></script>
  
  <script>
  	set_path('${pageContext.request.contextPath}');
  </script>
  
  <c:if test="${ sessionScope.user ne null }">
		
		<script type="text/javascript">
			$(function(){
				list();
			
			});
		</script>
	</c:if>
	
</head>
<body>
  <div class="signup_form"> 	
  	<form action="${pageContext.request.contextPath}/member_insert.do" enctype="multipart/form-data" method="POST" id="r_form">
  	<input type="file" name="photo" id="input_file">
  	<a href="javascript:$('.signup_form > form > input[type=\'file\']').click();" id="profile">
  		<img src="${pageContext.request.contextPath}/resources/images/profile.jpg">
  	</a>
  	<div>회원가입</div>
  	<input type="text" name="userid" placeholder="아이디">
  	<input type="password" name="password" placeholder="비밀번호">
  	<input type="text" name="username" placeholder="이름" />
    <input type="date" name="birthday" placeholder="생일" />
    <input type="tel" name="phonenumber" placeholder="전화번호" />
    <a href="javascript:get_addr();">
    <input type="text" name="addr">
  	</a>
  	</form>
  	<a href="javascript:signup($('#r_form'));">
        <button>회원가입</button>
     </a>
     <a href="javascript:signup_cancel($('#r_form'));">
        <button>취소</button>
     </a>
  </div>
  <div class="Message">
      <div class="title">메세지</div>
      <div class="content">
        <p></p>
      </div>
      <a href="javascript:ClosedMessage();">
        <button class="btn btn-primary">확인</button>
      </a>
  </div>
  <div class="background">
    <img src="${pageContext.request.contextPath}//resources/images/bg1.jpg">
  </div>
  <header>
    <nav>
      <div class="logo"><span>C</span>repas <span>Story</span></div>
    </nav>
  </header>
  <section>

    <div class="form">
      <input type="text" class="user_id" placeholder="아이디" value="test"/>
      <input type="password" class="user_pwd" placeholder="패스워드" value="test"/>
      
      <a href="javascript:Login();"><button>Login</button></a>
      <a href="javascript:$('.signup_form').css({visibility:'visible'});"><button>Sign UP</button></a>
    </div>
  </section>
</body>
</html>