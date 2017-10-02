
function appInitVar($scope, $http, $modal, $log, $sce){
	
	$scope.es.selectedPage = menuMap["1HOME"];
	$scope.es.showSearch = 'C';
	$scope.es.searchedProfiles = {};
	$scope.es.editProfile = {};
	
	appOnLoad ($scope, $http, $modal, $log, $sce);
}


var appOnLoad = function($scope, $http, $modal, $log, $sce){
	
	// http://localhost:8080/#/?searchById=FETWP1WO
	
	$scope.es.searchId = $scope.es.location.search().searchById;
	if($scope.es.searchId){
		$scope.es.selectedPage = menuMap["1HOME"];
		$scope.es.searchById($scope, $http);
	}
	
	
	// TEST
	$scope.es.slideshow = {};
	$scope.es.slideshow.title = "Demo Title";
	$scope.es.slideshow.interval = 3000;
	$scope.es.slideshow.slides = [
	    {
	      image: 'http://lorempixel.com/400/200/'
	    },
	    {
	      image: 'http://lorempixel.com/400/200/food'
	    },
	    {
	      image: 'http://lorempixel.com/400/200/sports'
	    },
	    {
	      image: 'http://lorempixel.com/400/200/people'
	    }
	  ];
	
};
