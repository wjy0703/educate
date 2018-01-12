package cn.com.educate.app.util;

import org.hibernate.tool.hbm2x.StringUtils;


/**
 * 将jsp页面传递过来的参数，转换成数据库对应的字段的值或值得集合 例如： 传递
 * teamLeader，代表的是团队经理,则转换成TUAN,即枚举类对应的EmployeeLeveCodeEnum.teamLeader对应的字符串的值.
 * 
 * @author xjs
 * 
 */
public class MetaDataTypeConverter {

    public static String getCodingName(String coding) {

    	if (StringUtils.equals("bank", coding)) {                 // 银行(01-->中国工商银行,02-->中国建设银行,03-->中国邮政储蓄银行有限责任公司,04-->中国银行,05-->招商银行,06-->中国农业银行,07-->中国光大银行,09-->中国民生银行,10-->兴业银行,12-->中国交通银行,13-->中国农商银行,14-->广东发展银行,15-->中国深发银行,11-->中信银行)
            
            return MetaDataTypeEnum.BANK.toString();
        
        } else if (StringUtils.equals("cardType", coding)) {             // 证件类型(身份证-->身份证，军官证-->军官证，护照-->护照，户口簿-->户口簿，港澳居民往来大陆通行证-->港澳居民往来大陆通行证)
        
            return MetaDataTypeEnum.CARD_TYPE.toString();
        
        }else if(StringUtils.equals("marryState", coding)){				//婚姻状况(5-->已婚有子女，3-->已婚无子女，2-->未婚，2-->再婚，0-->离婚)
        	
        	return MetaDataTypeEnum.MARRY_STATE.toString();
        	
        }else if(StringUtils.equals("studyLevel", coding)){     		//文化程度(4-->硕士及以上，3-->本科，2-->大专，1-->高中专，0-->初中及以下)
        	
        	return MetaDataTypeEnum.STUDY_LEVEL.toString();
        	
        }else if(StringUtils.equals("yesOrNo", coding)){    //是否选择内容(1-->是，0-->否)
        	
        	return MetaDataTypeEnum.YES_OR_NO.toString();
        	
        }else if(StringUtils.equals("vtypes", coding)){    //是否在用：在用、历史
        	
        	return MetaDataTypeEnum.VTYPES.toString();
        	
        }else if(StringUtils.equals("sexType", coding)){                //性别(男-->男，女-->女)
            
            return MetaDataTypeEnum.SEX_TYPE.toString();
            
        }else if(StringUtils.equals("postType", coding)){                //职业
            
            return MetaDataTypeEnum.POST_TYPE.toString();
            
        }else if(StringUtils.equals("systype", coding)){                //系统属性，系统、业务
            
            return MetaDataTypeEnum.SYS_TYPE.toString();
            
        }else if(StringUtils.equals("menutype", coding)){                //菜单属性，一级、二级、三级
            
            return MetaDataTypeEnum.MENU_TYPE.toString();
            
        }else if(StringUtils.equals("orgflags", coding)){                //门店类别，行业，区域，门店
            
            return MetaDataTypeEnum.ORGFLAG.toString();
            
        }else if(StringUtils.equals("onjob", coding)){                //是否在职，在职，离职
            
            return MetaDataTypeEnum.ON_JOB.toString();
            
        }else if(StringUtils.equals("cyckes", coding)){                //周期，一个月，半年，一年，两年
            
            return MetaDataTypeEnum.CYCKES.toString();
            
        }
    	
    	//50003 liveStateType
        else {
            return "";
        }
    }
}
