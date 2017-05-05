
var initClients = function initClients($scope, $http){	
	initializeClientsGrid($scope, $http);
	$scope.es.client={};
	$scope.es.membership={};
	$scope.es.payment={};
	$scope.es.balanceStr="";
	$scope.es.loadClients();
	$scope.es.loadActivePackages();
	$scope.es.loadActiveEmployees();
};

var initializeClientsGrid = function initializeClientGrid($scope, $http){	
	$scope.es.clientsGridOptions = { 
									enableFiltering: true, enableRowSelection: true, multiSelect: false,
									columnDefs: [{field:'clientId'},{field:'name'},{field:'gender'},{field:'phone'},{field:'emailId'},{field:'profilePic'},
									             {field:'balanceAmount', cellFilter: "currency:''"},
									             {field:'lastPaidOn', cellFilter: "date:'yyyy-MM-dd'"},
									             {field:'membershipEndDate', cellFilter: "date:'yyyy-MM-dd'"},
									             {field:'updatedOn', cellFilter: "date:'yyyy-MM-dd'"}
									             ]
								 };
	$scope.es.clientsGridOptions.onRegisterApi = function(gridApi){
	      //set gridApi on scope
	      $scope.gridApi = gridApi;
	      gridApi.selection.on.rowSelectionChanged($scope,function(row){
	    	  if(row.isSelected){
	    		  $scope.es.client = row.entity;
	    	  }
	      });
	    };

};

/*var addEnquiry = function addEnquiry($scope, $http){	
	startAjax('SAVE_ENQUIRY', $scope);
	$http({
		method:'POST',
		url:'server/secured/saveEnquiry.json',
		headers: {
			   'Content-Type': undefined
			 },
		data: $scope.es.client
	}).then(function successCallback(response) {
    	handleAjaxSuccess('SAVE_ENQUIRY', $scope, response.data, response.status, response.headers, response.config);
    	$scope.es.membership={};
    	$scope.es.payment={};
    	$scope.es.balanceStr="";
    	$scope.es.client=response.data.edgeResponse.responseData;
    	loadClients($scope, $http);
    },function errorCallback(data) {
    	handleAjaxError('SAVE_ENQUIRY', $scope, data, status, headers, config);
    });
};*/


var addEnquiry = function addEnquiry($scope, $http){	
	startAjax('SAVE_ENQUIRY', $scope);
	$http.post('server/secured/saveEnquiry.json', $scope.es.client ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SAVE_ENQUIRY', $scope, data, status, headers, config);
    	$scope.es.membership={};
    	$scope.es.payment={};
    	$scope.es.balanceStr="";
    	$scope.es.client=data.edgeResponse.responseData;
    	$scope.es.fileUploadId=$scope.es.client.clientId;
    	$scope.es.fileUploadType="ProfilePicUpload";
    	$scope.es.fileUploadFile=$scope.es.profilePic;
    	loadClients($scope, $http);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SAVE_ENQUIRY', $scope, data, status, headers, config);
    });
};


var addClient = function addClient($scope, $http){	
	startAjax('SAVE_CLIENT', $scope);
	$scope.es.newClient = {};
	$scope.es.newClient.client=$scope.es.client;
	$scope.es.membership.packageName = $scope.es.membership.selectedPackage.name;
	$scope.es.newClient.membership=$scope.es.membership;	
	$scope.es.newClient.payment=$scope.es.payment;
	$http.post('server/secured/saveClient.json', $scope.es.newClient ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SAVE_CLIENT', $scope, data, status, headers, config);
    	$scope.es.membership={};
    	$scope.es.payment={};
    	$scope.es.balanceStr="";
    	$scope.es.client=data.edgeResponse.responseData.client;
    	generateInvoice($scope, $http);
    	loadClients($scope, $http);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SAVE_CLIENT', $scope, data, status, headers, config);
    });
};

var loadClients = function loadClients($scope, $http){	
	
	startAjax('LOAD_CLIENTS', $scope);
	$http.post('server/secured/getAllClients.json').
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_CLIENTS', $scope, data, status, headers, config);
    	$scope.es.allClients = data.edgeResponse.responseData;
    	$scope.es.clientsGridOptions.data = $scope.es.allClients;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_CLIENTS', $scope, data, status, headers, config);
    });
};


var generateInvoice = function generateInvoice($scope, $http){	
	
	startAjax('GENERATE_INVOICE', $scope);
	
	$http.post('server/secured/generateInvoice.json', $scope.es.client.clientId).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('GENERATE_INVOICE', $scope, data, status, headers, config);
    	$scope.es.invoice = data.edgeResponse.responseData;
    	$scope.es.openPopup('INVOICE_POPUP');
    	//$scope.es.hideLogo = true;
    	//$scope.es.selectedPage = extendedMenuMap["INVOICE_MENU"];
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('GENERATE_INVOICE', $scope, data, status, headers, config);
    });
};