
var initFoundReports = function initFoundReports($scope, $http){
	initializeFoundReportsGrid($scope, $http);
	$scope.es.foundReport = {};
	$scope.es.loadFoundReports();
};

var initializeFoundReportsGrid = function initializeClientGrid($scope, $http){	
	$scope.es.foundReportsGridOptions = { 
									enableFiltering: true, enableRowSelection: true, multiSelect: false,
									columnDefs: [{field:'foundReportId'},{field:'details'},
									             {field:'paidAmount', cellFilter: "currency:''"},
									             {field:'pymtMode'},
									             {field:'paidOn', cellFilter: "date:'yyyy-MM-dd'"},
									             {field:'updatedOn', cellFilter: "date:'yyyy-MM-dd'"}
									             ]
								 };
	$scope.es.foundReportsGridOptions.onRegisterApi = function(gridApi){
	      //set gridApi on scope
	      $scope.gridApi = gridApi;
	      gridApi.selection.on.rowSelectionChanged($scope,function(row){
	    	  if(row.isSelected){
	    		  $scope.es.foundReport = row.entity;
	    	  }
	      });
	    };

};

var saveFoundReport = function addFoundReport($scope, $http){	
	startAjax('SAVE_FOUND_REPORT', $scope);
	$http.post('server/unsecured/saveFoundReport.json', $scope.es.foundReport ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SAVE_FOUND_REPORT', $scope, data, status, headers, config);
    	//loadFoundReports($scope, $http);
    	$scope.es.foundReport = {};
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SAVE_FOUND_REPORT', $scope, data, status, headers, config);
    });
};

var loadFoundReports = function loadFoundReports($scope, $http){	
	
	startAjax('LOAD_FOUND_REPORTS', $scope);
	$http.post('server/unsecured/getAllFoundReports.json').
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_FOUND_REPORTS', $scope, data, status, headers, config);
    	$scope.es.allFoundReports = data.edgeResponse.responseData;
    	$scope.es.foundReportsGridOptions.data = $scope.es.allFoundReports;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_FOUND_REPORTS', $scope, data, status, headers, config);
    });
};
