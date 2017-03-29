
var register = function register($scope, $http){	
	startAjax('REGISTER', $scope);
	$http.post('server/unsecured/register.json', $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('REGISTER', $scope, data, status, headers, config);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('REGISTER', $scope, data, status, headers, config);
    });
};