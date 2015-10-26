(function () {
    'use strict';

    var app = angular.module('teacher');

    app.controller('HomeController', ['$scope', '$localStorage' ,function($scope, $localStorage) {
        $scope.username = $localStorage.username;
    }]);
}());


