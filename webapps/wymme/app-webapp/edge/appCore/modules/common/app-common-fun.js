
// Define App Specific Directives Here

edgeApp.directive('profileSecure', function() {
  return {
    scope: {
    	profile: '=profile'
    },
    templateUrl: 'edge/app/modules/templates/profile_secure.html'
  };
});

edgeApp.directive('profileNonSecure', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es'
    },
    templateUrl: 'edge/app/modules/templates/profile_non_secure.html'
  };
});

// Other Functions

function appInitFun($scope, $http, $modal, $log, $sce){
	$scope.es.register = function () {
		register($scope, $http);
	};
	
	$scope.es.openMyProfile = function () {
		openMyProfile($scope, $http);
	};
	
	$scope.es.updateMyProfile = function () {
		updateMyProfile($scope, $http);
	};
	
	$scope.es.searchById = function () {
		searchById($scope, $http);
	};
	
	// WALL FUNCTIONS

	$scope.es.initializeWall = function () {
		initializeWall($scope, $http);
	};
	
	$scope.es.removeFromWall = function (toRemove) {
		if (confirm("This Profile '" + toRemove + "' will be permanently removed from your Wall, Are you sure to continue?")) removeFromWall($scope, $http, toRemove);
	};
	
	// SEARCH FUNCTIONS

	$scope.es.loadRemovedProfiles = function () {
		loadRemovedProfiles($scope, $http);
	};
	
	$scope.es.undoRemoveFromWall = function (toAdd) {
		if (confirm("This Profile '" + toAdd + "' will be added back to your Wall, Are you sure to continue?")) undoRemoveFromWall($scope, $http, toAdd);
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
