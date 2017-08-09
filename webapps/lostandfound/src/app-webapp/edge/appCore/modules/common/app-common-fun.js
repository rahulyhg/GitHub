
function appInitFun($scope, $http, $modal, $log, $sce){
	$scope.es.lost = function () {
		lost($scope, $http);
	};
	
	$scope.es.openMyProfile = function () {
		openMyProfile($scope, $http);
	};
	
	$scope.es.updateMyProfile = function () {
		updateMyProfile($scope, $http);
	};
	
	$scope.es.searchById = function () {
		searchById($scope, $http);
	};
	
	// LOST REQUEST 
	
	$scope.es.saveLostRequest = function () {
		saveLostRequest($scope, $http);
	};

	// FOUND REQUEST 
	
	$scope.es.saveFoundRequest = function () {
		saveFoundRequest($scope, $http);
	};
};	
	
var openMyProfile = function($scope, $http){
	startAjax('MYPROFILE', $scope);
	$http.post('server/secured/profile/openMyProfile.json',"").
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('MYPROFILE', $scope, data, status, headers, config);
    	$scope.es.editProfile = data.edgeResponse.responseData;
    	$scope.es.selectedPage = extendedMenuMap["UPDATE_PROFILE"];    	
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('MYPROFILE', $scope, data, status, headers, config);
    });
};