
var initTagCreations = function initTagCreations($scope, $http){
	$scope.es.tagCreation = {};
	$scope.es.loadTagCreations();
};

var saveTagCreation = function saveTagCreation($scope, $http){	
	startAjax('SAVE_TAG_CREATION', $scope);
	$http.post('server/unsecured/saveTagCreation.json', $scope.es.tagCreation ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SAVE_TAG_CREATION', $scope, data, status, headers, config);
    	//loadTagCreations($scope, $http);
    	$scope.es.tagCreation = {};
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SAVE_TAG_CREATION', $scope, data, status, headers, config);
    });
};
