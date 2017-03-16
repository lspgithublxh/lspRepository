'use strict';
angular.module("oneApp", ['ui.router','ui-module1', 'ui-module2'])//
.run(["$rootScope","$state","$stateParams", function($rootScope, $state, $stateParams){
	$rootScope.rootName = "lishaxxx";
}]);