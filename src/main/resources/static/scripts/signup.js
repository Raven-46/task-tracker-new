function registerUser(event)
{
    event.preventDefault();
    var username = document.getElementById("username").value;
    var password =  document.getElementById("password").value; 
    var passwordconfirm = document.getElementById("passwordconfirm").value;
    var email = document.getElementById("email").value;
    var phone = document.getElementById("phone").value;
    var profession = document.getElementById("profession").value;
    console.log(username); 
    console.log(password);
    console.log(passwordconfirm);
    console.log(email);
    console.log(phone);
    console.log(profession);
    console.log('hiii');            
    if(password == passwordconfirm)
    {
        object = {username,password,email,phone,profession}; 
        console.log(object);
        console.log(JSON.stringify(object));
        fetch("/signup?mode=1", {
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
                alert('Username already taken!');
            else if(json==1)
                alert('Email already exists!');
            else if(json==2)
            {
                localStorage.setItem('username', username);
                localStorage.setItem('password', password);
                console.log('otp time');
                window.location.href = "http://localhost:8080/confirm-otp";
            }
        })
    }
    else
        alert('Passwords don\'t match!');
}        