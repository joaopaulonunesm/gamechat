angular.module("gameChatApp").factory("shareAPI", function ($http, config){

    var _toShare = function(share){
        return $http.post(config.baseUrl + config.version + '/sharing', share);
    };

    var _usersSharingPublication= function(idPublication){
        return $http.get(config.baseUrl + config.version + '/sharing/publication/' + idPublication);
    }

    return {
      toShare: _toShare,
      usersSharingPublication: _usersSharingPublication
    };

});