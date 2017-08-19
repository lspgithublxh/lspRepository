'use strict';
angular.module("oneApp", ['ui.router','ui-module1', 'ui-module2'])//
.run(["$rootScope","$state","$stateParams", function($rootScope, $state, $stateParams){
	$rootScope.rootName = "lishaxxx";
	$rootScope.$on("$stateChangeStart", function(event, toState, toParams, fromState, fromParams){
		console.log("oneApp listen state change!");//$stateChangeSuccess事件也类似功能
	});
}]);