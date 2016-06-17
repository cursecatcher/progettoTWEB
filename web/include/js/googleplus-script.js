var auth2 = {};


jQuery(document).ready(function ($) {

});

/* This method sets up the sign-in listener after the client library loads.*/
function startApp() {
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
        
        $.post(
                "ServletController",
                {
                    action: "login-googleplus", 
                    id_token: authResponse.id_token,
                    access_token: authResponse.access_token
                },
                function (response) {
                    console.log(response); 
                }
        );
    } else if (authResult['error'] || authResult.currentUser.get().getAuthResponse() === null) {
        // There was an error, which means the user is not signed in.
        console.log('There was an error: ' + authResult['error']);
    }
}

/*  Calls the OAuth2 endpoint to disconnect the app for the user. */
function disconnect() {
    auth2.disconnect();
}

