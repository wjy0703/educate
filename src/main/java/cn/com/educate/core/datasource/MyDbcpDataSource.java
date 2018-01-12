/**
 * 重载apache dbcp的数据源，使其能够接受加密的数据库密码串。
 * @author jiangxd
 * create at 2011-11-10
 */

package cn.com.educate.core.datasource;

import org.apache.commons.dbcp.BasicDataSource;

import cn.com.educate.core.utils.DesEncrypt;

public class MyDbcpDataSource extends BasicDataSource {
	
	/**
	 * 同时支持加密和非加密的数据库密码串，加密的密码串需要将加密串放到"ENC()"的括号中。
	 */
    public void setPassword(String password) {
    	super.setPassword(password);
    	if (password.indexOf("ENC(")==0){
    		this.password = DesEncrypt.decodeString(password.substring(4, password.length()-1));
    	}
    } 

}
