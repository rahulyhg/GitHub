
var initCashTransactions = function initCashTransactions($scope, $http){
	initializeCashTransactionsGrid($scope, $http);
	getCurrentDeskCashBalance($scope, $http);
	$scope.es.cashTransaction = {};
	$scope.es.loadAllCashTransactions();
};

var initializeCashTransactionsGrid = function initializeClientGrid($scope, $http){	
	$scope.es.cashTransactionsGridOptions = { 
									enableFiltering: true, enableRowSelection: true, multiSelect: false,
									columnDefs: [{field:'cashTransactionId'},{field:'transactionType'},{field:'details'}, 
												 {field:'status'},
									             {field:'updatedOn', cellFilter: "date:'yyyy-MM-dd'"},
												 {field:'mode'},
												 {field:'amount', cellFilter: "currency:''"},
												 {field:'balance', cellFilter: "currency:''"}
									             ]
								 };
	$scope.es.cashTransactionsGridOptions.onRegisterApi = function(gridApi){
	      //set gridApi on scope
	      $scope.gridApi = gridApi;
	      gridApi.selection.on.rowSelectionChanged($scope,function(row){
	    	  if(row.isSelected){
	    		  $scope.es.cashTransaction = row.entity;
	    	  }
	      });
	    };
};

var getCurrentDeskCashBalance = function addCashTransaction($scope, $http){	
	startAjax('GET_CURRENT_DESK_CASH_BALANCE', $scope);
	$http.post('server/secured/getCurrentDeskCashBalance.json' ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('GET_CURRENT_DESK_CASH_BALANCE', $scope, data, status, headers, config);
    	$scope.es.deskCashBalance = data.edgeResponse.responseData;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('GET_CURRENT_DESK_CASH_BALANCE', $scope, data, status, headers, config);
    });
};

var addCashTransaction = function addCashTransaction($scope, $http){	
	startAjax('SAVE_CASH_TRANSACTION', $scope);
	$http.post('server/secured/saveCashTransaction.json', $scope.es.cashTransaction ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SAVE_CASH_TRANSACTION', $scope, data, status, headers, config);
    	loadAllCashTransactions($scope, $http);
    	getCurrentDeskCashBalance($scope, $http);
    	$scope.es.cashTransaction = {};
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SAVE_CASH_TRANSACTION', $scope, data, status, headers, config);
    });
};


var approveCashTransaction = function addPayment($scope, $http){	
	startAjax('APPROVE_CASH_TRANSACTION', $scope);
	$http.post('server/secured/approveCashTransaction.json', $scope.es.cashTransaction ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('APPROVE_CASH_TRANSACTION', $scope, data, status, headers, config);
    	$scope.es.cashTransaction = {};
    	$scope.es.loadAllCashTransactions();
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('APPROVE_CASH_TRANSACTION', $scope, data, status, headers, config);
    });
};

var loadAllCashTransactions = function loadAllCashTransactions($scope, $http){	
	
	startAjax('LOAD_ALL_CASH_TRANSACTIONS', $scope);
	$http.post('server/secured/getCashTransactions.json').
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_ALL_CASH_TRANSACTIONS', $scope, data, status, headers, config);
    	$scope.es.allCashTransactions = data.edgeResponse.responseData;
    	$scope.es.cashTransactionsGridOptions.data = $scope.es.allCashTransactions;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_ALL_CASH_TRANSACTIONS', $scope, data, status, headers, config);
    });
};

