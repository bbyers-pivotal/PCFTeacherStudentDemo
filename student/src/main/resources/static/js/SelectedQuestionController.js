(function () {
    'use strict';

    var app = angular.module('student');
    app.controller('SelectedQuestionController', ['$scope', '$http', '$localStorage', '$timeout', '$routeParams', '$location', function($scope, $http, $localStorage, $timeout, $routeParams, $location) {
        $scope.data = {
            saved: false,
            error: false,
            question: {},
            formSubmitted: false
        };

        var username = $localStorage.username;
        var id = $routeParams.id;

        function hideMessages() {
            $scope.data.saved = false;
            $scope.data.error = false;
        }

        $http.get('questions/' + username + '/' + id).success(function(data) {
            $scope.data.question = data;
        });

        $scope.answerQuestion = function(question) {
            if ($scope.data.question.answer.length === 0 || $scope.data.formSubmitted === true) {
                return;
            }
            $scope.data.formSubmitted = true; //prevent double submit

            hideMessages();

            $http.post('questions/' + username + '/' + question.id + '/answer', {id: question.id, answer: question.answer}).success(function() {
                $scope.data.saved = true;

                $timeout(function() {
                    hideMessages();
                    $location.path('questions');
                }, 1000)
            }).error(function() {
                $scope.data.formSubmitted = false;
                $scope.data.error = true;
            });
        }
    }]);
}());


