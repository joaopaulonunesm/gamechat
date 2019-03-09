angular.module("gameChatApp").controller("gameCtrl", function ($scope, config, gameAPI) {

    $scope.games = [];

	$scope.games = function() {
		
		gameAPI.getGames().then(function(response) {
			$scope.games = response.data;
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.games();

});