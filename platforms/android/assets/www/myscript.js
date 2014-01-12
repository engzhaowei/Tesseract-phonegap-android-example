$(document).ready(function() {
                  
    // Set the language and YOUR api key here
    var languageFrom = "en";
    var apiKey = "yQ77nreQGy";
        



        // Album image picker
        $("#selectImageAlbum").click(function(){
                navigator.camera.getPicture(onSuccess, onFail, { quality: 50, 
    sourceType : Camera.PictureSourceType.PHOTOLIBRARY, 
        destinationType: Camera.DestinationType.FILE_URI }); 
} 

        });
        
function successCallback (result) { 
   alert(result); 
} 
function errorCallback (error) { 
   alert(error); 
} 

function onSuccess(imageURI) {
    alert(imageURI);
    ocrapiservice.convert( successCallback, errorCallback, imageURI, "en", "yQ77nreQGy" ); 
}

function onFail(message) {
    alert('Failed because: ' + message);
}
        
    
        
        
        
});