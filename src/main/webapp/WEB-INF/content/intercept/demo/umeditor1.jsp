<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- 样式文件 -->
<link rel="stylesheet" href="${ctx}/umeditor/themes/default/css/umeditor.css">
<!-- 引用jquery -->
<script src="${ctx}/umeditor/third-party/jquery.min.js"></script>
<!-- 引入 etpl -->
<script type="text/javascript" src="${ctx}/third-party/template.min.js"></script>
<!-- 配置文件 -->
<script type="text/javascript" src="${ctx}/umeditor/umeditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="${ctx}/umeditor/umeditor.js"></script>
<!-- 语言包文件 -->
<script type="text/javascript" src="${ctx}/umeditor/lang/zh-cn/zh-cn.js"></script>
<!-- 实例化编辑器代码 -->
<script type="text/javascript">
    $(function(){
        window.um = UM.getEditor('container', {
        	/* 传入配置参数,可配参数列表看umeditor.config.js */
            toolbar: ['undo redo | bold italic underline']
        });
    });
</script>