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

    app.directive('content', function(){
        return {
            restrict: 'E',
            templateUrl: './static/content.html',
            controller:  function($http, $rootScope, $scope, $interval) {

                this.hidden = true;

                this.conversationId = 0;
                this.msg = "";
                var cntrl = this;

                this.getContent = function() {
                    $http.get('./conversations/check/' + cntrl.conversationId)
                    .success(function(msg) {
                        cntrl.hidden = false;
                        cntrl.msg = msg.code;
//                        if(msg.code != '1') {
//                            return;
//                        }
//                        $interval.cancel(cntrl.getContent);
                    });
                }

                this.initConversation = function() {
                    $http.post('./conversations/start', JSON.stringify({lang : "en"})).success(function(msg) {
                        cntrl.conversationId = msg.conversationId;
                        alert(msg.conversationId);
                        $interval(cntrl.getContent, 100);
                    });
                }

                $rootScope.$on('LoggedIn', function (event) {
                    cntrl.initConversation();
                });


            },
            controllerAs: 'contentController'
        };
    });


})();