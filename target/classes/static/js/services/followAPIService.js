angular.module("gameChatApp").factory("followAPI", function ($http, config){

    var _getFollowersByNickName = function(nickName){
        return $http.get(config.baseUrl + config.version + '/followers/nickname/' + nickName);
    };

    var _getFollowersByToken = function(){
        return $http.get(config.baseUrl + config.version + '/followers');
    };

    var _getFollowingsByNickName = function(nickName){
        return $http.get(config.baseUrl + config.version + '/follows/nickname/' + nickName);
    };

    var _getFollowingsByToken = function(){
        return $http.get(config.baseUrl + config.version + '/follows');
    };

    var _getSuggestionsByFollowing = function(nickName){
        return $http.get(config.baseUrl + config.version + '/suggestion/following/' + nickName);
    };

    var _getSuggestionsByFollowings = function(){
        return $http.get(config.baseUrl + config.version + '/suggestion/followings');
    };

    var _toFollow = function(follow){
        return $http.post(config.baseUrl + config.version + '/follow', follow);
    };

    var _verifyImfFollowing = function(nickName){
        return $http.get(config.baseUrl + config.version + '/follows/verify/ifolow/' + nickName);
    };

    var _verifyImFollowed = function(nickName){
        return $http.get(config.baseUrl + config.version + '/follows/verify/imfollowed/' + nickName);
    };

    return {
        getFollowersByNickName: _getFollowersByNickName,
        getFollowersByToken: _getFollowersByToken,
        getFollowingsByNickName: _getFollowingsByNickName,
        getFollowingsByToken: _getFollowingsByToken,
        getSuggestionsByFollowing: _getSuggestionsByFollowing,
        getSuggestionsByFollowings: _getSuggestionsByFollowings,
        toFollow: _toFollow,
        verifyImfFollowing: _verifyImfFollowing,
        verifyImFollowed: _verifyImFollowed
    };

});