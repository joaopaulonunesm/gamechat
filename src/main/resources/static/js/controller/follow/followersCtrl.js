angular.module("gameChatApp").controller("followersCtrl", function ($scope, config, followAPI) {

    $scope.followers = [];

	$scope.listMyFollowers = function() {
		
		followAPI.getFollowersByToken().then(function(response) {
			$scope.followers = response.data;
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.listMyFollowers();

});