angular.module("gameChatApp").factory("gameAPI", function ($http, config){

    var _getGames = function(){
        return $http.get(config.baseUrl + config.version + '/game');
    };

    return {
        getGames: _getGames
    }

});