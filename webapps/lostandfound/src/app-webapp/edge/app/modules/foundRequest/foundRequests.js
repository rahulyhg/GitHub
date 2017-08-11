
var initFoundRequests = function initFoundRequests($scope, $http){
	initializeFoundRequestsGrid($scope, $http);
	$scope.es.foundRequest = {};
	$scope.es.loadFoundRequests();
};

var initializeFoundRequestsGrid = function initializeClientGrid($scope, $http){	
	$scope.es.foundRequestsGridOptions = { 
									enableFiltering: true, enableRowSelection: true, multiSelect: false,
									columnDefs: [{field:'foundRequestId'},{field:'details'},
									             {field:'paidAmount', cellFilter: "currency:''"},
									             {field:'pymtMode'},
									             {field:'paidOn', cellFilter: "date:'yyyy-MM-dd'"},
									             {field:'updatedOn', cellFilter: "date:'yyyy-MM-dd'"}
									             ]
								 };
	$scope.es.foundRequestsGridOptions.onRegisterApi = function(gridApi){
	      //set gridApi on scope
	      $scope.gridApi = gridApi;
	      gridApi.selection.on.rowSelectionChanged($scope,function(row){
	    	  if(row.isSelected){
	    		  $scope.es.foundRequest = row.entity;
	    	  }
	      });
	    };

};

var saveFoundRequest = function addFoundRequest($scope, $http){	
	startAjax('SAVE_FOUND_REQUEST', $scope);
	$http.post('server/unsecured/saveFoundRequest.json', $scope.es.foundRequest ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SAVE_FOUND_REQUEST', $scope, data, status, headers, config);
    	//loadFoundRequests($scope, $http);
    	$scope.es.foundRequest = {};
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SAVE_FOUND_REQUEST', $scope, data, status, headers, config);
    });
};

var loadFoundRequests = function loadFoundRequests($scope, $http){	
	
	startAjax('LOAD_FOUND_REQUESTS', $scope);
	$http.post('server/unsecured/getAllFoundRequests.json').
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_FOUND_REQUESTS', $scope, data, status, headers, config);
    	$scope.es.allFoundRequests = data.edgeResponse.responseData;
    	$scope.es.foundRequestsGridOptions.data = $scope.es.allFoundRequests;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_FOUND_REQUESTS', $scope, data, status, headers, config);
    });
};
