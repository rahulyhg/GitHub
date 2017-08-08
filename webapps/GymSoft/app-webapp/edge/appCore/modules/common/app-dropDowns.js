
var initDropDowns = function($scope){

	$scope.es.genderList =  ['Female','Male'];
	
	$scope.es.pymtModeList =  ['Card','Cheque','Paytm','Online','Cash','Other'];
	
	$scope.es.statusList =  ['Draft','Active','Inactive'];
	
	$scope.es.allTransactionType =  [ 'Desk To Bank','Desk Expense','Credit To Desk'];
	
	$scope.es.allTransactionTypeAdmin =  [ 'Desk To Bank','Desk Expense','Credit To Desk'
	                                      	
	                                      ,'Bank To Desk', 'Bank Expense', 'Credit To Bank'];
	
};
