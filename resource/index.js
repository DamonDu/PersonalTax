$(document).ready(function(){
	$.ajax({
  		url:"index.css",
  		success:function(data){
    	$("head").append("<style>" + data + "</style>");
  		}
	});
});

$(document).ready(function(){
  loadXMLDoc("config.xml");
});

$("#submit_btn").click(function(){
	var text = $("#inputIncome")[0].value;
	$.post("/", text, function(){
		console.log("post");
	})
	/*var xmlhttp;
	var numOfLevel, firstThreshold, threshold, taxRate, item, xml;
	if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
  		xmlhttp=new XMLHttpRequest();
  	}
	else{// code for IE6, IE5
  		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  	}
  	xmlhttp.overrideMimeType("text/xml");
	xmlhttp.onreadystatechange = function(){
  		if (xmlhttp.readyState == 4 && xmlhttp.status == 200){
   			var tax = xmlhttp.responseXML.documentElement.getElementsByTagName("tax")[0].innerHTML;
			console.log(tax);
	    }
  	}
  	var formData = new FormData();
  	formData.append("tax", text);
	xmlhttp.send(formData);*/
	/*if(text != null){
		$.ajax({
  		url:"tax?income=" + text,
  		success:function(data){
    		
  		}
	});*/
});

$("#modify_btn").click(function(){
	$(".panel-container input").each(function(){
		$(this).removeAttr("readonly");
	})
});

$("#save_btn").click(function(){
	$(".panel-container input").each(function(){
		$(this).attr("readonly","readonly");
	})
});


function loadXMLDoc(url){
	var xmlhttp;
	var numOfLevel, firstThreshold, threshold, taxRate, item, xml;
	if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
  		xmlhttp=new XMLHttpRequest();
  	}
	else{// code for IE6, IE5
  		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  	}
  	xmlhttp.overrideMimeType("text/xml");
	xmlhttp.onreadystatechange = function(){
  		if (xmlhttp.readyState == 4 && xmlhttp.status == 200){
   			txt = "<thead><tr><th>Level</th><th>Threshold</th><th>Tax Rate</th></tr></thead><tbody>";
    		numOfLevel = parseInt(xmlhttp.responseXML.documentElement.getElementsByTagName("numOfLevel")[0].innerHTML);
    		firstThreshold = xmlhttp.responseXML.documentElement.getElementsByTagName("firstThreshold")[0].innerHTML;
			threshold = xmlhttp.responseXML.documentElement.getElementsByTagName("threshold")[0];
			taxRate = xmlhttp.responseXML.documentElement.getElementsByTagName("taxRate")[0];
			for (var i = 0; i < numOfLevel; i++) {
				thresholdItem = threshold.getElementsByTagName("item")[i].innerHTML;
				taxRateItem = taxRate.getElementsByTagName("item")[i].innerHTML;
				txt = txt + "<tr><th scope='row'>" + i + "</th><td><input type='text' class='form-control' readonly='readonly' value='"+ thresholdItem + "'></td><td><input type='text' class='form-control' readonly='readonly' value='"+ taxRateItem + "'></td></tr>";
			}
		    txt=txt + "</tbody>";
		    $(".table").append(txt);
		    $("#num-of-level").attr("value", numOfLevel);
		    $("#first-threshold").attr("value", firstThreshold);
	    }
  	}
  	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}