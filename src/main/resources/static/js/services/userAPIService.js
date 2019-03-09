angular.module("gameChatApp").factory("userAPI", function ($http, config) {

    var _getUserLogged = function (){
        return $http.get(config.baseUrl + config.version + '/user');
    };

    var _putUserLogged = function (user){
        return $http.put(config.baseUrl + config.version + '/user', user);
    };

    var _getUserByNickName = function (nickName){
        return $http.get(config.baseUrl + config.version + '/user/nickname/' + nickName);
    }

    var _getAllUsers = function (){
        return $http.get(config.baseUrl + config.version + '/user/all');
    };

    return {
        getUserLogged: _getUserLogged,
        putUserLogged: _putUserLogged,
        getUserByNickName: _getUserByNickName,
        getAllUsers: _getAllUsers
    };

});