<!DOCTYPE html>
<html>
<head>
<script src="/js/jquery.js" type="text/javascript"></script>
<script src="https://echarts.baidu.com/dist/echarts.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="/css/easy.css" />
<script type="text/javascript">
function submitConfig(){
  	var truthBeTold = window.confirm("确定修改？");
	if (!truthBeTold) {
	 return;
	}

 var data = $("#config").serialize();
 var fdata = $("#config").serializeArray();
 var target = {};
 for(var j in fdata){
   console.log(fdata[j]);
   target[fdata[j]["name"]] = fdata[j]["value"];
 }
 target['configVersion'] = "${config.configVersion}";
 target = JSON.stringify(target);
 target = encodeURI(target);
 $.get("/update?token=" + target, function(result){
    if("success" == result){
    	alert("修改成功");
    }else{
        alert("修改失败");
    }

 	
 });
}

//click

</script>
</head>
<body >
<div style="margin: 0px;background-color: bisque;">
<div>
<img class="pic" src="/img/head.png" style="width: 100%; height: 45px;margin-right: 0;margin-left: 0;">
</div>
<div style="width:20%;display: inline-block;vertical-align: top;">
<ul id="menu" role="menu" class="el-menu el-menu--inline" data-old-padding-top="" data-old-padding-bottom="" data-old-overflow="" style="background-color: rgb(48, 65, 86);"> <a class="">
<li value="1" role="menuitem" tabindex="-1" class="el-menu-item" style="padding-left: 40px; color: rgb(191, 203, 217); background-color: rgb(48, 65, 86);"><svg data-v-81d70f2a="" aria-hidden="true" class="svg-icon"><use data-v-81d70f2a="" xlink:href="#icon-product-list"></use></svg> <span>消防</span></li>
</a><a class="router-link-exact-active router-link-active">
<li value="2" role="menuitem" tabindex="-1" class="el-menu-item is-active" style="padding-left: 40px; color: rgb(191, 203, 217); background-color: rgb(48, 65, 86);"><svg data-v-81d70f2a="" aria-hidden="true" class="svg-icon"><use data-v-81d70f2a="" xlink:href="#icon-product-add"></use></svg> <span>基金</span></li>
</a><!----><!----><!----><a class="">
<li value="3" role="menuitem" tabindex="-1" class="el-menu-item" style="padding-left: 40px; color: rgb(191, 203, 217); background-color: rgb(48, 65, 86);"><svg data-v-81d70f2a="" aria-hidden="true" class="svg-icon"><use data-v-81d70f2a="" xlink:href="#icon-product-cate"></use></svg> <span>会计</span></li>
</a><!----><!----><a  class="">
<li value="4" role="menuitem" tabindex="-1" class="el-menu-item" style="padding-left: 40px; color: rgb(191, 203, 217); background-color: rgb(48, 65, 86);"><svg data-v-81d70f2a="" aria-hidden="true" class="svg-icon"><use data-v-81d70f2a="" xlink:href="#icon-product-attr"></use></svg> <span>法考</span></li>
</a><!----><!----><!----><a  class="">
<li value="5" role="menuitem" tabindex="-1" class="el-menu-item" style="padding-left: 40px; color: rgb(191, 203, 217); background-color: rgb(48, 65, 86);"><svg data-v-81d70f2a="" aria-hidden="true" class="svg-icon"><use data-v-81d70f2a="" xlink:href="#icon-product-brand"></use></svg> <span>一建</span></li>
</a><!----><!---->
</ul>
</div>

<div style="margin-top: 10px;display: inline-block;margin-left: 50px;">
<form id="config">
<table>
<tr>
<td>考试类型：</td>
<td><input id="examTypeId" value="${config.examTypeId}" readonly name="examTypeId" /></br></td>
</tr>
<tr>
<td>问题优先级 参数1：</td>
<td><input value="${config.priorityAlgorithmParam1}" name="priorityAlgorithmParam1" /></br></td>
</tr>
<tr>
<td>问题优先级 参数2：</td>
<td><input value="${config.priorityAlgorithmParam2}" name="priorityAlgorithmParam2" /></br></td>
</tr>
<tr>
<td>综合推荐知识点的个数：</td>
<td><input value="${config.compositeTopn}" name="compositeTopn" /></br></td>
</tr>
<tr>
<td>综合推荐知识点的复杂度总和限制：</td>
<td><input value="${config.compositeComplexTotalMax}" name="compositeComplexTotalMax" /></br></td>
</tr>
<tr>
<td>限定一级知识点推荐时的推荐知识点最多个数：</td>
<td><input value="${config.topKpRequestTopn}" name="topKpRequestTopn" /></br></td>
</tr>
<tr>
<td>知识点最大级别</td>
<td><input value="${config.knowledgeLvlMax}" name="knowledgeLvlMax" /></br></td>
</tr>
<tr>
<td>初始化知识点的掌握度:</td>
<td><input value="${config.initKpGraspVal}" name="initKpGraspVal" /></br></td>
</tr>
<tr>
<td>知识点相似度阈值：</td>
<td><input value="${config.kpSimilarThredshold}" name="kpSimilarThredshold" /></br></td>
</tr>
<tr>
<td>相邻层知识点的相似递减率：</td>
<td><input value="${config.kpLvlReduceRadio}" name="kpLvlReduceRadio" /></br></td>
</tr>
<tr>
<td>筛选知识点点时知识点的最低优先级：</td>
<td><input value="${config.kpPriorityMin}" name="kpPriorityMin" /></br></td>
</tr>
<tr>
<td>复杂度归一化参数值：</td>
<td><input value="${config.comlexNormalizeParam}" name="comlexNormalizeParam" /></br></td>
</tr>
<tr>
<td>知识点更新步长系数：</td>
<td><input value="${config.kpUpdateStep}" name="kpUpdateStep" /></br></td>
</tr>
<tr>
<td>知识点更新练习系数：</td>
<td><input value="${config.kpUpdateLearn}" name="kpUpdateLearn" /></br></td>
</tr>
<tr>
<td>知识点更新学习系数：</td>
<td><input value="${config.comlexNormalizeParam}" name="comlexNormalizeParam" /></br></td>
</tr>
<tr>
<td>知识点更新遗忘系数：</td>
<td><input value="${config.kpUpdateForget}" name="kpUpdateForget" /></br></td>
</tr>
<tr>
<td>添加时间：</td>
<td><input value="${config.addTime}" name="addTime" /></br></td>
</tr>
<tr style="display:none">
<td>配置版本：</td>
<td><input value="${config.configVersion}" name="configVersion" /></br></td>
</tr>
<tr>
<td colspan="2">
<div data-v-adc76164="" class="el-form-item el-form-item--small" style="text-align: center;"><!----><div class="el-form-item__content" style="margin-left: 120px;"><button onclick="submitConfig()" data-v-adc76164="" type="button" class="el-button el-button--primary el-button--medium"><!----><!----><span>修改</span></button><!----></div></div>
</td>
</tr>
</table>
</form>
</div>
</div>
<div style="margin: 0px;background-color: bisque;" >
<div style="width:20%;vertical-align: top;">
<ul id="menu2" role="menu" class="el-menu el-menu--inline" data-old-padding-top="" data-old-padding-bottom="" data-old-overflow="" style="background-color: rgb(48, 65, 86);"> <a class="">
<li value="1" role="menuitem" tabindex="-1" class="el-menu-item" style="padding-left: 40px; color: rgb(191, 203, 217); background-color: rgb(48, 65, 86);"><svg data-v-81d70f2a="" aria-hidden="true" class="svg-icon"><use data-v-81d70f2a="" xlink:href="#icon-product-list"></use></svg> <span>用户知识点优先级</span></li>
</a><a class="router-link-exact-active router-link-active">
<li value="2" role="menuitem" tabindex="-1" class="el-menu-item is-active" style="padding-left: 40px; color: rgb(191, 203, 217); background-color: rgb(48, 65, 86);"><svg data-v-81d70f2a="" aria-hidden="true" class="svg-icon"><use data-v-81d70f2a="" xlink:href="#icon-product-add"></use></svg> <span>用户问题推荐次数</span></li>
</a><!----><!----><!---->
</ul>
</div>
<div id="main" style="width: 600px;height:400px;"></div>
</div>
</body>
<script  type="text/javascript">
 $("ul#menu a li").on("click",function(){     
  $("ul#menu a li").each(function(){
     $(this).css("color","rgb(191, 203, 217)");
   });
  $(this).css("color","rgb(64, 158, 255)");
  //$.get("/ab?examTypeId=" + $(this).attr("value"), function(result){
  	//alert(result);
 // });
 window.location.href="/config?examTypeId=" + $(this).attr("value");
 });
 //当前状态
 var url = window.location.href;
 var type = url.split("?")[1].split("=")[1];
 $("ul#menu a li").each(function(){
     if($(this).attr("value") == type + ""){
       $(this).css("color","rgb(64, 158, 255)");
     }
 });
</script>
<script src="/js/chart.js" type="text/javascript"></script>
</html>