(function () {
    'use strict';

    var app = angular.module('student');

    app.controller('LoginController', ['$scope', '$localStorage', '$location', function($scope, $localStorage, $location) {
        $scope.login = function(username) {
            if (username) {
                $localStorage.username = username;
                $location.path('/');
            }
        }
    }]);
}());


