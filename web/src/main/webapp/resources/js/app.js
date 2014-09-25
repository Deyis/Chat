(function(){

    Object.toparams = function(obj) {
        var p = [];
        for (var key in obj) {
            p.push(key + '=' + obj[key]);
        }
        return p.join('&');
    };

    var app = angular.module('chat', [ ]);

    app.directive('loginForm', function(){
        return {
            restrict: 'E',
            templateUrl: './static/login-form.html',
            controller: function($http, $rootScope, $scope) {

                this.signed = true;

                this.loginData = {};

                var cntrl = this;

                this.hideLoginForm = function() {
                    this.signed = true;
                }

                this.showLoginForm = function() {
                    this.signed = false;
                }

                $http.get('./hello.json').success(function(msg) {
                   cntrl.hideLoginForm();
                   $rootScope.$emit('LoggedIn');
                }).error(function(data, status, headers, config) {
                   cntrl.showLoginForm();
                });

                this.sendData = function() {
                    $http({
                        method: 'POST',
                        url: './login',
                        data: Object.toparams(this.loginData),
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                    }).success(function(data){
                        cntrl.hideLoginForm();
						$rootScope.$emit('LoggedIn');
                    });
                }
            },
            controllerAs: 'loginController'
        };
    });

    app.directive('greeting', function(){
        return {
            restrict: 'E',
            templateUrl: './static/greeting.html',
            controller:  function($http, $rootScope, $scope) {		

                this.hidden = true;
                this.greeting = "";
				var cntrl = this;				
				
				this.getGreeting= function() {
				    $http.get('./hello.json').success(function(msg) {
				        cntrl.greeting = msg.greeting;
                        cntrl.hidden = false;
				    });
				}
                
				$rootScope.$on('LoggedIn', function (event) {
                   cntrl.getGreeting();
                });


            },
            controllerAs: 'greetingController'
        };
    });

    app.directive('conversation', function(){
        return {
            restrict: 'E',
            templateUrl: './static/conversation.html',
            controller:  function($http, $rootScope, $scope, $interval) {

                this.hidden = true;

                this.getCheckForConversationInterval;

                this.conversationId = 0;
                this.lastMessageNumber = 0;
                this.messages = [];
                this.message = {};

                var cntrl = this;

                this.proceedWithNewMessages = function(response) {
                    console.log("response conversation ID:" + cntrl.conversationId);
                    console.log(response);

                    if(cntrl.lastMessageNumber == response.lastNumber) {
                        return;
                    }
                    cntrl.lastMessageNumber = response.lastNumber;
                    cntrl.messages.push.apply(cntrl.messages, response.newMessages);
                }

                this.getMessages = function() {
                    $http.get('./conversations/'+ cntrl.conversationId + '/messages/' + cntrl.lastMessageNumber)
                        .success(cntrl.proceedWithNewMessages);
                }

                this.sendMessage = function() {
                    cntrl.newMessage.conversationId = cntrl.conversationId;
                    cntrl.newMessage.lastNumber = cntrl.lastMessageNumber;
                    console.log("send message conversation ID:" + cntrl.newMessage.conversationId + " message: " + cntrl.newMessage.message);

                    $http.post('./conversations/message', JSON.stringify(cntrl.newMessage))
                        .success(cntrl.proceedWithNewMessages);

                    cntrl.newMessage = {};
                }

                this.getCheckForConversation = function() {
                    $http.get('./conversations/check/' + cntrl.conversationId)
                        .success(function(msg) {
                            if(msg.code != '1') {
                                console.log("waiting for conversation ID:" + cntrl.conversationId);
                                return;
                            }
                            console.log("begin conversation ID:" + cntrl.conversationId);
                            $interval.cancel(cntrl.getCheckForConversationInterval);
                            cntrl.hidden = false;
                            $interval(cntrl.getMessages, 2000);
                        });
                }

                this.initConversation = function() {
                    $http.post('./conversations/start', JSON.stringify({lang : "en"}))
                        .success(function(msg) {
                            cntrl.conversationId = msg.conversationId;
                            console.log("start conversation ID:" + cntrl.conversationId);
                            cntrl.getCheckForConversationInterval = $interval(cntrl.getCheckForConversation, 100);
                        });
                }

                $rootScope.$on('LoggedIn', function (event) {
                    cntrl.initConversation();
                });
            },
            controllerAs: 'conversationController'
        };
    });

})();