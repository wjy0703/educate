package cn.com.educate.app.entity.login;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 菜单类
 * @author xjs
 *
 */
public class UpdatedMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1960184739612606201L;
    
	protected Long id;
	
	private String menuName;
	
	private String menuUrl;
	
	private Integer menuType;
	
	private Integer levelId;
	
	private Integer sortNo;
	
	private List<UpdatedMenu> subMenu;
	
	
	/**
	 * 窗口打开目标区域
	 * 取值：_blank, 默认：navTab,
	 */
	private String target;

	/**
	 * 窗口引用名称，用来标识不同功能的窗口
	 */
	private String rel;
	
	/**
	 * 打开的页面是否是外部页面，默认值：P
	 */
	private String external;
	
	/**
	 * 配合rel使用，决定要打开的页面是否在相同ref页面引用中打开。默认值：P
	 */
	private String fresh;
	
	/**
	 * 自定义打开窗口的标签,默认值为空，当等于空时，打开的页面标签标题使用menuName值。
	 */
	private String title;
	
	private String sts;
	
   

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<UpdatedMenu> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<UpdatedMenu> subMenu) {
		this.subMenu = subMenu;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}


	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}


	public String getExternal() {
		return external;
	}

	public void setExternal(String external) {
		this.external = external;
	}


	public String getFresh() {
		return fresh;
	}

	public void setFresh(String fresh) {
		this.fresh = fresh;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getSts() {
		return sts;
	}


	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}


	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}


	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}


	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}


	public String getAttrTarget(){
		String _target = target;
		if (null==target || "".equals(target) || "null".equals(target)){
			_target = "navTab";
		}
		return "target=\"" + _target + "\" ";
	}
	
	
	public String getAttrRel(){
		String _rel = rel;
		if (null==rel || "".equals(rel) || "null".equals(rel)){
			_rel = "rel_"+ getId();
		}
		return "rel=\"" + _rel + "\" ";
	}

	
	public String getAttrExternal(){
		if (external.equals("Y")){
			return "external=\"true\" ";
		}
		else if(external.equals("N")){
			return "external=\"false\" ";
		}
		else{
			return "";
		}
	}


	public String getAttrFresh(){
		if (fresh.equals("N")){
			return "fresh=\"false\" ";
		}
		else if (fresh.equals("Y")){
			return "fresh=\"true\" ";
		}
		else{
			return "";
		}
	}
	

	public String getAttrTitle(){		
		if (null!=title && !"".equals(title) && !"null".equals(title)){
			return "title=\"" + title + "\" ";
		}
		else{
			return "";
		}
	}


	public String getAttrMenuUrl(){
		/*if (null!=menuUrl && !"".equals(menuUrl) && !"null".equals(menuUrl)){
			if(menuUrl.indexOf("http://")==0){
				return "href=\"" + menuUrl + "\" ";
			}
			else if(menuUrl.indexOf("javascript:")==0){
				return "href=\"" + menuUrl + "\" ";
			}
			else{
				return "href=\"_CTX_" + menuUrl + "\" ";
			}
		}
		else{
			return "";
		}*/
		return menuUrl;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UpdatedMenu other = (UpdatedMenu) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}