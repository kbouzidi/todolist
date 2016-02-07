angular
    .module('MyApp', ['ngRoute', 'ngMaterial', 'ngMessages'])
    .controller('AppCtrl', AppCtrl)
    .controller('ListCtrl', ListCtrl).controller('DialogController', DialogController);

function AppCtrl($scope, $log, $mdBottomSheet, $mdDialog, $timeout) {
    var tabs = [
            { title: 'One', content: "Tabs will become paginated if there isn't enough room for them."},
            { title: 'Two', content: "You can swipe left and right on a mobile device to change tabs."},
            { title: 'Three', content: "You can bind the selected tab via the selected attribute on the md-tabs element."}


        ],


        tasks = [
            { id: '1', desc: "Desc 1.", author: "Test1", state: "Started"},
            { id: '2', desc: "Desc 1.", author: "Test1", state: "Started"},
            { id: '3', desc: "Desc 2.", author: "Test3", state: "Started"},
            { id: '4', desc: "Desc 3.", author: "Test2", state: "Started"},
            { id: '5', desc: "Desc 4.", author: "Test1", state: "Started"}

        ],
        selected = null,
        previous = null;
    $scope.tabs = tabs;
    $scope.tasks = tasks;

    $scope.selectedIndex = 1;

    $scope.$watch('selectedIndex', function (current, old) {
        previous = selected;
        selected = tabs[current];
        if (old + 1 && (old != current)) $log.debug('Goodbye ' + previous.title + '!');
        if (current + 1)                $log.debug('Hello ' + selected.title + '!');
    });
    $scope.addTab = function (title, view) {
        view = view || title + " Content View";
        tabs.push({ title: title, content: view, disabled: false});
        $timeout(function () {
            $scope.selectedIndex = tabs.length - 1;
        });
        $mdDialog.hide();
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
