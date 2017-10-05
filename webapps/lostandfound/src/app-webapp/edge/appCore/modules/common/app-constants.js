
//var siteBaseUrl  = 'http://localhost:8080/rootPage';

// SERVERSIDE
var siteBaseUrl  = 'http://app4gym.in/';

var contextRoot = 'contextRoot';

var menuMap = {
	"1HOME" : {
		id : "HOME",
		title : "Home",
		templateUrl : "edge/app/modules/home/homeIndex.html",
		icon : "edge/appCore/modules/layout/images/home_icon.gif"
	},
	"2LOST" : {
		id : "LOST",
		title : "Lost",
		templateUrl : "edge/app/modules/lostReport/lostReportsIndex.html",
		icon : ""
	},
	"3FOUND" : {
		id : "FOUND",
		title : "Found",
		templateUrl : "edge/app/modules/foundReport/foundReportsIndex.html",
		icon : ""
	},
	"4TRACK" : {
		id : "TRACK",
		title : "Track",
		templateUrl : "edge/app/modules/trackReport/trackReportsIndex.html",
		icon : ""
	},
	"5DONATE" : {
		id : "DONATE",
		title : "Donate",
		templateUrl : "edge/app/modules/donate/donateIndex.html",
		icon : ""
	},
	"6ABOUT" : {
		id : "ABOUT",
		title : "About",
		templateUrl : "edge/app/modules/about/aboutIndex.html",
		icon : ""
	}
};


var extendedMenuMap = {
		"UPDATE_PROFILE" : {
			id : "UPDATEPROFILE",
			title : "Update Profile",
			templateUrl : "edge/app/modules/profile/updateProfileIndex.html",
			icon : ""
		},
		"TAG_CREATION" : {
			id : "TAGCREATION",
			title : "Tag Creation",
			templateUrl : "edge/app/modules/tagCreation/tagCreationIndex.html",
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