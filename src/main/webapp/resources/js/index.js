var background = [ "/resources/images/bg1.jpg", "/resources/images/bg2.jpg",
		"/resources/images/bg3.jpg", "/resources/images/bg4.jpg" ];
var idx = 0;

var path = '';
var upload = false;
window.onload = function() {

	document.getElementById("input_file").onchange = function() {
		readImg('input_file');
	}

}

// input 태그의 id와 출력할 img 태그의 id를 받아서 함수를 호출하게 된다.
function readImg(inputId) {

	var file = document.getElementById(inputId).files[0];

	var reader = new FileReader();

	reader.readAsDataURL(file);

	reader.onload = function() {

		$('.signup_form > a > img').attr("src", reader.result);
	}

	reader.onerror = function(e) {
		alert("읽기 오류:" + e.target.error.code);
		return;
	}
}

function set_path(mypath) {
	path = mypath;
	for (i = 0; i < background.length; i++) {
		background[i] = path + background[i];
	}
}

$(function() {
	var insert_url = path + '/member_insert.do';
	caller();
	$("form#r_form").submit(function(event){
		 
		  //disable the default form submission
		  event.preventDefault();
		 
		  //grab all form data  
		  var formData = new FormData($(this)[0]);

		  $.ajax({
		    url: insert_url,
		    type: 'POST',
		    data: formData,
		    async: false,
		    cache: false,
		    contentType: false,
		    processData: false,
		    success: function (returndata) {
		    	ShowMessage("회원가입 성공!");
		    }
		  });
		 
		  return false;
		});
	$(".signup_form").draggable();
	$('.signup_form > form > input[type=\'file\']').change(function() {

		if (upload == true)
			return;
		console.log('start!');
		upload = true;
		previewFile();
	});
	$('input[name=\'userid\']').focusout(function() {
		// console.log(event.keyCode);
		var id = $('input[name=\'userid\']').val();
		// console.log(id);

		if (id.length < 4) {
			ShowMessage("영문자 숫자조합 4~8자리");

			return;
		} else if (id.length > 8) {
			$('input[name=\'userid\']').val(id.substring(0, 8));
			ShowMessage("영문자 숫자조합 4~8자리");

		}

		var url = "/check_id.do";

		var param = "userid=" + encodeURIComponent(id);
		url = path + url;
		$.post(url, param, function(result) {
			if (result.result == 'yes') {
				ShowMessage("사용이 가능한 아이디 입니다.");
			} else {
				ShowMessage("중복된 아이디 입니다.");

			}
		})

	});
});
function caller() {
	if (idx == background.length) {
		idx = 0;
		setTimeout(function() {
			caller();
		}, 0);

		return;
	}
	$(".background > img").animate({
		opacity : 0
	}, 4000, function() {
		$(".background > img").attr("src", background[idx]).css({
			opcaity : 0.1
		}).animate({
			opacity : 1
		}, 2000, function() {
			idx++;
			setTimeout(function() {
				caller();
			}, 0);
		})
	});
}
function Login() {
	var user_id = $('.user_id').val();
	var user_pw = $('.user_pwd').val();
	try {
		$.post(path + '/login.do',
				'userid=' + user_id + '&password=' + user_pw, function(result) {
					if (result.result == 'yes') {
						list();
					} else {
						ShowMessage('Login Failed');
					}
				});
	} catch (e) {
		ShowMessage('Login Failed');

	}
}

function list() {
	$("div").slideUp(
			"slow",
			function() {
				$.get(path + '/list.do', function(result) {
					var head = '<head>\n'
							+ result.split('<head>')[1].split('</head>')[0]
							+ '</head>';
					var body = '';
					try {
						body = result.split('<!-- Start -->')[1]
								.split('<!-- Exit -->')[0];
					} catch (e) {
						console.log(e);
					}
					$('head').html(head);
					$('body').html(body);
				})
			});
}

function ShowMessage(text) {
	$('.Message > .content > p').html(text);
	$('.Message').css({
		'visibility' : 'visible'
	});
	$('.Message').animate({
		height : '250px'
	}, 700);
}
function ClosedMessage() {
	$('.Message').animate({
		height : '0px'
	}, 700, function() {
		$('.Message').css({
			'visibility' : 'hidden'
		});
	});
}
function get_addr() {
	daum.postcode.load(function() {
		new daum.Postcode({
			oncomplete : function(data) {
				$('input[name=\'addr\']').val(data.address);
			}
		}).open();
	});
}
function signup(f) {
   
   var id = $(f).find("input[name='userid']").val();
   var pwd = $(f).find("input[name='password']").val();
   var username = $(f).find("input[name='username']").val();
   var date = $(f).find("input[name='birthday']").val();
   var tel = $(f).find("input[name='phonenumber']").val();
   var addr = $(f).find("input[name='addr']").val();
   
   if(id == "") {
      alert("빠짐없이 기입하세요!");
      $(f).find("input[name='userid']").focus();
      return;
   }
   else if(pwd == '') {
      alert("빠짐없이 기입하세요!");
      $(f).find("input[name='password']").focus();
      return;
   }
   else if(username == '') {
      alert("빠짐없이 기입하세요!");
      $(f).find("input[name='username']").focus();
      return;
   }
   else if(date == '') {
      alert("빠짐없이 기입하세요!");
      $(f).find("input[name='birthday']").focus();
      return;
   }
   else if(tel == '') {
      alert("빠짐없이 기입하세요!");
      $(f).find("input[name='phonenumber']").focus();
      return;
   }
   else if(addr == '') {
      alert("빠짐없이 기입하세요!");
      $(f).find("input[name='addr']").focus();
      return;
   }
   
   $("form#r_form").submit();
}

function signup_cancel(f) {
   $('.signup_form').css({visibility:'hidden'});
   $(f).find("input[name='userid']").val("");
   $(f).find("input[name='password']").val("");
   $(f).find("input[name='username']").val("");
   $(f).find("input[name='birthday']").val("");
   $(f).find("input[name='phonenumber']").val("");
   $(f).find("input[name='addr']").val("");
   $('.signup_form > form > a > img').attr("src", path+ "/resources/images/profile.jpg");
}
function previewFile() {

	var preview = document.querySelector('#profile');
	var files = document.querySelector('#input_file').files;

	if ($('.signup_form > img').length < 2) {
		function readAndPreview(file) {
			// 'file.name' 형태의 확장자 규칙에 주의
			if (/\.(jpe?g|png|gif)$/i.test(file.name)) {
				var reader = new FileReader();
				upload = false;
				reader.addEventListener("load", function(img) {
					var image = new Image();
					image.title = file.name;
					image.src = this.result;
					$('.signup_form > form > a > img').attr("src", image.src);

				}, false);

				reader.readAsDataURL(file);

			} else {
				alert('이미지파일만 등록하세요');
				upload = false;
				return;
			}
		}

		if (files) {
			[].forEach.call(files, readAndPreview);
		}
	}

}
