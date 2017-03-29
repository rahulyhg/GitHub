
var initPayments = function initPayments($scope, $http){
	initializePaymentsGrid($scope, $http);
	$scope.es.balanceStr = "";
	$scope.es.payment = {};
	$scope.es.loadClients();
	$scope.es.loadActiveEmployees();
	$scope.es.loadPayments();
};

var initializePaymentsGrid = function initializeClientGrid($scope, $http){	
	$scope.es.paymentsGridOptions = { 
									enableFiltering: true, enableRowSelection: true,
									columnDefs: [{field:'paymentId'},
									             {field:'client.name', name:'Client'},
									             {field:'paidAmount', cellFilter: "currency:''"},
									             {field:'paidOn', cellFilter: "date:'yyyy-MM-dd'"},
									             {field:'pymtMode'},
									             {field:'details'},
									             {field:'status'}
									                 ]
								 };
	$scope.es.paymentsGridOptions.onRegisterApi = function(gridApi){
	      //set gridApi on scope
	      $scope.gridApi = gridApi;
	      gridApi.selection.on.rowSelectionChanged($scope,function(row){
	    	  if(row.isSelected){
	    		  $scope.es.payment = row.entity;
	    	  }
	      });
	    };

};

var addPayment = function addPayment($scope, $http){	
	startAjax('SAVE_PAYMENT', $scope);
	$scope.es.payment.client = $scope.es.client;
	$http.post('server/secured/savePayment.json', $scope.es.payment ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SAVE_PAYMENT', $scope, data, status, headers, config);
    	$scope.es.balanceStr = "";
    	$scope.es.payment = {};
    	$scope.es.loadClients();
    	$scope.es.loadPayments();
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SAVE_PAYMENT', $scope, data, status, headers, config);
    });
};


var approvePayment = function addPayment($scope, $http){	
	startAjax('APPROVE_PAYMENT', $scope);
	$scope.es.payment.client = $scope.es.client;
	$http.post('server/secured/approvePayment.json', $scope.es.payment ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('APPROVE_PAYMENT', $scope, data, status, headers, config);
    	$scope.es.balanceStr = "";
    	$scope.es.payment = {};
    	$scope.es.loadClients();
    	$scope.es.loadPayments();
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('APPROVE_PAYMENT', $scope, data, status, headers, config);
    });
};


var loadPayments = function loadPayments($scope, $http){	
	
	startAjax('LOAD_PAYMENTS', $scope);
	$http.post('server/secured/getAllPayments.json').
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_PAYMENTS', $scope, data, status, headers, config);
    	$scope.es.allPayments = data.edgeResponse.responseData;
    	$scope.es.paymentsGridOptions.data = $scope.es.allPayments;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_PAYMENTS', $scope, data, status, headers, config);
    });
};
