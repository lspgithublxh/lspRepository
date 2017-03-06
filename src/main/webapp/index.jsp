<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
<script src="global/jquery/jquery-3.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
(function(){
	var postTest = function(){
		var obj = JSON.stringify({name:"李少平",id:9});
	//	$.post("user/all",{name:"lishaoping",id:9},function(response){
	//		alert(JSON.stringify(response));
	//	},"json");
		$.ajax({
			url:"user/all",
			data:obj,
			datatype:"json",
			type:"post",
			success:function(e){
				alert(JSON.stringify(e));
			},
			error:function(e){
				alert("error");
			},
			contentType:"application/json; charset=UTF-8"
		});
	}
	postTest();
})()

</script>
</head>
<body>
<h2>Hello World!</h2>

</body>
</html>
