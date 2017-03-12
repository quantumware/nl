
angular.module('nl', ['ngRoute'])

  .controller('mainCtrl', function($scope, $route, $routeParams, $location, $http) {
    $scope.$route = $route;
    $scope.$location = $location;
    $scope.$routeParams = $routeParams;
    
	$http.get('/nl/').success(function(data) {
		$scope.greeting = data;
	});
	$http.get('/nl/loaddata').success(function(data) {
		//alert("status:" + data);
	});
  })

  .controller('booksCtrl', function($scope, $routeParams, $http) {
    $scope.name = 'booksCtrl';
    $scope.params = $routeParams;
	$http.get('/nl/books').success(function(data) {
		$scope.bookList = data;
	});
  })

  .controller('personsCtrl', function($scope, $routeParams, $location, $http) {
    $scope.name = 'personsCtrl';
    $scope.params = $routeParams;
	$http.get('/nl/persons').success(function(data) {
		$scope.personList = data;
	});
    $scope.submitForm = function() {
    	$http.get("/nl/addperson?name=" + $scope.form.name + "&phoneNumber=" + $scope.form.phoneNumber + "&emailAddress=" + $scope.form.emailAddress)
    	.success(function(data){
	        console.log("OK", data);
	    });
    };
  })

  .controller('lendCtrl', function($scope, $routeParams, $http) {
    $scope.name = 'lendCtrl';
    $scope.params = $routeParams;
    //var personId = 1;
    $scope.submitForm = function() {
    	//alert("form:" + $scope.form);
		personId = $scope.form.personId;
		alert(personId);
    	$http.post("/nl/lend/" + $scope.form.personId + "/" + $scope.form.bookId)
    	.success(function(data){
	        console.log("OK", data)
	    });
    };
    //alert(personId);
    /*if (personId != undefined) {
		$http.get("/nl/booklent/" + personId)
		.success(function(data){
	        $scope.bookList = data;
	    });
	}*/
  })
 
  .config(function($locationProvider, $routeProvider) {

    $locationProvider.html5Mode(false);

    $routeProvider
      .when('/', {
        templateUrl: 'hello.html',
        controller: 'mainCtrl'
      })
      .when('/books', {
        templateUrl: 'views/books.html',
        controller: 'booksCtrl'
      })
      .when('/persons', {
        templateUrl: 'views/persons.html',
        controller: 'personsCtrl'
      })
      .when('/lend', {
        templateUrl: 'views/lend.html',
        controller: 'lendCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });

  });
