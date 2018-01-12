/*验证问题名是否重复*/
function vBusiAcc(oldvale,ppname,errmes)
{
	var $box = navTab.getCurrentPanel();
	var NAME = $("#"+ppname,$box).val();
	//alertMsg.error(oldvale+";"+ppname+";"+errmes+";"+NAME);
	if(''!=NAME)
	{
		var url = "businessinfo/chkBusi?oldValue="+encodeURI(encodeURI(oldvale))+"&propertyName="+ppname+"&errmes="+encodeURI(encodeURI(errmes))+"&"+ppname+"="+encodeURI(encodeURI(NAME));
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