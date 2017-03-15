'use strict';
calcioApp.controller('TeamController', ['TeamService', '$scope', '$log',
    function (TeamService, $scope, $log) {
        var self = this;
        self.team = {};
        self.teams = [];

        self.submit = submit;
        self.getAllTeams = getAllTeams;
        self.createTeam = createTeam;
        self.updateTeam = updateTeam;
        self.removeTeam = removeTeam;
        self.editTeam = editTeam;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        //self.onlyIntegers = /^\d+$/;
        //self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function submit() {
            $log.log('Submitting');
            if (self.team.id === undefined || self.team.id === null) {
                $log.log('Saving New Team', self.team);
                createTeam(self.team);
            } else {
                updateTeam(self.team, self.team.id);
                $log.log('Team updated with id ', self.team.id);
            }
        }

        function createTeam(team) {
            $log.log('About to create team');
            TeamService.createTeam(team)
                .then(
                    function (response) {
                        $log.log('Team created successfully');
                        self.successMessage = 'Team created successfully';
                        self.errorMessage = '';
                        self.done = true;
                        self.team = {};
                        $scope.teamForm.$setPristine();
                    },
                    function (errResponse) {
                        $log.error('Error while creating Team: ' + team);
                        self.errorMessage = 'Error while creating Team: ' + errResponse.data.errorMessage;
                        self.successMessage = '';
                    }
                );
        }

        function updateTeam(team, id) {
            $log.log('About to update team');
            TeamService.updateTeam(team, id)
                .then(
                    function (response) {
                        $log.log('Team updated successfully');
                        self.successMessage = 'Team updated successfully';
                        self.errorMessage = '';
                        self.done = true;
                        self.team = {};
                        $scope.teamForm.$setPristine();
                    },
                    function (errResponse) {
                        $log.error('Error while updating Team');
                        if (errResponse.data.errorMessage === undefined || errResponse.data.errorMessage === null) {
                            self.errorMessage = errResponse.status + ' - ' + errResponse.statusText;
                        } else {
                            self.errorMessage = 'Error while updating Team. ' + errResponse.data.errorMessage;
                        }
                        self.successMessage = '';
                    }
                );
        }

        function removeTeam(id) {
            $log.log('About to remove Team with id ' + id);
            TeamService.removeTeam(id)
                .then(
                    function () {
                        $log.log('Team ' + id + ' removed successfully');
                    },
                    function (errResponse) {
                        $log.error('Error while removing team ' + id + ', Error :' + errResponse.data);
                    }
                );
        }

        function getAllTeams() {
            return TeamService.getAllTeams();
        }

        function editTeam(id) {
            self.successMessage = '';
            self.errorMessage = '';
            TeamService.getTeam(id).then(
                function (team) {
                    self.team = team;
                },
                function (errResponse) {
                    $log.error('Error while removing team ' + id + ', Error :' + errResponse.data);
                }
            );
        }

        function reset() {
            self.successMessage = '';
            self.errorMessage = '';
            self.team = {};
            $scope.teamForm.$setPristine(); //reset Form
        }
    }
]);