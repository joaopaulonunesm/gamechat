angular.module("gameChatApp").controller("changeProfileCtrl", function ($scope, $location, $route, config, userAPI) {

    $scope.profile = {};

	$scope.getProfile = function() {

		userAPI.getUserLogged().then(function(response) {
			$scope.profile = response.data;
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.getProfile();

	$scope.changeProfile = function() {

		userAPI.putUserLogged($scope.profile).then(function(response) {
			$route.reload();
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	// Rever essa function pois não funciona no firefox
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