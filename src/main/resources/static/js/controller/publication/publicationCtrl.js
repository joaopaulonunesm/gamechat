angular.module("gameChatApp").controller("publicationCtrl", function ($scope, $sce, config, publicationAPI) {

	$scope.publications = [];
	
	$scope.newPublications = [];

	$scope.diferentPublication = 0;
   
	$scope.publication = {};

	$scope.game = {};
	
	$scope.myPublications = [];

	//Criar publicação e inserir na timeline
	$scope.createPublication = function() {

		publicationAPI.createPublication($scope.publication).then(function(response) {

			if(response.data.picture != null){
				response.data.picture = "<img src='" + response.data.picture + "' class='img-responsive img-thumbnail' width='80%' height='80%'>";
			}
			
			$scope.publications.unshift(response.data);

			$scope.picture = null;
			$scope.publication.text = null;
			$scope.publication.picture = null;
			document.getElementById("game").options.selectedIndex = 0;
			
		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	//listar publicações do Home
	$scope.listPublications = function() {

		publicationAPI.getPublicationsTimeLine().then(function(response) {

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

	$scope.listPublications();

	//Verifica se houve nova publicação
	$scope.verifyNewListPublications = function() {
		
		publicationAPI.getPublicationsTimeLine().then(function(response) {

			angular.forEach(response.data, function(element) {

				if(element.picture != null){
					element.picture = "<img src='" + element.picture + "' class='img-responsive img-thumbnail' width='80%' height='80%'>";
				}

			});

			$scope.newPublications = response.data;

			if($scope.publications.length <= $scope.newPublications.length){

				$scope.diferentPublication = $scope.newPublications.length - $scope.publications.length;
			}

		}, function(response) {
			console.log(response.data);
			console.log(response.status);
		});
	};	

	//inseri as novas publicações
	$scope.refreshPublications = function(){
		for(i = 0; i < $scope.diferentPublication; i++){
			$scope.publications.unshift($scope.newPublications[i]);
		}
		$scope.diferentPublication = 0;
	}

	setInterval($scope.verifyNewListPublications, 30000);

	$scope.addPictureModal = function(url){
		$scope.picture = "<img src='" + url + "' class='img-responsive img-thumbnail' width='80%' height='80%'>";
	};

	$scope.removePictureModal = function(url){
		$scope.picture = null;
		document.getElementById("urlPicture").value = null;
	};
	
	//invocar ng-bind-html com 
 	$scope.htmlSafe = function (data) {
    	return $sce.trustAsHtml(data);
 	}
});