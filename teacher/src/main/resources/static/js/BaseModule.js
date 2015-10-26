(function () {
    'use strict';
    angular.module('teacher', [ 'ngRoute', 'ngStorage' ]).config(function($routeProvider, $httpProvider) {

        $routeProvider
        .when('/', {
            templateUrl : 'templates/home.html',
            controller : 'HomeController'
        })
        .when('/questions', {
            templateUrl : 'templates/questions.html',
            controller : 'QuestionController'
        })
        .when('/login', {
            templateUrl : 'templates/login.html',
            controller : 'LoginController'
        })
        .when('/questions/ask', {
            templateUrl : 'templates/askQuestion.html',
            controller : 'AskQuestionController'
        })
        .when('/questions/:id', {
            templateUrl : 'templates/selectedQuestion.html',
            controller : 'SelectedQuestionController'
        })
        .otherwise('/');

        $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
    })
    .run(['$rootScope', '$location', '$localStorage', function ($rootScope, $location, $localStorage) {
        $rootScope.$on('$routeChangeStart', function (event) {
            var storage = $localStorage.$default({
                username: '',
                answeredQuestions: []
            });

            var username = storage.username;
            if (!username) {
                //console.log('DENY');
                event.preventDefault();
                $location.path('/login');
            }
        });
    }]);
})();
