
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

edgeApp.directive('file', function () {
    return {
        scope: {
            file: '='
        },
        link: function (scope, el, attrs) {
            el.bind('change', function (event) {
                var file = event.target.files[0];
                scope.file = file ? file : undefined;
                scope.$apply();
            });
        }
    };
});

// Other Functions

function appInitFun($scope, $http, $modal, $log, $sce){
	$scope.es.register = function (registerForm) {
		register($scope, $http, registerForm);
	};
	
	$scope.es.openMyProfile = function () {
		openMyProfile($scope, $http);
	};
	
	$scope.es.updateMyProfile = function (updateForm) {
		updateMyProfile($scope, $http, updateForm);
	};
	
	$scope.es.searchById = function () {
		searchById($scope, $http);
	};
	
	$scope.es.showEnlarged = function (showProfile) {
		showEnlarged($scope, $http, showProfile);
	};
	
	// COMMON
	
	$scope.es.showAlbum = function (profile) {
		showAlbum($scope, $http, profile);
	};

	$scope.es.uploadImage = function (imageType) {
		uploadImage($scope, $http, imageType);
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

var showEnlarged = function showEnlarged($scope, $http, showProfile){
	$scope.es.showProfile = showProfile;
	$scope.es.openPopup('ENLARGED_PROFILE_POPUP');
};

var showAlbum = function showAlbum($scope, $http, profile){
	$scope.es.slideshow.title = " Album : " + profile.profileId;
	$scope.es.slideshow.slides = profile.profileImages;
	$scope.es.openPopup('SLIDESHOW_POPUP');
};
