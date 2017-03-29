
var initAttendances = function initAttendances($scope, $http){
	initializeAttendancesGrid($scope, $http);
	$scope.es.attendance = {};
	$scope.es.loadActiveEmployees();
	$scope.es.loadAttendances();
};

var initializeAttendancesGrid = function initializeClientGrid($scope, $http){	
	$scope.es.attendancesGridOptions = { 
									enableFiltering: true, enableRowSelection: true,
									columnDefs: [{field:'attendanceId'}, {name: 'Employee', field:'employee.display'}, 
									             {field:'attendanceOn', cellFilter: "date:'yyyy-MM-dd'"},{field:'checkInHr'},{field:'checkOutHr'},{field:'hoursWorked'},{field:'details'},
									             {field:'updatedOn', cellFilter: "date:'yyyy-MM-dd'"}
									             ]
								 };
	$scope.es.attendancesGridOptions.onRegisterApi = function(gridApi){
	      //set gridApi on scope
	      $scope.gridApi = gridApi;
	      gridApi.selection.on.rowSelectionChanged($scope,function(row){
	    	  if(row.isSelected){
	    		  $scope.es.attendance = row.entity;
	    	  }
	      });
	    };

};

var addAttendance = function addAttendance($scope, $http){	
	startAjax('SAVE_ATTENDANCE', $scope);
	$http.post('server/secured/saveAttendance.json', $scope.es.attendance ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SAVE_ATTENDANCE', $scope, data, status, headers, config);
    	$scope.es.balanceStr = "";
    	$scope.es.attendance = {};
    	$scope.es.loadActiveEmployees();
    	$scope.es.loadAttendances();
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SAVE_ATTENDANCE', $scope, data, status, headers, config);
    });
};

var loadAttendances = function loadAttendances($scope, $http){	
	
	startAjax('LOAD_ATTENDANCES', $scope);
	$http.post('server/secured/getAllAttendances.json').
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_ATTENDANCES', $scope, data, status, headers, config);
    	$scope.es.allAttendances = data.edgeResponse.responseData;
    	$scope.es.attendancesGridOptions.data = $scope.es.allAttendances;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_ATTENDANCES', $scope, data, status, headers, config);
    });
};


var setAttendanceId = function setAttendanceId($scope, $http){	
	$scope.es.attendance.attendanceId = $scope.es.attendance.employee.employeeId + "-" + $scope.es.attendance.attendanceOn.toLocaleDateString();
	
};