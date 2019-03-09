angular.module("gameChatApp").controller("toFollowCtrl", function ($scope, $routeParams, config, followAPI) {

	$scope.follow = {};

    $scope.following = {};

	$scope.toFollow = function(id) {

		$scope.following.id = id;

		$scope.follow.following = $scope.following;

		followAPI.toFollow($scope.follow).then(function(response) {

		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	
	
	$scope.toFollowSuggestion = function(id) {

		$scope.following.id = id;

		$scope.follow.following = $scope.following;

		followAPI.toFollow($scope.follow).then(function(response) {

			$scope.users = $scope.suggestions($routeParams.nickName);
			
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	
});