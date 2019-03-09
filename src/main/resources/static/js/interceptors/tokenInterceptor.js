angular.module("gameChatApp").factory("tokenInterceptor", function($q, $location){

	return {

		'request': function(config){

			config.headers.Authorization = 'Bearer ' + localStorage.getItem("userToken");

			return config;
		},


		'responseError': function(rejection){

			if(rejection.status == 401){
				localStorage.removeItem("userToken");
				$location.path("/login");
			}

			if(rejection.status < 0){
				$location.path("/connectionrefused");
			}

			return $q.reject(rejection);
		}

	};
});