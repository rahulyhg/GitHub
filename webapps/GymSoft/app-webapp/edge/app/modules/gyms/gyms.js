

var initGyms = function initGyms($scope, $http){
	$scope.es.showAllGyms = false;
	$scope.es.gym = {};
	initializeGymsGrid($scope, $http);
	$scope.es.callIfLoggedInRole('ROLE_SUPER_ADMIN', $scope.es.loadGyms);
};

var initializeGymsGrid = function initializeClientGrid($scope, $http){	
	$scope.es.gymsGridOptions = { 
									enableFiltering: true, enableRowSelection: true,
									columnDefs: [{field:'gymId'},
									             {field:'name'},
									             {field:'phone'},
									             {field:'emailId'},
									             {field:'address'},
									             {field:'updatedOn', cellFilter: "date:'yyyy-MM-dd'"}
									             ]
								 };
	$scope.es.gymsGridOptions.onRegisterApi = function(gridApi){
	      //set gridApi on scope
	      $scope.gridApi = gridApi;
	      gridApi.selection.on.rowSelectionChanged($scope,function(row){
	    	  if(row.isSelected){
	    		  $scope.es.gym = row.entity;
	    	  }
	      });
	    };

};

var addGym = function addGym($scope, $http){	
	startAjax('ADD_GYM', $scope);
	$http.post('server/addGym.json', $scope.es.gym ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('ADD_GYM', $scope, data, status, headers, config);
    	loadGyms($scope, $http);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('ADD_GYM', $scope, data, status, headers, config);
    });
};

var updateGym = function updateGym($scope, $http){	
	startAjax('UPDATE_GYM', $scope);
	$http.post('server/superAdmin/updateGym.json', $scope.es.gym ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('UPDATE_GYM', $scope, data, status, headers, config);
    	loadGyms($scope, $http);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('UPDATE_GYM', $scope, data, status, headers, config);
    });
};

var loadGyms = function loadGyms($scope, $http){	
	
	startAjax('LOAD_GYMS', $scope);
	$http.post('server/superAdmin/getAllGyms.json').
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_GYMS', $scope, data, status, headers, config);
    	$scope.es.showAllGyms = true;
    	$scope.es.allGyms = data.edgeResponse.responseData;
    	$scope.es.gymsGridOptions.data = $scope.es.allGyms;
    }).
    error(function(data, status, headers, config) {
    	$scope.es.showAllGyms = false;
    	//handleAjaxError('LOAD_GYMS', $scope, data, status, headers, config);
    });
};