angular.module("gameChatApp").config(function ($httpProvider) {

	$httpProvider.interceptors.push("tokenInterceptor");

});