/*
 * 弹出DIV层
*/
function showDiv(url,obj)
{
	 var localObj = window.location;
	 var contextPath = localObj.pathname.split("/")[1];
     var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
     url = basePath + url;
     
	 e = obj || window.event;
	  // 此处记录鼠标停留在组建上的时候的位置, 可以自己通过加减常量来控制离鼠标的距离.
	  mouseX = e.layerX|| e.offsetX;
	  mouseY = e.layerY|| e.offsetY; 
	var Idiv     = document.getElementById("Idiv");
	var mou_head = document.getElementById('mou_head');
	Idiv.style.display = "block";
	//以下部分要将弹出层居中显示
//	Idiv.style.left=(document.documentElement.clientWidth-Idiv.clientWidth)/2+document.documentElement.scrollLeft+"px";
//	Idiv.style.top =(document.documentElement.clientHeight-Idiv.clientHeight)/2+document.documentElement.scrollTop-50+"px";
	Idiv.style.left=mouseX;
	Idiv.style.top =mouseY;
	//以下部分实现弹出层的拖拽效果
	/*
	var posX;
	var posY;
	
	mou_head.onmousedown=function(e)
	{
		if(!e) e = window.event; //IE
		posX = e.clientX - parseInt(Idiv.style.left);
		posY = e.clientY - parseInt(Idiv.style.top);
		document.onmousemove = mousemove;
	};
	
	document.onmouseup = function()
	{
		document.onmousemove = null;
	};
	
	function mousemove(ev)
	{
	if(ev==null) ev = window.event;//IE
	Idiv.style.left = (ev.clientX - posX) + "px";
	Idiv.style.top = (ev.clientY - posY) + "px";
	}
	*/
	erwei(url);
}


function closeDiv() //关闭弹出层
{
	var Idiv=document.getElementById("Idiv");
	Idiv.style.display="none";
	document.body.style.overflow = "auto"; //恢复页面滚动条
	var body = document.getElementsByTagName("body");
	var mybg = document.getElementById("mybg");
	body[0].removeChild(mybg);
}

var qrcode ;
 function erwei(url){
	 if(qrcode == null){
		 qrcode = new QRCode(document.getElementById("qrcode"), {
			width : 100,//设置宽高
			height : 100
		});
	 }
     qrcode.makeCode(url);
 }