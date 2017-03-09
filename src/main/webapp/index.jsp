<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
<script src="global/jquery/jquery-3.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
var fun = {};
function func(type){
	switch(type){
	case 'update':
		fun.update();
		break;
	}
}
(function(){
	var postTest = function(){
		var obj = JSON.stringify({name:"李少平",id:9});
		//1.post对象传输失败的原因
	//	$.post("user/all",obj,function(response){
//		alert(JSON.stringify(response));
	//	},"json");
		 //2.ajax传输没有问题
		$.ajax({
			url:"json/user",
			data:obj,
			datatype:"json",
			type:"post",
			success:function(e){
			//	alert(JSON.stringify(e));
				$("#div").html(JSON.stringify(e));
			},
			error:function(e){
				alert("error");
			},
			contentType:"application/json; charset=UTF-8"
		});
	}
	postTest();
	fun.update = function(){
		window.location.href = "user/page?name=lishaoping";  //如何发请求，而在请求返回来的时候更新url
	}
})()

</script>
</head>
<body>
<h2>Hello World!</h2>
<div id="div"></div>
<button onclick="func('update')">click</button>
</body>
</html>
