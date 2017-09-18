
var searchById = function($scope, $http){
	
	if($scope.es.searchId){
		startAjax('SEARCH_BY_ID', $scope);
		$http.post('server/unsecured/searchById.json',$scope.es.searchId).
	    success(function(data, status, headers, config) {
	    	
	    	handleAjaxSuccess('SEARCH_BY_ID', $scope, data, status, headers, config);
	    	
	    	if(data.edgeResponse && data.edgeResponse.success){
	    		
	    		$scope.es.searchedProfiles = data.edgeResponse.responseData;
	    		$scope.es.editProfile = $scope.es.searchedProfiles[0];
	    		
	    		$scope.es.showSearch = 'P'; 
	    		$scope.es.currSearchType = 'Search By Id';
	    		$scope.es.searched=true; 
	    		$scope.es.profileOpened=true;
	    	}
	    }).
	    error(function(data, status, headers, config) {
	    	handleAjaxError('SEARCH_BY_ID', $scope, data, status, headers, config);
	    });
	}
	
};