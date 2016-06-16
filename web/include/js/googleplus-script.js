var auth2 = {};


jQuery(document).ready(function ($) {
    
});

/* This method sets up the sign-in listener after the client library loads.*/
function startApp() {
    //gapi.load carica dinamicamente librerie js
    gapi.load('auth2', function () {
        gapi.client.load('plus', 'v1').then(function () {
            /* render dei bottoni */
            gapi.signin2.render('gConnect', {
                scope: 'https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/userinfo.email',
                fetch_basic_profile: false,
                theme: 'dark',
                width: 568,
                height: 60
            });
            /* inizializza il listener */
            gapi.auth2.init({fetch_basic_profile: false,
                scope: 'https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/userinfo.email'}).then(
                    function () {
                        console.log('init');
                        auth2 = gapi.auth2.getAuthInstance();
                        //auth2.isSignedIn.listen(updateSignIn);

                        if (auth2.isSignedIn.get()) {
                            console.log('sign out forzato');
                            auth2.signOut();
                        }

                        console.log('mi metto in attesa del click');
                        auth2.isSignedIn.listen(signinChanged);
                    });
        });
    });
}

/* sta in attesa del login */
var signinChanged = function (val) {
    if (val) {
        console.log('Signin state changed to ', val);
        onSignInCallback(gapi.auth2.getAuthInstance());
    }
};


/* Accede al profilo utente e recupera le informazioni necessarie al login */
function onSignInCallback(authResult) {
    if (authResult.isSignedIn.get()) {
        var authResponse = authResult.currentUser.get().getAuthResponse();
        var $form = $('#form-login-googleplus');

        $form.find('input[name="id_token"]').val(authResponse.id_token);
        $form.find('input[name="access_token"]').val(authResponse.access_token);
        

        gapi.client.plus.people.get({'userId': 'me'}).then(
                function (profile) {
                    $form.find('input[name="name"]').val(profile.result.name.givenName);
                    $form.find('input[name="surname"]').val(profile.result.name.familyName);
                    /* The URL of the person's profile photo. 
                     * To resize the image and crop it to a square, append the query string 
                     * ?sz=x, where x is the dimension in pixels of each side. */
                    $form.find('input[name="url-profile-img"]').val(profile.result.image.url);

                    for (var i = 0; i < profile.result.emails.length; i++) {
                        if (profile.result.emails[i].type === 'account')
                            $form.find('input[name="email"]').val(profile.result.emails[i].value);
                    }
                    
                    $form.submit();  
                },
                function (err) {
                    ; // da gestire più avanti magari #ancheno
                }
        );
    } else if (authResult['error'] || authResult.currentUser.get().getAuthResponse() === null) {
        // There was an error, which means the user is not signed in.
        // As an example, you can handle by writing to the console:
        console.log('There was an error: ' + authResult['error']);
        /*
         $('#authResult').append('Logged out');
         $('#authOps').hide('slow');
         $('#gConnect').show(); */
    }
}

/*  Calls the OAuth2 endpoint to disconnect the app for the user. */
function disconnect() {
    auth2.disconnect();
}

