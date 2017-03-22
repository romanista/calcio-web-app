var calcioApp = angular.module('calcioApp', ['ui.router', 'ngStorage', 'ui.bootstrap']);

calcioApp.constant('urls', {
    BASE: 'http://localhost:8080/calcio',
    TEAM_SERVICE_API: 'http://localhost:8080/calcio/team/'
});

calcioApp.config(['$stateProvider', '$urlRouterProvider', '$locationProvider',
    function ($stateProvider, $urlRouterProvider, $locationProvider) {
        $stateProvider
            .state('team', {
                abstract: true,
                url: '/team',
                template: '<ui-view/>'
            })
            .state('team.list', {
                url: '/',
                templateUrl: 'fragments/team-list',
                controller: 'TeamController as teamCtrl',
                resolve: {
                    teams: function (TeamService) {
                        return TeamService.loadAllTeams();
                    }
                }
            })
            .state('team.details', {
                url: '/:id',
                templateUrl: 'fragments/team-details',
                controller: 'TeamController as teamCtrl',
                resolve: {
                    team: function (TeamService, $stateParams) {
                        return TeamService.loadTeam($stateParams.id);
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
        $locationProvider.html5Mode(true);
    }]);