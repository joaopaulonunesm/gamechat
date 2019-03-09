angular.module("gameChatApp").controller("toLikeCtrl", function ($scope, $modal, $routeParams, config, likeAPI) {

  	$scope.like = {};

	$scope.publicationLike = {};

	$scope.likes = [];

	$scope.toLike = function(id) {

		$scope.publicationLike.id = id;

		$scope.like.publication = $scope.publicationLike;

		likeAPI.toLike($scope.like).then(function(response) {

			$scope.usersLiked(id);

		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.usersLiked = function(id) {
		
		likeAPI.usersLikedByPublication(id).then(function(response) {

			$scope.likes = response.data;

		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.usersLikedRouteParams = function(id) {

		if(id != null){

			likeAPI.usersLikedByPublication(id).then(function(response) {

				$scope.likes = response.data;
				
			}, function(response) {
				console.log(response.data);
				console.log(response.status);
			});

		}

	};	

	$scope.usersLikedRouteParams($routeParams.id);

 	$scope.open = function (idPublication) {

		$scope.likes = $scope.usersLiked(idPublication);

		modalInstance = $modal.open({
		template: '<modal-like></modal-like>',
				scope: $scope
		});
	};
});

