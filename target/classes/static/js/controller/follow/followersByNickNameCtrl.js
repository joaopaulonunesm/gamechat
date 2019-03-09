angular.module("gameChatApp").controller("followersByNickNameCtrl", function ($scope, $routeParams, config, followAPI) {

    $scope.followers = [];

	$scope.listMyfollowers = function(nickName) {

		followAPI.getFollowersByNickName(nickName).then(function(response) {
			$scope.followers = response.data;
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.listMyfollowers($routeParams.nickName);

});