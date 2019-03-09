angular.module("gameChatApp").factory("loginAPI", function ($http, config){

    var _toAuthenticate = function (user){
        return $http.post(config.baseUrl + '/authenticate', user);
    };

    var _createLogin = function (login){
        return $http.post(config.baseUrl + '/login', login);
    }

    return {
        toAuthenticate: _toAuthenticate,
        createLogin: _createLogin
    };

});