
function coreInitVar ($scope, $http, $modal, $log, $sce){
		
	$scope.es.ajaxCount = 0;
	$scope.es.name = "vinay";
	$scope.es.alerts = [];
	
	$scope.es.popupMap = popupMap;
	$scope.es.openedPopups = {};
	
	$scope.es.menuMap = menuMap;
	$scope.es.extendedMenuMap = extendedMenuMap;
	$scope.es.selectedPage = menuMap["HOME"];
		
	$scope.es.signUpForm = {};	
	$scope.es.signInForm = {};
	$scope.es.signUpForm.emailId = 'a@a.com';
	
	initDropDowns($scope);
	
	coreOnLoad ($scope, $http, $modal, $log, $sce);
}


var coreOnLoad = function($scope, $http, $modal, $log, $sce){
	// CORE - INIT TASKS
	if($scope.es.location.search().auth == 'fail'){
		$scope.es.openPopup('SIGN_IN_POP_UP');
	}
	$scope.es.getLoggedInUser();
	$scope.es.callIfLoggedInRole();
};
