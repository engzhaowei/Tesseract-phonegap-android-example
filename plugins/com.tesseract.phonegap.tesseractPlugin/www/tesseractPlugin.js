var tesseractPlugin = {
    createEvent: function(str, Callback) {
        cordova.exec(callback, function(err) {
		callback('Nothing to echo.');
			},
			"TesseractPlugin",			//Service: Mapped to native java.class
			"addTesseractPluginEntry",	//Action: The action name to call into on the native side
			[{							//An array of arguments 
			str							//This string should contain path to image
			}]);						          
     }
}
module.exports = tesseract;