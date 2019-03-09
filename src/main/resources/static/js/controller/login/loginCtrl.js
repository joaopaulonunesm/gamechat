angular.module("gameChatApp").controller("loginCtrl", function ($scope, $location, config, loginAPI) {

    $scope.user = {};

	$scope.login = {};

	$scope.authentication = function() {

		loginAPI.toAuthenticate($scope.user).then(function(response) {

			localStorage.setItem("userToken", response.data.token);

			$location.path("/");

		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.createLogin = function() {

		loginAPI.createLogin($scope.login).then(function(response) {

			$location.path("/login");

		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};

	$scope.openLogin = function(){

		if(localStorage.getItem("userToken")){
			$location.path("/");
		}
	}

	$scope.openLogin();

	$scope.validateNickname = function (){
		var nickname = document.getElementById("nickname");
		if(nickname != null){
			nickname.onkeypress = function(e) {
				var chr = String.fromCharCode(e.which);
				if ("0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM".indexOf(chr) < 0)
				return false;
			}
		}
	};
	
	$scope.validateNickname();

});