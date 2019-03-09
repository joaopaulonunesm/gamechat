angular.module("gameChatApp").factory("viewAPI", function ($http, config){

    var _toView = function(view){
        return $http.post(config.baseUrl + config.version + '/viewPublication', view);
    }

    var _usersViewPublication = function(id) {
        return $http.get(config.baseUrl + config.version + '/viewPublication/publication/' + id);
    }

    return {
        toView: _toView,
        usersViewPublication: _usersViewPublication
    };

});