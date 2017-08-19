'use strict';
angular.module('default',[])
.controller('defaultController', function($scope){
	$scope.name = "tom";
});
angular.module('main',[])
.controller('oneController',['$scope',function($scope){
	$scope.input1 = "Tom";
	$scope.input2 = 34;
}]);
angular.module("two",[])
.constant("constant", {
	getUserEntity : "../user/all"
})
.service("serv1", ["$http", "constant", function($http, constant){
	return {
		getUserEntity: function(){
			return $http.post(constant.getUserEntity,{username:'sss',password:'xxx'});
		}
	};
}])
.service("serv2",["$http", "constant", function($http, constant){
	
}])
.controller("con1",["$scope",function($scope){
	$scope.name = "lishaoping";
}])
.controller("con2",["$scope" ,"$location" , "serv1",function($scope, $location, serv1){
	$scope.age = 25;
	$scope.arr = [];
	for(var i = 0; i < 10; i++){
		$scope.arr.push({"a":Math.random() * 100,"b":2,"c":4,"index":i});
	}
	console.log($location.absUrl());
//	$scope.arr = new Array(10).map(function(ele,index){
//		return {"a":Math.random() * 100,"b":2,"c":4,"index":index};
//	});
	serv1.getUserEntity().success(function(response){
		$scope.arr2 = response;
	}).error(function(response){
		alert(error + "  " + response.code);
	});
	$scope.options = [];
	for(var i = 0 ;i < 5; i++){
		$scope.options.push({"url" : "http://localhost:8080/web/user/all" + i, "siteName":"localhost" + Math.random()});
	}
	$scope.getValue = function(url){
		alert(url);
	}
//	console.log($scope.arr);
}]);
angular.module("rootModule",['module1','module2', 'ngRoute'])
.config(["$routeProvider", function($routeProvider){
	$routeProvider.when("/",{template:"这是首页内容"})
	.when("/cpu",{template:"这是CPU页面内容"})
	.when("/mem",{template:'这是MEM页面内容'})
	.when("/dev",{template:"这是DEV页面内容"})
	.otherwise({redirectTo:"/"});
}])
.run(["$rootScope", function($rootScope){// "$state" , "$stateParam" 可见是angularjs-ui中的服务，补充定义的对象
	$rootScope.name = "lishaoping222";
	$rootScope.$on("$routeChangeStart", function(evt, next, current){
	//	alert("run function on $on ");//next.templateUrl
		console.log(next);
	});
//	alert("haha");
}]);
angular.element(document).ready(function(){
	angular.bootstrap(document.getElementById("main"),["main"]);
	angular.bootstrap(document.getElementById("two"),["two"]);
	angular.bootstrap(document.getElementById("rootModule"), ["rootModule"]);
});