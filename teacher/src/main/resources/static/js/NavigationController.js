(function () {
    'use strict';

    var app = angular.module('teacher');

    app.controller('NavigationController', ['$rootScope', '$scope', '$http', '$location', '$route', function($rootScope, $scope, $http, $location, $route) {
        $scope.tab = function(route) {
            return $route.current && route === $route.current.controller;
        };
    }]);
}());


