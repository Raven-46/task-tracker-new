var friends;

function searchUsers()
{
	// Declare variables
	var input, filter, cards, body, txtValue;
	input = document.getElementById("userSearchInput");
	filter = input.value.toUpperCase();
	cards = document.getElementsByClassName("card");
	console.log(input);
	for(c=0; c<cards.length; c++)
	{
		console.log(c);
		console.log(cards[c]);
		body = cards[c].getElementsByClassName("card-body")[0];
		console.log(body);
        if (body)
        {
            txtValue = body.textContent || body.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1)
            {
                cards[c].style.display = "";
                exists=1;
            }
            else
            {
                cards[c].style.display = "none";
            }
        }
	}
}

function acceptReq(friendshipid)
{
    fetch(`/acceptfriend/${friendshipid}`, { 
        method: "PUT",
        headers: { 
                "Content-type": "application/json; charset=UTF-8",
                "Authorization": `Bearer ${localStorage.getItem('token')}`
            } 
        })
    .then(() => {location.reload();});
}

function rejectReq(friendshipid)
{
    fetch(`/rejectfriend/${friendshipid}`, { 
        method: "DELETE",
        headers: { 
                "Content-type": "application/json; charset=UTF-8",
                "Authorization": `Bearer ${localStorage.getItem('token')}`
            } 
        })
    .then(() => {location.reload();});
}

function unfriend(friendshipid)
{
    fetch(`/friend/${friendshipid}`, { 
        method: "DELETE",
        headers: { 
                "Content-type": "application/json; charset=UTF-8",
                "Authorization": `Bearer ${localStorage.getItem('token')}`
            } 
        })
    .then(() => {location.reload();});
}

function sendReq(username)
{
    var username2 = username;
    object = {username2};
    fetch("/addfriend", { 
        method: "POST",
        body: JSON.stringify(object),
        headers: { 
                "Content-type": "application/json; charset=UTF-8",
                "Authorization": `Bearer ${localStorage.getItem('token')}`
            } 
        })
    .then(() => {location.reload();});
}

fetch("/future-friends", { 
    method: "GET",
    headers: { 
            "Content-type": "application/json; charset=UTF-8",
            "Authorization": `Bearer ${localStorage.getItem('token')}`
        } 
    })      
.then(response => response.json())
.then(json => {
	console.log(json);
	friends = json;
})
.catch(()=>{
    alert("please sign in again");
    window.location.href = "http://localhost:8080/login";
})
.then(() => {renderPage();});

function renderPage()
{
	document.getElementById("usernameHead").appendChild(document.createTextNode(localStorage.getItem('username') + "\'s Friends"));
    friends.sort(function(a, b){return b.status - a.status});
    console.log(friends);
	for(f in friends)
	{
        var card = document.createElement('div');
        var cardHeader = document.createElement('div')
        cardHeader.className = "card-header";
        
        var cardBody = document.createElement('div');
        cardBody.className="card-body";

        var cardTitle = document.createElement("h3");
        cardTitle.className="card-title";

        var cardTools = document.createElement('div');
        cardTools.className = "card-tools";

        switch(friends[f]["status"])
        {
            case 3:
                {
                    console.log('3');
                    card.classList.add('card', 'card-danger');
                    cardTitle.appendChild(document.createTextNode("Pending Request"));
                    var acceptIcon = document.createElement('i');
                    acceptIcon.className = "fas fa-user-check";
                    acceptIcon.setAttribute('onclick', `acceptReq("${friends[f]["friendshipid"]}")`);
                    var deleteIcon = document.createElement('i');
                    deleteIcon.className = "fas fa-trash-alt";
                    deleteIcon.setAttribute('onclick', `rejectReq("${friends[f]["friendshipid"]}")`);
                    cardTools.appendChild(acceptIcon);
                    cardTools.appendChild(document.createTextNode(" "));
                    cardTools.appendChild(deleteIcon);
                    break;
                }
            case 2:
                {
                    console.log('2');
                    card.classList.add('card', 'card-success');
                    cardTitle.appendChild(document.createTextNode("Friend"));
                    var unfriendIcon = document.createElement('i');
                    unfriendIcon.className = "fas fa-user-minus";
                    unfriendIcon.setAttribute('onclick', `unfriend("${friends[f]["friendshipid"]}")`);
                    cardTools.appendChild(unfriendIcon);
                    break;
                }
            case 1:
                {
                    console.log('1');
                    card.classList.add('card', 'card-warning');
                    cardTitle.appendChild(document.createTextNode("Request Sent"));
                    break;
                }
            case 0:
                {
                    console.log('0');
                    card.classList.add('card', 'card-primary');
                    cardTitle.appendChild(document.createTextNode("User"));
                    var sendIcon = document.createElement('i');
                    sendIcon.className = "fas fa-user-plus";
                    sendIcon.setAttribute('onclick', `sendReq("${friends[f]["username"]}")`);
                    cardTools.appendChild(sendIcon);
                    break;
                }
        }

        var usernameText = document.createTextNode("USERNAME: " + friends[f]["username"]);
        cardBody.appendChild(usernameText);
        cardBody.appendChild(document.createElement('br'));
        var emailText = document.createTextNode(friends[f]["email"]);
        cardBody.appendChild(emailText); 
        cardHeader.appendChild(cardTitle);
        cardHeader.appendChild(cardTools);
        card.appendChild(cardHeader);
        card.appendChild(cardBody); 

        document.getElementById("container").appendChild(card);
		
	}
}