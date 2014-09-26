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

                $http.get('./details.json').success(function(msg) {
                   cntrl.hideLoginForm();
                   $rootScope.$emit('LoggedIn', msg.username);
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
						$rootScope.$emit('LoggedIn', cntrl.loginData.login);
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


                this.login = "";
                this.getCheckForConversationInterval;

                this.createFormHidden = true;


                this.newConversation = {};

                this.conversations = [];
                this.conversationsToCheck = [];

                var cntrl = this;

                this.showCreationForm = function() {
                    this.createFormHidden = false;
                }

                this.hideCreationForm = function() {
                    this.createFormHidden = true;
                }

                this.isHidden = function(conversationId) {
                    return false;
                }

                this.getCheckForConversation = function() {

                    cntrl.conversationsToCheck.forEach(function(conversation) {

                        $http.get('./conversations/check/' + conversation.id)
                            .success(function(msg) {
                                if(msg.code != '1') {
                                    console.log("waiting for conversation ID:" + msg.conversationId);
                                    return;
                                }
                                console.log("begin conversation ID:" + msg.conversationId);

                                cntrl.conversationsToCheck = cntrl.conversationsToCheck.filter(
                                    function(c) { return c.id !==  msg.conversationId}
                                );

                                cntrl.conversations.push({id: msg.conversationId, me: cntrl.login });
                            });

                    });
                }

                this.initConversation = function() {
                    cntrl.hideCreationForm();
                    $http.post('./conversations/start', JSON.stringify(cntrl.newConversation))
                        .success(function(msg) {
                            console.log("start conversation ID:" + msg.conversationId);
                            cntrl.conversationsToCheck.push({id: msg.conversationId});
                            cntrl.getCheckForConversationInterval = $interval(cntrl.getCheckForConversation, 2000);
                        });

                    cntrl.newConversation = {};
                }

                this.closeConversation = function(conversationId) {
                    console.log("Conversation closed ID: " + conversationId);
                    cntrl.conversations = cntrl.conversations.filter(
                        function(c) { return c.id !==  conversationId}
                    );
                }

                $rootScope.$on('LoggedIn', function (event, userName) {
                    cntrl.login = userName;
                });

                $rootScope.$on('ConversationClosed', function (event, conversationId) {
                    cntrl.closeConversation(conversationId);
                });
            },
            controllerAs: 'conversationController'
        };
    });



    app.directive('conversationContent', function(){
            return {
                restrict: 'E',
                templateUrl: './static/conversation-content.html',
                controller:  function($http, $rootScope, $scope, $interval) {

                    this.login = "";

                    this.conversationId = 0;
                    this.lastMessageNumber = 0;
                    this.messages = [];
                    this.message = {};

                    this.getMessagesInterval;
                    var cntrl = this;

                    this.init = function(conversation) {
                        cntrl.conversationId = conversation.id;
                        cntrl.login = conversation.me;
                        cntrl.getMessagesInterval = $interval(cntrl.getMessages, 2000);
                    }

                    this.close = function(conversationId) {
                        $interval.cancel(cntrl.getMessagesInterval);
                        $rootScope.$emit('ConversationClosed', cntrl.conversationId);
                    }

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

                    this.isMine = function(msg) {
                        return msg.user.username === cntrl.login;
                    }

                },
                controllerAs: 'conversationContentController'
            };
        });

    app.directive('conversationHeader', function(){
            return {
                restrict: 'E',
                templateUrl: './static/conversation-header.html'
            };
        });

    app.directive('conversationCreateForm', function(){
            return {
                restrict: 'E',
                templateUrl: './static/conversation-create-form.html'
            };
        });



})();