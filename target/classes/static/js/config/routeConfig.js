angular.module("gameChatApp").config(function ($routeProvider) {

	$routeProvider.when("/", {
		templateUrl: "view/home/home.html",
		controller: "publicationCtrl",
	});
	
	$routeProvider.when("/login", {
		templateUrl: "view/login/login.html",
		controller: "loginCtrl",
	});

	$routeProvider.when("/signup", {
		templateUrl: "view/login/signup.html",
		controller: "loginCtrl",
	});
		
	$routeProvider.when("/connectionrefused", {
		templateUrl: "view/error/errConnectionRefused.html"
	});


	//Profile
	$routeProvider.when("/profile/settings", {
		templateUrl: "view/changeProfile/changeProfile.html",
		controller: "changeProfileCtrl"
	});

	$routeProvider.when("/:nickName", {
		templateUrl: "view/profile/profile.html",
		controller: "profileCtrl"
	});

	$routeProvider.when("/:nickName/notfound", {
		templateUrl: "view/error/notFound.html"
	});

	//Seguindo e Seguidores
	$routeProvider.when("/:nickName/following", {
		templateUrl: "view/follows/following.html",
		controller: "followsByNickNameCtrl"
	});

	$routeProvider.when("/:nickName/followers", {
		templateUrl: "view/follows/followers.html",
		controller: "followersByNickNameCtrl"
	});

	//Publication
	$routeProvider.when("/publication/:id", {
		templateUrl: "view/publication/publicationDetails.html",
		controller: "onePublicationCtrl"
	});

	$routeProvider.otherwise({redirectTo: "/"});
});