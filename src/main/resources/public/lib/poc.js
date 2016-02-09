angular
    .module('MyApp', ['ngRoute', 'ngMaterial', 'ngMessages'])
    .constant('ApiUrl', 'http://localhost:8080')
    .controller('AppCtrl', AppCtrl)
    .controller('ListCtrl', ListCtrl)
    .controller('DialogController', DialogController)
    .controller('LoginController', LoginController);

function AppCtrl($scope, $log, $mdBottomSheet, $mdDialog, $timeout, $rootScope, $http, ApiUrl) {
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


    $rootScope.tabs = [];

    $http.get(ApiUrl + '/projects').success(function (data) {
        $rootScope.tabs = data;
    }).error(function (err, status) {
        console.log('Error ' + err)
    });

    var tasks = [];
    var selected = null;
    var previous = null;

    // $scope.tabs = tabs;
    $rootScope.tasks = tasks;

    if (!(selected == null || selected == null)) {
        $http.get(ApiUrl + '/tasks').success(function (data) {
            $rootScope.tasks = data;
        }).error(function (err, status) {
            console.log('Error ' + err)
        });
    }


    $scope.selectedIndex = 1;

    $scope.$watch('selectedIndex', function (current, old) {
        previous = selected;
        var currentTab = $rootScope.tabs[current];


        if ($rootScope.tabs[current]) {
            var currentProject = $rootScope.tabs[current].projectName;
            $http.get(ApiUrl + '/tasks/' + currentProject).success(function (data) {
                $rootScope.tasks = data;
            }).error(function (err, status) {
                console.log('Error ' + err)
            });

        }

        selected = $rootScope.tabs[current];

    });
    $scope.addTab = function (title, view) {
        view = view || title + " Content View";
        $http.post('/add', $scope.todo).success(function (data) {
            tabs.push({ title: title, content: view, disabled: false});
        }).error(function (data, status) {
            console.log('Error ' + data)
        })

    };
    $scope.removeTab = function (tab) {
        var index = tabs.indexOf(tab);
        tabs.splice(index, 1);
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


    $scope.showAdd = function (ev) {
        $mdDialog.show({
            controller: DialogController,
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
        { name: 'Print this page', icon: 'print' },
    ];
    $scope.listItemClick = function ($index) {
        var clickedItem = $scope.items[$index];
        $mdBottomSheet.hide(clickedItem);
    };
}

function DialogController($scope, $mdDialog) {
    $scope.hide = function () {
        $mdDialog.hide();
    };
    $scope.cancel = function () {
        $mdDialog.cancel();
    };
    $scope.answer = function (answer) {
        $mdDialog.hide(answer);
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