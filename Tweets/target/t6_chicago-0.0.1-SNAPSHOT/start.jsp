<!doctype html>

<html lang="en">
<head>
  <meta charset="utf-8">

  <title>Tweets Harvesting Applicaton - Team6</title>
  <meta name="description" content="Tweets Harvesting Application">
  <meta name="author" content="Team6">

  <!--[if lt IE 9]>
  <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
  <![endif]-->
  
  <style type="text/css">
    #frm {font-size:25px;margin:30px 10px;}
  	#btnStart {font-size:20px;height:35px;width:100px;}
  </style>
  <script type="text/javascript" src="/Tweets/js/jquery-1.11.0.min.js"></script>
  <script type="text/javascript">
	  $(document).ready(function() {
		    $('#btnStart').click(function(event) {
		    	$("#btnStart").val("RUNNING").attr("disabled", true);
		        $.get('http://127.0.0.1:8080/Tweets/start?to=tweet',{},function(data) {
		        	//alert(data);
		        	$("#btnStart").val("START").removeAttr("disabled");
		        });
		    });
		});  
  </script>
</head>

<body>
  <form id="frm">
     <label>Tweets Harvesting:</label><input type="button" id="btnStart" value="START" />
  </form>
  
</body>
</html>