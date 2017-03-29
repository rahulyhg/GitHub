
var contextRoot = 'contextRoot';

var menuMap = {
	"1HOME" : {
		id : "HOME",
		title : "Home",
		templateUrl : "edge/app/modules/home/homeIndex.html",
		icon : "edge/appCore/modules/layout/images/home_icon.gif"
	},
	"2CLIENTS" : {
		id : "CLIENTS",
		title : "Clients",
		templateUrl : "edge/app/modules/clients/clientsIndex.html",
		icon : ""
	},
	"3MEMBERSHIPS" : {
		id : "MEMBERSHIPS",
		title : "Memberships",
		templateUrl : "edge/app/modules/memberships/membershipsIndex.html",
	},
	"4PAYMENTS" : {
		id : "PAYMENTS",
		title : "Payments",
		templateUrl : "edge/app/modules/payments/paymentsIndex.html",
		icon : ""
	},
	"5ATTENDANCE" : {
		id : "ATTENDANCE",
		title : "Attendance",
		templateUrl : "edge/app/modules/attendances/attendancesIndex.html",
		icon : ""
	},
	"6BULK_UPLOAD" : {
		id : "BULK_UPLOAD",
		title : "Bulk Upload",
		templateUrl : "edge/app/modules/upload/uploadIndex.html",
		icon : ""
	},
	"7EMPLOYEES" : {
		id : "EMPLOYEES",
		title : "Employees",
		templateUrl : "edge/app/modules/employees/employeesIndex.html",
		icon : ""
	},
	"8EXPENSES" : {
		id : "EXPENSES",
		title : "Expenses",
		templateUrl : "edge/app/modules/expenses/expensesIndex.html",
		icon : ""
	},
	"9PACKAGES" : {
		id : "PACKAGES",
		title : "Packages",
		templateUrl : "edge/app/modules/packages/packagesIndex.html",
		icon : ""
	},
	"10REPORTS" : {
		id : "REPORTS",
		title : "Reports",
		templateUrl : "edge/app/modules/reports/reportsIndex.html",
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
		"DASHBOARD" : {
			id : "DASHBOARD",
			title : "Dashboard",
			templateUrl : "edge/app/modules/home/dashboardIndex.html",
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
	"INVOICE_POPUP" : {
		id : "INVOICE_POPUP",
		title : "Invoice",
		templateUrl : "edge/app/modules/clients/clientsInvoice.html",
		/*size : "sm",*/
		controller : "edgeController"
	}
};