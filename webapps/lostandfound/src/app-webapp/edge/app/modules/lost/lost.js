
var lost = function lost($scope, $http){	
	startAjax('LOST', $scope);
	$http.post('server/unsecured/lost.json', $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOST', $scope, data, status, headers, config);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOST', $scope, data, status, headers, config);
    });
};