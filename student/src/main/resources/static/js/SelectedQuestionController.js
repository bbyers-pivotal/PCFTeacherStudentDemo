(function () {
    'use strict';

    var app = angular.module('student');
    app.controller('SelectedQuestionController', ['$scope', '$http', '$localStorage', '$timeout', '$routeParams', '$location', function($scope, $http, $localStorage, $timeout, $routeParams, $location) {
        $scope.data = {
            saved: false,
            error: false,
            question: {}
        };

        $scope.data.answeredQuestions = $localStorage.answeredQuestions;

        function hideMessages() {
            $scope.data.saved = false;
            $scope.data.error = false;
        }

        var id = $routeParams.id;

        $http.get('questions/' + id).success(function(data) {
            $scope.data.question = data;
        });

        $scope.answerQuestion = function(question) {
            hideMessages();

            $http.post('questions/' + question.id + '/answer', {id: question.id, answer: question.answer}).success(function() {
                $localStorage.answeredQuestions.push(question);
                $scope.data.saved = true;

                $timeout(function() {
                    hideMessages();
                    $location.path('questions');
                }, 2000)
            }).error(function() {
                $scope.data.error = true;
            });
        }
    }]);
}());


