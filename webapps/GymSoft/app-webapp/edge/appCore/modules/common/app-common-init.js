
function appInitVar($scope, $http, $modal, $log, $sce){
	
	$scope.es.selectedPage = menuMap["1HOME"];
	$scope.es.showSearch = 'C';
	$scope.es.searchedProfiles = {};
	$scope.es.editProfile = {};
	
	appOnLoad ($scope, $http, $modal, $log, $sce);
}


var appOnLoad = function($scope, $http, $modal, $log, $sce){
	
	// http://localhost:8080/contextRoot/#/?searchById=FETWP1WO
	
	$scope.es.searchId = $scope.es.location.search().searchById;
	if($scope.es.searchId){
		$scope.es.selectedPage = menuMap["1HOME"];
		$scope.es.searchById($scope, $http);
	}
	
};
