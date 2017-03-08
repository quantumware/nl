angular.module('nl', []).controller('home', function($scope, $http) {
	$http.get('/resources').success(function(data) {
		$scope.greeting = data;
	});
	$http.get('/books').success(function(data) {
		$scope.books = data;
	});
});
