window.onload=function(){
    $(".cool").append("<h1>The Coolest Chat Ever</h1>");

    $.ajax({
        type: "GET",
        url: "./hello.json"
    }).done(function(data) {
        $(".cool").append("<h1>"+ data.greeting +"</h1>");
    });
}