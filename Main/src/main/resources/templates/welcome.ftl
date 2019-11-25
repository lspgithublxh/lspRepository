<!DOCTYPE html>
<html>
<head>
<script src="/js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="/css/easy.css" />
<script type="text/javascript">
function submitConfig(){
 alert($("#examTypeId").attr("value"));
 // console.log($("#config").serializeArray());
 alert(encodeURI($("#config").serialize()));
 var data = $("#config").serialize();
 // alert($("#config").serializeObject());
 var fdata = $("#config").serializeArray();
 var target = {};
 for(var j in fdata){
   console.log(fdata[j]);
   target[fdata[j]["name"]] = fdata[j]["value"];
 }
 target = JSON.stringify(target);
 target = encodeURI(target);
 alert(target);
 $.get("/html?token=" + target, function(result){
 	alert(result);
 });
}

</script>
</head>
<body style="margin: 0px;">
<div>
<img class="pic" src="https://t12.baidu.com/it/u=3999260081,4172311933&amp;fm=76" style="width: 100%; height: 75px;margin-right: 0;margin-left: 0;">
</div>
<div style="width:20%;display: inline-block;vertical-align: top;">
<ul role="menu" class="el-menu el-menu--inline" data-old-padding-top="" data-old-padding-bottom="" data-old-overflow="" style="background-color: rgb(48, 65, 86);"> <a href="#/pms/product" class="">
<li role="menuitem" tabindex="-1" class="el-menu-item" style="padding-left: 40px; color: rgb(191, 203, 217); background-color: rgb(48, 65, 86);"><svg data-v-81d70f2a="" aria-hidden="true" class="svg-icon"><use data-v-81d70f2a="" xlink:href="#icon-product-list"></use></svg> <span>商品列表</span></li>
</a><a href="#/pms/addProduct" class="router-link-exact-active router-link-active">
<li role="menuitem" tabindex="-1" class="el-menu-item is-active" style="padding-left: 40px; color: rgb(64, 158, 255); background-color: rgb(48, 65, 86);"><svg data-v-81d70f2a="" aria-hidden="true" class="svg-icon"><use data-v-81d70f2a="" xlink:href="#icon-product-add"></use></svg> <span>添加商品</span></li>
</a><!----><!----><!----><a href="#/pms/productCate" class="">
<li role="menuitem" tabindex="-1" class="el-menu-item" style="padding-left: 40px; color: rgb(191, 203, 217); background-color: rgb(48, 65, 86);"><svg data-v-81d70f2a="" aria-hidden="true" class="svg-icon"><use data-v-81d70f2a="" xlink:href="#icon-product-cate"></use></svg> <span>商品分类</span></li>
</a><!----><!----><a href="#/pms/productAttr" class="">
<li role="menuitem" tabindex="-1" class="el-menu-item" style="padding-left: 40px; color: rgb(191, 203, 217); background-color: rgb(48, 65, 86);"><svg data-v-81d70f2a="" aria-hidden="true" class="svg-icon"><use data-v-81d70f2a="" xlink:href="#icon-product-attr"></use></svg> <span>商品类型</span></li>
</a><!----><!----><!----><a href="#/pms/brand" class="">
<li role="menuitem" tabindex="-1" class="el-menu-item" style="padding-left: 40px; color: rgb(191, 203, 217); background-color: rgb(48, 65, 86);"><svg data-v-81d70f2a="" aria-hidden="true" class="svg-icon"><use data-v-81d70f2a="" xlink:href="#icon-product-brand"></use></svg> <span>品牌管理</span></li>
</a><!----><!---->
</ul>
</div>

<div style="margin-top: 10px;display: inline-block;margin-left: 50px;">
<form id="config">
考试类型：<input id="examTypeId" value="${config.examTypeId}" name="examType" /></br>
问题优先级 参数1：<input value="${config.priorityAlgorithmParam1}" name="priorityAlgorithmParam1" /></br>
问题优先级 参数2：<input value="${config.priorityAlgorithmParam2}" name="priorityAlgorithmParam2" /></br>
综合推荐知识点的个数：<input value="${config.compositeTopn}" name="compositeTopn" /></br>
综合推荐知识点的复杂度总和限制：<input value="${config.compositeComplexTotalMax}" name="compositeComplexTotalMax" /></br>
限定一级知识点推荐时的复杂度总和限制：<input value="${config.topKpComplexTotalMax}" name="topKpComplexTotalMax" /></br>
限定一级知识点推荐时的推荐知识点最多个数：<input value="${config.topKpRequestTopn}" name="topKpRequestTopn" /></br>
知识点最大级别<input value="${config.knowledgeLvlMax}" name="knowledgeLvlMax" /></br>
初始化知识点的掌握度<input value="${config.initKpGraspVal}" name="initKpGraspVal" /></br>
知识点相似度阈值：<input value="${config.kpSimilarThredshold}" name="kpSimilarThredshold" /></br>
相邻层知识点的相似递减率：<input value="${config.kpLvlReduceRadio}" name="kpLvlReduceRadio" /></br>
筛选知识点点时知识点的最低优先级：<input value="${config.kpPriorityMin}" name="kpPriorityMin" /></br>
复杂度归一化参数值：<input value="${config.comlexNormalizeParam}" name="comlexNormalizeParam" /></br>
重要度归一化参数值：<input value="${config.importanceNormalizeParam}" name="importanceNormalizeParam" /></br>
知识点更新步长系数：<input value="${config.kpUpdateStep}" name="kpUpdateStep" /></br>
知识点更新练习系数：<input value="${config.kpUpdatePractise}" name="kpUpdatePractise" /></br>
知识点更新学习系数：<input value="${config.kpUpdateLearn}" name="kpUpdateLearn" /></br>
知识点更新遗忘系数：<input value="${config.kpUpdateForget}" name="kpUpdateForget" /></br>
添加时间：<input value="${config.addTime}" name="addTime" /></br>
<input type="button" value="点击" name="点击" onclick="submitConfig()"><!--alert('hello'); -->
</form>
</div>
</body>
</html>