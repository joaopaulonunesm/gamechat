angular.module("gameChatApp").controller("suggestionByFollowsCtrl", function ($scope, config, followAPI) {
	
    $scope.users = {};

	$scope.suggestions = function() {

		followAPI.getSuggestionsByFollowings().then(function(response) {
			 $scope.users = response.data;
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.suggestions();

});