<!DOCTYPE html>
<html>
<head>
<script src="/js/jquery.js" type="text/javascript"></script>
<script src="https://echarts.baidu.com/dist/echarts.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="/css/easy.css" />
</head>
<body >
<div style="margin: 0px;background-color: bisque;" >
<div>
<img class="pic" src="/img/head.png" style="width: 100%; height: 45px;margin-right: 0;margin-left: 0;">
</div>
<div style="width:20%;display: inline-block;vertical-align: top;">
<ul id="menu2" role="menu" class="el-menu el-menu--inline" data-old-padding-top="" data-old-padding-bottom="" data-old-overflow="" style="background-color: rgb(48, 65, 86);"> <a class="">
<li value="1" role="menuitem" tabindex="-1" class="el-menu-item" style="padding-left: 40px; color: rgb(191, 203, 217); background-color: rgb(48, 65, 86);"><svg data-v-81d70f2a="" aria-hidden="true" class="svg-icon"><use data-v-81d70f2a="" xlink:href="#icon-product-list"></use></svg> <span>用户知识点优先级</span></li>
</a><a class="router-link-exact-active router-link-active">
<li value="2" role="menuitem" tabindex="-1" class="el-menu-item is-active" style="padding-left: 40px; color: rgb(191, 203, 217); background-color: rgb(48, 65, 86);"><svg data-v-81d70f2a="" aria-hidden="true" class="svg-icon"><use data-v-81d70f2a="" xlink:href="#icon-product-add"></use></svg> <span>用户问题推荐次数</span></li>
</a><!----><!----><!---->
</ul>
</div>
<div id="priority" style="width: 1200px;height:400px;margin-top: 20px;"></div>
<div id="recommendTimes" style="width: 1200px;height:400px;"></div>
</div>
</body>
<script src="/js/kpPriority.js" type="text/javascript"></script>
<script src="/js/recommendTimes.js" type="text/javascript"></script>
</html>