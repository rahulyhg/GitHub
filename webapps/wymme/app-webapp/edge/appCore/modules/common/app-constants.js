
var contextRoot = 'rootPage';

var menuMap = {
	"1HOME" : {
		id : "HOME",
		title : "Home",
		templateUrl : "edge/app/modules/home/homeIndex.html",
		icon : "fa-home"
	},
	"2REGISTER" : {
		id : "REGISTER",
		title : "Register",
		templateUrl : "edge/app/modules/register/registerIndex.html",
		icon : "fa-user"
	},
	"3SEARCH" : {
		id : "SEARCH",
		title : "Search",
		templateUrl : "edge/app/modules/search/searchIndex.html",
		icon : "fa-search"
	},
	"4TESTIMONIALS" : {
		id : "TESTIMONIALS",
		title : "Testimonials",
		templateUrl : "edge/app/modules/testimonials/testimonialsIndex.html",
		icon : "fa-comments-o"
	}
	
};


var extendedMenuMap = {
		"UPDATE_PROFILE" : {
			id : "UPDATEPROFILE",
			title : "Update Profile",
			templateUrl : "edge/app/modules/profile/updateProfileIndex.html",
			icon : ""
		},

		"WALL_CRITERIA" : {
			id : "WALL_CRITERIA",
			title : "Update Wall Criteria",
			templateUrl : "edge/app/modules/wall/wallCriteriaIndex.html",
			icon : ""
		},
		
		"NOTIFICATIONS" : {
			id : "NOTIFICATIONS",
			title : "Notifications",
			templateUrl : "edge/app/modules/notifications/notificationsIndex.html",
			icon : ""
		},
		
		"EVENTS" : {
			id : "EVENTS",
			title : "Events",
			templateUrl : "edge/app/modules/events/eventsIndex.html",
			icon : ""
		}
};

var popupMap = {
	"SIGN_IN_POP_UP" : {
		id : "SIGN_IN_POP_UP",
		title : "Sign In",
		templateUrl : "edge/core/modules/auth/signInPopup.html",
		/*size : "sm",*/
		controller : "edgeController"		
	},
	"SIGN_UP_POP_UP" : {
		id : "SIGN_UP_POP_UP",
		title : "Sign Up",
		templateUrl : "edge/core/modules/auth/signUpPopup.html",
		/*size : "sm",*/
		controller : "edgeController"
	},
	"FORGOT_PWD_POP_UP" : {
		id : "FORGOT_PWD_POP_UP",
		title : "Forgot Password",
		templateUrl : "edge/core/modules/auth/forgotPasswordPopup.html",
		/*size : "sm",*/
		controller : "edgeController"
	},
	"ENLARGED_PROFILE_POPUP" : {
		id : "ENLARGED_PROFILE_POPUP",
		title : "Enlarged Profile",
		templateUrl : "edge/app/modules/profile/enlargedProfilePopup.html",
		size : "lg",
		controller : "edgeController"
	},
	"SECURE_PROFILE_POPUP" : {
		id : "SECURE_PROFILE_POPUP",
		title : "Profile - Contact Details",
		templateUrl : "edge/app/modules/profile/secureProfilePopup.html",
		/*size : "sm",*/
		controller : "edgeController"
	},
	"SLIDESHOW_POPUP" : {
		id : "SLIDESHOW_POPUP",
		title : "Slideshow",
		templateUrl : "edge/core/modules/utilities/slideshowPopup.html",
		/*size : "sm",*/
		controller : "edgeController"
	}
	
	
};