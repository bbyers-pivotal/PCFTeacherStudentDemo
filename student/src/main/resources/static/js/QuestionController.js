(function () {
    'use strict';

    var app = angular.module('student');
    app.controller('QuestionController', ['$scope', '$http', '$localStorage', '$interval', '$timeout', function($scope, $http, $localStorage, $interval, $timeout) {
        $scope.data = {
            saved: false,
            error: false
        };

        $scope.data.answeredQuestions = $localStorage.answeredQuestions;
        function refreshQuestions() {
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
        $interval(refreshQuestions, 1000);

        $scope.data.selectedQuestion = {};

        $scope.selectQuestion = function(question) {
            $scope.data.selectedQuestion = question;
        };

        function hideMessages() {
            $scope.data.saved = false;
            $scope.data.error = false;
        }

        $scope.answerQuestion = function(question) {
            hideMessages();

            $http.post('questions/' + question.id + '/answer', {id: question.id, answer: question.answer}).success(function() {
                $localStorage.answeredQuestions.push(question);
                _.remove($scope.data.questions, function(q) {
                    return q.id == question.id;
                });
                $scope.data.selectedQuestion = {};
                $scope.data.saved = true;

                $timeout(function() {
                    hideMessages();
                }, 5000)
            }).error(function() {
                $scope.data.error = true;
            });
        }
    }]);
}());


