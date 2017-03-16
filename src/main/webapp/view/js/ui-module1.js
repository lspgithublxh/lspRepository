'use strict';
angular.module("ui-module1",['ui.router'])
.config(["$urlRouterProvider", "$stateProvider", function($urlRouterProvider,$stateProvider){
	$urlRouterProvider.when("/next", "/next/module")
					  .when("/last", "/next/module")
					  .otherwise("/main");//
	$stateProvider.state("next", {
		url: "/next",
		templateUrl: "html/ui-main.html",
		controller: "nextController"
	})
	.state("next.module", {
		url: "/module",
		templateUrl: "html/ui-next.html",
		//dd:'',增加这一行错误的也不会报错
		controller: "moduleController"
	});
}])
.controller("nextController",["$scope", function($scope){
	
}])
.controller("moduleController",["$scope", function($scope){
	
}]);
