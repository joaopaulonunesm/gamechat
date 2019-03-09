angular.module("gameChatApp").filter("playing", function (){
    return function (input){

        if(input == null){ return null; }

        var output = "Jogando " + input;

        return output;
    }
});