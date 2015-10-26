(function () {
    'use strict';

    var app = angular.module('student');

    app.controller('HomeController', ['$scope', '$localStorage' ,function($scope, $localStorage) {
        $scope.username = $localStorage.username;
    }]);
}());


