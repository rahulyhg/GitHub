
function appInitFun($scope, $http, $modal, $log, $sce){
	
	var confirmationMsg = 'Press OK to proceed or Cancel to abort...';
	
	$scope.es.register = function () {
		register($scope, $http);
	};

	// PACKAGES
	
	$scope.es.initPackages = function () {
		initPackages($scope, $http);
	};
	
	$scope.es.loadAllPackages = function () {
		loadAllPackages($scope, $http);
	};

	$scope.es.loadActivePackages = function () {
		loadActivePackages($scope, $http);
	};
	
	$scope.es.savePackage = function () {
		if(confirm(confirmationMsg))  addPackage($scope, $http);
	};
	
	// PARENTS
	
	$scope.es.initParents = function () {
		initParents($scope, $http);
	};
	
	$scope.es.loadParents = function () {
		loadParents($scope, $http);
	};
	
	$scope.es.addParent = function () {
		if(confirm(confirmationMsg))  addParent($scope, $http);
	};
	
	$scope.es.updateParent = function () {
		if(confirm(confirmationMsg))  updateParent($scope, $http);
	};
	
	// CLIENTS
	$scope.es.initClients = function () {
		initClients($scope, $http);
	};
	
	$scope.es.loadClients = function () {
		loadClients($scope, $http);
	};

	$scope.es.saveEnquiry = function () {
		if(confirm(confirmationMsg))  addEnquiry($scope, $http);
	};
	
	$scope.es.saveClient = function () {
		if(confirm(confirmationMsg))  addClient($scope, $http);
	};
	
	$scope.es.setToDate = function () {
		setToDate($scope, $http);
	};
	
	$scope.es.generateInvoice = function () {
		generateInvoice($scope, $http);
	};
	
	// MEDMBERSHIP
	$scope.es.initMemberships = function () {
		initMemberships($scope, $http);
	};
	
	$scope.es.loadMemberships = function () {
		loadMemberships($scope, $http);
	};
	
	$scope.es.saveMembership = function () {
		if(confirm(confirmationMsg))  addMembership($scope, $http);
	};
	
	$scope.es.setEffective = function () {
		setEffective($scope, $http);
	};
	
	// PAYMENTS
	
	$scope.es.initPayments = function () {
		initPayments($scope, $http);
	};
	
	$scope.es.loadPayments = function () {
		loadPayments($scope, $http);
	};
	
	$scope.es.savePayment = function () {
		if(confirm(confirmationMsg))  addPayment($scope, $http);
	};

	$scope.es.approvePayment = function () {
		if(confirm(confirmationMsg))  approvePayment($scope, $http);
	};
	
	// EXPENSES
	
	$scope.es.initExpenses = function () {
		initExpenses($scope, $http);
	};
	
	$scope.es.loadExpenses = function () {
		loadExpenses($scope, $http);
	};
	
	$scope.es.saveExpense = function () {
		if(confirm(confirmationMsg))  addExpense($scope, $http);
	};
	
	// EMPLOYEES
	
	$scope.es.initEmployees = function () {
		initEmployees($scope, $http);
	};

	$scope.es.loadAllEmployees = function () {
		loadAllEmployees($scope, $http);
	};
	
	$scope.es.loadActiveEmployees = function () {
		loadActiveEmployees($scope, $http);
	};
	
	$scope.es.saveEmployee = function () {
		if(confirm(confirmationMsg))  addEmployee($scope, $http);
	};
	
	// ATTENDANCE
	
	$scope.es.initAttendances = function () {
		initAttendances($scope, $http);
	};
	
	$scope.es.loadAttendances = function () {
		loadAttendances($scope, $http);
	};
	
	$scope.es.saveAttendance = function () {
		if(confirm(confirmationMsg))  addAttendance($scope, $http);
	};
	
	$scope.es.setAttendanceId = function () {
		setAttendanceId($scope, $http);
	};
	
	// REMINDERS
	
	$scope.es.initReminders = function () {
		initReminders($scope, $http);
	};
	
	$scope.es.loadReminders = function () {
		loadReminders($scope, $http);
	};
	
	$scope.es.saveReminder = function () {
		if(confirm(confirmationMsg))  addReminder($scope, $http);
	};
	
	// REPORTS
	
	$scope.es.initReports = function () {
		initReports($scope, $http);
	};

	$scope.es.getMembershipsReport = function () {
		getMembershipsReport($scope, $http);
	};
	
	$scope.es.getPaymentsReport = function () {
		getPaymentsReport($scope, $http);
	};

	$scope.es.getAttendancesReport = function () {
		getAttendancesReport($scope, $http);
	};
	
	// OTHERS
	$scope.es.openMyProfile = function () {
		openMyProfile($scope, $http);
	};
	
	$scope.es.updateMyProfile = function () {
		if(confirm(confirmationMsg))  updateMyProfile($scope, $http);
	};
	
	$scope.es.searchById = function () {
		searchById($scope, $http);
	};
};	
	
var openMyProfile = function($scope, $http){
	startAjax('MYPROFILE', $scope);
	$http.post('server/secured/profile/openMyProfile.json',"").
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('MYPROFILE', $scope, data, status, headers, config);
    	$scope.es.editProfile = data.edgeResponse.responseData;
    	$scope.es.selectedPage = extendedMenuMap["UPDATE_PROFILE"];    	
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('MYPROFILE', $scope, data, status, headers, config);
    });
};