//Ensures the Email is inputed correctly
function isAvailable()
{
	$.post("/isUserNow",
	{
		username:$("#uname").val(),
	},
	function(data)
	{
		if(data.username == "")
		{
			alert(data.username + " is not a valid email");
			document.example.email.focus();
		}else{
			alert(data.username + "is your email");	
		}
	},"json");	
}

//Login calls
function login()
{
	$.ajax({
		type: "GET",
	    url: "/userLogin",
	    data: { username: $("#uname").val(),
	    		password: $("#pwd").val()},
	    dataType: "json", 
	    success: function (data) {
	    	returned_data = data;
	    	alert(data.username);
	    	if(data.username == "" & data.password ==""){
				alert("No user found");
			}
	    	/*else{
				$.cookie("userId", data.id, { expires: 1, path: '/' });
				alert('UserId ' + data.id);
			}*/
        	if(username == data.username && password == data.password)
        	{
        		location.href = "ModuleDisplay.html"
        	}
        }
    });
}

//Checks Database to ensure the email isnt already in use
function isAvail(){
	$.post("/isUserNow",
		{
			username:$("#newUname").val(),
		},
		function(data)
		{
			if(data.username == "")
			{
				alert(data.username + " is avaiable");
				return true;	
				//Show submit button here
			}else{
				alert(data.username + "is in use");
				document.example.email.focus();
				
				}
			return false;
		},"json");	
}

//Sees if the email is valid
function isValid()
{
	var email=document.example.email.value
	if(email.indexOf("@"&&"dcu.ie")==-1)
	{
		alert("Please enter a valid email");
		document.example.email.focus();
	}
	else{
		isAvail();
	}
	
}

function createAccount()
{
	$.ajax({
		type: "POST",
	    url: "/createAccount",
	    data:{ 
	    	username: $("#newUname").val(),
	    	password: $("#newPwd").val(),
	    },
	    dataType: "json",
	    success: function (data) {
	    	data.username;
	    	data.password;
	    }
 	});           
}