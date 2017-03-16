'use strict';
angular.module("ui-module2",['ui.router'])
.config(["$urlRouterProvider","$stateProvider",function($urlRouterProvider, $stateProvider){
	//url转发
	$urlRouterProvider.when("", "/main")
					  .when("/", "/main")
					  .otherwise("/main");//
	//状态到url的映射，即页面上点击超链接所标明的状态，点击后修改url#后面的样子
	$stateProvider.state("main",{
		url : "/main",
		templateUrl : "html/ui-main.html",
		controller: "mainController"
	})
	.state("main.module1", {
		url: "/module1",
		templateUrl: "html/ui-module2.html",
		controller: "module1Controller"
	});
}])
.controller("mainController",["$scope", function($scope){
	//alert("mainController");
}])
.controller("module1Controller",["$scope", function($scope){
	//alert("moduleController");
}]);