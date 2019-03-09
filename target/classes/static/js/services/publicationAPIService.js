angular.module("gameChatApp").factory("publicationAPI", function ($http, config){

    var _createPublication = function(publication){
        return $http.post(config.baseUrl + config.version + '/publication', publication);
    };

    var _getPublicationsTimeLine = function (){
        return $http.get(config.baseUrl + config.version + '/publication/friends');
    };
    
    var _getPublicationById = function (id){
        return $http.get(config.baseUrl + config.version + '/publication/' + id);
    };

    var _getPublicationsByNickName = function (nickName){
        return $http.get(config.baseUrl + config.version + '/publication/user/nickname/' + nickName);
    };

    return {
        createPublication: _createPublication,
        getPublicationsTimeLine: _getPublicationsTimeLine,
        getPublicationById: _getPublicationById,
        getPublicationsByNickName: _getPublicationsByNickName
    };

});