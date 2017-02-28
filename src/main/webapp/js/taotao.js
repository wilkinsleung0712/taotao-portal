var TT = TAOTAO = {
	checkLogin : function() {
		// 需要提取token信息，如果系统已经成功登陆，后台会把用户信息写在cookie里面
		var _ticket = $.cookie("TT_TOKEN");
		if (!_ticket) {
			return;
		}
		
		$.ajax({
			url : "http://localhost:8084/user/token/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data) {
				if (data.status == 200) {
					var username = data.data.username;
					var html = username
							+ "，欢迎来到淘淘！<a href=\"javascript:void()\" class=\"link-logout\" onclick=\"logout()\">[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}

$(function() {
	// Jquery方法会在完成DOM加载后被调用
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});

function logout() {
	// 需要提取token信息，如果系统已经成功登陆，后台会把用户信息写在cookie里面
	var _ticket = $.cookie("TT_TOKEN");
	// 提交logout请求
	$.ajax({
		url:"http://localhost:8084/user/logout/" + _ticket,
		dataType : "jsonp",
		type:"GET",
		success:function(data){
			if(data.status == 200){
				location="http://localhost:8082/";
			}
		}
	});
}