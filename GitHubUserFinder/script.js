function reqListener () {
    console.log(this.responseText);
}

function getGithubInfo(user) {
    //1. Create an instance of XMLHttpRequest class and send a GET request using it. The function should finally return the object(it now contains the response!)
    var url = "https://api.github.com/users/" + user;
    var conn = new XMLHttpRequest();
    conn.addEventListener("load", reqListener);
    conn.open('GET', url, false);
    // conn.onload = function() {
    //     if (conn.status >= 200 && conn.status < 400) {
    //         // Success!
    //         var resp = conn.responseText;
    //         alert(resp);
    //     } else {
    //         // We reached our target server, but it returned an error
    //         alert("broken");
    //     }
    // };
    conn.send();
    return conn;
}

function showUser(user) {
    //2. set the contents of the h2 and the two div elements in the div '#profile' with the user content
    $('#profile h2').html(user.login);
    $('#profile .avatar').html("<img src=" + user.avatar_url + ", alt = 'avatar'>");
    $('#profile .information').html(user.location);
    $('#profile').append("<div class='user_name'>" + user.name + "</div>");
    $('#profile').append("<a href=\"" + user.html_url + "\">" + user.html_url + "</a>");
    //<a href="https://www.w3schools.com/html/">Visit our HTML tutorial</a>

}

function noSuchUser(username) {
    //3. set the elements such that a suitable message is displayed
    $('#profile h2').html("failed: " + username);
    $('#profile .avatar').html("<img src='https://avatars0.githubusercontent.com/u/705559?s=200&amp;v=4', alt='@teeworlds', width='100', height='100'>");
    $('#profile .information').html("failed: " + username);
}

$(document).ready(function(){
    $(document).on('keypress', '#username', function(e){
        //check if the enter(i.e return) key is pressed
        if (e.which == 13) {
            //get what the user enters
            let username = $(this).val();
            //reset the text typed in the input
            $(this).val("");
            //get the user's information and store the respsonse
            response = getGithubInfo(username);
            console.log("response: " + response.statusText + "\n" + response.responseText);
            //if the response is successful show the user's details
            if (response.status == 200 || response.statusText == "OK") {
                showUser(JSON.parse(response.responseText));
                //else display suitable message
            } else {
                noSuchUser(username);
            }
        }
    })
});
