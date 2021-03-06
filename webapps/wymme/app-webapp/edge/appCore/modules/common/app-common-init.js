
function appInitVar($scope, $http, $modal, $log, $sce){
	
	if (isMobile()) {
		// Mobile - On Launch Do not show Menu 
		$scope.es.showMenu=false;
	}else{
		$scope.es.showMenu=true;
	}
	
	$scope.es.selectedPage = menuMap["1HOME"];
	$scope.es.showSearch = 'C';
	$scope.es.searchedProfiles = {};
	$scope.es.editProfile = {};
	appOnLoad ($scope, $http, $modal, $log, $sce);
	
	//setTimeout(function() {$scope.es.setSelectedMenuRaw("1HOME"); }, 3000);
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
	    	image: 'http://lorempixel.com/400/200/people'
	    },
	    {
	    	image: 'http://localhost:8080/rootPage/server/secured/getImage/profilePic/FXWGTH61/07FH8EW.png'
		},
	    {
	      image: '/server/secured/getImage/profilePic/FXWGTH61/07FH8EW.png'
	    },
	    {
	      image: '/server/secured/getImage/albumImg1/FXWGTH61/DTLRMDB.png'
	    }
	  ];
	
};
