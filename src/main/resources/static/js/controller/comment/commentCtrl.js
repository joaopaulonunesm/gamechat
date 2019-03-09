angular.module("gameChatApp").controller("commentCtrl", function ($scope, $routeParams, config, commentAPI) {

    $scope.comment = {};

	$scope.publicationComment = {};

	$scope.comments = [];

	$scope.toComment = function(id) {

		$scope.publicationComment.id = id;

		$scope.comment.publication = $scope.publicationComment;

		commentAPI.createComment($scope.comment).then(function(response) {
			$scope.comment.text = null;
			$scope.usersComments(id);
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.usersComments = function(id) {
		
		commentAPI.commentByPublication(id).then(function(response) {
			$scope.comments = response.data;
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.usersCommentsRouteParams = function(id) {

		if(id != null){

			commentAPI.commentByPublication(id).then(function(response) {
				$scope.comments = response.data;
			}, function(response) {
				console.log(response.data);
				console.log(response.status);
			});

		}

	};	

	$scope.usersCommentsRouteParams($routeParams.id);
});

