
var initLostRequests = function initLostRequests($scope, $http){
	initializeLostRequestsGrid($scope, $http);
	$scope.es.lostRequest = {};
	$scope.es.loadLostRequests();
};

var initializeLostRequestsGrid = function initializeClientGrid($scope, $http){	
	$scope.es.lostRequestsGridOptions = { 
									enableFiltering: true, enableRowSelection: true, multiSelect: false,
									columnDefs: [{field:'lostRequestId'},{field:'details'},
									             {field:'paidAmount', cellFilter: "currency:''"},
									             {field:'pymtMode'},
									             {field:'paidOn', cellFilter: "date:'yyyy-MM-dd'"},
									             {field:'updatedOn', cellFilter: "date:'yyyy-MM-dd'"}
									             ]
								 };
	$scope.es.lostRequestsGridOptions.onRegisterApi = function(gridApi){
	      //set gridApi on scope
	      $scope.gridApi = gridApi;
	      gridApi.selection.on.rowSelectionChanged($scope,function(row){
	    	  if(row.isSelected){
	    		  $scope.es.lostRequest = row.entity;
	    	  }
	      });
	    };

};

var saveLostRequest = function addLostRequest($scope, $http){	
	startAjax('SAVE_LOST_REQUEST', $scope);
	$http.post('server/unsecured/saveLostRequest.json', $scope.es.lostRequest ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SAVE_LOST_REQUEST', $scope, data, status, headers, config);
    	loadLostRequests($scope, $http);
    	$scope.es.lostRequest = {};
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SAVE_LOST_REQUEST', $scope, data, status, headers, config);
    });
};

var loadLostRequests = function loadLostRequests($scope, $http){	
	
	startAjax('LOAD_LOST_REQUESTS', $scope);
	$http.post('server/unsecured/getAllLostRequests.json').
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_LOST_REQUESTS', $scope, data, status, headers, config);
    	$scope.es.allLostRequests = data.edgeResponse.responseData;
    	$scope.es.lostRequestsGridOptions.data = $scope.es.allLostRequests;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_LOST_REQUESTS', $scope, data, status, headers, config);
    });
};
