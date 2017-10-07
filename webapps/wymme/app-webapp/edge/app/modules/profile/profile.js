	
var openMyProfile = function($scope, $http){
	startAjax('MY_PROFILE', $scope);
	$http.post('server/secured/profile/openMyProfile.json',"").
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('MY_PROFILE', $scope, data, status, headers, config);
    	$scope.es.editProfile = data.edgeResponse.responseData;
    	$scope.es.selectedPage = extendedMenuMap["UPDATE_PROFILE"];    	
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('MY_PROFILE', $scope, data, status, headers, config);
    });
};


var updateMyProfile = function updateMyProfile($scope, $http,updateForm){
	
	if(updateForm.$invalid){
		alert("Some input values are missing or invalid. Marked with red text or '**' sign. Please correct them and submit the form again.");
		return;
	}
	
	startAjax('UPDATE_PROFILE', $scope);
	$http.post('server/secured/profile/updateMyProfile.json', $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('UPDATE_PROFILE', $scope, data, status, headers, config);
    	openMyProfile($scope, $http);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('UPDATE_PROFILE', $scope, data, status, headers, config);
    });
};

var uploadImage = function uploadImage($scope, $http, imageType){
	startAjax('UPLOAD_IMAGE', $scope);
	
	var formData = new FormData();
	formData.append("file",$scope.es.uploadFile);
	formData.append("imageType",imageType);
	
	$http({
        method: 'POST',
        url: 'server/secured/uploadImage.json',
        headers: {
            'Content-Type': undefined
        },
        data: formData,
        transformRequest: function(data, headersGetterFunction) {
            return data; // do nothing! FormData is very good!
        }
        /*transformRequest: function (data, headersGetter) {
            var formData = new FormData();
            angular.forEach(data, function (value, key) {
                formData.append(key, value);
            });

            var headers = headersGetter();
            delete headers['Content-Type'];

            return formData;
        }*/
    })
    .success(function(data, status, headers, config) {
    	handleAjaxSuccess('UPLOAD_IMAGE', $scope, data, status, headers, config);
    	openMyProfile($scope, $http);
    })
    .error(function(data, status, headers, config) {
    	handleAjaxError('UPLOAD_IMAGE', $scope, data, status, headers, config);
    });
	
};

