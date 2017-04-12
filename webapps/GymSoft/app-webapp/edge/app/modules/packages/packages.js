
var initPackages = function initPackages($scope, $http){
	initializePackagesGrid($scope, $http);
	$scope.es.package = {};
	$scope.es.loadAllPackages();
};

var initializePackagesGrid = function initializeClientGrid($scope, $http){	
	$scope.es.packagesGridOptions = { 
									enableFiltering: true, enableRowSelection: true,
									columnDefs: [{field:'packageId'},{field:'name'},{field:'months'},
									             {field:'price', cellFilter: "currency:''"},{field:'maxDiscount', cellFilter: "currency:''"},
												 {field:'status'},
									             {field:'updatedOn', cellFilter: "date:'yyyy-MM-dd'"}
									             ]
								 };
	$scope.es.packagesGridOptions.onRegisterApi = function(gridApi){
	      //set gridApi on scope
	      $scope.gridApi = gridApi;
	      gridApi.selection.on.rowSelectionChanged($scope,function(row){
	    	  if(row.isSelected){
	    		  $scope.es.package = row.entity;
	    	  }
	      });
	    };
};

var addPackage = function addPackage($scope, $http){	
	startAjax('SAVE_PACKAGE', $scope);
	$http.post('server/secured/savePackage.json', $scope.es.package ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SAVE_PACKAGE', $scope, data, status, headers, config);
    	loadAllPackages($scope, $http);
    	$scope.es.package = {};
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SAVE_PACKAGE', $scope, data, status, headers, config);
    });
};

var loadAllPackages = function loadAllPackages($scope, $http){	
	
	startAjax('LOAD_ALL_PACKAGES', $scope);
	$http.post('server/secured/getAllPackages.json').
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_ALL_PACKAGES', $scope, data, status, headers, config);
    	$scope.es.allPackages = data.edgeResponse.responseData;
    	$scope.es.packagesGridOptions.data = $scope.es.allPackages;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_ALL_PACKAGES', $scope, data, status, headers, config);
    });
};

var loadActivePackages = function loadActivePackages($scope, $http){	
	
	startAjax('LOAD_ACTIVE_PACKAGES', $scope);
	$http.post('server/secured/getActivePackages.json').
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_ACTIVE_PACKAGES', $scope, data, status, headers, config);
    	$scope.es.allPackages = data.edgeResponse.responseData;
    	
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_ACTIVE_PACKAGES', $scope, data, status, headers, config);
    });
};

