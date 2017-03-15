'use strict';
angular.module("module1",['ngRoute'])
.config(["$routeProvider",function($routeProvider){
	$routeProvider.when("/module1",{templateUrl:"html/menu.html"})
	.otherwise({redirectTo:"/"});//template:"这是module1页面的内容"
}])
.controller("con1",["$scope",function($scope){
	
}]);