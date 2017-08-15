
var initLostReports = function initLostReports($scope, $http){
	initializeLostReportsGrid($scope, $http);
	$scope.es.lostReport = {};
	$scope.es.loadLostReports();
};

var initializeLostReportsGrid = function initializeClientGrid($scope, $http){	
	$scope.es.lostReportsGridOptions = { 
									enableFiltering: true, enableRowSelection: true, multiSelect: false,
									columnDefs: [{field:'lostReportId'},{field:'details'},
									             {field:'paidAmount', cellFilter: "currency:''"},
									             {field:'pymtMode'},
									             {field:'paidOn', cellFilter: "date:'yyyy-MM-dd'"},
									             {field:'updatedOn', cellFilter: "date:'yyyy-MM-dd'"}
									             ]
								 };
	$scope.es.lostReportsGridOptions.onRegisterApi = function(gridApi){
	      //set gridApi on scope
	      $scope.gridApi = gridApi;
	      gridApi.selection.on.rowSelectionChanged($scope,function(row){
	    	  if(row.isSelected){
	    		  $scope.es.lostReport = row.entity;
	    	  }
	      });
	    };

};

var saveLostReport = function addLostReport($scope, $http){	
	startAjax('SAVE_LOST_REPORT', $scope);
	$http.post('server/unsecured/saveLostReport.json', $scope.es.lostReport ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SAVE_LOST_REPORT', $scope, data, status, headers, config);
    	//loadLostReports($scope, $http);
    	$scope.es.lostReport = {};
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SAVE_LOST_REPORT', $scope, data, status, headers, config);
    });
};

var loadLostReports = function loadLostReports($scope, $http){	
	
	startAjax('LOAD_LOST_REPORTS', $scope);
	$http.post('server/unsecured/getAllLostReports.json').
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_LOST_REPORTS', $scope, data, status, headers, config);
    	$scope.es.allLostReports = data.edgeResponse.responseData;
    	$scope.es.lostReportsGridOptions.data = $scope.es.allLostReports;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_LOST_REPORTS', $scope, data, status, headers, config);
    });
};
