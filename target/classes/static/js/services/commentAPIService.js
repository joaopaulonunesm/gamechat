angular.module("gameChatApp").factory("commentAPI", function ($http, config){

    var _createComment = function(comment){
        return $http.post(config.baseUrl + config.version + '/comment', comment);
    };

    var _commentByPublication = function(idPublication){
        return $http.get(config.baseUrl + config.version + '/comment/publication/' + idPublication);
    };

    return {
        createComment: _createComment,
        commentByPublication: _commentByPublication
    }

});