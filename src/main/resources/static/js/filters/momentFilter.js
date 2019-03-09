angular.module("gameChatApp").filter("moment", function (){
    return function (input){

        return moment(input).format('LLL');
    }
});