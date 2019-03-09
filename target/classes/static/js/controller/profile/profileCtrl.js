angular.module("gameChatApp").controller("profileCtrl", function ($scope, $location, $routeParams, $sce, config, publicationAPI, userAPI, followAPI) {

    $scope.profile = {};

	$scope.publications = [];

	$scope.iFollow = {};
	$scope.imFollowed = {};

	$scope.userProfile = function(nickName) {

		userAPI.getUserByNickName(nickName).then(function(response) {
			$scope.profile = response.data;
		}, function(response) {
			$location.path("/" + nickName + "/notfound");
		});
	};	

	$scope.userProfile($routeParams.nickName);

	$scope.listPublications = function(nickName) {

		publicationAPI.getPublicationsByNickName(nickName).then(function(response) {

			angular.forEach(response.data, function(element) {

				if(element.picture != null){
					element.picture = "<img src='" + element.picture + "' class='img-responsive img-thumbnail' width='80%' height='80%'>";
				}

			});

			$scope.publications = response.data;
			
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.listPublications($routeParams.nickName);

	$scope.verifyIFollow = function(nickName) {

		followAPI.verifyImfFollowing(nickName).then(function(response) {

			$scope.iFollow = response.data;

		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.verifyImFollowed = function(nickName) {

		followAPI.verifyImFollowed(nickName).then(function(response) {
			$scope.imFollowed = response.data;
			$scope.msgImFollowed = "Segue você!"
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	//invocar ng-bind-html com 
 	$scope.htmlSafe = function (data) {
    	return $sce.trustAsHtml(data);
 	}
});