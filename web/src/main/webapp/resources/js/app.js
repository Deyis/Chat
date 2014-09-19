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
            controller:  ['$http', function($http) {

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
                    });
                }
            }],
            controllerAs: 'loginController'
        };
    });


    app.controller('HelloController', function(){

    });
})();