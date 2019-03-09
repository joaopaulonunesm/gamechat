angular.module("gameChatApp").controller("followsCtrl", function ($scope, config, followAPI) {

    $scope.follows = [];

	$scope.listMyFollows = function() {
		
		followAPI.getFollowingsByToken().then(function(response) {
			$scope.follows = response.data;
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.listMyFollows();

});