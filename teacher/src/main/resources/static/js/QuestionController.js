(function () {
    'use strict';

    var app = angular.module('teacher');
    app.controller('QuestionController', ['$scope', '$http', '$localStorage', '$interval', '$timeout', '$location', function($scope, $http, $localStorage, $interval, $timeout, $location) {
        $scope.data = {
            questions: []
        };

        $http.get('questions').success(function(data) {
            $scope.data.questions = data;
        });

        $scope.selectQuestion = function(question) {
            $location.path('/questions/' + question.id);
        };
    }]);
}());


