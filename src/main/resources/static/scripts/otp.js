function sendOtp(event)
{   
    event.preventDefault();
    var otp = document.getElementById("otpinpt").value;
    console.log(otp);
    var username = localStorage.getItem('username');
    var password = localStorage.getItem('password');
    object = {username, password, otp};
    console.log(object);
    console.log(JSON.stringify(object));
    fetch("/confirm-otp", {
        method: "POST",
        body: JSON.stringify(object),
        headers: { 
            "Content-type": "application/json; charset=UTF-8"
        } 
    })
    .then(response => response.json())
    .then(json => {
        console.log(json);
        if(json==0)
        {
            alert('Timeout');
            window.location.href = "http://localhost:8080/signup";
        }
        else if(json==1)
            alert('Wrong OTP')
        else
        {
            localStorage.clear();
            localStorage.setItem('token', json.jwt);
            window.location.href = "http://localhost:8080/my/items";
        }
    })
}