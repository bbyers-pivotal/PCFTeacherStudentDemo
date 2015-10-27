(function () {
    'use strict';

    var app = angular.module('teacher');
    app.controller('SelectedQuestionController', ['$scope', '$http', '$interval', '$routeParams', function($scope, $http, $interval, $routeParams) {

        $scope.data = {
            question: {}
        };

        var id = $routeParams.id;

        function refreshAnswers(id) {
            //console.log('Refreshing answers');
            $http.get('questions/' + id).success(function(data) {
                console.log(data.answers);
                $scope.data.question = data;
            });
        }

        refreshAnswers(id);
        var intervalPromise = $interval(refreshAnswers, 1000, 0, true, id);
        $scope.$on('$destroy', function () { $interval.cancel(intervalPromise); });
    }]);
}());


