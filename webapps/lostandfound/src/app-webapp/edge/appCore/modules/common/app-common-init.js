
function appInitVar($scope, $http, $modal, $log, $sce){
	
	$scope.es.selectedPage = menuMap["1HOME"];
	$scope.es.showFound = 'C';
	$scope.es.searchedProfiles = {};
	$scope.es.editProfile = {};
	
	appOnLoad ($scope, $http, $modal, $log, $sce);
}


var appOnLoad = function($scope, $http, $modal, $log, $sce){
	
	$scope.es.lostAndFoundId = $scope.es.location.search().found;
	
	if($scope.es.lostAndFoundId){
		$scope.es.selectedPage = $scope.es.menuMap['3FOUND'];
		$scope.es.foundReport = {};
		$scope.es.foundReport.idType = "LOST_AND_FOUND_ID";
		$scope.es.foundReport.lostAndFoundId=$scope.es.lostAndFoundId;
		alert("Please fill up below details and submit the form. Thank You!");
	}
};
