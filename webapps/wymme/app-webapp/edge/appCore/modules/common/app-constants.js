
var contextRoot = 'wymme';

var menuMap = {
	"1HOME" : {
		id : "HOME",
		title : "Home",
		templateUrl : "edge/app/modules/home/homeIndex.html",
		icon : "edge/appCore/modules/layout/images/home_icon.gif"
	},
	"2REGISTER" : {
		id : "REGISTER",
		title : "Register",
		templateUrl : "edge/app/modules/register/registerIndex.html",
		icon : ""
	},
	"3SEARCH" : {
		id : "SEARCH",
		title : "Search",
		templateUrl : "edge/app/modules/search/searchIndex.html",
		icon : "edge/appCore/modules/layout/images/proposal.png"
	},
	"4EVENTS" : {
		id : "EVENTS",
		title : "Events",
		templateUrl : "edge/app/modules/events/eventsIndex.html",
		icon : ""
	}
};


var extendedMenuMap = {
		"UPDATE_PROFILE" : {
			id : "UPDATEPROFILE",
			title : "Update Profile",
			templateUrl : "edge/app/modules/profile/updateProfileIndex.html",
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
	}
};