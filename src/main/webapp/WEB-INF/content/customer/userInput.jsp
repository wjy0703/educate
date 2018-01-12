<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="panel">
<div class="pageContent">
	<form method="post" action="${ctx}/userinfo/saveuser"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" name="id" value="${user.id}" />
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>账户：</label> <input name="account" type="text" size="30"
					alt="请输入账户" value="${user.account }" class="required" <c:if test="${user.id != null}">readonly</c:if>/>
			</p>
			<p>
				<label>姓名：</label> <input name="vname" type="text" size="30"
					alt="请输入姓名" value="${user.vname }" class="required" <c:if test="${user.id != null}">readonly</c:if>/>
			</p>
			<p>
				<label>证件号码：</label> <input name="card" type="text" size="30"
					alt="请输入证件号码" value="${user.card }" class="required" <c:if test="${user.id != null}">readonly</c:if>/>
			</p>
			<p>
				<label>性别：</label> 
					<sen:select clazz="combox required" name="sex" id="sex" coding="sexType" value="${user.sex }" title="请选择"/>
			</p>
			<div class="divider"></div>
			<p>
				<label>手机号码：</label> <input name="phone" type="text" size="30"
					value="${user.phone }" />
			</p>
			<div class="divider"></div>
			<p>
				<label>邮箱：</label> <input name="mail" type="text" size="30"
					value="${user.mail }" />
			</p>
			<p>
				<label>选择机构/部门：</label> <input type="hidden" name="dept.id" value="1" />
				<input type="hidden" name="organizeinfo.id"  value="${user.organizeinfo.id}" />
				<input type="text" id="deptname" class="required" 
					name="organizeinfo.orgname" value="${user.organizeinfo.orgname }"/>
				<a class="btnLook" href="${ctx }/baseinfo/getdept"
								lookupGroup="organi" <c:if test="${user.id != null}">disabled</c:if>><hi:text key="查找带回" /></a>
			</p>

			<p>
				<label>岗位：</label> 
				<c:if test="${canLook=='1'}">
					<sen:select clazz="combox required" name="post" id="post" coding="postType" value="${user.post }" title="请选择" 
					unShowName="超级管理员"/>
				</c:if>
				<c:if test="${canLook=='0'}">
					<sen:select clazz="combox required" name="post" id="post" coding="postType" value="${user.post }" title="请选择" 
					/>
				</c:if>
			</p>

		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div></li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div></div>
