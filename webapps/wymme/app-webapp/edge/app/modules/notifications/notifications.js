
var initializeNotifications = function initializeSearch($scope, $http){
	if($scope.es.loggedInUserId){
		loadNotifications($scope, $http);
	}
};

var loadNotifications = function loadNotifications($scope, $http){	
	startAjax('LOAD_NOTIFICATIONS', $scope);
	$http.post('server/secured/loadNotifications.json', $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_NOTIFICATIONS', $scope, data, status, headers, config);
    	$scope.es.notifications = data.edgeResponse.responseData;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_NOTIFICATIONS', $scope, data, status, headers, config);
    });
};

var markNotificationAsRead = function markNotificationAsRead($scope, $http, notificationId){	
	startAjax('MARK_NOTIFICATION_AS_READ', $scope);
	$http.post('server/secured/markNotificationAsRead.json', notificationId ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('MARK_NOTIFICATION_AS_READ', $scope, data, status, headers, config);
    	loadNotifications($scope, $http);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('MARK_NOTIFICATION_AS_READ', $scope, data, status, headers, config);
    });
};


