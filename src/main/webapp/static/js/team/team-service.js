'use strict';
calcioApp.factory('TeamService', ['$localStorage', '$http', '$q', '$log', 'urls',
    function ($localStorage, $http, $q, $log, urls) {
        var factory = {
            loadAllTeams: loadAllTeams,
            getAllTeams: getAllTeams,
            getTeam: getTeam,
            createTeam: createTeam,
            updateTeam: updateTeam,
            removeTeam: removeTeam
        };
        return factory;

        function loadAllTeams() {
            $log.log('Fetching all teams');
            var deferred = $q.defer();
            $http.get(urls.TEAM_SERVICE_API)
                .then(
                    function (response) {
                        $log.log('Fetched successfully all teams');
                        $localStorage.teams = response.data;
                        deferred.resolve(response);
                    },
                    function (errResponse) {
                        $log.error('Error while loading teams');
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        }

        function getAllTeams() {
            return $localStorage.teams;
        }

        function getTeam(id) {
            $log.log('Fetching Team with id :' + id);
            var deferred = $q.defer();
            $http.get(urls.TEAM_SERVICE_API + id)
                .then(
                    function (response) {
                        $log.log('Fetched successfully Team with id :' + id);
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                        $log.error('Error while loading Team with id :' + id);
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        }

        function createTeam(team) {
            $log.log('Creating Team');
            var deferred = $q.defer();
            $http.post(urls.TEAM_SERVICE_API, team)
                .then(
                    function (response) {
                        loadAllTeams();
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                        $log.error('Error while creating Team : ' + errResponse.data);
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        }

        function updateTeam(team, id) {
            $log.log('Updating Team with id ' + id);
            var deferred = $q.defer();
            $http.put(urls.TEAM_SERVICE_API + id, team)
                .then(
                    function (response) {
                        loadAllTeams();
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                        $log.error('Error while updating Team with id :' + id);
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        }

        function removeTeam(id) {
            $log.log('Removing Team with id ' + id);
            var deferred = $q.defer();
            $http.delete(urls.TEAM_SERVICE_API + id)
                .then(
                    function (response) {
                        loadAllTeams();
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                        $log.error('Error while removing Team with id :' + id);
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        }
    }
]);