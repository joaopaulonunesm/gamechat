angular.module("gameChatApp").controller("followsByNickNameCtrl", function ($scope, $routeParams, config, followAPI) {

    $scope.follows = [];

	$scope.listMyFollows = function(nickName) {
		
		followAPI.getFollowingsByNickName(nickName).then(function(response) {
			$scope.follows = response.data;
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.listMyFollows($routeParams.nickName);

});