angular.module("gameChatApp").controller("onePublicationCtrl", function ($scope, $routeParams, $sce, config, publicationAPI) {
   
	$scope.publication = {};

    $scope.game = {};

	//listar publicações do Home
	$scope.onePublication = function(id) {

		publicationAPI.getPublicationById(id).then(function(response) {

            element = response.data;

            if(element.picture != null){
                element.picture = "<img src='" + element.picture + "' class='img-responsive img-thumbnail' width='80%' height='80%'>";
            }

			$scope.publication = element;

		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	$scope.onePublication($routeParams.id);

	//invocar ng-bind-html com 
 	$scope.htmlSafe = function (data) {
    	return $sce.trustAsHtml(data);
 	}
});