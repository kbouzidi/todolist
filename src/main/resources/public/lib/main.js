angular.module('todowebapp', [
    'ui.router'
])
    .config(function ($urlRouterProvider, $stateProvider) {
        /*$routeProvider.when('/', {
         templateUrl: 'views/todos.html',
         controller: 'MainCtrl'
         }).when('/add', {
         templateUrl: 'views/add.html',
         controller: 'AddCtrl'
         }).when('/login', {
         templateUrl: 'views/login.html',
         controller: 'LoginCtrl'
         }).when('/todos', {
         templateUrl: 'views/todos.html',
         controller: 'TodosCtrl'
         }).otherwise({
         redirectTo: '/'
         })*/


        // For any unmatched url, send to /index
        $urlRouterProvider.otherwise("/todos");

        $stateProvider
            .state('login', {
                url: "/login",
                templateUrl: "views/login.html",
                controller: "LoginCtrl"
            })
            .state('home', {
                url: "/todos",
                templateUrl: "views/todos.html",
                controller: "TodosCtrl"
            })
            .state('add', {
                url: "/add",
                templateUrl: "views/add.html",
                controller: "AddCtrl"
            });


    })
    .controller('MainCtrl', function ($scope, $http, $location, $rootScope) {
        if (!$rootScope.user) {
            $location.path("/login");
            if (!$scope.$$phase) {
                $scope.$apply();
            }
        } else {
            $location.path('/todos');

        }
    })

    .controller('TodosCtrl', function ($scope, $http, $location, $rootScope) {

        $scope.todos = {};
        $http.get('/todos').success(function (data) {
            $scope.todos = data;
        }).error(function (err, status) {
            console.log('Error ' + err)
        });


        $scope.updateTodo = function (todo) {
            $http.put('/update' + todo.id, todo).success(function (data) {
                console.log('status changed');
            }).error(function (data, status) {
                console.log('Error ' + data)
            })
        }

    })
    .controller('LoginCtrl', function ($scope, $http, $location, $rootScope) {

        $scope.connect = function (logged) {
            $rootScope.user = logged;
            $location.path('/todos');
        }

    })
    .controller('AddCtrl', function ($scope, $http, $location) {

        $scope.todo = {
            state: "STARTED"
        };

        $scope.createTodo = function () {
            console.log($scope.todo);
            $http.post('/add', $scope.todo).success(function (data) {
                $location.path('/');
            }).error(function (data, status) {
                console.log('Error ' + data)
            })
        }
    })
;
