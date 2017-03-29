

var updateMyProfile = function updateMyProfile($scope, $http){
	startAjax('UPDATE_PROFILE', $scope);
	$http.post('server/secured/profile/updateMyProfile.json', $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('UPDATE_PROFILE', $scope, data, status, headers, config);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('UPDATE_PROFILE', $scope, data, status, headers, config);
    });
};