/*验证问题名是否重复*/
function vRoleName(oldvale,ppname,errmes)
{
	var $box = navTab.getCurrentPanel();
	var NAME = $("#"+ppname,$box).val();
	var busacc = $("#busacc",$box).val();
	var canLook = $("#canLook",$box).val();
	if(''!=NAME)
	{
		if('1' == canLook){
			var names= new Array(); //定义一数组
			names = NAME.split(busacc); //字符分割    
	//	alertMsg.error(NAME+";"+busacc+";"+names+";"+names.length);
			if(names.length==1){
				NAME = busacc + "-" + NAME;
				$("#"+ppname,$box).val(NAME);
			}
		}
		var url = "role/chkrole?oldValue="+encodeURI(encodeURI(oldvale))+"&propertyName="+ppname+"&errmes="+encodeURI(encodeURI(errmes))+"&"+ppname+"="+encodeURI(encodeURI(NAME));
		var urlOld = convertURL(url);
		$.get(urlOld,null,function(data,text)
		{
			if(data!='')
			{
				//alert(data);
				alertMsg.error(data);
				$("#"+ppname,$box).val("");
			};
		});
	}
}

/*验证问题名是否重复*/
function vUserAccount(oldvale,ppname,errmes)
{
	var $box = navTab.getCurrentPanel();
	var NAME = $("#"+ppname,$box).val();
	var busacc = $("#busacc",$box).val();
	var canLook = $("#canLook",$box).val();
	if(''!=NAME)
	{
		var url = "userinfo/chUserAccount?oldValue="+encodeURI(encodeURI(oldvale))+"&propertyName="+ppname+"&errmes="+encodeURI(encodeURI(errmes))+"&"+ppname+"="+encodeURI(encodeURI(NAME));
		var urlOld = convertURL(url);
		$.get(urlOld,null,function(data,text)
		{
			if(data!='')
			{
				//alert(data);
				alertMsg.error(data);
				$("#"+ppname,$box).val("");
			};
		});
	}
}
//给url地址增加时间戳，不读取缓存
function convertURL(url) {
    var timstamp = (new Date()).valueOf();
    //将时间戳信息拼接到url上
    if (url.indexOf("?") >= 0) {
        url = url + "&t=" + timstamp;
    } else {
        url = url + "?t=" + timstamp;
    }
    return url;
}
