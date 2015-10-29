(function () {
    'use strict';

    var app = angular.module('student');
    app.controller('QuestionController', ['$scope', '$http', '$localStorage', '$interval', '$timeout', '$location', function($scope, $http, $localStorage, $interval, $timeout, $location) {
        $scope.data = {
            loaded: false
        };

        var username = $localStorage.username;
        $scope.data.answeredQuestions = [];

        function refreshQuestions() {
            //console.log('Refreshing questions');
            $http.get('questions/' + username).success(function(data) {
                $scope.data.answeredQuestions = _.filter(data, function(q) { return q.answer !== ''; });
                $scope.data.questions = _.filter(data, function(q) { return q.answer === ''; });
                $scope.data.loaded = true;
            });
        };

        refreshQuestions();
        var intervalPromise = $interval(refreshQuestions, 2000);
        $scope.$on('$destroy', function () { $interval.cancel(intervalPromise); });

        $scope.selectQuestion = function(question) {
            $location.path('questions/' + question.id);
        };

        $scope.resetQuestions = function() {
            $http.delete('answers/' + $localStorage.username).success(function() {
                $scope.data.answeredQuestions = [];
                refreshQuestions();
            });
        }
    }]);
}());


