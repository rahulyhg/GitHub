
var initializeWall = function  initializeWall($scope, $http){
	loadWallProfiles($scope, $http);
};

var loadWallProfiles = function loadWallProfiles($scope, $http){	
	startAjax('GET_WALL_PROFILES', $scope);
	$http.post('server/secured/loadWallProfiles.json', $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('GET_WALL_PROFILES', $scope, data, status, headers, config);
    	$scope.es.wallProfiles = data.edgeResponse.responseData;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('GET_WALL_PROFILES', $scope, data, status, headers, config);
    });
};


var removeFromWall = function removeFromWall($scope, $http, toRemove){
	startAjax('REMOVE_FROM_WALL', $scope);
	$http.post('server/secured/removeFromWall.json', toRemove ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('REMOVE_FROM_WALL', $scope, data, status, headers, config);
    	loadWallProfiles($scope, $http);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('REMOVE_FROM_WALL', $scope, data, status, headers, config);
    });
};