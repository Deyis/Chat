window.onload=function(){
    $(".cool").append("<h1>The Coolest Chat Ever</h1>");

    function getHelloContent() {
        $.ajax({
            type: "GET",
            url: "./hello.json"
        }).fail(function(error) {
            if(error.status == 401) {
                showLoginForm();
            }
        }).done(function(data) {
            $(".cool").append("<h1>"+ data.greeting +"</h1>");
        });
    }


    function showLoginForm() {
        $(".login-container").removeClass("invisible").addClass("visible");
    }

    function hideLoginForm() {
        $(".login-container").removeClass("visible").addClass("invisible");
        getHelloContent();
    }


    getHelloContent();

    $(".form-signin" ).submit(function( event ) {

        event.preventDefault();

        var $form = $( this ),
        user = $form.find( "input[name='user']" ).val(),
        pass = $form.find( "input[name='pass']" ).val(),
        url = $form.attr( "action" );

        var posting = $.post( url, { login: user, password: pass } );

        posting.done(function( data ) {
            hideLoginForm();
        });
    });




}