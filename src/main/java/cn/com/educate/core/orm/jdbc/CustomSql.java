package cn.com.educate.core.orm.jdbc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 使用JAXB2.0标注的待转换Java Bean.
 */

//指定子节点的顺序
@XmlType(propOrder = { "name", "before", "after", "sqlString" })
public class CustomSql {

	private String name;
	private String before;
	private String after;
	private String sqlString;
	private String common; 
	
	//设置转换为xml节点中的属性	
	@XmlAttribute
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlAttribute	
	public String getCommon() {
		return common;
	}

	public void setCommon(String common) {
		this.common = common;
	}
	
	@XmlAttribute
	public String getBefore() {
		return before;
	}

	



	public void setBefore(String before) {
		this.before = before;
	}
	
	@XmlAttribute
	public String getAfter() {
		return after;
	}

	public void setAfter(String after) {
		this.after = after;
	}

	@XmlElement(name = "sqlString")
	public String getSqlString() {
		return sqlString;
	}

	public void setSqlString(String sql) {
		this.sqlString = sql;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
