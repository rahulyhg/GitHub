
var initAllTransactions = function initAllTransactions($scope, $http){
	initializeAllTransactionsGrid($scope, $http);
	getParentData($scope, $http);
	$scope.es.allTransaction = {};
	$scope.es.loadAllAllTransactions();
};

var initializeAllTransactionsGrid = function initializeClientGrid($scope, $http){
	if($scope.es.loggedInUser.appRole == 'PARENT_ADMIN'){
		$scope.es.allTransactionsGridOptions = { 
				enableFiltering: true, enableRowSelection: true, multiSelect: false,
				columnDefs: [{field:'allTransactionId'},{field:'transactionType'},{field:'details'}, 
							 {field:'status'},
				             {field:'updatedOn', cellFilter: "date:'yyyy-MM-dd'"},
							 {field:'mode'},
							 {field:'amount', cellFilter: "currency:''"},
							 {field:'allBalance', cellFilter: "currency:''"},
							 {field:'deskCashBalance', cellFilter: "currency:''"}
				             ]
			 };
	}else{
		$scope.es.allTransactionsGridOptions = { 
				enableFiltering: true, enableRowSelection: true, multiSelect: false,
				columnDefs: [{field:'allTransactionId'},{field:'transactionType'},{field:'details'}, 
							 {field:'status'},
				             {field:'updatedOn', cellFilter: "date:'yyyy-MM-dd'"},
							 {field:'mode'},
							 {field:'amount', cellFilter: "currency:''"},
							 {field:'deskCashBalance', cellFilter: "currency:''"}
				             ]
			 };
	};
	
	$scope.es.allTransactionsGridOptions.onRegisterApi = function(gridApi){
	      //set gridApi on scope
	      $scope.gridApi = gridApi;
	      gridApi.selection.on.rowSelectionChanged($scope,function(row){
	    	  if(row.isSelected){
	    		  $scope.es.allTransaction = row.entity;
	    	  }
	      });
	    };
	
};

var getParentData = function addAllTransaction($scope, $http){	
	startAjax('GET_CURRENT_DESK_CASH_BALANCE', $scope);
	$http.post('server/secured/getParentData.json' ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('GET_CURRENT_DESK_CASH_BALANCE', $scope, data, status, headers, config);
    	$scope.es.parentData = data.edgeResponse.responseData;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('GET_CURRENT_DESK_CASH_BALANCE', $scope, data, status, headers, config);
    });
};

var addAllTransaction = function addAllTransaction($scope, $http){	
	startAjax('SAVE_CASH_TRANSACTION', $scope);
	$http.post('server/secured/saveAllTransaction.json', $scope.es.allTransaction ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SAVE_CASH_TRANSACTION', $scope, data, status, headers, config);
    	loadAllAllTransactions($scope, $http);
    	getParentData($scope, $http);
    	$scope.es.allTransaction = {};
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SAVE_CASH_TRANSACTION', $scope, data, status, headers, config);
    });
};


var approveAllTransaction = function addPayment($scope, $http){	
	startAjax('APPROVE_CASH_TRANSACTION', $scope);
	$http.post('server/secured/approveAllTransaction.json', $scope.es.allTransaction ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('APPROVE_CASH_TRANSACTION', $scope, data, status, headers, config);
    	$scope.es.allTransaction = {};
    	$scope.es.loadAllAllTransactions();
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('APPROVE_CASH_TRANSACTION', $scope, data, status, headers, config);
    });
};

var loadAllAllTransactions = function loadAllAllTransactions($scope, $http){	
	
	startAjax('LOAD_ALL_CASH_TRANSACTIONS', $scope);
	$http.post('server/secured/getAllTransactions.json').
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_ALL_CASH_TRANSACTIONS', $scope, data, status, headers, config);
    	$scope.es.allAllTransactions = data.edgeResponse.responseData;
    	$scope.es.allTransactionsGridOptions.data = $scope.es.allAllTransactions;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_ALL_CASH_TRANSACTIONS', $scope, data, status, headers, config);
    });
};

