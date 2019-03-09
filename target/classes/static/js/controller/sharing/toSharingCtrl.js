angular.module("gameChatApp").controller("toSharingCtrl", function ($scope, $modal, $routeParams, config, shareAPI) {

  	$scope.sharing = {};

	$scope.publicationSharing = {};

	$scope.sharings = [];

	$scope.toSharing = function(id) {

		$scope.publicationSharing.id = id;

		$scope.sharing.publication = $scope.publicationSharing;

		shareAPI.toShare($scope.sharing).then(function(response) {
			$scope.usersSharing(id);
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.usersSharing = function(id) {
		
		shareAPI.usersSharingPublication(id).then(function(response) {
			$scope.sharings = response.data;
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.usersSharingRouteParams = function(id) {

		if(id != null){

			shareAPI.usersSharingPublication(id).then(function(response) {
				$scope.sharings = response.data;
			}, function(response) {
				console.log(response.data);
				console.log(response.status);
			});

		}

	};	

	$scope.usersSharingRouteParams($routeParams.id);

 	$scope.open = function (idPublication) {

	$scope.sharings = $scope.usersSharing(idPublication);

		modalInstance = $modal.open({
		template: '<modal-sharing></modal-sharing>',
			scope: $scope
		});
	};
});

