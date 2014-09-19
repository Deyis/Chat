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

                $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";

                this.signed = false;
                this.loginData = {};

                this.hideLoginForm = function() {
                    this.signed = true;                    
                }

                this.showLoginForm = function() {
                    this.signed = false;
                }

                this.sendData = function() {
                    var cntrl = this;

                    $http.post('./login', Object.toparams(this.loginData))
                    .success(function(data){
                        cntrl.hideLoginForm();
						$rootScope.$emit('LoggedIn', 'denis');
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

                $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";

                this.hidden = true;
                this.greeting = "";
				var cntrl = this;				
				
				this.getGreeting= function() {
				    $http.get('./hello.json').success(function(msg) {
				        cntrl.greeting = msg.greeting;
                        cntrl.hidden = false;
				    });
				}
                
				$rootScope.$on('LoggedIn', function (event, name) {
                   cntrl.getGreeting();
                });


            },
            controllerAs: 'greetingController'
        };
    });

})();