'use strict';
angular.module("ui-module1",['ui.router'])
.config(["$urlRouterProvider", "$stateProvider", function($urlRouterProvider,$stateProvider){
	$urlRouterProvider.when("/next", "/next/module")
					  .when("/last", "/next/module")
					  .otherwise("/main");//
	$stateProvider.state("next", {
		url: "/next",
		templateUrl: "html/ui-next.html",
		controller: "nextController"
	})
	.state("next.module", {//提高复用度，独立出去，分离出去
		url: "/module",
		views: {
			'header': {
				templateUrl: "html/ui-module1-header.html",
				//dd:'',增加这一行错误的也不会报错
				controller: "moduleHeaderController"
			},
			'body': {
				templateUrl: "html/ui-module1-body.html",
				//dd:'',增加这一行错误的也不会报错
				controller: "moduleBodyController"
			},
			'footer': {
				templateUrl: "html/ui-module1-footer.html",
				//dd:'',增加这一行错误的也不会报错
				controller: "moduleFooterController"
			}
		}
	});
}])
.controller("nextController",["$scope", function($scope){
	
}])
.controller("moduleHeaderController",["$scope", function($scope){
	
}])
.controller("moduleBodyController",["$scope", function($scope){
	
}])
.controller("moduleFooterController",["$scope", function($scope){
	
}]);
