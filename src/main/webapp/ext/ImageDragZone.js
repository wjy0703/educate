	ImageDragZone = function(view, config){
	    this.view = view;
	    ImageDragZone.superclass.constructor.call(this, this.view.getEl(), config);
	};
	
	Ext.extend(ImageDragZone, Ext.dd.DragZone, {
    getDragData : function(e){
      var target = e.getTarget('.thumb-wrap');
      if(target){
        var view = this.view;
        if(!view.isSelected(target)){
          view.onClick(e);
        }
        var selNodes = view.getSelectedNodes();
        var dragData = {
          nodes: selNodes
        };
        if(selNodes.length == 1){
          dragData.ddel = target.firstChild.firstChild; // the img element
          dragData.single = true;
        }else{
          var div = document.createElement('div'); // create the multi element drag "ghost"
          div.className = 'multi-proxy';
          for(var i = 0, len = selNodes.length; i < len; i++){
            div.appendChild(selNodes[i].firstChild.firstChild.cloneNode(true));
            if((i+1) % 3 == 0){
              div.appendChild(document.createElement('br'));
            }
          }
          dragData.ddel = div;
          dragData.multi = true;
        }
        return dragData;
      }
      return false;
   },
	
    
    afterRepair:function(){
      for(var i = 0, len = this.dragData.nodes.length; i < len; i++){
          Ext.fly(this.dragData.nodes[i]).frame('#8db2e3', 1);
      }
      this.dragging = false;
    },
	    
	    // override the default repairXY with one offset for the margins and padding
    getRepairXY : function(e){
      if(!this.dragData.multi){
        var xy = Ext.Element.fly(this.dragData.ddel).getXY();
        xy[0]+=3;xy[1]+=3;
        return xy;
      }
      return false;
    }
	});
