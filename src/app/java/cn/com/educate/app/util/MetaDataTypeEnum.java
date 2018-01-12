package cn.com.educate.app.util;

public enum MetaDataTypeEnum {

	
	/**
	 * 银行
	 */
	BANK {

		@Override
		public String toString() {
			return "0001";
		}
	},
	/**
	 * 门店类别，行业，区域，门店
	 */
	ORGFLAG {

		@Override
		public String toString() {
			return "0002";
		}
	}
	,
	/**
	 * 证件类型 ,身份证等
	 */
	CARD_TYPE {

		@Override
		public String toString() {
			return "0005";
		}
	},
	/**
	 * 婚姻状况,已婚有子女，已婚无子女，未婚，再婚，离婚
	 */
	MARRY_STATE {

		@Override
		public String toString() {
			return "30200";
		}
	},
	/**
	 * 文化程度,硕士及以上，本科，大专，高中专，初中及以下
	 */
	STUDY_LEVEL {

		@Override
		public String toString() {
			return "30300";
		}
	},
	/**
	 * 是否选择 : yes，no
	 */
	YES_OR_NO {

		@Override
		public String toString() {
			return "40000";
		}
	}
    ,
    /**
     * 是否在用：在用、历史
     */
    VTYPES{
		@Override
		public String toString() {
			return "vtypes";
		}	
    },
	/**
	 * 性别
	 */
	SEX_TYPE {
		@Override
		public String toString() {
			return "0004";
		}
	},
	/**
	 * 职业
	 */
	POST_TYPE {
		@Override
		public String toString() {
			return "0003";
		}
	},
	/**
	 * 系统属性，系统、业务
	 */
	SYS_TYPE {
		@Override
		public String toString() {
			return "systype";
		}
	},
	/**
	 * 菜单属性，一级、二级、三级
	 */
	MENU_TYPE {
		@Override
		public String toString() {
			return "menutype";
		}
	},
	/**
	 * 是否在职，在职，离职
	 */
	ON_JOB {
		@Override
		public String toString() {
			return "onjob";
		}
	}
	,
	/**
	 * 周期，一个月，半年，一年，两年
	 */
	CYCKES {
		@Override
		public String toString() {
			return "cyckes";
		}
	}
	
}
