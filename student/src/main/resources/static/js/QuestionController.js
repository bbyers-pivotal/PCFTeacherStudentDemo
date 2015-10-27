(function () {
    'use strict';

    var app = angular.module('student');
    app.controller('QuestionController', ['$scope', '$http', '$localStorage', '$interval', '$timeout', '$location', function($scope, $http, $localStorage, $interval, $timeout, $location) {
        $scope.data = {};

        $scope.data.answeredQuestions = $localStorage.answeredQuestions;
        function refreshQuestions() {
            //console.log('Refreshing questions');
            $http.get('questions/').success(function(data) {
                var answeredQuestions = $localStorage.answeredQuestions;

                var questionIds = _.pluck(answeredQuestions, 'id');
                var unansweredQuestions = [];
                angular.forEach(data, function(value, key) {
                    if (!_.includes(questionIds, value.id)) {
                        unansweredQuestions.push(value);
                    }
                });
                $scope.data.questions = unansweredQuestions;
            });
        };
        refreshQuestions();
        $interval(refreshQuestions, 5000);

        $scope.selectQuestion = function(question) {
            $location.path('questions/' + question.id);
        };

        $scope.resetQuestions = function() {
            $localStorage.answeredQuestions = [];
            $scope.data.answeredQuestions = [];
            refreshQuestions();
        }
    }]);
}());


