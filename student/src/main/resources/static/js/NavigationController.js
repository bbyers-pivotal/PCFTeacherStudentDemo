(function () {
    'use strict';

    var app = angular.module('student');

    app.controller('NavigationController', ['$rootScope', '$scope', '$http', '$location', '$route', '$interval', function($rootScope, $scope, $http, $location, $route, $interval) {
        var intervalPromise = $interval(function() {
            $http.get('makeItAngry');
        }, 1000);
        $scope.$on('$destroy', function () { $interval.cancel(intervalPromise); });

        $scope.tab = function(route) {
            return $route.current && route === $route.current.controller;
        };
    }]);
}());


