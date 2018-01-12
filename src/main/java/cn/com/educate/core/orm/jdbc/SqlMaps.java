package cn.com.educate.core.orm.jdbc;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.common.collect.Lists;

/**
 * 使用JAXB2.0标注的待转换Java Bean.
 */
//根节点
@XmlRootElement(name = "sqlmaps")
public class SqlMaps {

	private List<CustomSql> sqlmaps = Lists.newArrayList();
	//设置对List<Object>的映射, xml为<sqlmaps><sqlmap name="queryAccount"/></sqlmaps>
	@XmlElement(name = "sql")	
	public List<CustomSql> getSqlmaps() {
		return sqlmaps;
	}

	public void setSqlmaps(List<CustomSql> sqlmaps) {
		this.sqlmaps = sqlmaps;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
