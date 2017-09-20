
var initializeSearch = function initializeSearch($scope, $http){
	$scope.es.search = {};
	$scope.es.search.type = "";
	$scope.es.searchedProfiles = {};
};

var loadRemovedProfiles = function loadRemovedProfiles($scope, $http){	
	startAjax('LOAD_REMOVED_PROFILES', $scope);
	$http.post('server/secured/loadRemovedProfiles.json', $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_REMOVED_PROFILES', $scope, data, status, headers, config);
    	$scope.es.searchedProfiles = data.edgeResponse.responseData;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_REMOVED_PROFILES', $scope, data, status, headers, config);
    });
};


var undoRemoveFromWall = function undoRemoveFromWall($scope, $http, toAdd){
	startAjax('UNREMOVE_FROM_WALL', $scope);
	$http.post('server/secured/undoRemoveFromWall.json', toAdd ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('UNREMOVE_FROM_WALL', $scope, data, status, headers, config);
    	loadRemovedProfiles($scope, $http);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('UNREMOVE_FROM_WALL', $scope, data, status, headers, config);
    });
};


var searchProfiles = function searchProfiles($scope, $http){
	startAjax('SEARCH_PROFILES', $scope);
	$http.post('server/secured/searchProfiles.json', $scope.es.search.type ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SEARCH_PROFILES', $scope, data, status, headers, config);
    	$scope.es.searchedProfiles = data.edgeResponse.responseData;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SEARCH_PROFILES', $scope, data, status, headers, config);
    });
};

var searchById = function($scope, $http){
	
	if($scope.es.searchId){
		startAjax('SEARCH_BY_ID', $scope);
		$http.post('server/unsecured/searchById.json',$scope.es.searchId).
	    success(function(data, status, headers, config) {
	    	
	    	handleAjaxSuccess('SEARCH_BY_ID', $scope, data, status, headers, config);
	    	$scope.es.searchedProfiles = data.edgeResponse.responseData;
	    	
	    }).
	    error(function(data, status, headers, config) {
	    	handleAjaxError('SEARCH_BY_ID', $scope, data, status, headers, config);
	    });
	}
	
};