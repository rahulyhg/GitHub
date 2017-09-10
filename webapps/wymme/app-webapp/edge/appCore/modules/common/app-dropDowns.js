
var initDropDowns = function($scope){

	$scope.es.genderList =  ['Female','Male'];
	
	$scope.es.bloodGroupList = ['A+','A-','AB+','AB-','B+','B-','O+','O-'];
	
	$scope.es.bodyTypeList = ['Slim','Athletic','Average','Healthy'];
	
	$scope.es.skinColorList = ['Fair','Brown','Black'];
	
	$scope.es.residingWithList = ['Family','Friends','Relatives','Others'];
	
	$scope.es.kundaliNadiList = ['Adya','Madhya','Antya'];
	
	$scope.es.kundaliCharanList = [1,2,3,4];
	
	$scope.es.kundaliGanList =  ['Dev','Manushya','Rakshas'];
	
	$scope.es.heightFtList = [3,4,5,6,7];
	
	$scope.es.heightInchList = [0,1,2,3,4,5,6,7,8,9,10,11];
	
	$scope.es.birthHrList = [0,1,2,3,4,5,6,7,8,9,10,11];
	
	$scope.es.birthMinList = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59];
	
	$scope.es.birthAPList = ['AM','PM'];
	
	$scope.es.siblingsList = [0,1,2,3,4,5,6];
	
	$scope.es.religionList = ['Buddhist','Christian','Hindu','Jain','Muslim','Sikh','Others'];
	
	$scope.es.castList = ['Brahmins','Kshatriyas','Pariah/Harijans','Sudras','Vaisyas','Others'];
	
	$scope.es.motherTongueList =  [
        'Adi','Angami','Ao','Assamese','Bengali','Bhili/Bhilodi','Bodo','Coorgi/Kodagu','Dimasa',
		'Dogri','English','Garo','Gondi','Gujarati','Halabi','Hindi','Ho','Kannada','Karbi/Mikir',
		'Kashmiri','Khandeshi','Kharia','Khasi','Khond/Kondh','Kisan','Kolami','Konkani','Konyak','Korku',
		'Koya','Kui','Kurukh','Ladakhi','Lotha','Lushai/Mizo','Maithili','Malayalam','Malto','Marathi','Meitei/Manipuri',
		'Miri/Mishing','Munda','Mundari','Nepali','Nissi/Dafla','Odia','Phom','Punjabi','Rabha','Santali','Savara',
		'Sema','Sindhi','Tamil','Tangkhul','Telugu','Thado','Tripuri','Tulu','Urdu','Others'];
	
	$scope.es.degreeTypeList = [
	     { server: 'Diploma', client:'Diploma' },
	     { server: 'Graduate', client:'Graduate - eg. BA/BCom/BSc' },
	     { server: 'Engineer', client:'Engineer - eg. BE/BE-MBA' },
	     { server: 'Doctor', client:'Doctor - eg. MD/MBBS/MS/BD/BAMS/BHMS' },
	     { server: 'Finance', client:'Finance - eg. CA/MBA' },
	     { server: 'PostGraduate', client:'Post Graduate - eg. MBA/MCA' },
	     { server: 'Phd', client:'PHD' },
	     { server: 'Others', client:'Others' }
	     ];
	
};
