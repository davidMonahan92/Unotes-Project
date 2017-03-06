function logout()
{
	$.cookie("userId", null, { path: '/' });
	window.location.replace("login.html");
}