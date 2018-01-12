//Ext.app.SearchField----start
Ext.app.SearchField = Ext.extend(Ext.form.TwinTriggerField, {
    initComponent : function(){
        if(!this.store.baseParams){
			this.store.baseParams = {};
		}
		Ext.app.SearchField.superclass.initComponent.call(this);
		this.on('specialkey', function(f, e){
            if(e.getKey() == e.ENTER){
                this.onTrigger2Click();
            }
        }, this);
    },

    validationEvent:false,
    validateOnBlur:false,
    trigger1Class:'x-form-clear-trigger',
    trigger2Class:'x-form-search-trigger',
    hideTrigger1:true,
    width:180,
    hasSearch : false,
    paramName : 'query',

    onTrigger1Click : function(){
        var o = {start: 0};
        //alert(this.store.baseParams[this.paramName]);
        if(this.hasSearch){
            this.store.baseParams[this.paramName] = '';
        this.store.reload({params:o});
//			this.store.removeAll();
			this.el.dom.value = '';
            this.triggers[0].hide();
            this.hasSearch = false;
			this.focus();
        }
    },

    onTrigger2Click : function(){
        var v = this.getRawValue();
//        Ext.Ajax.request({
//			url:'terminalList.action?search='+v
//		})
        if(v.length < 1){
            this.onTrigger1Click();
            return;
        }
		this.store.baseParams[this.paramName] = v;
        var o = {start: 0 , paramName: v};
        this.store.reload({params:o});
        this.hasSearch = true;
        this.triggers[0].show();
		this.focus();
		
    }
});
//Ext.app.SearchField----end
