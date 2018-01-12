package cn.com.educate.app.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 将jsp页面传递过来的参数，转换成数据库对应的字段的值或值得集合
 * 例如： 传递 teamLeader，代表的是团队经理,则转换成TUAN,即枚举类对应的EmployeeLeveCodeEnum.teamLeader对应的字符串的值.
 * @author xjs
 *
 */
public class EmployeeLevelConverter {
	public static List<String> getLevelList(String level) {
		List<String> levels = new ArrayList<String>();
		/**
		 * 如果为all则为查找所有的人员
		 */
		if("all".equals(level)){
			return levels;
		}else if("teamLeader".equals(level)){
			levels.add(EmployeeLeveCodeEnum.teamLeader.toString());
		}else if("customerLeader".equals(level)){
			levels.add(EmployeeLeveCodeEnum.customerLeader.toString());
		}
		return levels;
	}
}
