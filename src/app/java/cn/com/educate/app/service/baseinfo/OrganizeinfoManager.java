package cn.com.educate.app.service.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.educate.app.dao.JdbcDao;
import cn.com.educate.app.dao.login.OrganizeinfoDao;
import cn.com.educate.app.dao.login.UserinfoDao;
import cn.com.educate.app.entity.login.Businessinfo;
import cn.com.educate.app.entity.login.Organizeinfo;
import cn.com.educate.app.entity.security.Userinfo;
import cn.com.educate.app.service.security.OperatorDetails;
import cn.com.educate.core.orm.JdbcPage;
import cn.com.educate.core.orm.Page;
import cn.com.educate.core.security.springsecurity.SpringSecurityUtils;



//Spring Bean的标识.
@Component
//默认将类中的所有函数纳入事务管理.
@Transactional
public class OrganizeinfoManager {

	private OrganizeinfoDao organizeinfoDao;
	@Autowired
	public void setOrganizeinfoDao(OrganizeinfoDao organizeinfoDao) {
		this.organizeinfoDao = organizeinfoDao;
	}
	@Autowired
	private UserinfoDao userinfoDao;
	
	private JdbcDao jdbcDao;
	
	@Autowired
	public void setJdbcDao(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	
	/**
     * 根据机构查找员工
     * @param id
     * @return
     */
    public List<Userinfo> findUserByTree(Long id) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("orgid", id);
        return userinfoDao.find(params);
    }
    
    @Transactional(readOnly = true)
	public Organizeinfo getOrgani(Long parentId){
    	Organizeinfo upOrgani = new Organizeinfo();
		if(parentId == 0){
			OperatorDetails operator = (OperatorDetails)SpringSecurityUtils.getCurrentUser();
			Businessinfo bus = new Businessinfo();
			upOrgani.setId(new Long(0));
			upOrgani.setOrgname("组织机构");
			bus.setId(operator.getBusid());
			upOrgani.setBusinessinfo(bus);
		}else{
			upOrgani = organizeinfoDao.get(parentId);
		}
		return upOrgani;
	}
    
    public void saveOrgani(Organizeinfo entity) {
    	organizeinfoDao.save(entity);
	}
    
    /**
	 * 重载查询组织机构方法
	 *
	 * @param sysType 系统属性
	 * @return
	 * @author 
	 * @date 2014-3-31 下午4:56:12
	 */
    public String buildOrganiByType(){
        List<Organizeinfo> organiList = getOrganiByParent(new Long(0));
        StringBuffer tree = new StringBuffer(); 
        for(Organizeinfo o : organiList){
//            if ("".equals(orgCode) || orgCode.equals(o.getOrganiCode())) {
                tree.append(buildOrgnizationNode(o.getId()));
//            }
        }
        return tree.toString();
    }
    
    private String buildOrgnizationNode(Long orgId){
    	Organizeinfo organization = organizeinfoDao.get(orgId);
		StringBuffer node = new StringBuffer();
		node.append("<li><a href=\"javascript:\" onclick=\"$.bringBack({id:'"+organization.getId()+"', name:'"+organization.getOrgname()+"',busid:'"+organization.getBusinessinfo().getId()+"'})\">"+organization.getOrgname()+"</a>");
		List<Organizeinfo> organiList = getOrganiByParent(orgId);
		if(organiList != null && organiList.size()>0 ){
			node.append("<ul>");
			for (Organizeinfo o : organiList) {
				node.append(buildOrgnizationNode(o.getId()));
			}
			node.append("</ul>");
		}
		node.append("</li>");
		return node.toString();
	}
	/**
	 * 
	 * 生成树的同时设置节点的onclick为clickfunction，这种写法并不好，争取到写法是在前台形成js。
	 * 
	 * @param clickFunction
	 * @return
	 */
	public String buildOrganiByTopId(String clickFunction){
        List<Organizeinfo> organiList = getOrganiByParent(new Long(0));
        StringBuffer tree = new StringBuffer(); 
        //tree.append("<ul class=\"tree treeFolder\"><li><a href=\"#\">组织机构</a><ul>");
        for(Organizeinfo o : organiList){
            tree.append(buildOrgnizationNode(o.getId(),clickFunction));
        }
        //tree.append("</ul></li></ul>");
        return tree.toString();
    }
	private String buildOrgnizationNode(Long orgId,String clickFunction){
		Organizeinfo organization = organizeinfoDao.get(orgId);
        StringBuffer node = new StringBuffer();
        //node.append("<li><a href=\"javascript:\" onclick=\""+ clickFunction +"({id:'"+organization.getId()+"', name:'"+organization.getRganiName()+"',organiFlag:'"+organization.getOrganiFlag()+"',levelMess:'"+organization.getLevelMess()+"',organiCode:'"+organization.getOrganiCode()+"'})\">"+organization.getRganiName()+"</a>");
        node.append("<li><a id=\""+organization.getId()+"\" href=\"organize/getUserByTreeId/"+organization.getId()+"\" target=\"ajax\" rel=\"cfmdBox\" title=\"组织机构管理\" onclick=\""+ clickFunction +"({id:'"+organization.getId()+"'})\">"+organization.getOrgname()+"</a>");
        List<Organizeinfo> organiList = getOrganiByParent(orgId);
        if(organiList != null && organiList.size()>0 ){
            node.append("<ul>");
            for (Organizeinfo o : organiList) {
                node.append(buildOrgnizationNode(o.getId(),clickFunction));
            }
            node.append("</ul>");
        }
        node.append("</li>");
        return node.toString();
    }
	public List<Organizeinfo> getOrganiByParent(Long parentId) {
		return organizeinfoDao.getOrganiByParent(parentId);
	}
	
	@Transactional(readOnly = true)
	public Page<Organizeinfo> searchOrganizeinfo(final Page<Organizeinfo> page, final Map<String,Object> filters) {
		return organizeinfoDao.queryOrganizeinfo(page, filters);
	}
	@Transactional(readOnly = true)
	public Organizeinfo getOrganizeinfo(Long id) {
		return organizeinfoDao.get(id);
	}

	public void saveOrganizeinfo(Organizeinfo entity) {
		organizeinfoDao.save(entity);
	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteOrganizeinfo(Long id) {
		organizeinfoDao.delete(id);
	}
	
	public boolean batchDelOrganizeinfo(String[] ids){
		
		try {
			for(String id: ids){
				deleteOrganizeinfo(Long.valueOf(id));
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void insertOrganizeinfo(String insertName,Map<String,Object> conditions){
		jdbcDao.insertBySqlTemplate(insertName, conditions);
	}
	
	public void updateOrganizeinfo(String updateName,Map<String,Object> conditions){
		jdbcDao.updateBySqlTemplate(updateName, conditions);
	}
	@Transactional(readOnly = true)
	public List<Map<String,Object>> searchOrganizeinfo(String queryName,Map<String,Object> filter,JdbcPage page)
	{
		StringBuffer sql=new StringBuffer();
		String value = "";
		//名称
		if(filter.containsKey("orgname")){
			value = String.valueOf(filter.get("orgname"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.orgname = '").append(value).append("'");
				//sql = sql + " and a.orgname = '" +  value + "'";
			}
		}
		//地址
		if(filter.containsKey("address")){
			value = String.valueOf(filter.get("address"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.address = '").append(value).append("'");
				//sql = sql + " and a.address = '" +  value + "'";
			}
		}
		//类别（行业、区域、门店）
		if(filter.containsKey("orgflag")){
			value = String.valueOf(filter.get("orgflag"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.orgflag = '").append(value).append("'");
				//sql = sql + " and a.orgflag = '" +  value + "'";
			}
		}
		//属性（在用、停用）
		if(filter.containsKey("vtypes")){
			value = String.valueOf(filter.get("vtypes"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.vtypes = '").append(value).append("'");
				//sql = sql + " and a.vtypes = '" +  value + "'";
			}
		}
		//创建时间
		if(filter.containsKey("createtime")){
			value = String.valueOf(filter.get("createtime"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.createtime = '").append(value).append("'");
				//sql = sql + " and a.createtime = '" +  value + "'";
			}
		}
		//修改时间
		if(filter.containsKey("modifytime")){
			value = String.valueOf(filter.get("modifytime"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.modifytime = '").append(value).append("'");
				//sql = sql + " and a.modifytime = '" +  value + "'";
			}
		}
		//创建人
		if(filter.containsKey("createuser")){
			value = String.valueOf(filter.get("createuser"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.createuser = '").append(value).append("'");
				//sql = sql + " and a.createuser = '" +  value + "'";
			}
		}
		//修改人
		if(filter.containsKey("modifyuser")){
			value = String.valueOf(filter.get("modifyuser"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.modifyuser = '").append(value).append("'");
				//sql = sql + " and a.modifyuser = '" +  value + "'";
			}
		}
		//所属企业
		if(filter.containsKey("busid")){
			value = String.valueOf(filter.get("busid"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.busid = '").append(value).append("'");
				//sql = sql + " and a.busid = '" +  value + "'";
			}
		}
		//上级ID
		if(filter.containsKey("parentid")){
			value = String.valueOf(filter.get("parentid"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.parentid = '").append(value).append("'");
				//sql = sql + " and a.parentid = '" +  value + "'";
			}
		}
		//所有上级
		if(filter.containsKey("parentids")){
			value = String.valueOf(filter.get("parentids"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.parentids = '").append(value).append("'");
				//sql = sql + " and a.parentids = '" +  value + "'";
			}
		}
		
		if (page.getOrderBy()!=null){
//			sql = sql + " order by " + page.getOrderBy() + " " + page.getOrder();
			sql.append(" order by ").append(page.getOrderBy()).append(" ").append(page.getOrder());
		}
		
		System.out.println("sql=======>" + sql.toString());
		Map<String, Object> conditions = new HashMap<String,Object>();
		conditions.put("sql", sql.toString());
		
		return jdbcDao.searchPagesByMergeSqlTemplate(queryName, conditions, page);
	}
}
