angular
    .module('MyApp', ['ngRoute', 'ngMaterial', 'ngMessages'])
    .controller('AppCtrl', AppCtrl)
    .controller('ListCtrl', ListCtrl)
    .controller('DialogAddTaskController', DialogAddTaskController)
    .controller('LoginController', LoginController);

function AppCtrl($scope, $log, $mdBottomSheet, $mdDialog, $timeout, $rootScope, $http) {
    if (!$rootScope.user) {
        /* $mdDialog.show({
         controller: LoginController,
         templateUrl: 'views/login.html'

         })
         .then(function (answer) {
         $scope.alert = 'You said the information was "' + answer + '".';
         }, function () {
         $scope.alert = 'You cancelled the dialog.';
         });*/

    }
    $rootScope.userName = "USER2"; // TODO remove it after test

    $rootScope.tabs = [];

    $http.get('/projects').success(function (projects) {
        $rootScope.tabs = projects;

        $http.get('/tasks/' + projects[0].projectName).success(function (tasks) {
            $rootScope.tasks = tasks;
            $scope.selectedIndex = projects.length;
        }).error(function (err, status) {
            console.log('Error ' + err)
        });

    }).error(function (err, status) {
        console.log('Error ' + err)
    });

    var tasks = [];
    var selected = null;
    var previous = null;

    // $scope.tabs = tabs;
    $rootScope.tasks = tasks;

    /*   if (!(selected == null || selected == null)) {
     $http.get( '/tasks').success(function (data) {
     $rootScope.tasks = data;
     }).error(function (err, status) {
     console.log('Error ' + err)
     });
     }*/


    $scope.selectedIndex = 0;


    $scope.$watch('selectedIndex', function (current, old) {
        previous = selected;
        var currentTab = $rootScope.tabs[current];


        if ($rootScope.tabs[current]) {
            $rootScope.projectName = $rootScope.tabs[current].projectName;
            $http.get('/tasks/' + $rootScope.projectName).success(function (data) {
                $rootScope.tasks = data;
            }).error(function (err, status) {
                console.log('Error ' + err)
            });

        }

        selected = $rootScope.tabs[current];

    });


    $scope.addTab = function (projectName, description) {
        // view = view || title + " Content View";
        $http.post('/add/project', {projectName: projectName, projectDescription: description}).success(function (data) {
            console.log('data ' + data);
            $scope.tabs.push({ projectName: projectName, disabled: false});
        }).error(function (data, status) {
            console.log('Error ' + data)
        })

    };
    $scope.removeTab = function (tab) {
        var index = $rootScope.tabs.indexOf(tab);
        // TODO call remove Project
        $rootScope.tabs.splice(index, 1);
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
            controller: AppCtrl,
            templateUrl: 'views/addProject.html'

        })
            .then(function (answer) {

                $scope.alert = 'You said the information was "' + answer + '".';
            }, function () {
                $scope.alert = 'You cancelled the dialog.';
            });
    };


}

function ListCtrl($scope) {

    $scope.items = [
        { name: 'Share', icon: 'share-arrow' },
        { name: 'Upload', icon: 'upload' },
        { name: 'Copy', icon: 'copy' },
        { name: 'Print this page', icon: 'print' }
    ];
    $scope.listItemClick = function ($index) {
        var clickedItem = $scope.items[$index];
        $mdBottomSheet.hide(clickedItem);
    };
}

function DialogAddTaskController($scope, $http, $mdDialog, $rootScope) {

    $scope.cancelAddTask = function () {
        $mdDialog.cancel();
    };
    /*
     {
     "state":"ONGOING",
     "taskName":
     "Task1",
     "description":
     "This is a Task",
     "user":
     {"userName":"User2"},
     "project":
     {"projectName":"Project2"}
     }

     */
    $scope.addTask = function (answer) {
        answer.user = {userName: $rootScope.userName};
        answer.project = {projectName: $rootScope.projectName};
        $http.post('/add/task', answer).success(function (data) {
            console.log('data ' + data);
            $rootScope.tasks.push(answer);
            $mdDialog.hide(answer);
        }).error(function (data, status) {
            console.log('Error ' + data);
            $mdDialog.hide(answer);
        });

    };
}


function LoginController($scope, $mdDialog, $rootScope) {
    $scope.hide = function () {
        $mdDialog.hide();
    };
    $scope.cancel = function () {
        $mdDialog.cancel();
    };

    $scope.connect = function (logged) {
        $rootScope.user = logged;
        $mdDialog.hide(logged);
    }
}