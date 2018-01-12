/*	$(function(){
		alert('goaadddaa');
		$('td.operation').bind('click',function(){
			alert(1);
			return false;
		});
	});*/

function showOrHide(buttonSelf){
	var $currentTab = navTab.getCurrentPanel();
	$(".OperationsPopUp",$currentTab).hide();
	var sideBarWidth = $('#sidebar').width() + $('#splitBar').width();
	var leftOfButton = $(buttonSelf).offset().left;
	var popButtonsWidth = $(buttonSelf).parents('.buttonActive').next().width();
	if($('#sidebar').css('display')!='none'){
		var left = leftOfButton - popButtonsWidth - sideBarWidth - 15;
	}else{
		var left = leftOfButton - popButtonsWidth - 27;
	}
	
	$(buttonSelf).parents('.buttonActive').next().css({left:left}).show();
	//$(".OperationsPopUp").hide();
	return false;
}

$(function(){
	$(document).click(function(event){
		if(event.target.nodeName != 'BUTTON')
		{
			var $currentTab = navTab.getCurrentPanel();
			$(".OperationsPopUp",$currentTab).hide();
		}
	});
});
