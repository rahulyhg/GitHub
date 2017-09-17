
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