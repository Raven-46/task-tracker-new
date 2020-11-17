fetch("/diary", { 
      
    	// Adding method type 
   	 	method: "GET", 
      
    	// Adding body or contents to send 
    
      
    	// Adding headers to the request 
    	headers: { 
        	"Content-type": "application/json; charset=UTF-8",
        	"Authorization": `Bearer ${localStorage.getItem('token')}`
    		} 
		})  
// Converting to JSON       
.then(response => response.json())
// Displaying results to console 
.then(json => {
	console.log(json);
})
.catch(()=>{
    alert("please sign in again");
});