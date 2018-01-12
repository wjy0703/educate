
  var oTime;
  //滚轮缩放图片
     function   bbimg(id){ 
     var o = document.getElementById(id);
		var   zoom=parseInt(o.style.zoom,   10)||100;
		zoom+=event.wheelDelta/12;
		if   (zoom> 0)   o.style.zoom=zoom+ '% '; 
		return   false; 
	} 
     
     // 缩放图片
    function imgToSize(oBool) {
     var oImg = document.all('oImg');
     oImg.style.zoom = parseInt(oImg.style.zoom) + (oBool ? +1 : -1) + '%';
     oTime = window.setTimeout('imgToSize(' + oBool + ')', 1);
    }

 //还原图片
 function imgToBack(oBool) {
     var oImg = document.all('oImg');
     oImg.style.zoom = '100%';
     oTime = window.setTimeout('imgToSize(' + oBool + ')', 1);
    }
   // 旋转图片
   var oArcSize = 1;
   function imgRoll() {
     var oImg = document.all('oImg');
     oImg.style.filter = 'Progid:DXImageTransform.Microsoft.BasicImage(Rotation='+ oArcSize +')';
     oArcSize += 1;
     oArcSize = oArcSize==4 ? 0 : oArcSize ;
   }
 // 拖动图片 
   var ie = document.all;
   
   var nn6 = document.getElementById && !document.all;
   
   var isdrag = false;
   
   var x, y;
   
   var dobj;
   
   function movemouse(e){
   
       if (isdrag) {
       
           dobj.style.left = nn6 ? tx + e.clientX - x : tx + event.clientX - x;
           
           dobj.style.top = nn6 ? ty + e.clientY - y : ty + event.clientY - y;
           
           return false;
           
       }
       
   }
   
   function selectmouse(e){
   
       var fobj = nn6 ? e.target : event.srcElement;
       
       var topelement = nn6 ? "HTML" : "BODY";
       
       while (fobj.tagName != topelement && fobj.className != "dragme") {
       
           fobj = nn6 ? fobj.parentNode : fobj.parentElement;
           
       }
       
       if (fobj.className == "dragme") {
       
           isdrag = true;
           
           dobj = fobj;
           
           tx = parseInt(dobj.style.left + 0);
           
           ty = parseInt(dobj.style.top + 0);
           
           x = nn6 ? e.clientX : event.clientX;
           
           y = nn6 ? e.clientY : event.clientY;
           
           document.onmousemove = movemouse;
           
           return false;
           
       }
       
   }
   
   document.onmousedown = selectmouse;
   
   document.onmouseup = new Function("isdrag=false");