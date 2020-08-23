  <div class="post_data" >
    <a href="#">
      <img src="${pageContext.request.contextPath}/resources/images/gd.jpg">
    </a>
    <a href="#">
      <div class="nick">${ sessionScope.user.username }</div>
    </a>
    <div class="content">
	      |Content|
    </div>
    <div class="comment_write">
      <a href="#">
        <img src="${pageContext.request.contextPath}/resources/images/heart.png" class="heart">
      </a>
      <input type="text">
      <a href="#">
        <button>작성</button>
      </a>
    </div>
    
    
    <div class="comment_list" >
      <div class="comment">
        <a href="#">
          <img src="${pageContext.request.contextPath}/resources/images/gd.jpg">
        </a>
        <a href="#">
          <span>츠키코</span>
        </a>
        <span>힘들겠다... 쉬어라</span>
      </div>
     
    </div>
  </div>