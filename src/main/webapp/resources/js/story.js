var page = 6;
var status = 0;
var max;
var myurl = '';
var sc = 1;
var data = [];
var html;
var saveScroll=0;
var a;

// 로드 : 1 => 내 게시글, 2=> 친구게시글, 3=> 게시글 없음
var load = 1;
// 로그인 된 유저(나)의 user_idx (confirm용)
var my_idx;
// 메인 화면 피드의 유저정보 저장
var g_user;
var g_friend_idx;
// 친구가 맞나?
var is_friend;

$(document).ready(function() {
	
	// 로그인 한 유저의 user_idx 저장
	my_idx = $('.user_menu').find("input[name='user_idx']").val();
	
	$('nav').slideDown(600, function() {
		$('.body').slideDown("slow", function() {
			$('.user_menu').slideDown(500, function() {
				max = $(document).height();

			});
		});
	});
	$(document).scroll(function() {

		status = $(document).scrollTop()+$('body').innerHeight();
		
		// 내피드
		if(load==1) {
			// sc : 스크롤 여부, status : 스크롤 진행 정도, max : 최대 스크롤, -100 : 'near' scroll bottom
			if (sc == 1 && status > max-100) {
				sc = 0;

				myurl = 'list_json.do?startPage=' + page + '&endPage=' + (page + 4);

				$.ajax({
					type : 'GET',
					url : myurl,
					dataType : 'json',
					success : function(result) {
						data = result;
						get_json(data);

						page += 5;
						if (html != '') {
							$('.posts').html($('.posts').html() + html);
							max = $(document).height();
						}
						else if (html == '') {
							load = 3;
						}
						sc = 1;
					}
				});


			}
		}
		
		// 친구 피드
		else if(load==2) {
			
			if (sc == 1 && status > max-100) {
				sc = 0;
				
				myurl = 'board.do?user_idx='+ g_friend_idx +'&startPage=' + page + '&endPage=' + (page + 4);

				$.ajax({
					type : 'GET',
					url : myurl,
					dataType : 'json',
					success : function(result) {
						
						data = result.list;
						get_board_list(data);
						page += 5;
						if (html != '') {
							$('.body').html($('.body').html() + html);
							max = $(document).height();
						}
						else if (html == '') {
							load = 3;
						}
						sc = 1;
					}
				});


			}
			
		}
		
		// 게시글 없음
		else if(load==3) {
		}
	});
});


// 본인의 메인화면만 보인다.
function get_json(data) {
	var content = '';
	var comment = '';
	var photo = '';
	var content_data;
	var comment_data;
	var username;
	var profile;
	html = '';
	
	for (i = 0; i < data.length; i++) {
		content_data = data[i].content;
		profile = data[i].user.profile;
		username = data[i].user.username;
		comment = '';
		photo = '';
		// 만약 코멘트가 존재할 경우
		if (data[i].comment != null) {
			comment_data = data[i].comment;
			for (k = 0; k < comment_data.length; k++) {
				comment += '<div class="comment_list">\
								<div class="comment" style="display: block;">\
									<a href="javascript:board('+ comment_data[k].user_idx +');">\
										<img src="/crepas_story/resources/images/'+ comment_data[k].user.profile+'">\
									</a>\
									<a href="javascript:board('+ comment_data[k].user_idx +');">\
										<span>' + comment_data[k].user.username + '\</span>\
									</a>\
									<span>\
										<span>' + comment_data[k].content + '\</span>\
										<input type="hidden" name="comment_idx" value="' + comment_data[k].comment_idx + '">\
										<input type="hidden" name="post_idx" value="' + comment_data[k].post_idx + '">\
										<button onclick="comment_update_form($(this).parent().parent());">수정</button>\
										<button onclick="comment_delete($(this).parent());saveScroll=$(\'body\').scrollTop();">삭제</button>\
									</span>\
								</div>\
								<div class="comment_form" style="display: none;">\
									<span>\
										<input type="text" name="comment_content" value=" '+ comment_data[k].content + '">\
										<input type="hidden" name="comment_idx" value="' + comment_data[k].comment_idx + '">\
										<input type="hidden" name="post_idx" value="' + comment_data[k].post_idx + '">\
										<button onclick="comment_update_cancel($(this).parent().parent());">취소</button>\
										<button onclick="comment_update($(this).parent().parent());saveScroll=$(\'body\').scrollTop();">완료</button>\
									</span>\
								</div>\
							</div>';
			}
		}
		
		// 게시글에 사진 추가
		for(j=0; j<data[i].photo.length; j++ ) {
			
			// 사진이 없을 경우
			if(data[i].photo[j].photoname == 'no_file'){
				photo += '<div>\
							<img onerror="this.style.display=\'none\'" alt=\'\' />\
						</div>';
			}
			// 사진이 있을 경우
			else if(data[i].photo[j].photoname != 'no_file'){
				photo += '<div class="photo" style="display: block;">\
									<img src="/crepas_story/resources/images/'+data[i].photo[j].photoname +'" width="200">\
							</div>';
			}
		}
		
		content += '<div class="post_data">\
						<div class="pick_box">\
							<a onclick="post_update_form($(this).parent().parent());saveScroll=$(\'body\').scrollTop();">수정</a>\
							<a onclick="post_delete($(this).parent().parent());saveScroll=$(\'body\').scrollTop();">삭제</a>\
						</div>\
						<a class="profile_img">\
							<img src="/crepas_story/resources/images/'+profile+'">\
						</a>\
						<a class="pick_view"\
							onclick="saveScroll=$(\'body\').scrollTop();pickbox($(this).parent());"> <img\
							src="/crepas_story/resources/images/more.png">\
						</a> \
						<a href="#">\
							<div class="nick">' + username + '\</div>\
						</a>\
						'+photo+'\
						<div class="content">\
							' + content_data + '\
						</div>\
						<button onclick="post_delete($(this).parent());">삭제</button>\
						<button onclick="post_update_form($(this).parent());">수정</button>\
						<div class="comment_write">\
							<a href="#">\
								<img src="/crepas_story/resources/images/heart.png" class="heart">\
							</a>\
							<input type="text">\
							<input name="post_idx" type="hidden" value='+data[i].post_idx+'>\
							<a href="#">\
								<button onclick="comment_insert($(this).parent().parent());saveScroll=$(\'body\').scrollTop();">작성</button>\
							</a>\
						</div>\
						<div id="comment_list_'+data[i].post_idx+'">\
								' + comment + '\
						</div>\
					</div>';	
	}
	html += content;
}


//이미지 업로드 추가 코드
function post_insert(v) {
	if (confirm("정말 글을 올리시겠습니까?")==false) return;
	
	// form : story.jsp <form> 안의 내용 가져옴
	var form = $('#photo_form')[0];
	var formData = new FormData(form);
	
	/*formData.append("photo1", $("div[id='preview'] > img")[1]);
	formData.append("photo2", $("div[id='preview'] > img")[2]);*/
	
	formData.append("photo", $("#photo")[0].files[0]);
	//formData.append("photo", $("input[id=photo]")[0].files[1]);
	formData.append("content", $("div[id='content']").html());
	
	$(v).find("div[id='content']").html("내용을 입력해주세요");
	$.ajax({
		type : 'POST',
		url : 'post_insert.do',
		data : formData,	// formData안에 content도 포함 되 있음
		contentType: false,	// Server에서 Client로 전송할때의 Type, false:'application/json; charset=UTF-8'
		processData: false,
		//dataType : 'json',	// 주석처리해도 무관 (default: json)
		success : function(result) {
			if(result.result == 'yes') {
				location.href = "index.do";
			}
		}
	});
}

// 게시글 수정 폼 띄우기
function post_update_form(v) {
	var content = v.find("div[class='content']").html();
	var post_idx = v.find("input[name='post_idx']").val();
	// 숨긴창 표시
	$(v).parents().find(".modify_form").css({visibility: 'visible'});
	$(v).parents().find("div[id='m_content']").html(content);
	$(v).parents().find(".modify_form").find("input[name='post_idx']").val(post_idx);
}

// 게시글 수정 취소, 수정폼 값 초기화
function post_update_cancel() {
	$('.modify_form').css({visibility: 'hidden'});
	$('.modify_form').find("div[id='m_content']").html('입력하세요');
	$('.modify_form').find("input[name='post_idx']").val("");
}

// 게시글 수정
function post_update() {
	if(confirm("정말 수정하실래요?")==false) return;
	
	var content = $('.modify_form').find("div[id='m_content']").html();
	var post_idx = $('.modify_form').find("input[name='post_idx']").val();
	
	$.ajax({
		type : 'GET',
		url : 'post_update.do',
		data : {'post_idx':post_idx, 'content': content },
		dataType : 'json',
		success : function(result) {
			if(result.result == 'yes') {
				location.href = "index.do";
			}
		}
		
	});
	
}

// 게시글 삭제
function post_delete(v) {
	if (confirm("정말 글을 삭제 하시겠습니까?")==false) return;
	
	var post_idx = v.find("input[name='post_idx']").val();

	$.ajax({
		type : 'GET',
		url : 'post_delete.do',
		data : {'post_idx':post_idx},
		dataType : 'json',
		success : function(result) {
			if(result.result == 'yes') {
				location.href = "index.do";
				
			}
		}
	});
}


/*------------------------------------------------------------------------------------------------------------------------------------------------------------*/
// 코멘트

// 댓글 추가
function comment_insert(v){
	var content = v.find("input[type='text']").val();
	var post_idx = v.find("input[name='post_idx']").val();
	var user_idx = $(".user_menu").find("input[name='user_idx']").val();
	var username = $(".user_menu").find("input[name='username']").val();

	// 입력된 text 값 지워주기
	$(v).find("input[type='text']").val("");
	
	$.ajax({
		type : 'GET',
		url : 'comment_insert.do',
		data : {'post_idx':post_idx, 'content':content, 'user_idx':user_idx, 'username':username},
		dataType : 'json',
		success : function(result) {
			data = result;
			// 변경되 DB 댓글 불러와서 삽입
			comment_list(data);
			// 저장한 스크롤 위치로 회귀
			$('body').scrollTop(saveScroll);
		}
	});
}

// 댓글 수정 폼 띄우기
function comment_update_form(v){
	$(v).css({display:"none"});
	$(v).parent().find("div.comment_form").css({display:"block"});
}

// 댓글 수정 취소 후 댓글창 원복
function comment_update_cancel(v){
	$(v).css({display:"none"});
	$(v).parent().find("div.comment").css({display:"block"});
}

// 댓글 수정
function comment_update(v){	
	if(confirm("정말 수정하시겠어요?")==false) return;
	
	// 수정폼 원복
	$(v).css({display:"none"});
	$(v).parent().find("div.comment").css({display:"block"});

	var post_idx = v.find("input[name=post_idx]").val();
	var comment_idx = v.find("input[name='comment_idx']").val();
	var content = v.find("input[name='comment_content']").val();
	
	$.ajax({
		type : 'GET',
		url : 'comment_update.do',
		data : {'comment_idx' : comment_idx, 'content' : content, 'post_idx': post_idx},
		dataType : 'json',
		success : function(result) {
			data = result;
			comment_list(data);
			$('body').scrollTop(saveScroll);
		}
	});
}

// 댓글 삭제
function comment_delete(v){
	if(confirm("정말 삭제하실래요?")==false)return;
	
	var comment_idx = v.find("input[name='comment_idx']").val();
	var post_idx = v.find("input[name='post_idx']").val();
	
	$.ajax({
		type : 'GET',
		url : 'comment_delete.do',
		data : {'comment_idx' : comment_idx, 'post_idx': post_idx},
		dataType : 'json',
		success : function(result) {
			data = result;
			comment_list(data);
			$('body').scrollTop(saveScroll);
		}
	});
}

// 변경된 DB 댓글 삽입
function comment_list(data){
	// 작성한 글이 나의 글인지 비교하기 위한 변수
	var c_post_user_idx = $('.comment_write').find("input[name='user_idx']").val();
	var comment = '';
	var button = '';
	var list = data.list;
	
	if (list.length != 0) {
		for (k = 0; k < list.length; k++) {
			// 남의 피드에서 내글만 수정, 지우기 가능
			if (list[k].user_idx == my_idx) {
				button = '<button onclick="comment_update_form($(this).parent().parent());">수정</button>\
					<button onclick="comment_delete($(this).parent());saveScroll=$(\'body\').scrollTop();">삭제</button>';
			}
			// 내 피드의 댓글을 새로고침 할때 댓글 삭제기능만 추가
			else if(c_post_user_idx == my_idx) {
				button = '<button onclick="comment_delete($(this).parent());saveScroll=$(\'body\').scrollTop();">삭제</button>';
			}
			
			comment += '<div class="comment_list">\
							<div class="comment" style="display: block;">\
								<a href="javascript:board('+ list[k].user_idx +');">\
									<img src="/crepas_story/resources/images/'+ list[k].user.profile+'">\
								</a>\
								<a href="javascript:board('+ list[k].user_idx +');">\
									<span>' + list[k].user.username + '\</span>\
								</a>\
								<span>\
									<span>' + list[k].content + '\</span>\
									<input type="hidden" name="comment_idx" value="' + list[k].comment_idx + '">\
									<input type="hidden" name="post_idx" value="' + list[k].post_idx + '">\
									' + button + '\
								</span>\
							</div>\
							<div class="comment_form" style="display: none;">\
								<span>\
									<input type="text" name="comment_content" value=" '+ list[k].content + '">\
									<input type="hidden" name="comment_idx" value="' + list[k].comment_idx + '">\
									<input type="hidden" name="post_idx" value="' + list[k].post_idx + '">\
									<button onclick="comment_update_cancel($(this).parent().parent());">취소</button>\
									<button onclick="comment_update($(this).parent().parent());saveScroll=$(\'body\').scrollTop();">완료</button>\
								</span>\
							</div>\
						</div>';
			button = '';
		}
	}
	
	var comment_list = '#comment_list_' + data.post_idx;
	$(comment_list).html(comment);		
}






/*-------------------------------------------------------------------------------------------------------------------------*/
// 친구의 게시글 불러오기
function board(friend_idx) {
// 만약 나의 초상화를 클릭했다면 내 메인화면으로
	if (my_idx == friend_idx) {
		load = 1;
		location.href = 'index.do';
		return;
	}
	// 친구인가 확인
	check_friend(friend_idx);
	
	// 페이지 지워주기
	$('.body').html("");
	
	// 전역변수에 값 넣기
	g_friend_idx = friend_idx;
	
	// 스크롤을 위한 조건설정
	load = 2; // 친구의 피드
	page = 6; // paging 초기화
	sc = 1;   // 스크롤 초기화
	
	// 친구의 게시글 목록 새로 그리기
	$.ajax({
		type : 'GET',
		url : 'board.do',
		data : {'user_idx' : friend_idx},
		dataType : 'json',
		success : function(result) {
			data = result.list;
			g_user = result.user;
			

			// 피드 불러오기
			get_board_list(data);
			// 불러온 피드 순차 배치
			$('.body').html($('.body').html() + html);
			// 스크롤 최댓값 설정
			max = $(document).height();
		}
	});
}

// 친구 피드
function get_board_list(data) {
	
	// 본 게시글
	var content = '';
	// 게시글의 댓글
	var comment = '';
	// 포토
	var photo = '';
	// 1건의 게시글에 대한 속성들
	var content_data;
	// 1건의 댓글에 대한 속성들
	var comment_data;
	// 게시글의 댓글
	var username;
	// 유저사진
	var profile;
	// 조건에 따라 버튼 추가
	var button;
	// 프로필
	var profile_page;
	// 친구신청 버튼
	var bt_friend = '';
	
	html = '';
	
	if($('.body').html()=="") {
		if(is_friend=='no') {
			bt_friend = '<a href="javascript:send_friend('+g_friend_idx+', '+my_idx+');" onmouseover="$(this).css({background : \'#cccccc\', color : \'#888888\'});" onmouseleave="$(this).css({background : \'#777777\', color : \'white\'});">\
							친구신청\
						</a>';
		}
		else if(is_friend=='yes') {
			bt_friend = '<a href="#" onmouseover="$(this).css({background : \'#cccccc\', color : \'#888888\'});" onmouseleave="$(this).css({background : \'#777777\', color : \'white\'});">\
							친구정보\
						</a>';
		}
		
		// 반복문 돌기 전에 프로필 윈도우 추가
		profile_page = '<!-- 메인(홈) -->\
					<div class="home">\
						<a href="#">\
							<img src="/crepas_story/resources/images/' +g_user.profile+ '">\
						</a>\
						<a href="#">\
							<div class="nick">'+g_user.username+'</div>\
						</a>\
						<div class="home_menu">\
							<a href="#" onmouseover="$(this).css({color : \'#778844\'});" onmouseleave="$(this).css({color : \'black\'});">\
								전체\
							</a>\
							<a href="#" onmouseover="$(this).css({color : \'#778844\'});" onmouseleave="$(this).css({color : \'black\'});">\
								사진\
							</a>\
							'+ bt_friend +'\
						</div>\
					</div>';
		$('.body').html(profile_page);
	}
	

	for (i = 0; i < data.length; i++) {
		content_data = data[i].content;
		username = data[i].user.username;
		profile = data[i].user.profile;
		comment = '';
		button = '';
		photo = '';
		
		// 댓글이 있는 경우에만 불러옴
		if (data[i].comment != null) {
			comment_data = data[i].comment;
			for (k = 0; k < comment_data.length; k++) {
				// 만약 친구 피드에 내가 쓴 댓글이 있다면 버튼 추가
				if (comment_data[k].user_idx == my_idx) {
					button = '<button onclick="comment_update_form($(this).parent().parent());">수정</button>\
						<button onclick="comment_delete($(this).parent());saveScroll=$(\'body\').scrollTop();">삭제</button>';
					
				}
				comment += '<div class="comment_list">\
								<div class="comment" style="display: block;">\
									<a href="javascript:board('+ comment_data[k].user_idx +');">\
										<img src="/crepas_story/resources/images/'+comment_data[k].user.profile+'">\
									</a>\
									<a href="javascript:board('+ comment_data[k].user_idx +');">\
										<span>' + comment_data[k].user.username + '\</span>\
									</a>\
									<span>\
										<span>' + comment_data[k].content + '\</span>\
										<input type="hidden" name="comment_idx" value="' + comment_data[k].comment_idx + '">\
										<input type="hidden" name="post_idx" value="' + comment_data[k].post_idx + '">\
										'+ button+'\
									</span>\
								</div>\
								<div class="comment_form" style="display: none;">\
									<span>\
										<input type="text" name="comment_content" value=" '+ comment_data[k].content + '">\
										<input type="hidden" name="comment_idx" value="' + comment_data[k].comment_idx + '">\
										<input type="hidden" name="post_idx" value="' + comment_data[k].post_idx + '">\
										<button onclick="comment_update_cancel($(this).parent().parent());">취소</button>\
										<button onclick="comment_update($(this).parent().parent());saveScroll=$(\'body\').scrollTop();">완료</button>\
									</span>\
								</div>\
							</div>';
				button = '';
			}
		}
		
		for(j=0; j<data[i].photo.length; j++ ) {
			if(data[i].photo[j].photoname == 'no_file'){
				photo += '<div>\
							<img onerror="this.style.display=\'none\'" alt=\'\' />\
						</div>';
			}
			else if(data[i].photo[j].photoname != 'no_file'){
				photo += '<div class="photo" style="display: block;">\
									<img src="/crepas_story/resources/images/'+data[i].photo[j].photoname +'" width="200">\
							</div>';
			}
		}

		content += '<div class="post_data">\
						<a class="profile_img" href="#">\
							<img src="/crepas_story/resources/images/'+ profile +'">\
						</a>\
						<a href="#">\
							<div class="nick">' + username + '\</div>\
						</a>\
						'+ photo +'\
						<div class="content">\
							' + content_data + '\
						</div>\
						<div class="comment_write">\
							<a href="#">\
								<img src="/crepas_story/resources/images/heart.png" class="heart">\
							</a>\
							<input type="text">\
							<a href="#">\
								<input name="post_idx" type="hidden" value='+data[i].post_idx+'>\
								<button onclick="comment_insert($(this).parent().parent());saveScroll=$(\'body\').scrollTop();">작성</button>\
							</a>\
						</div>\
						<div id="comment_list_'+data[i].post_idx+'">\
								' + comment + '\
						</div>\
					</div>';		
	}
	
	// $('.body').html(content);
	html+=content;
}



var pickbox_result=false;

//pickbox(수정/삭제)
function pickbox(obj){
	
	pickbox_result = (pickbox_result==false) ? true : false;
	if(pickbox_result==true)
		$(obj).find(".pick_box").css({display:"block"});
	else
		$(obj).find(".pick_box").css({display:"none"});
	
	$('body').scrollTop(saveScroll);		
}

var menu_show=false;
function Show_Menu(){
	if(!menu_show){
		$(".menu_bundle").css({"visibility":"visible"});
		
	}else{
		$(".menu_bundle").css({"visibility":"hidden"});	
	}
	menu_show=!menu_show;
}


/*-------------------------------------------------------------------------------------------------------------------------*/
//친구신청

// 친구인지 확인
function check_friend(friend_idx) {
	$.ajax({
		type : 'GET',
		url : 'check_friend.do',
		data : {'user_idx':my_idx, 'friend_idx' : friend_idx},
		dataType : 'json',
		success : function(result) {
			is_friend = result.result;
		}
	});
}


// 친구신청 보내기
function send_friend(friend_idx, user_idx) {
	if(confirm("친구신청 하시겠습니까?")==false) return;
	location.href='friend_insert.do?user_idx='+user_idx+'&friend_idx='+friend_idx;
}

// 친구 삭제
function friend_delete(friend_idx){
	if(confirm("정말로 친구를 끊으시겠어요??")==false) return;
	location.href='friend_delete.do?user_idx='+my_idx+'&friend_idx='+friend_idx;	
}











































