
var edgeApp = angular.module('edgeApp', ['ui.bootstrap', 'ui.grid', 'ui.grid.resizeColumns', 'ui.grid.moveColumns', 'ui.grid.selection', 'ngSanitize']);

edgeApp.controller("edgeController", function($scope, $http, $modal, $log, $sce, $location) {
	
	$scope.es = {};
	$scope.es.location = $location;
	
	coreInitFun($scope, $http, $modal, $log, $sce);	
	coreInitVar($scope, $http, $modal, $log, $sce);
	
	appInitFun($scope, $http, $modal, $log, $sce);	
	appInitVar($scope, $http, $modal, $log, $sce);
	
});

function coreInitFun($scope, $http, $modal, $log, $sce){
	
	$scope.es.addAlertNative = function(type, heading, messages, errorHtml){
		$scope.es.alerts.push({ type : type, heading : heading, messages : messages, errorHtml: errorHtml });
	};
	
	$scope.es.addAlert = function(edgeResponse) {
		if(edgeResponse.header){
			$scope.es.alerts.push({ type : edgeResponse.type, header : edgeResponse.header, messages : edgeResponse.messages, errors : edgeResponse.errors, footer : edgeResponse.footer });
		}		
	};

	$scope.es.closeAlert = function(index) {
		$scope.es.alerts.splice(index, 1);
	};
	
	$scope.es.setSelectedMenuRaw = function(setSelectedMenuRaw) {	
		$scope.es.selectedPage = menuMap[setSelectedMenuRaw];
		if(!$scope.es.selectedPage){
			$scope.es.selectedPage = extendedMenuMap[setSelectedMenuRaw];
		}
		$scope.es.manageMenuOnSelection();
	};
	
	$scope.es.setSelectedMenu = function(setSelectedMenu) {	
		$scope.es.selectedPage = setSelectedMenu;
		$scope.es.manageMenuOnSelection();
	};
	
	$scope.es.isSelectedMenu = function(setSelectedMenu) {	
		return $scope.es.selectedPage.id === setSelectedMenu.id ;
	};
		
	$scope.es.openPopup = function(popupId) {
		$scope.es.closePopup(popupId);
		var popUpDetails = $scope.es.popupMap[popupId];
		$modal.open({
		      templateUrl: popUpDetails.templateUrl,
		      size: popUpDetails.size,
		      controller: 'PopupInstanceCtrl',
		      resolve: {
		    	  es : function () {
		            return $scope.es;
		          },
		          popupId : function () {
		            return popupId;
		          }
		        }
		    });
	};
	
	$scope.es.closePopup = function (popupId) {
		var toClose = $scope.es.openedPopups[popupId];
		if(toClose){
			toClose.dismiss('cancel');
		}
	};
	
	$scope.es.printDiv = function (divName) {
		var printContents = document.getElementById(divName).innerHTML;
		  var popupWin = window.open('', '_blank', 'width=300,height=300');
		  popupWin.document.open();
		  popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" href="style.css" /></head><body onload="window.print()">' + printContents + '</body></html>');
		  popupWin.document.close(); 
	};
	
	$scope.es.signUp = function () {
		signUp($scope, $http);
	};
	
	$scope.es.signIn = function () {
		signIn($scope, $http);
	};
	
	$scope.es.sendVerificationCode = function () {
		sendVerificationCode($scope, $http);
	};
	
	$scope.es.resetPassword = function () {
		resetPassword($scope, $http);
	};
	
	$scope.es.getLoggedInUser = function () {		
		startAjax(null, $scope);
		$http.post('server/unsecured/auth/getLoggedInUser.json').
	    success(function(data, status, headers, config) {
	    	handleAjaxSuccess(null, $scope, data, status, headers, config);
	    	if(data.edgeResponse){	    		
	    		$scope.es.loggedInUser = data.edgeResponse.responseData;
	    		$scope.es.loggedInUserId=$scope.es.loggedInUser.profileId;
	    	}
	    }).
	    error(function(data, status, headers, config) {
	    	handleAjaxError(null, $scope, data, status, headers, config);
	    });
	};
	

	$scope.es.callIfLoggedInRole = function (role, fun) {		
		startAjax(null, $scope);
		$http.post('server/unsecured/auth/getLoggedInRole.json').
	    success(function(data, status, headers, config) {
	    	handleAjaxSuccess(null, $scope, data, status, headers, config);
	    	if(data.edgeResponse){	    		
	    		$scope.es.loggedInRole = data.edgeResponse.responseData;
	    		if($scope.es.loggedInRole == role){
	    			fun();
	    		}
	    	}else{
	    		$scope.es.loggedInRole = "";
	    	}
	    }).
	    error(function(data, status, headers, config) {
	    	$scope.es.loggedInRole = "";
	    	handleAjaxError(null, $scope, data, status, headers, config);
	    });
	};
	
	$scope.es.manageMenuOnSelection = function () {
		if (isMobile()) {
			// Mobile
			$scope.es.showMenu=false;
		}else{
			$scope.es.showMenu=true;
		}
	};
	
};

edgeApp.controller('PopupInstanceCtrl', function ($scope, $modalInstance, es, popupId) {
	  
	  $scope.es = es;
	  $scope.es.openedPopups[popupId] = $modalInstance;	 
});

edgeApp.filter('unsafe', function($sce) {
	return function(val) {
		return $sce.trustAsHtml(val);
	};
});

function startAjax(popupId, $scope){	
	$scope.es.ajaxCount = $scope.es.ajaxCount + 1;	
}

function handleAjaxSilent (popupId, $scope, data, status, headers, config){
	$scope.es.ajaxCount = $scope.es.ajaxCount - 1;
};

function handleAjaxSuccess (popupId, $scope, data, status, headers, config){

	$scope.es.ajaxCount = $scope.es.ajaxCount - 1;
	if(data.edgeResponse){
		$scope.es.addAlert(data.edgeResponse);
	}else{
		$scope.es.alerts.push({ type : 'danger', heading : 'Some Error occured while processing. Apologies!', errorHtml : data });
	}	
	
	if(data.edgeResponse && data.edgeResponse.responseData == 'login'){
		$scope.es.alerts.push({ 
			type : 'info', 
			header : 'Please login to use this feature, Thank You!' 
		});
	}
};

function handleAjaxError (popupId, $scope, data, status, headers, config){

	$scope.es.ajaxCount = $scope.es.ajaxCount - 1;
	if(data.edgeResponse){
		$scope.es.addAlert(data.edgeResponse);
	}else{
		$scope.es.alerts.push({ type : 'danger', heading : 'Some Error occured while processing. Apologies!' });
	}	
};

function isMobile(){
	if (window.matchMedia('(max-device-width: 960px)').matches) {
		// Mobile
		return true;
	}else{
		return false;
	}
};
