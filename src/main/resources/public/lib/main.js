angular
    .module('MyApp', ['ngRoute', 'ngMaterial', 'ngMessages'])
    .controller('AppCtrl', AppCtrl)
    .controller('DialogAddTaskController', DialogAddTaskController)
    .controller('DialogAddProjectController', DialogAddProjectController)
    .controller('LoginController', LoginController);

function AppCtrl($scope, $log, $mdBottomSheet, $mdDialog, $rootScope, $mdSidenav, $http) {
    $rootScope.isNotProject = true;
    if (!$rootScope.user) {
        $mdDialog.show({
            controller: LoginController,
            templateUrl: 'views/login.html'

        })
            .then(function (answer) {
                $scope.alert = 'You said the information was "' + answer + '".';
            }, function () {
                $scope.alert = 'You cancelled the dialog.';
            });

    }
    // $rootScope.userName = "USER2"; // TODO remove it after test

    $rootScope.tabs = [];

    $http.get('/projects').success(function (projects) {
        $rootScope.tabs = projects;
        if (projects.length > 0) {
            $rootScope.isNotProject = false;
            $http.get('/tasks/' + projects[0].projectName).success(function (tasks) {
                $rootScope.tasks = tasks;
                $scope.selectedIndex = projects.length;
            }).error(function (err, status) {
                $log.error(err + 'status' + status)
            });
        }


    }).error(function (err, status) {
        $log.error(err + 'status' + status)
    });

    var tasks = [];
    var selected = null;
    var previous = null;

    $rootScope.tasks = tasks;


    $scope.selectedIndex = 0;


    $scope.$watch('selectedIndex', function (current, old) {
        previous = selected;
        if ($rootScope.tabs[current]) {
            $rootScope.projectName = $rootScope.tabs[current].projectName;
            $http.get('/tasks/' + $rootScope.projectName).success(function (data) {
                $rootScope.tasks = data;
            }).error(function (err, status) {
                $log.error(err + 'status' + status)
            });

        }

        selected = $rootScope.tabs[current];

    });


    $scope.deleteProject = function (project) {

        if (project) {
            $http.delete('/project/' + project.projectName).success(function (data) {
                $log.debug('data ' + data);
                $rootScope.tabs = $rootScope.tabs.filter(function (obj) {
                    return obj.projectName !== project.projectName;
                });
                if ($rootScope.tabs.length<1){
                    $rootScope.isNotProject = true;
                }
            }).error(function (err, status) {
                $log.error(err + 'status' + status);
            })
        }
    };


    $scope.showListBottomSheet = function () {
        $scope.alert = '';
        $mdBottomSheet.show({
            templateUrl: 'views/list.html',
            controller: 'ListCtrl'
        }).then(function (clickedItem) {
            $scope.alert = clickedItem['name'] + ' clicked!';
        });
    };


    $scope.showAddTask = function (ev) {
        $mdDialog.show({
            controller: DialogAddTaskController,
            templateUrl: 'views/addTask.html'

        })
            .then(function (answer) {
                $scope.alert = 'You said the information was "' + answer + '".';
            }, function () {
                $scope.alert = 'You cancelled the dialog.';
            });
    };


    $scope.addProject = function () {
        $mdDialog.show({
            controller: DialogAddProjectController,
            templateUrl: 'views/addProject.html'

        })
            .then(function (answer) {

                $scope.alert = 'You said the information was "' + answer + '".';
            }, function () {
                $scope.alert = 'You cancelled the dialog.';
            });
    };


    $scope.deleteTask = function (task) {
        if (task) {
            $http.delete('/task/name/' + task.taskName).success(function (data) {
                $log.debug('data ' + data);
                $rootScope.tasks = $rootScope.tasks.filter(function (obj) {
                    return obj.taskName !== task.taskName;
                });
            }).error(function (err, status) {
                $log.error(err + 'status' + status);
            })
        }

    };


}


function DialogAddProjectController($scope, $http, $mdDialog, $log, $rootScope) {

    $scope.addProject = function (project) {
        $http.post('/project/add', {projectName: project.projectName, projectDescription: project.description}).success(function (data) {
            $log.debug('data ' + data);
            $rootScope.isNotProject = false;
            $rootScope.tabs.push({projectName: project.projectName, disabled: false});
            $mdDialog.hide(project);
        }).error(function (err, status) {
            $log.error(err + 'status' + status);
        });
    };

    $scope.cancelAddProject = function () {
        $mdDialog.cancel();
    };
}

function DialogAddTaskController($scope, $http, $log, $mdDialog, $rootScope) {

    $scope.cancelAddTask = function () {
        $mdDialog.cancel();
    };
    /*
     {"state": "ONGOING",
     "taskName": "TASK1",
     "description": "This is a Task",
     "createdBy": {
     "userName": "USER2"
     },
     "project": {
     "projectName": "PROJECT2",
     }
     }

     */
    $scope.addTask = function (answer) {
        // temp fix for user
        if (!$rootScope.userName) {
            $rootScope.userName = 'USER1'

        }
        answer.createdBy = {userName: $rootScope.userName};
        answer.project = {projectName: $rootScope.projectName};
        $http.post('/task/add', answer).success(function (data) {
            $log.debug('data ' + data);
            $rootScope.tasks.push(answer);
            $mdDialog.hide(answer);
        }).error(function (err, status) {
            $log.error(err + 'status' + status);
            $mdDialog.hide(answer);
        });

    };
}


function LoginController($scope, $mdDialog, $log, $http, $rootScope) {
    $scope.hide = function () {
        $mdDialog.hide();
    };
    $scope.cancelUser = function () {
        $mdDialog.cancel();
    };

    $scope.connect = function (logged) {
        $rootScope.userName = logged;
        $log.debug($rootScope.userName);
        $mdDialog.hide(logged);
    };

    $scope.addUser = function (user) {
        $http.post('/user/add', {userName: user.userName}).success(function (data) {
            $log.debug('data ' + data);
            $rootScope.userName = user.userName;
            $mdDialog.hide(user);
        }).error(function (err, status) {
            $log.error(err + 'status' + status);
        });
    }
}