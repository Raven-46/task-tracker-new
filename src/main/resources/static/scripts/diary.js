var diary;

function addEntryPage()
{
	document.getElementById("diaryScreen").style.display = "block";
}
function closeEntryPage()
{
	document.getElementById("exampleInputDate").value = "";
	document.getElementById("dateMySQL").value = "";
	document.getElementById("exampleInputContent").value = "";
    document.getElementById("diaryScreen").style.display = "none";
}
function sendRequest(event)
{
	event.preventDefault();
    var date = document.getElementById("exampleInputDate").value;
    var content =  document.getElementById("exampleInputContent").value;
    console.log(date); 
	console.log(content);
	console.log('in send');
	
	object = {date, content};
	
	console.log('post');
	fetch("/diary/", {
		method: "POST",
		body: JSON.stringify(object),
		headers: { 
			"Content-type": "application/json; charset=UTF-8",
			"Authorization": `Bearer ${localStorage.getItem('token')}`
		}
	})
	.then(() => {location.reload();	}); 
	
}
fetch("/diary", { 
		method: "GET",
    	headers: { 
				"Content-type": "application/json; charset=UTF-8",
				"Authorization": `Bearer ${localStorage.getItem('token')}`
    		} 
		})      
.then(response => response.json())
.then(json => {
	diary = json;
	console.log(json);
})
.catch(()=>{
    alert("please sign in again");
	window.location.href = "http://localhost:8080/login";
})
.then(() => {renderPage(diary);});

function renderPage(diary)
{
	document.getElementById("usernameHead").appendChild(document.createTextNode(localStorage.getItem('username') + "\'s Diary"));

	var dates = [];
	for (e in diary)
	{
		if(!dates.includes(diary[e]["date"]))
		{
			dates.push(diary[e]["date"]);
		}
	}
	console.log(dates);
	dates.sort(function(a, b){return new Date(a) - new Date(b)});
	for(date in dates)
	{
		console.log(date);

		var card = document.createElement('div');
		card.classList.add('card', 'card-primary');

		var cardHeader = document.createElement('div')
		cardHeader.className = "card-header";

		var cardTitle = document.createElement("h3");
		cardTitle.className="card-title";

		var dateText = document.createTextNode(dates[date]);
		cardTitle.appendChild(dateText);

		cardHeader.appendChild(cardTitle);
/*		
		var cardTools = document.createElement('div');
		cardTools.className="card-tools";
		cardHeader.appendChild(cardTools);

		var expand = document.createElement('button');
		expand.type = "button";
		expand.classList.add("btn", "btn-tool");
		expand.setAttribute("data-card-widget", "maximise");
		var expandIcon = document.createElement('i');
		expandIcon.classList.add("fas", "fa-expand");
		expand.appendChild(expandIcon);

		var collapse = document.createElement('button');
		collapse.type = "button";
		collapse.classList.add("btn", "btn-tool");
		collapse.setAttribute("data-card-widget", "collapse");
		var collapseIcon = document.createElement('i');
		collapseIcon.classList.add("fas", "fa-minus");
		collapse.appendChild(collapseIcon);

		cardTools.appendChild(expand);
		cardTools.appendChild(collapse);          */

		var cardBody = document.createElement('div');
		cardBody.className="card-body applyScroll";

		card.appendChild(cardHeader);
		card.appendChild(cardBody);

		for(e in diary)
		{
			if(diary[e]["date"] == dates[date])
			{
				var entryBody = document.createElement('div');
				var entryText = document.createTextNode(diary[e]["content"] + "\n");
				entryBody.appendChild(entryText);
				cardBody.appendChild(entryBody);
			}
		}
		document.getElementById("container").appendChild(card);
	}
}