(function () {
    'use strict';

    var app = angular.module('teacher');
    app.controller('QuestionController', ['$scope', '$http', '$localStorage', '$interval', '$timeout', '$location', function($scope, $http, $localStorage, $interval, $timeout, $location) {
        $scope.data = {
            questions: []
        };

        function refreshQuestions() {
            $http.get('questions').success(function(data) {
                $scope.data.questions = data;
            });
        }
        refreshQuestions();

        $scope.deleteAllQuestions = function() {
            $http.delete('questions').success(function() {
                refreshQuestions();
            });
        }

        $scope.selectQuestion = function(question) {
            $location.path('/questions/' + question.id);
        };
    }]);
}());


