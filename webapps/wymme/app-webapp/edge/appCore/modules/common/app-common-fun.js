
// Define App Specific Directives Here


edgeApp.directive('personalEdit', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es'
    },
    templateUrl: 'edge/app/modules/templates/personal_edit.html'
  };
});


edgeApp.directive('familyEdit', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es'
    },
    templateUrl: 'edge/app/modules/templates/family_edit.html'
  };
});


edgeApp.directive('secureEdit', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es'
    },
    templateUrl: 'edge/app/modules/templates/secure_edit.html'
  };
});


edgeApp.directive('profileConnection', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es'
    },
    templateUrl: 'edge/app/modules/templates/profile_connection.html'
  };
});


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
	
	$scope.es.showEnlarged = function (showProfile) {
		showEnlarged($scope, $http, showProfile);
	};
	
	
	// WALL FUNCTIONS

	$scope.es.initializeWall = function () {
		initializeWall($scope, $http);
	};
	
	$scope.es.removeFromWall = function (toRemove) {
		if (confirm("This Profile '" + toRemove + "' will be permanently removed from your Wall, Are you sure to continue?")) removeFromWall($scope, $http, toRemove);
	};

	$scope.es.sendConnectionRequest = function (profileTo) {
		if (confirm("This Profile '" + profileTo + "' will be sent connection request, if accepeted, you both can see each other's contact details, Are you sure to continue?")) sendConnectionRequest($scope, $http, profileTo);
	};
	
	// SEARCH FUNCTIONS

	$scope.es.showAlbum = function (profile) {
		showAlbum($scope, $http, profile);
	};
	
	$scope.es.initializeSearch = function () {
		initializeSearch($scope, $http);
	};
	
	$scope.es.searchById = function () {
		searchById($scope, $http);
	};
	
	$scope.es.loadRemovedProfiles = function () {
		loadRemovedProfiles($scope, $http);
	};
	
	$scope.es.undoRemoveFromWall = function (toAdd) {
		if (confirm("This Profile '" + toAdd + "' will be added back to your Wall, Are you sure to continue?")) undoRemoveFromWall($scope, $http, toAdd);
	};

	$scope.es.searchProfiles = function () {
		searchProfiles($scope, $http);
	};

	$scope.es.showContactDetails = function (profileId) {
		showContactDetails($scope, $http, profileId);
	};
	
	$scope.es.actionRequest = function (profileId, connectionStatus) {
		var message="";
		if(connectionStatus == 'Accepted'){
			message="Both you and this profile '" + profileId + "' would be able to see each others Contact Details, Are you sure to continue?";
		}else if(connectionStatus == 'Rejected'){
			message="None of you would be able to connect with each other hence on, Are you sure to continue?";
		};
		if (confirm(message)) actionRequest($scope, $http, profileId, connectionStatus);
	};
	
	// NOTIFICATIONS
	
	$scope.es.initializeNotifications = function () {
		initializeNotifications($scope, $http);
	};
	
	$scope.es.markNotificationAsRead = function (notificationId) {
		markNotificationAsRead($scope, $http, notificationId);
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


var showEnlarged = function showEnlarged($scope, $http, showProfile){
	$scope.es.showProfile = showProfile;
	$scope.es.openPopup('ENLARGED_PROFILE_POPUP');
};
