
var initEmployees = function initEmployees($scope, $http){
	initializeEmployeesGrid($scope, $http);
	$scope.es.balanceStr = "";
	$scope.es.employee = {};
	$scope.es.loadClients();
	$scope.es.loadAllEmployees();
};

var initializeEmployeesGrid = function initializeClientGrid($scope, $http){	
	$scope.es.employeesGridOptions = { 
									enableFiltering: true, enableRowSelection: true,
									columnDefs: [{field:'employeeId'},{field:'name'},{field:'phone'},{field:'emailId'},
									             {field:'accountNumber'},{field:'ifscCode'},{field:'bankName'},{field:'branchAddress'},{field:'status'},
									             ]
								 };
	$scope.es.employeesGridOptions.onRegisterApi = function(gridApi){
	      //set gridApi on scope
	      $scope.gridApi = gridApi;
	      gridApi.selection.on.rowSelectionChanged($scope,function(row){
	    	  if(row.isSelected){
	    		  $scope.es.employee = row.entity;
	    	  }
	      });
	    };

};

var addEmployee = function addEmployee($scope, $http){	
	startAjax('SAVE_EMPLOYEE', $scope);
	$http.post('server/secured/saveEmployee.json', $scope.es.employee ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SAVE_EMPLOYEE', $scope, data, status, headers, config);
    	loadAllEmployees($scope, $http);
    	$scope.es.balanceStr = "";
    	$scope.es.employee = {};
    	$scope.es.loadClients();
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SAVE_EMPLOYEE', $scope, data, status, headers, config);
    });
};


var loadAllEmployees = function loadAllEmployees($scope, $http){	
	
	startAjax('LOAD_ALL_EMPLOYEES', $scope);
	$http.post('server/secured/getAllEmployees.json').
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_ALL_EMPLOYEES', $scope, data, status, headers, config);
    	$scope.es.allEmployees = data.edgeResponse.responseData;
    	$scope.es.employeesGridOptions.data = $scope.es.allEmployees;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_ALL_EMPLOYEES', $scope, data, status, headers, config);
    });
};

var loadActiveEmployees = function loadActiveEmployees($scope, $http){	
	
	startAjax('LOAD_ACTIVE_EMPLOYEES', $scope);
	$http.post('server/secured/getActiveEmployees.json').
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_ACTIVE_EMPLOYEES', $scope, data, status, headers, config);
    	$scope.es.allEmployees = data.edgeResponse.responseData;
    	
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_ACTIVE_EMPLOYEES', $scope, data, status, headers, config);
    });
};
