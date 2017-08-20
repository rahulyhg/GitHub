
function appInitFun($scope, $http, $modal, $log, $sce){

	var confirmationMsg = 'Are you sure? Press OK to confirm. ';
	
	// LOST REPORT 
	
	$scope.es.saveLostReport = function () {
		if(confirm(confirmationMsg)) saveLostReport($scope, $http);
	};

	// FOUND REPORT 
	
	$scope.es.initFoundReports = function () {
		initFoundReports($scope, $http);
	};
	
	$scope.es.saveFoundReport = function () {
		if(confirm(confirmationMsg))  saveFoundReport($scope, $http);
	};
	
	// TRACK REPORT 

	$scope.es.searchMatchingReqAsPerLRI = function () {
		searchMatchingReqAsPerLRI($scope, $http);
	};
	
	$scope.es.searchMatchingReqAsPerLFI = function () {
		searchMatchingReqAsPerLFI($scope, $http);
	};
	
	// TAG Creation

	$scope.es.saveTagCreation = function () {
		if(confirm(confirmationMsg))  saveTagCreation($scope, $http);
	};

	$scope.es.sendOTPForTagUpdate = function () {
		sendOTPForTagUpdate($scope, $http);
	};

	$scope.es.verifyOTPForTagUpdate = function () {
		verifyOTPForTagUpdate($scope, $http);
	};
	
};	
