(function () {
    'use strict';

    var app = angular.module('teacher');
    app.controller('AskQuestionController', ['$scope', '$http', '$timeout', '$location', function($scope, $http, $timeout, $location) {
        $scope.data = {
            saved: false,
            error: false
        };

        function hideMessages() {
            $scope.data.saved = false;
            $scope.data.error = false;
        }

        $scope.saveQuestion = function(question) {
            hideMessages();

            $http.post('questions/', {question: question.question}).success(function() {
                $scope.data.saved = true;

                $timeout(function() {
                    hideMessages();
                    $location.path('questions');
                }, 2000)
            }).error(function() {
                $scope.data.error = true;
            });
        };
    }]);
}());


