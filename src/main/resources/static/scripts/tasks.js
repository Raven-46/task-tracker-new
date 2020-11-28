var tasks;

function searchTasks()
{
	// Declare variables
	var input, filter, cards, uList, li, task, i, txtValue;
	input = document.getElementById("taskSearchInput");
	filter = input.value.toUpperCase();
	cards = document.getElementsByClassName("category-container");
	console.log(input);
	for(c=0; c<cards.length; c++)
	{
		var exists = 0;
		console.log(c);
		console.log(cards[c]);
		uList = cards[c].getElementsByTagName("ul")[0];
		li = uList.getElementsByTagName("li");
		// Loop through all table rows, and hide those who don't match the search query
		for (i = 0; i < li.length; i++)
		{
			task = li[i].getElementsByTagName("span")[0];
			if (task)
			{
				txtValue = task.textContent || task.innerText;
				if (txtValue.toUpperCase().indexOf(filter) > -1)
				{
					cards[c].style.display = "";
					li[i].style.display = "";
					exists=1;
				}
				else
				{
					li[i].style.display = "none";
				}
			}
		}
		if(exists==0)
		{
			console.log('no table');
			cards[c].style.display = "none";
		}
	}
}

function toggleRemindBefore(){
	console.log('in toggle');
	var remindBefore = document.getElementById("remindBefore");
	console.log(remindBefore.style.display);
	if(remindBefore.style.display=="none")
	{
		console.log('changing to block');
		remindBefore.style.display = "block";
	}
	else
	{
		console.log('changing to none');
		remindBefore.style.display = "none";
	}
}

function addCategoryPage()
{
	var title =document.createElement('h3');
	title.className = "card-title";
	title.setAttribute('id', 'action-title');
	title.appendChild(document.createTextNode("Add Category with Task"));
	document.getElementById("closebtncont").appendChild(title);
	document.getElementById("del").style.display = "none";
	document.getElementById("taskScreen").style.display = "block";
}

function addTaskPage(category)
{
	var title =document.createElement('h3');
	title.className = "card-title";
	title.setAttribute('id', 'action-title');
	title.appendChild(document.createTextNode("Add Task"));
	document.getElementById("closebtncont").appendChild(title);
	document.getElementById("del").style.display = "none";
	document.getElementById("exampleInputCategory").value = category;
	document.getElementById("taskScreen").style.display = "block";
}

function editTaskPage(id, category, name, date, required, remindBefore)
{
	var title =document.createElement('h3');
	title.className = "card-title";
	title.setAttribute('id', 'action-title');
	title.appendChild(document.createTextNode("Edit Task"));
	document.getElementById("closebtncont").appendChild(title);
	document.getElementById("exampleInputTaskName").value = name;
	document.getElementById("taskId").value = id;
	document.getElementById("exampleInputCategory").value = category;
	document.getElementById("exampleInputTime").value = date;
	document.getElementById("timeMySQL").value = date;
	document.getElementById("reminderReq").checked = required;
	var units;
	console.log(remindBefore);
	if(remindBefore.includes('mins'))
	{
		console.log('mins');
		document.getElementById("timeNumber").value = parseInt(remindBefore.substring(0, remindBefore.length-5));
		units = "mins";
	}
	else if(remindBefore.includes('hours'))
	{
		console.log('hours');
		document.getElementById("timeNumber").value = parseInt(remindBefore.substring(0, remindBefore.length-6));
		units = "hours";
	}
	else if(remindBefore.includes('days'))
	{
		console.log('days');
		document.getElementById("timeNumber").value = parseInt(remindBefore.substring(0, remindBefore.length-5));
		units = "days";
	}
	else if(remindBefore.includes('weeks'))
	{
		console.log('weeks');
		document.getElementById("timeNumber").value = parseInt(remindBefore.substring(0, remindBefore.length-6));
		units = "weeks";
	}
	else if(remindBefore.includes('months'))
	{
		console.log('months');
		document.getElementById("timeNumber").value = parseInt(remindBefore.substring(0, remindBefore.length-7));
		units = "months";
	}
	var mySelect = document.getElementById('units');
	for(var i, j = 0; i = mySelect.options[j]; j++) {
		if(i.value == units) {
			mySelect.selectedIndex = j;
			break;
		}
	}
	document.getElementById("taskScreen").style.display = "block";
}
function closeTaskPage()
{
	document.getElementById("action-title").remove();
	document.getElementById("exampleInputTaskName").value = "";
	document.getElementById("taskId").value = "";
	document.getElementById("exampleInputCategory").value = "";
	document.getElementById("exampleInputTime").value = "";
	document.getElementById("timeMySQL").value = "";
	document.getElementById("taskScreen").style.display = "none";
}

function openPicker()
{
	let simplepicker = new SimplePicker({
	zIndex: 10
	});
	simplepicker.open();
	// $eventLog.innerHTML += '\n\n';
	simplepicker.on('submit', (date, readableDate) => {
	document.getElementById("exampleInputTime").value = readableDate;
	console.log(date);
	console.log(readableDate);
	var newDate = new Date(date.getTime() + 5.5*60*60*1000);
	newDate = newDate.getUTCFullYear() + '-' +
		('00' + (newDate.getUTCMonth()+1)).slice(-2) + '-' +
		('00' + newDate.getUTCDate()).slice(-2) + ' ' + 
		('00' + newDate.getUTCHours()).slice(-2) + ':' + 
		('00' + newDate.getUTCMinutes()).slice(-2) + ':' + 
		('00' + newDate.getUTCSeconds()).slice(-2);
	document.getElementById("timeMySQL").value = newDate;
	console.log(newDate);
	});
}
function setDone(id)
{
	var line = document.getElementById("taskLine" + id);
	line.classList.toggle("done");
	fetch(`/tasks/${id}/done`, {
		method: "PUT",
		headers: {
			"Content-type": "application/json; charset=UTF-8",
        	"Authorization": `Bearer ${localStorage.getItem('token')}`
		}
	})
	.then(() => {location.reload();	});
}
function sendRequest(event)
{
	event.preventDefault();
	var id = document.getElementById("taskId").value
    var category = document.getElementById("exampleInputCategory").value;
    var name =  document.getElementById("exampleInputTaskName").value;
    var date = document.getElementById("timeMySQL").value;
	var required = document.getElementById("reminderReq").checked;
	var remindBefore = document.getElementById("timeNumber").value;
	remindBefore = remindBefore.concat(" ", document.getElementById("units").value);

    console.log(id); 
	console.log(category);
	console.log(name);
	console.log(date);
	console.log(required);  
	console.log(remindBefore);
	console.log(document.getElementById("units").value);
	
	object = {category, name, date, remindBefore, required};
	
	if(id == "")
	{
		console.log('post');
		fetch("/tasks", { 
      		method: "POST",
      		body: JSON.stringify(object),
      		headers: { 
				"Content-type": "application/json; charset=UTF-8",
				"Authorization": `Bearer ${localStorage.getItem('token')}`
    		}  
  		})
  		.then(() => {location.reload();	});
	}
	else
	{
		console.log('post');
		fetch(`/tasks/${id}`, {
      		method: "PUT",
      		body: JSON.stringify(object),
      		headers: { 
				"Content-type": "application/json; charset=UTF-8",
				"Authorization": `Bearer ${localStorage.getItem('token')}`
    		}
  		})
  		.then(() => {location.reload();	});
	}
}
fetch("/tasks", {
	method: "GET",
	headers: { 
		"Content-type": "application/json; charset=UTF-8",
		"Authorization": `Bearer ${localStorage.getItem('token')}`
	}
})   
.then(response => response.json())
.then(json => {
	tasks = json;
	console.log(json);
})
.catch(()=>{
    alert("please sign in again");
	window.location.href = "http://localhost:8080/login";
})
.then(() => {renderPage(tasks);});

function renderPage(tasks)
{
//	document.addEventListener('DOMContentLoaded',()=>{
	document.getElementById("usernameHead").appendChild(document.createTextNode(localStorage.getItem('username') + "\'s Tasks"));

	var categories = [];		

	for(t in tasks)
	{
		if(!categories.includes(tasks[t]["category"]))
		{			
			categories.push(tasks[t]["category"]);
		}			
	}

	for(category in categories)
	{
		var card = document.createElement('div');
		card.className="card category-container";

		var cardHeader = document.createElement('div');
		cardHeader.className ="card-header";

		var cardTitle = document.createElement("h3");
		cardTitle.className="card-title";

		var cardIcon = document.createElement('i');
		cardIcon.className = "ion ion-clipboard mr-1";
		var catText = document.createTextNode(categories[category]);
		cardTitle.appendChild(cardIcon);
		cardTitle.appendChild(catText);

		var cardTools = document.createElement('div');
		cardTools.className="card-tools";

		cardHeader.appendChild(cardTitle);
		cardHeader.appendChild(cardTools);

		var addTask = document.createElement('a');
		var addTaskText = document.createTextNode("+ ADD TASK");
		addTask.appendChild(addTaskText);
		addTask.className="btn btn-tool btn-sm";
		addTask.setAttribute('onclick', `addTaskPage("${categories[category]}")`);
		cardTools.appendChild(addTask);	

		var cardBody = document.createElement('div');
		cardBody.className="card-body applyScroll";

		var uList = document.createElement('ul');
		uList.className = "todo-list";
		uList.setAttribute('data-widget', 'todo-list');
		cardBody.appendChild(uList);
		card.appendChild(cardHeader);
		card.appendChild(cardBody);
		
		var cTasks = [];
		for (var t = 0; t < Object.keys(tasks).length; t++)
		{
			if(tasks[t]["category"] == categories[category])
			{
				var task = tasks[t];
				cTasks.push(task);
			}
		}
		cTasks.sort(function(a, b){return new Date(a["date"]) - new Date(b["date"])});
		for(task in cTasks)
		{
			var diff = new Date(cTasks[task]["date"]) - new Date();
			var absTime = (diff>0)? diff : -diff;
			var msec = absTime;
			console.log(absTime);
			var yy = Math.floor(msec / 1000 / 60 / 60 / 24 / 365);
			console.log(yy);
			console.log(msec);
			msec -= yy * 1000 * 60 * 60 * 24 * 365;
			var momo = Math.floor(msec / 1000 / 60 / 60 / 24 / 30);
			console.log(momo);
			console.log(msec);
			msec -= momo * 1000 * 60 * 60 * 24 * 30;
			var ww = Math.floor(msec / 1000 / 60 / 60 / 24 / 7);
			console.log(ww);
			console.log(msec);
			msec -= ww * 1000 * 60 * 60 * 24 * 7;
			var dd = Math.floor(msec / 1000 / 60 / 60 / 24);
			console.log(dd);
			console.log(msec);
			msec -= dd * 1000 * 60 * 60 * 24;
			var hh = Math.floor(msec / 1000 / 60 / 60);
			console.log(hh);
			console.log(msec);
			msec -= hh * 1000 * 60 * 60;
			var mm = Math.floor(msec / 1000 / 60);
			console.log(mm);
			console.log(msec);
			msec -= mm * 1000 * 60;
			var ss = Math.floor(msec / 1000);
			msec -= ss * 1000;

			var taskLine = document.createElement('li');
			taskLine.setAttribute('id', `taskLine${cTasks[task]["id"]}`);

			var checkBox = document.createElement('div');
			checkBox.className = "icheck-primary d-inline ml-2";
			var input = document.createElement('input');
			input.type = "checkbox";
			if(cTasks[task]["done"] == true)
			{
				console.log(diff);
				if(diff<0)
				{
					console.log('hidden');
					taskLine.classList.add("hidden");
				}
				console.log(cTasks[task]["done"]);
				taskLine.classList.add("done");
				input.checked = true;
				console.log('in done');
			}
			input.setAttribute('onclick', `setDone(${cTasks[task]["id"]})`);

			checkBox.appendChild(input);

			var taskName = document.createElement('span');
			taskName.className = "text";

			var taskText = document.createTextNode(cTasks[task]["name"]);
			taskName.appendChild(taskText);

			var time = document.createElement('small');
			var clock = document.createElement('i');
			clock.className = "far fa-clock";
			var dispTime = null;
			if(yy)
			{
				dispTime = (yy>1)?(yy + ' years'):(yy + ' year');
				console.log(momo);
				if(momo)
				{
					dispTime = dispTime.concat((momo>1)?(' ' + momo + ' months'):(' ' + momo + ' month'));
				}
				time.classList.add("badge");
				time.classList.add("badge-secondary");
			}
			else if(momo)
			{
				dispTime = (momo>1)?(momo + ' months'):(momo + ' month');
				if(ww)
				{
					dispTime = dispTime.concat((ww>1)?(' ' + ww + ' weeks'):(' ' + ww + ' week'));
				}
				time.classList.add("badge");
				time.classList.add("badge-secondary");
			}
			else if(ww)
			{
				dispTime = (ww>1)?(ww + ' weeks'):(ww + ' week');
				if(dd)
				{
					dispTime = dispTime.concat((dd>1)?(' ' + dd + ' days'):(' ' + dd + ' day'));
				}
				time.classList.add("badge");
				time.classList.add("badge-primary");
			}
			else if(dd)
			{
				dispTime = (dd>1)?(dd + ' days'):(dd + ' day');
				if(hh)
				{
					dispTime = dispTime.concat((hh>1)?(' ' + hh + ' hours'):(' ' + hh + ' hour'));
				}
				time.classList.add("badge");
				time.classList.add("badge-success");
			}
			else if(hh)
			{
				dispTime = (hh>1)?(hh + ' hours'):(hh + ' hour');
				if(mm)
				{
					dispTime = dispTime.concat((mm>1)?(' ' + mm + ' mins'):(' ' + mm + ' min'));
				}
				time.classList.add("badge");
				time.classList.add("badge-info");
			}
			else if(mm)
			{
				dispTime = (mm>1)?(mm + ' mins'):(mm + ' min');
				time.classList.add("badge");
				time.classList.add("badge-warning");
			}
			else if(ss)
			{
				dispTime = "1 min";
				time.classList.add("badge");
				time.classList.add("badge-warning");
			}
			if(diff<0)
			{
				dispTime = "Overdue by " + dispTime;
				time.setAttribute('class', 'badge badge-danger')
			}
			console.log(dispTime);
			var timeText = document.createTextNode(" " + dispTime);
			time.appendChild(clock);
			time.appendChild(timeText);

			var tools = document.createElement('div');
			tools.className = "tools";
			var editIcon = document.createElement('i');
			editIcon.setAttribute('onclick', `editTaskPage("${cTasks[task]["id"]}", "${categories[category]}", "${cTasks[task]["name"]}", "${cTasks[task]["date"]}", "${cTasks[task]["required"]}", "${cTasks[task]["remindBefore"]}")`);
			editIcon.className = "fas fa-edit";
			tools.appendChild(editIcon);

			taskLine.appendChild(checkBox);
			taskLine.appendChild(taskName);
			taskLine.appendChild(time);
			taskLine.appendChild(tools);
			uList.appendChild(taskLine);	
		}	
		document.getElementById("container").appendChild(card);			
	}
}