var calcioApp = angular.module('calcioApp', ['ui.router', 'ngStorage', 'ui.bootstrap']);

calcioApp.constant('urls', {
    BASE: 'http://localhost:8080/calcio',
    TEAM_SERVICE_API: 'http://localhost:8080/calcio/team/'
});

calcioApp.config(['$stateProvider', '$urlRouterProvider', '$locationProvider',
    function ($stateProvider, $urlRouterProvider, $locationProvider) {
        $stateProvider
            .state('team', {
                url: '/team',
                templateUrl: 'fragments/team',
                controller: 'TeamController as teamCtrl',
                resolve: {
                    teams: function ($q, TeamService) {
                        var deferred = $q.defer();
                        TeamService.loadAllTeams().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
        $locationProvider.html5Mode(true);
    }]);