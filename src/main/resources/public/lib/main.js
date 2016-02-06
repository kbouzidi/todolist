angular.module('todowebapp', [
    'ngRoute'
])
    .config(function ($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: 'views/todos.html',
            controller: 'AllCtrl'
        }).when('/add', {
            templateUrl: 'views/add.html',
            controller: 'AddCtrl'
        }).otherwise({
            redirectTo: '/'
        })
    })

    .controller('AllCtrl', function ($scope, $http) {
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
