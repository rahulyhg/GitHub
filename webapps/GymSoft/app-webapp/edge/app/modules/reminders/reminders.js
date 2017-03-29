
var initReminders = function initReminders($scope, $http){
	initializeRemindersGrid($scope, $http);
	$scope.es.reminder = {};
	$scope.es.loadReminders();
};

var initializeRemindersGrid = function initializeClientGrid($scope, $http){	
	$scope.es.remindersGridOptions = { 
									enableFiltering: true, enableRowSelection: true,
									columnDefs: [{field:'clientId'},{field:'name'},{field:'phone'},{field:'emailId'},
									             {field:'balanceAmount', cellFilter: "currency:''"},
									             {field:'lastPaidOn', cellFilter: "date:'yyyy-MM-dd'"},
									             {field:'membershipEndDate', cellFilter: "date:'yyyy-MM-dd'"},
									             {field:'reminderOn', cellFilter: "date:'yyyy-MM-dd'"},
									             {field:'reminderAbout'}
									             ]
								 };
	$scope.es.remindersGridOptions.onRegisterApi = function(gridApi){
	      //set gridApi on scope
	      $scope.gridApi = gridApi;
	      gridApi.selection.on.rowSelectionChanged($scope,function(row){
	    	  if(row.isSelected){
	    		  $scope.es.reminder = row.entity;
	    	  }
	      });
	    };

	/*$scope.es.reminder.nextReminderOn.dateOptions = {
	    	    maxDate: new Date(2020, 5, 22),
	    	    minDate: new Date(),
	    	    startingDay: 1
	    	  };*/
};

var addReminder = function addReminder($scope, $http){	
	startAjax('SAVE_REMINDER', $scope);
	$http.post('server/secured/saveReminder.json', $scope.es.reminder ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SAVE_REMINDER', $scope, data, status, headers, config);
    	loadReminders($scope, $http);
    	$scope.es.reminder = {};
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SAVE_REMINDER', $scope, data, status, headers, config);
    });
};

var loadReminders = function loadReminders($scope, $http){	
	
	startAjax('LOAD_REMINDERS', $scope);
	$http.post('server/secured/getAllReminders.json').
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_REMINDERS', $scope, data, status, headers, config);
    	$scope.es.allReminders = data.edgeResponse.responseData;
    	$scope.es.remindersGridOptions.data = $scope.es.allReminders;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_REMINDERS', $scope, data, status, headers, config);
    });
};
