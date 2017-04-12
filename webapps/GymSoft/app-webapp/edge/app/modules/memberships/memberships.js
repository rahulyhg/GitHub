
var initMemberships = function initMemberships($scope, $http){
	initializeMembershipGrid($scope, $http);
	$scope.es.membership = {};
	$scope.es.loadClients();
	$scope.es.loadMemberships();
	$scope.es.loadActivePackages();
	$scope.es.loadActiveEmployees();
};

var initializeMembershipGrid = function initializeClientGrid($scope, $http){	
	$scope.es.membershipsGridOptions = { 
									enableFiltering: true, enableRowSelection: true,
									columnDefs: [{field:'membershipId'},{field:'client.name'},{field:'packageName'},
									             {field:'fromDate', cellFilter: "date:'yyyy-MM-dd'"},
									             {field:'toDate', cellFilter: "date:'yyyy-MM-dd'"},
									             {field:'totalAmount', cellFilter: "currency:''"},
									             {field:'discountAmount', cellFilter: "currency:''"},
									             {field:'effectiveAmount', cellFilter: "currency:''"},
									             {field:'collectionByName', name:'Collection By'},
									             {field:'updatedOn', cellFilter: "date:'yyyy-MM-dd'"}
									             ]
								 };
	$scope.es.membershipsGridOptions.onRegisterApi = function(gridApi){
	      //set gridApi on scope
	      $scope.gridApi = gridApi;
	      gridApi.selection.on.rowSelectionChanged($scope,function(row){
	    	  if(row.isSelected){
	    		  $scope.es.membership = row.entity;
	    	  }
	      });
	    };

};


var addMembership = function addMembership($scope, $http){	
	startAjax('SAVE_MEMBERSHIP', $scope);
	$scope.es.membership.client = $scope.es.client;
	$scope.es.membership.packageName = $scope.es.membership.selectedPackage.name;
	$http.post('server/secured/saveMembership.json', $scope.es.membership ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SAVE_MEMBERSHIP', $scope, data, status, headers, config);
    	$scope.es.membership = {};
    	loadMemberships($scope, $http);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SAVE_MEMBERSHIP', $scope, data, status, headers, config);
    });
};

var loadMemberships = function loadMemberships($scope, $http){	
	
	startAjax('LOAD_MEMBERSHIPS', $scope);
	$http.post('server/secured/getAllMemberships.json').
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_MEMBERSHIPS', $scope, data, status, headers, config);
    	$scope.es.allMemberships = data.edgeResponse.responseData;
    	$scope.es.membershipsGridOptions.data = $scope.es.allMemberships;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_MEMBERSHIPS', $scope, data, status, headers, config);
    });
};

var setToDate  = function setToDate($scope, $http) {
  date = moment($scope.es.membership.fromDate);
  date = date.add($scope.es.membership.selectedPackage.months,'months').format('YYYY-MM-DD');
  $scope.es.membership.toDate = date;
  $scope.es.membership.totalAmount=$scope.es.membership.selectedPackage.price;
  $scope.es.membership.paidOn = moment().format('YYYY-MM-DD');
};


var setEffective  = function setEffective($scope, $http) {
	amount =  $scope.es.membership.totalAmount - $scope.es.membership.discountAmount;
	if(amount < 0){
		alert("Invalid Amounts..");
		$scope.es.membership.effectiveAmount = "";
		$scope.es.membership.discountAmount = "";

	}else{
		$scope.es.membership.effectiveAmount = amount;
		$scope.es.balanceStr = " + " + amount;
	}
};
