angular.module("gameChatApp").controller("userCtrl", function ($scope, $route, $location, config, userAPI) {

	$scope.user = {};
	
	$scope.users = [];

	$scope.selectedUser = {};

	$scope.me = function() {

		userAPI.getUserLogged().then(function(response) {
			$scope.user = response.data;
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.me();

	$scope.logout = function(){
		localStorage.removeItem("userToken");
		$location.path("/login");
	};

	$scope.allUsers = function(){

		userAPI.getAllUsers().then(function(response) {
			$scope.users = response.data;
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};

	$scope.refreshRoute = function(){
		$route.reload();
	};
});