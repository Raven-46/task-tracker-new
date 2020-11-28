var token;
function loginUser(event){
    event.preventDefault();
    var username = document.getElementById("username").value;
    var password =  document.getElementById("password").value;        
    console.log(username); 
    console.log(password);
    console.log('hi');            


    object = {username,password};
    
    console.log(object);
    console.log(JSON.stringify(object));
    fetch("/login", {
            method: "POST",
            body: JSON.stringify(object),
            headers: { 
                    "Content-type": "application/json; charset=UTF-8"
                }
            })
    .then(response => response.json()) 
    .then(json => {
        console.log(json.jwt);
        if(json.jwt=="" || json.jwt == undefined || json.jwt==null)
            alert('Incorrect username or password');
        else{
            localStorage.clear();
            localStorage.setItem('token', json.jwt);
            window.location.href = "http://localhost:8080/my/items";
        }
    })
}