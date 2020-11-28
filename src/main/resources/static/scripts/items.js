var c_items;
var items;
var friends = [];

function searchItems()
{
	// Declare variables
	var input, filter, cards, table, tr, td, i, txtValue;
	input = document.getElementById("itemSearchInput");
	filter = input.value.toUpperCase();
	cards = document.getElementsByClassName("category-container");
	console.log(input);
	for(c=0; c<cards.length; c++)
	{
		var exists = 0;
		console.log(c);
		console.log(cards[c]);
		table = cards[c].getElementsByTagName("table")[0];
		console.log(table.rows[0].cells.length);
		tr = table.getElementsByTagName("tr");
  
		// Loop through all table rows, and hide those who don't match the search query
		for (i = 0; i < tr.length; i++)
		{
			td = (table.rows[0].cells.length==4)?tr[i].getElementsByTagName("td")[0] : tr[i].getElementsByTagName("td")[1];
			if (td)
			{
				txtValue = td.textContent || td.innerText;
				if (txtValue.toUpperCase().indexOf(filter) > -1)
				{
					cards[c].style.display = "";
					tr[i].style.display = "";
					exists=1;
				}
				else
				{
					tr[i].style.display = "none";
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

function addCategoryPage()
{
	var title =document.createElement('h3');
	title.className = "card-title";
	title.setAttribute('id', 'action-title');
	title.appendChild(document.createTextNode("Add Category with Item"));
	document.getElementById("closebtncont").appendChild(title);
	document.getElementById("del").style.display = "none";
	document.getElementById("itemScreen").style.display = "block";
}

function addItemPage(category)
{
	var title =document.createElement('h3');
	title.className = "card-title";
	title.setAttribute('id', 'action-title');
	title.appendChild(document.createTextNode("Add Item"));
	document.getElementById("closebtncont").appendChild(title);
	document.getElementById("del").style.display = "none";
	document.getElementById("exampleInputCategory").value = category;
	document.getElementById("itemScreen").style.display = "block";
}
function addCommonItemPage(category)
{
	var title =document.createElement('h3');
	title.className = "card-title";
	title.setAttribute('id', 'action-title');
	title.appendChild(document.createTextNode("Add Common Item"));
	document.getElementById("closebtncontcommon").appendChild(title);
	var select = document.getElementById("exampleInputShare");
	console.log(friends);
	for(f in friends)
	{  
		console.log("in friends");
		var option = document.createElement("option");
		option.text = `${friends[f]}`;
		select.add(option);
	}
	document.getElementById("delCommon").style.display = "none";
	document.getElementById("exampleInputCategoryCommon").value = category;
	document.getElementById("commonItemScreen").style.display = "block";
}

function editItemPage(id, category, itemName, quantity, price)
{
	var title =document.createElement('h3');
	title.className = "card-title";
	title.setAttribute('id', 'action-title');
	title.appendChild(document.createTextNode("Edit Item"));
	document.getElementById("closebtncont").appendChild(title);
 	document.getElementById("itemId").value = id;
	document.getElementById("exampleInputCategory").value = category;
	document.getElementById("exampleInputItemName").value = itemName;
	document.getElementById("exampleInputQuantity").value = quantity;
	document.getElementById("exampleInputPrice").value = price;
	document.getElementById("del").style.display = "";
	document.getElementById("itemScreen").style.display = "block";
}
function editCommonItemPage(id, person, category, itemName, quantity, price)
{
	var title =document.createElement('h3');
	title.className = "card-title";
	title.setAttribute('id', 'action-title');
	title.appendChild(document.createTextNode("Edit Item"));
	document.getElementById("closebtncont").appendChild(title);
	var select = document.getElementById("exampleInputShare");
	console.log(friends);
	for(f in friends)
	{  
		console.log("in friends");
		var option = document.createElement("option");
		option.text = `${friends[f]}`;
		select.add(option);
	}
	var mySelect = document.getElementById('exampleInputShare');
	for(var i, j = 0; i = mySelect.options[j]; j++) {
		if(i.value == person) {
			mySelect.selectedIndex = j;
			break;
		}
	}
 	document.getElementById("itemIdCommon").value = id;
	document.getElementById("exampleInputCategoryCommon").value = category;
	document.getElementById("exampleInputItemNameCommon").value = itemName;
	document.getElementById("exampleInputQuantityCommon").value = quantity;
	document.getElementById("exampleInputPriceCommon").value = price;
	document.getElementById("del").style.display = "";
	document.getElementById("commonItemScreen").style.display = "block";
}

function closeItemPage()
{
	document.getElementById("action-title").remove();
	document.getElementById("itemId").value = "";
	document.getElementById("exampleInputCategory").value = "";
	document.getElementById("exampleInputItemName").value = "";
	document.getElementById("exampleInputQuantity").value = "";
	document.getElementById("exampleInputPrice").value = "";
	document.getElementById("itemScreen").style.display = "none";
}
function closeCommonItemPage()
{
	document.getElementById("action-title").remove();
	document.getElementById("itemIdCommon").value = "";
	var select = document.getElementById("exampleInputShare");
	var length = select.options.length;
	for (i = length-1; i >= 0; i--) {
	select.options[i] = null;
	}
	document.getElementById("exampleInputCategoryCommon").value = "";
	document.getElementById("exampleInputItemNameCommon").value = "";
	document.getElementById("exampleInputQuantityCommon").value = "";
	document.getElementById("exampleInputPriceCommon").value = "";
	document.getElementById("commonItemScreen").style.display = "none";
}

function sendRequest(event)
{
	event.preventDefault();
	var id = document.getElementById("itemId").value
    var category = document.getElementById("exampleInputCategory").value;
    var name =  document.getElementById("exampleInputItemName").value;
    var quantity = document.getElementById("exampleInputQuantity").value;
    var price = document.getElementById("exampleInputPrice").value;
    console.log(id); 
	console.log(category);
	console.log(name);
	console.log(quantity);
	console.log(price);  
	console.log('in send');
	
	object = {category, name, quantity, price};
	
	if(id == "")
	{
		console.log('post');
		fetch("/item", { 
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
		fetch(`/items/${id}`, {
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

function addCommonItem(id)
{
	fetch(`/commons/${id}/add`, {
   	 	method: "PUT", 
    	headers: { 
        	"Content-type": "application/json; charset=UTF-8",
        	"Authorization": `Bearer ${localStorage.getItem('token')}`
    	} 
	})  
	.then(() => {location.reload();	});		
}
function addItem(id)
{
	fetch(`/items/${id}/add`, {
   	 	method: "PUT", 
    	headers: { 
        	"Content-type": "application/json; charset=UTF-8",
        	"Authorization": `Bearer ${localStorage.getItem('token')}`
    	} 
	})  
	.then(() => {location.reload();	});		
}

function deleteItem()
{
	var id = document.getElementById("itemId").value;
	fetch(`/items/${id}`, {
		method: "DELETE", 
		headers: { 
			"Content-type": "application/json; charset=UTF-8",
			"Authorization": `Bearer ${localStorage.getItem('token')}`
		}
	})  
	.then(() => {location.reload();});
}
function deleteCommonItem()
{
	var id = document.getElementById("itemIdCommon").value;
	fetch(`/commons/${id}`, {
		method: "DELETE", 
		headers: { 
			"Content-type": "application/json; charset=UTF-8",
			"Authorization": `Bearer ${localStorage.getItem('token')}`
		}
	})
	.then(() => {location.reload();});
}

function subtractCommonItem(id)
{
	fetch(`/commons/${id}/subtract`, {
   	 	method: "PUT", 
    	headers: { 
        	"Content-type": "application/json; charset=UTF-8",
        	"Authorization": `Bearer ${localStorage.getItem('token')}`
    	} 
	})  
	.then(() => {location.reload();	});		
}
function subtractItem(id)
{
	fetch(`/items/${id}/subtract`, {
   	 	method: "PUT", 
    	headers: { 
        	"Content-type": "application/json; charset=UTF-8",
        	"Authorization": `Bearer ${localStorage.getItem('token')}`
    	} 
	})  
	.then(() => {location.reload();	});		
}

fetch("/friends", { 
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

fetch("/get-username", { 
	method: "GET", 
	headers: { 
		"Content-type": "application/json; charset=UTF-8",
		"Authorization": `Bearer ${localStorage.getItem('token')}`
	} 
})
.then(response => response.text())
.then(data => {
	console.log(data);
	localStorage.setItem('username', data);
  })
.then(() => {getCommons();});

function getCommons()
{
	fetch("/commons", { 
		method: "GET", 
		headers: { 
			"Content-type": "application/json; charset=UTF-8",
			"Authorization": `Bearer ${localStorage.getItem('token')}`
		} 
	})
	.then(response => response.json())
	.then(json => {
		c_items = json;
		console.log(json);
	})
	.then(() => {getItems();});
}
 function getItems()
 {
	fetch("/items", { 
		method: "GET", 
		headers: { 
			"Content-type": "application/json; charset=UTF-8",
			"Authorization": `Bearer ${localStorage.getItem('token')}`
		} 
	})
	.then(response => response.json())
	.then(json => {
		items = json;
		console.log(json);
	})
	.catch(()=>{
		alert("please sign in again");
		window.location.href = "http://localhost:8080/login";
	})
	.then(() => {renderPage();});
 }
    
function renderPage()
{
	//	document.addEventListener('DOMContentLoaded',()=>{
		document.getElementById("usernameHead").appendChild(document.createTextNode(localStorage.getItem('username') + "\'s Items"));
		var c_categories = [];
		for(i in c_items)
		{
			console.log(c_items[i]["category"]);		
			console.log(c_categories);
			if(!c_categories.includes(c_items[i]["category"]))
			{
				console.log('hi');				
				c_categories.push(c_items[i]["category"]);
			}			
		}
	
		for(c_category in c_categories)
		{
			var card = document.createElement('div');
			card.className="card category-container card-success";
	
			var cardHeader = document.createElement('div');
			cardHeader.className ="card-header border-0";
	
			var cardTitle = document.createElement("h3");
			cardTitle.className="card-title";
	
			var cardTools = document.createElement('div');
			cardTools.className="card-tools";
	
			var addItem = document.createElement('a');
			var addItemText = document.createTextNode("+ ADD ITEM");
			addItem.appendChild(addItemText);
			addItem.className="btn btn-tool btn-sm";
			addItem.setAttribute('onclick', `addCommonItemPage("${c_categories[c_category]}")`);
			cardTools.appendChild(addItem);	
	
			var cardBody = document.createElement('div');
			cardBody.className="card-body table-responsive p-0 applyScroll";
	
			var table = document.createElement('table');
			table.className="table table-striped table-valign-middle";
	
			var head = document.createElement('thead');
			
			var headRow = document.createElement('tr');
			var jointOwnerHead = document.createElement('th');
			var itemNameHead = document.createElement('th');
			var quantityHead = document.createElement('th');
			var priceHead = document.createElement('th');
			var editHead = document.createElement('th');
			
			var jointOwnerText = document.createTextNode('Joint Owner');
			var itemNameText = document.createTextNode('Item');
			var quantityText = document.createTextNode('quantity');
			var priceText = document.createTextNode('price');
			
			jointOwnerHead.appendChild(jointOwnerText);
			itemNameHead.appendChild(itemNameText);
			quantityHead.appendChild(quantityText);
			priceHead.appendChild(priceText);
			headRow.appendChild(jointOwnerHead);
			headRow.appendChild(itemNameHead);
			headRow.appendChild(quantityHead);
			headRow.appendChild(priceHead);
			headRow.appendChild(editHead);
			
			var body = document.createElement('tbody');
	
			head.appendChild(headRow);
			table.appendChild(head);
			table.appendChild(body);
			
			var cat = document.createTextNode(c_categories[c_category]);
			cardTitle.appendChild(cat);
			cardHeader.appendChild(cardTitle);
			cardHeader.appendChild(cardTools);
			card.appendChild(cardHeader);
			card.appendChild(cardBody);
			cardBody.appendChild(table);
			
			for (var i = 0; i < Object.keys(c_items).length; i++)
			{
				if(c_items[i]["category"] == c_categories[c_category])
				{
					var row = document.createElement('tr');   
					row.className = "edit-on-hover";

					var jointOwerBody = document.createElement('td');
					var itemBody = document.createElement('td');
					var quantityBody = document.createElement('td');
					var priceBody = document.createElement('td');
					var editBody = document.createElement('td');
					editBody.className = "tools";
					var editIcon = document.createElement('i');
					editIcon.className = "fas fa-edit placement";
					var otherPerson = (localStorage.getItem('username')==c_items[i]["username1"])? c_items[i]["username2"] : c_items[i]["username1"];
					editIcon.setAttribute('onclick', `editCommonItemPage("${c_items[i]["itemid"]}", "${otherPerson}", "${c_categories[c_category]}", "${c_items[i]["name"]}", "${c_items[i]["quantity"]}", "${c_items[i]["price"]}")`);
					editBody.appendChild(editIcon);
					var jointOwner = (localStorage.getItem('username')==c_items[i]["username2"])?document.createTextNode(c_items[i]["username1"]) : document.createTextNode(c_items[i]["username2"]);
					var item = document.createTextNode(c_items[i]["name"]);
					var quantity = document.createTextNode(" " + c_items[i]["quantity"] + " ");
					var price = document.createTextNode(c_items[i]["price"]);				
					
					var minusbutton = document.createElement('i');
					minusbutton.className="fas fa-minus-circle";
					minusbutton.setAttribute('onclick', `subtractCommonItem(${c_items[i]["itemid"]})`);
	
					var plusbutton = document.createElement('i');
					plusbutton.className="fas fa-plus-circle";
					plusbutton.setAttribute('onclick', `addCommonItem(${c_items[i]["itemid"]})`);
					
					jointOwerBody.appendChild(jointOwner);

					itemBody.appendChild(item);
					
					quantityBody.appendChild(minusbutton);
					quantityBody.appendChild(quantity);
					quantityBody.appendChild(plusbutton);
					
					priceBody.appendChild(price);
	
					row.appendChild(jointOwerBody);
					row.appendChild(itemBody);
					row.appendChild(quantityBody);
					row.appendChild(priceBody);
					row.appendChild(editBody);
	
					body.appendChild(row);
				}				
			}
			document.getElementById("container").appendChild(card);			
		}

	var categories = [];
	for(i in items)
	{
		console.log(items[i]["category"]);		
		console.log(categories);
		if(!categories.includes(items[i]["category"]))
		{
			console.log('hi');				
			categories.push(items[i]["category"]);
		}			
	}

	for(category in categories)
	{
		var card = document.createElement('div');
		card.className="card category-container";

		var cardHeader = document.createElement('div');
		cardHeader.className ="card-header border-0";

		var cardTitle = document.createElement("h3");
		cardTitle.className="card-title";

		var cardTools = document.createElement('div');
		cardTools.className="card-tools";

		var addItem = document.createElement('a');
		var addItemText = document.createTextNode("+ ADD ITEM");
		addItem.appendChild(addItemText);
		addItem.className="btn btn-tool btn-sm";
		addItem.setAttribute('onclick', `addItemPage("${categories[category]}")`);
		cardTools.appendChild(addItem);	

		var cardBody = document.createElement('div');
		cardBody.className="card-body table-responsive p-0 applyScroll";

		var table = document.createElement('table');
		table.className="table table-striped table-valign-middle";

		var head = document.createElement('thead');
		
		var headRow = document.createElement('tr');
		var itemNameHead = document.createElement('th');
		var quantityHead = document.createElement('th');
		var priceHead = document.createElement('th');
		var editHead = document.createElement('th');

		var itemNameText = document.createTextNode('Item');
		var quantityText = document.createTextNode('quantity');
		var priceText = document.createTextNode('price');

		itemNameHead.appendChild(itemNameText);
		quantityHead.appendChild(quantityText);
		priceHead.appendChild(priceText);
		headRow.appendChild(itemNameHead);
		headRow.appendChild(quantityHead);
		headRow.appendChild(priceHead);
		headRow.appendChild(editHead);
		
		var body = document.createElement('tbody');

		head.appendChild(headRow);
		table.appendChild(head);
		table.appendChild(body);
		
		var cat = document.createTextNode(categories[category]);
		cardTitle.appendChild(cat);
		cardHeader.appendChild(cardTitle);
		cardHeader.appendChild(cardTools);
		card.appendChild(cardHeader);
		card.appendChild(cardBody);
		cardBody.appendChild(table);
		
		for (var i = 0; i < Object.keys(items).length; i++)
		{
			if(items[i]["category"] == categories[category])
			{
				var row = document.createElement('tr');   

				var itemBody = document.createElement('td');
				var quantityBody = document.createElement('td');
				var priceBody = document.createElement('td');
				var editBody = document.createElement('td');
				editBody.className = "tools";
				var editIcon = document.createElement('i');
				editIcon.className = "fas fa-edit";
				editIcon.setAttribute('onclick', `editItemPage("${items[i]["id"]}", "${categories[category]}", "${items[i]["name"]}", "${items[i]["quantity"]}", "${items[i]["price"]}")`);
				editBody.appendChild(editIcon);

				var item = document.createTextNode(items[i]["name"]);
				var quantity = document.createTextNode(" " + items[i]["quantity"] + " ");
				var price = document.createTextNode(items[i]["price"]);				
				
				var minusbutton = document.createElement('i');
				minusbutton.className="fas fa-minus-circle";
				minusbutton.setAttribute('onclick', `subtractItem(${items[i]["id"]})`);

				var plusbutton = document.createElement('i');
				plusbutton.className="fas fa-plus-circle";
				plusbutton.setAttribute('onclick', `addItem(${items[i]["id"]})`);
				
				itemBody.appendChild(item);
				
				quantityBody.appendChild(minusbutton);
				quantityBody.appendChild(quantity);
				quantityBody.appendChild(plusbutton);
				
				priceBody.appendChild(price);

				row.appendChild(itemBody);
				row.appendChild(quantityBody);
				row.appendChild(priceBody);
				row.appendChild(editBody);

				body.appendChild(row);
			}				
		}
		document.getElementById("container").appendChild(card);			
	}
}