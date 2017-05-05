
var initExpenses = function initExpenses($scope, $http){
	initializeExpensesGrid($scope, $http);
	$scope.es.expense = {};
	$scope.es.loadExpenses();
};

var initializeExpensesGrid = function initializeClientGrid($scope, $http){	
	$scope.es.expensesGridOptions = { 
									enableFiltering: true, enableRowSelection: true, multiSelect: false,
									columnDefs: [{field:'expenseId'},{field:'details'},
									             {field:'paidAmount', cellFilter: "currency:''"},
									             {field:'pymtMode'},
									             {field:'paidOn', cellFilter: "date:'yyyy-MM-dd'"},
									             {field:'updatedOn', cellFilter: "date:'yyyy-MM-dd'"}
									             ]
								 };
	$scope.es.expensesGridOptions.onRegisterApi = function(gridApi){
	      //set gridApi on scope
	      $scope.gridApi = gridApi;
	      gridApi.selection.on.rowSelectionChanged($scope,function(row){
	    	  if(row.isSelected){
	    		  $scope.es.expense = row.entity;
	    	  }
	      });
	    };

};

var addExpense = function addExpense($scope, $http){	
	startAjax('SAVE_EXPENSE', $scope);
	$http.post('server/secured/saveExpense.json', $scope.es.expense ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SAVE_EXPENSE', $scope, data, status, headers, config);
    	loadExpenses($scope, $http);
    	$scope.es.expense = {};
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SAVE_EXPENSE', $scope, data, status, headers, config);
    });
};

var loadExpenses = function loadExpenses($scope, $http){	
	
	startAjax('LOAD_EXPENSES', $scope);
	$http.post('server/secured/getAllExpenses.json').
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_EXPENSES', $scope, data, status, headers, config);
    	$scope.es.allExpenses = data.edgeResponse.responseData;
    	$scope.es.expensesGridOptions.data = $scope.es.allExpenses;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_EXPENSES', $scope, data, status, headers, config);
    });
};
