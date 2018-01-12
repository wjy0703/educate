package cn.com.educate.app.service.baseinfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.educate.app.dao.baseinfo.CityinfoDao;
import cn.com.educate.app.entity.login.Cityinfo;



//Spring Bean的标识.
@Component
//默认将类中的所有函数纳入事务管理.
@Transactional
public class CityinfoManager {

	private CityinfoDao cityinfoDao;
	@Autowired
	public void setCityinfoDao(CityinfoDao cityinfoDao) {
		this.cityinfoDao = cityinfoDao;
	}
	
	@Transactional(readOnly = true)
	public Cityinfo getCityinfo(Long id) {
		return cityinfoDao.get(id);
	}

	public void saveCityinfo(Cityinfo entity) {
		cityinfoDao.save(entity);
	}
	
	
	/**
     * 检查是否唯一. propertyName 所要验证的字段名
     * 
     * @return newValue在数据库中唯一或等于oldValue时返回true.
     */
    @Transactional(readOnly = true)
    public boolean isCheckUnique(String propertyName, String newValue, String oldValue) {
        return cityinfoDao.isPropertyUnique(propertyName, newValue, oldValue);
    }
    
    /**
     * 
     * @param string
     * @return
     */
    public String buildCityByTopId(String clickFunction) {
        // 第一遍这里装了所有 省
        List<Cityinfo> cityList = getCityByParent(new Long(0));
        StringBuffer tree = new StringBuffer();
        for (Cityinfo o : cityList) {
            tree.append(buildCitytionNode(o.getId(), clickFunction));
        }
        return tree.toString();
    }
    
    /**
     * @param parentId
     * @return
     */
    public List<Cityinfo> getCityByParent(Long parentId) {
        return cityinfoDao.getCityByParent(parentId);
    }
    
    /**
     * 
     * @param id
     * @param clickFunction
     * @return
     */
    private String buildCitytionNode(Long orgId, String clickFunction) {
    	Cityinfo cityinfo = cityinfoDao.get(orgId);
        StringBuffer node = new StringBuffer();
        node.append("<li><a id=\"" + cityinfo.getId() + "\" href=\"cityinfo/getEmpByTreeId/" + cityinfo.getId()
                + "\" target=\"ajax\" rel=\"cfmdBox\" title=\"城市管理\" onclick=\"" + clickFunction + "({id:'"
                + cityinfo.getId() + "'})\">" + cityinfo.getVname() + "</a>");
        // 走第二遍sql 查询市
        List<Cityinfo> cityList = getCityByParent(orgId);
        if (cityList != null && cityList.size() > 0) {
            node.append("<ul>");
            for (Cityinfo o : cityList) {
                node.append(buildCitytionNode2(o.getId(), clickFunction));
            }
            node.append("</ul>");
        }
        node.append("</li>");
        return node.toString();
    }

    private String buildCitytionNode2(Long orgId, String clickFunction) {
    	Cityinfo cityinfo = cityinfoDao.get(orgId);
        StringBuffer node = new StringBuffer();
        node.append("<li><a id=\"" + cityinfo.getId() + "\" href=\"cityinfo/getEmpByTreeId/" + cityinfo.getId()
                + "\" target=\"ajax\" rel=\"cfmdBox\" title=\"城市管理\" onclick=\"" + clickFunction + "({id:'"
                + cityinfo.getId() + "'})\">" + cityinfo.getVname() + "</a>");
        node.append("</li>");
        return node.toString();
    }
    
    /**
     * 根据父id找地3级城市
     * 
     * @param id
     * @return
     */
    public List<Cityinfo> findEmpByTree(Long id) {
        String hql = "from Cityinfo city  where city.parent.id=" + id + "and city.vtypes='0'  order by city.id";
        return cityinfoDao.find(hql);
    }

    public List<Cityinfo> findEmpByTreeInfo(Long id) {
        String hql = "from Cityinfo city  where city.id=" + id;
        return cityinfoDao.find(hql);
    }
    
    @Transactional(readOnly = true)
    public Cityinfo getBaseCity(Long parentId) {
    	Cityinfo baseCity = new Cityinfo();
        if (parentId == 0) {
            baseCity.setId(new Long(0));
            baseCity.setVname("城市管理");
        } else {
            baseCity = cityinfoDao.get(parentId);
        }
        return baseCity;
    }
    
    public boolean delDep(String id) {
        boolean flag = true;
        List<Cityinfo> list = cityinfoDao.findBy("parent.id", Long.valueOf(id));
        if (list != null && list.size() > 0) {
            flag = false;
        } else {
        	cityinfoDao.delete(Long.valueOf(id));
        }
        return flag;
    }
}
