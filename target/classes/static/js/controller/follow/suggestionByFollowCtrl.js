angular.module("gameChatApp").controller("suggestionByFollowCtrl", function ($scope, $routeParams, config, followAPI) {
	
    $scope.users = {};

	$scope.suggestions = function(nickName) {

		followAPI.getSuggestionsByFollowing(nickName).then(function(response) {
			 $scope.users = response.data;
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.suggestions($routeParams.nickName);

});