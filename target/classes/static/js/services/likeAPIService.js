angular.module("gameChatApp").factory("likeAPI", function ($http, config){

    var _toLike = function(like){
        return $http.post(config.baseUrl + config.version + '/like', like);
    };

    var _usersLikedByPublication = function(idPublication){
        return $http.get(config.baseUrl + config.version + '/like/publication/' + idPublication);
    };
    
    return {
        toLike: _toLike,
        usersLikedByPublication: _usersLikedByPublication
    };

});