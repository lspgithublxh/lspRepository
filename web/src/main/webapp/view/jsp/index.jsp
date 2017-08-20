<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>new page(runoob.com)</title>
<!-- 一起取过来 -->
<script src="../../global/jquery/jquery-3.1.1.min.js" type="text/javascript"></script>
<script  src="../../global/angularjs2/angularjs2.min.js" type="text/javascript"> </script>
<script  src="../../global/bootstrap/bootstrap.min.js" type="text/javascript"> </script>
<link rel="stylesheet" type="text/css" href="../../global/bootstrap/bootstrap.min.css" />
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
	var postTest = function(url){
		var obj = JSON.stringify({name:"lishaoping",id:9});
		//1.post 
	//	$.post("user/all",obj,function(response){
//		alert(JSON.stringify(response));
	//	},"json");
		 //2.ajax
		$.ajax({
			url:url,
			data:obj,
			datatype:"json",
			type:"post",
			success:function(e){
				$("#div").append(e);
			//	alert(JSON.stringify(e));
				//$("#div").html(JSON.stringify(e));
			},
			error:function(e){
				alert("error");
			},
			contentType:"application/json; charset=UTF-8"
		});
	}
	postTest("../../view/post.html");
	postTest("../../user/name");
	fun.update = function(){
		window.location.href = "../../view/inde.html";  //
	}
})()

</script>
</head>
<body ng-app>
<h2>Hello World!</h2>
<div id="div"></div>
<button onclick="func('update')">click</button>
</body>
</html>
