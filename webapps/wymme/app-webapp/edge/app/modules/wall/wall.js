
var initializeWall = function  initializeWall($scope, $http){
	if($scope.es.loggedInUserId){
		loadWallProfiles($scope, $http);
		loadUnreadNotifications($scope, $http);
	}
};

var loadWallProfiles = function loadWallProfiles($scope, $http){	
	startAjax('LOAD_WALL_PROFILES', $scope);
	$http.post('server/secured/loadWallProfiles.json', $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_WALL_PROFILES', $scope, data, status, headers, config);
    	$scope.es.wallProfiles = data.edgeResponse.responseData;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_WALL_PROFILES', $scope, data, status, headers, config);
    });
};

var loadUnreadNotifications = function loadWallProfiles($scope, $http){	
	startAjax('LOAD_UNREAD_NOTIFICATIONS', $scope);
	$http.post('server/secured/loadUnreadNotifications.json', $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_UNREAD_NOTIFICATIONS', $scope, data, status, headers, config);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_UNREAD_NOTIFICATIONS', $scope, data, status, headers, config);
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

var sendConnectionRequest = function sendConnectionRequest($scope, $http, profileTo){
	startAjax('SEND_CONNECTION_REQUEST', $scope);
	$http.post('server/secured/sendConnectionRequest.json', profileTo ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SEND_CONNECTION_REQUEST', $scope, data, status, headers, config);
    	loadWallProfiles($scope, $http);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SEND_CONNECTION_REQUEST', $scope, data, status, headers, config);
    });
};