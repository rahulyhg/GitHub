

var initParents = function initParents($scope, $http){
	$scope.es.showAllParents = false;
	$scope.es.parent = {};
	initializeParentsGrid($scope, $http);
	$scope.es.callIfLoggedInRole('ROLE_SUPER_ADMIN', $scope.es.loadParents);
};

var initializeParentsGrid = function initializeClientGrid($scope, $http){	
	$scope.es.parentsGridOptions = { 
									enableFiltering: true, enableRowSelection: true,
									columnDefs: [{field:'parentId'},
									             {field:'name'},
									             {field:'phone'},
									             {field:'emailId'},
									             {field:'address'},
									             {field:'updatedOn', cellFilter: "date:'yyyy-MM-dd'"}
									             ]
								 };
	$scope.es.parentsGridOptions.onRegisterApi = function(gridApi){
	      //set gridApi on scope
	      $scope.gridApi = gridApi;
	      gridApi.selection.on.rowSelectionChanged($scope,function(row){
	    	  if(row.isSelected){
	    		  $scope.es.parent = row.entity;
	    	  }
	      });
	    };

};

var addParent = function addParent($scope, $http){	
	startAjax('ADD_PARENT', $scope);
	$http.post('server/addParent.json', $scope.es.parent ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('ADD_PARENT', $scope, data, status, headers, config);
    	loadParents($scope, $http);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('ADD_PARENT', $scope, data, status, headers, config);
    });
};

var updateParent = function updateParent($scope, $http){	
	startAjax('UPDATE_PARENT', $scope);
	$http.post('server/superAdmin/updateParent.json', $scope.es.parent ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('UPDATE_PARENT', $scope, data, status, headers, config);
    	loadParents($scope, $http);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('UPDATE_PARENT', $scope, data, status, headers, config);
    });
};

var loadParents = function loadParents($scope, $http){	
	
	startAjax('LOAD_PARENTS', $scope);
	$http.post('server/superAdmin/getAllParents.json').
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_PARENTS', $scope, data, status, headers, config);
    	$scope.es.showAllParents = true;
    	$scope.es.allParents = data.edgeResponse.responseData;
    	$scope.es.parentsGridOptions.data = $scope.es.allParents;
    }).
    error(function(data, status, headers, config) {
    	$scope.es.showAllParents = false;
    	//handleAjaxError('LOAD_PARENTS', $scope, data, status, headers, config);
    });
};