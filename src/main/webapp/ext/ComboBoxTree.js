/**
 * 下拉树ComboBoxTree
 * @extend Ext.form.ComboBox
 * @xtype 'combotree'
 * 
 * @author stworthy
 */

/**
 * ----------------------
 * Demo ComboBoxTree
 * ----------------------
 */
 /*-------------------------------------------------*
	treecombo = {
            xtype:'combotree',
            fieldLabel:'所属部门',
            id:'department_id',
            name:'department_name',
            allowUnLeafClick:false,
            treeHeight:200,
            url:'/myoa/department/getTrees',
            onSelect:function(id){
            }
	}
*-----------------------------------------------------*/

ComboBoxTree = Ext.extend(Ext.form.ComboBox, {
    treeHeight : 180,
    allowUnLeafClick:false,
    rootVisible:false,
    rootText:'',
    url:'',
    setFieldValue:function(id,text){
        this.setValue(text);
        this.hiddenField.value = id;
    },
    getFieldValue:function(){
        return this.hiddenField.value;
    },
    onSelect:function(id){
        
    },

    store : new Ext.data.SimpleStore({
            fields : [],
            data : [[]]
    }),

    //Default
    editable : false, // 禁止手写及联想功能
    mode : 'local',
    triggerAction : 'all',
    maxHeight : 500,
    selectedClass : '',
    onSelect : Ext.emptyFn,
    emptyText : '请选择...',

    /**
     * 初始化
     * Init
     */
    initComponent : function() {
        ComboBoxTree.superclass.initComponent.call(this);
        this.tplId = Ext.id();
        this.tpl = '<div id="' + this.tplId + '" style="height:' + this.treeHeight + 'px;overflow:hidden;"></div>';

        var tree = new Ext.tree.TreePanel({
            root:new Ext.tree.AsyncTreeNode({id:'0',text:this.rootText}),
            loader:new Ext.tree.TreeLoader({dataUrl:this.url}),
            autoScroll:true,
            height:this.treeHeight,
            rootVisible:this.rootVisible,
            border:false
        });
        var combo = this;
        tree.on('beforeload',function(node){
            tree.loader.dataUrl = combo.url+'?parentId='+node.id;
        });
        tree.on('click',function(node){
            if (combo.allowUnLeafClick == true){
                combo.setValue(node.text);
                combo.hiddenField.value = node.id;
                combo.collapse();
                combo.onSelect(node.id);
            }
            else if (node.leaf == true){
                combo.setValue(node.text);
                combo.hiddenField.value = node.id;
                combo.collapse();
                combo.onSelect(node.id);
            }
        });
        this.tree = tree;
    },

    /**
     * ------------------
     * 事件监听器 
     * Listener
     * ------------------
     */
    listeners : {
        'expand' : {
            fn: function() {
                if (!this.tree.rendered && this.tplId) {
                    this.tree.render(this.tplId);
                    this.tree.root.expand();
                    this.tree.root.select();
                }
                this.tree.show();
            }
        },
        'render':{
            fn:function(){
                this.hiddenField = this.el.insertSibling({
                    tag:'input',
                    type:'hidden',
                    name:this.getName()
                },'before',true);
                this.el.dom.removeAttribute('name');
            }
        }
    }
});

/**
 * --------------------------------- 
 * 将ComboBoxTree注册为Ext的组件,以便使用
 * Ext的延迟渲染机制，xtype:'combotree' 
 * ---------------------------------
 */
Ext.reg('combotree', ComboBoxTree);


