angular
    .module('TodoWebApp', ['ngRoute', 'ngMaterial', 'ngMessages', 'ngCookies'])
    .controller('TodoAppCtrl', TodoAppCtrl)
    .controller('DialogAddTaskController', DialogAddTaskController)
    .controller('DialogAddProjectController', DialogAddProjectController)
    .controller('LoginController', LoginController)
    .controller('DialogAssignController', DialogAssignController)
    .controller('DialogDeleteProject', DialogDeleteProject);
;


/**
 * App controller
 * @param $scope
 * @param $log
 * @param $mdBottomSheet
 * @param $mdDialog
 * @param $rootScope
 * @param $cookies
 * @param $http
 * @constructor
 */
function TodoAppCtrl($scope, $log, $mdBottomSheet, $mdDialog, $rootScope, $cookies, $http) {
    $rootScope.isNotProject = true;
    $scope.isLoading = true;
    var cookiesData = $cookies.get('userInput');
    if (cookiesData) {
        $rootScope.userData = JSON.parse(cookiesData);
    }

    if (!$rootScope.userData) {

        $scope.isLoading = false;
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

    // $rootScope.userData = "USER2"; // TODO remove it after test

    $rootScope.tabs = [];

    $http.get('/projects').success(function (projects) {
        $rootScope.tabs = projects;
        if (projects.length > 0) {
            $rootScope.isNotProject = false;
            $scope.isLoading = false;
            $http.get('/tasks/' + projects[0].projectId).success(function (tasks) {
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
            $rootScope.projectInfo = {
                projectName: $rootScope.tabs[current].projectName,
                projectDescription: $rootScope.tabs[current].projectDescription,
                projectId: $rootScope.tabs[current].projectId
            };
            $rootScope.projectName = $rootScope.tabs[current].projectName;
            $http.get('/tasks/' + $rootScope.projectInfo.projectId).success(function (data) {
                $rootScope.tasks = data;
            }).error(function (err, status) {
                $log.error(err + 'status' + status)
            });

        }

        selected = $rootScope.tabs[current];

    });


    $scope.deleteProject = function (project) {
        $mdDialog.show({
            controller: DialogDeleteProject,
            templateUrl: 'views/deleteProject.html',
            locals: {
                project: project
            }
        }).then(function (answer) {
            $scope.alert = 'You said the information was "' + answer + '".';
        }, function () {
            $scope.alert = 'You cancelled the dialog.';
        });
    };


    $scope.updateTo = function (task, state) {
        $log.debug('task to Update ', task, 'stae', state);
        if (task) {
            task.state = state;
            $scope.isLoading = true;
            $http.put('/task/' + task.taskId + '/' + state).success(function (data) {
                $log.debug('task to Update ' + data);

                $rootScope.tasks = $rootScope.tasks.filter(function (obj) {
                    return obj.taskName !== task.taskName;
                });

                $rootScope.tasks.push(task);
                $scope.isLoading = false;

            }).error(function (err, status) {
                $log.error(err + 'status' + status);
            })
        }

    };

    $scope.assignTask = function (task) {

        $http.get('/users').success(function (users) {
            var data = {task: task, users: users};
            $mdDialog.show({
                controller: DialogAssignController,
                templateUrl: 'views/assign.html',
                locals: {
                    data: data
                }
            }).then(function (answer) {
                $scope.alert = 'You said the information was "' + answer + '".';
            }, function () {
                $scope.alert = 'You cancelled the dialog.';
            });

        }).error(function (err, status) {
            $log.error(err + 'status' + status)
        });


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
        if (!$rootScope.projectInfo && $rootScope.tabs && $rootScope.tabs.length === 1) {
            $rootScope.projectInfo = {projectName: $rootScope.tabs[0].projectName, projectName: $rootScope.tabs[0].projectDescription};
        }

        var data = {projectInfo: $rootScope.projectInfo, userData: $rootScope.userData};
        $mdDialog.show({
            controller: DialogAddTaskController,
            templateUrl: 'views/addTask.html',
            locals: {
                data: data
            }
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
            $scope.isLoading = true;
            $http.delete('/task/' + task.taskId).success(function (data) {
                $log.debug('data ' + data);
                $rootScope.tasks = $rootScope.tasks.filter(function (obj) {
                    return obj.taskId !== data;
                });
                $scope.isLoading = true;
            }).error(function (err, status) {
                $log.error(err + 'status' + status);
            })
        }

    };


    $scope.disconnect = function () {
        $cookies.remove('userInput');
        $rootScope.userData = undefined;
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


}


function DialogAddProjectController($scope, $http, $mdDialog, $log, $rootScope) {

    $scope.addProject = function (project) {
        $scope.isLoading = true;
        $http.post('/project/add', {projectName: project.projectName, projectDescription: project.description}).success(function (data) {
            $log.debug('data ' + data);
            $rootScope.isNotProject = false;
            $rootScope.tabs.push({
                projectName: data.projectName,
                projectDescription: data.projectDescription,
                projectId: data.projectId,
                disabled: false});
            $rootScope.projectInfo = data;
            $scope.isLoading = false;
            $mdDialog.hide(project);
        }).error(function (err, status) {
            $log.error(err + 'status' + status);
        });
    };

    $scope.cancelAddProject = function () {
        $mdDialog.cancel();
    };
}

function DialogAddTaskController($scope, $http, $log, $mdDialog, $rootScope, data) {
    $rootScope.userData = data.userData;
    $rootScope.projectInfo = data.projectInfo;
    $log.debug($rootScope.projectInfo);
    $scope.cancelAddTask = function () {
        if (!$rootScope.userData) {
            $rootScope.userData = {
                userName: 'USER'
            }

        }
        $mdDialog.cancel();
    };

    $scope.addTask = function (answer) {
        // temp fix for user
        if (!$rootScope.userData) {
            $rootScope.userData = {
                userName: 'USER'
            }

        }
        answer.createdBy = $rootScope.userData;
        if ($rootScope.projectInfo) {
            answer.project = $rootScope.projectInfo;
        } else if ($rootScope.projectName) {
            answer.project = {
                projectName: $rootScope.projectName,
                projectDescription: $rootScope.projectDescription,
                projectId: $rootScope.projectId};
        } else if ($rootScope.tabs.length === 1) {
            answer.project = {
                projectName: $rootScope.tabs[0].projectName,
                projectDescription: $rootScope.tabs[0].projectDescription,
                projectId: $rootScope.tabs[0].projectId
            };

        }
        $scope.isLoading = true;
        $http.post('/task/add', answer).success(function (data) {
            $log.debug('data ' + data);
            $rootScope.tasks.push(data);
            $scope.isLoading = false;
            $mdDialog.hide(answer);
        }).error(function (err, status) {
            $log.error(err + 'status' + status);
            $mdDialog.hide(answer);
        });

    };
}


function LoginController($scope, $mdDialog, $log, $http, $rootScope, $cookies) {
    $scope.hide = function () {
        $mdDialog.hide();
    };
    $scope.cancelUser = function () {
        $mdDialog.cancel();
    };

    $scope.addUser = function (user) {
        $scope.isLoading = true;
        $http.post('/user/add', {userName: user.userName}).success(function (data) {
            $log.debug('data ' + data);
            $rootScope.userData = data;
            data = JSON.stringify(data);
            $cookies.put('userInput', data);
            $scope.isLoading = false;
            $mdDialog.hide(user);
        }).error(function (err, status) {
            $log.error(err + 'status' + status);
        });
    }
}


function DialogAssignController($scope, $http, $log, $mdDialog, data, $rootScope) {
    $scope.task = data.task;
    $scope.usersList = data.users;

    $scope.cancelAUpdateTask = function () {
        $mdDialog.cancel();
    };

    $scope.assignToUser = function (user, task) {
        if (user && task) {
            $scope.isLoading = true;
            $http.put('/task/assign/' + task.taskId + '/' + user.userId).success(function (userName) {
                $log.debug('data ' + userName);
                task.assignedToName = userName;
                $rootScope.tasks = $rootScope.tasks.filter(function (obj) {
                    return obj.taskId !== task.taskId;
                });

                $rootScope.tasks.push(task);
                $scope.isLoading = false;
                $mdDialog.hide(user.userId);
            }).error(function (err, status) {
                $log.error(err + 'status' + status);
            })
        }

    };
}

function DialogDeleteProject($scope, $http, $log, $mdDialog, project, $rootScope) {

    $scope.cancelProjectDeletion = function () {
        $mdDialog.cancel();
    };


    $scope.deleteProjectBtn = function () {
        if (project) {
            $scope.isLoading = true;
            $http.delete('/project/' + project.projectId).success(function (data) {
                $log.debug('data ' + data);
                $rootScope.tabs = $rootScope.tabs.filter(function (obj) {
                    return obj.projectName !== project.projectName;
                });
                if ($rootScope.tabs.length < 1) {
                    $rootScope.isNotProject = true;
                }
                $scope.isLoading = false;
                $mdDialog.hide(data);
            }).error(function (err, status) {
                $log.error(err + 'status' + status);
            })
        }
    };

}