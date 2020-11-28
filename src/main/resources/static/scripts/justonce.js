function finishJustOnce(event)
{
    console.log('in here');
    event.preventDefault();
    var username = document.getElementById("username").value;
    var phone = document.getElementById("phone").value;
    var profession = document.getElementById("profession").value;
    var secret = localStorage.getItem('secret');
    object = {username, phone, profession, secret};
    console.log(object);
    console.log(localStorage.getItem('email'));
    fetch(`/just-once?email=${localStorage.getItem('email')}`, {
        method: "POST",
        body: JSON.stringify(object),
        headers: { 
            "Content-type": "application/json; charset=UTF-8"
        } 
    })
    .then(response => response.json())
    .then(json => {
        console.log(json.message);
        if(json.message)
        {
            if(json.message == "Username already taken")
                alert('Username already taken!');
            else if(json.message == "Invalid")
            {
                localStorage.clear();
                alert('Something went wrong :( Try again?');
                window.location.href = "http://localhost:8080/signup";
            }
            else if(json.message == "Signup already done")
            {
                localStorage.clear();
                alert('Signup already done');
                window.location.href = "http://localhost:8080/";
            }
        }
        else
        {
            console.log(json.jwt);
            localStorage.clear();
            localStorage.setItem('token', json.jwt);
            window.location.href = "http://localhost:8080/my/items";
        }
    })
}