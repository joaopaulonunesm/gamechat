angular.module("gameChatApp").controller("viewPublicationCtrl", function ($scope, $modal, $routeParams, config, viewAPI) {

  $scope.viewPublication = {};

	$scope.publicationView = {};

	$scope.viewPublications = [];

	$scope.toView = function(id) {

		$scope.publicationView.id = id;

		$scope.viewPublication.publication = $scope.publicationView;

		viewAPI.toView($scope.viewPublication).then(function(response) {
			$scope.usersView(id);
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.usersView = function(id) {
		
		viewAPI.usersViewPublication(id).then(function(response) {
			$scope.viewPublications = response.data;
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.usersViewRouteParams = function(id) {

		if(id != null){

			viewAPI.usersViewPublication(id).then(function(response) {
				$scope.viewPublications = response.data;
			}, function(response) {
				console.log(response.data);
				console.log(response.status);
			});

		}

	};	

	$scope.usersViewRouteParams($routeParams.id);

 	$scope.open = function (idPublication) {

	$scope.viewPublications = $scope.usersView(idPublication);

		modalInstance = $modal.open({
		template: '<modal-view-publication></modal-view-publication>',
				scope: $scope
		});
	};
});

