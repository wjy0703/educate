package cn.com.educate.app.excel.utils;

import org.apache.poi.ss.usermodel.Cell;


public class LoanAuditXLS {
    GenerateSpecialXLS specialXLS;
    
    private static String[][] data_one = {   //2-9
        
        {"进件地区", null,"申请产品", null,"申请金额",null,"申请期限",null},
        {"客户来源",null, "借款目的",",D,H"},
        {"身份信息,A,H,style:header"},
        {"客户姓名",null,null,null,"身份号码",",F,H"},
        {"户籍地",",B,C",null,null,"婚姻状况",",F,H"},
        {"信用信息,A,H,style:header"},
        {"信用卡（在申请人门店填写，自动提取，信审有修改权限）,A,H,style:header"},
        {"激活账户总数","授信总额","在用账户数","单张最高授信","单张最低","已使用额度","月还款（10%）","信用卡使用率"}
    };
    
    private static String[][] data_two = {  //10-13
        
        {"逾期情况说明",",B,H"},
        {"本人近6个月查询记录：",",B,H"},
        {"借  款（在申请人门店填写，自动提取，信审有修改权限）,A,H,style:header"},
        {"银行或公司","借款类别","借款总额","借款期限","借款余额","月 还 款","有无还款证明","还款情况说明"}
    };
    
    private static String[][] data_three = {  //14-19
    	
    	{"银行流水信息（在申请人门店填写，自动提取，信审有修改权限）,A,H,style:header"},
    	{"银行名  ,A,D",null,null,null,"盖章（每个月）及余额加减是否正确: ,E,H"},
    	{null," 月"," 月"," 月"," 月"," 月"," 月","月 均"},
    	{"流水存入",null,null,null,null,null,null,null},
    	{"备注：",null,null,null,null,null,null,null},
    	{"截止到       年      月      日尚有存款余额: ,A,H"}
    };
    
    
    private static String[][] data_four = {  //20-25
    	
    	{"其它流水统计",",B,H"},
    	{"住址信息（在申请人门店填写，自动提取，信审有修改权限）,A,H,style:header"},
    	{"现住址",",B,H"},
    	{"居住成员：",",B,C",null,",D,F",null,null,"月租金/月供",null},
    	{"资产信息（如有，自动提取）,A,H,style:header"},
    	{"房产地址 1,A,D",null,null,null,"产权归属","抵押或无抵押","借款佘额","估 值"}
    	
    };
    
    private static String[][] data_five = {  //26
    	
    	{"估值/确认途径：,A,D",null,null,null,"市场报价（㎡）：,E,H"}
    	
    };
    
    private static String[][] data_six = {  //单位基本情况  27-32
    	
    	{"单位基本情况（如有，自动提取）,A,H,style:header"},
    	{"公司名称",",B,F",null,null,null,null,"客户岗位名称",null},
    	{"公司地址",",B,F",null,null,null,null,"任职年期",null},
    	{"工作职责",",B,H"},
    	{"工资收入",",B,C",null,"工作证明工资",null,"银行代发工资",",G,H"},
    	{"社保/公积金",",B,C",null,"社保/公积金缴费基数,D,E",null,null,"公司成立时间",null}
    };
    
    private static String[][] data_seven = {  // 信息确认  33-40
    	
    	{"信息确认,A,H"},
    	{"确认途径,A,B",null,"一致性,C,D,E",null,null,"情况说明,F,H"},
    	{"114 / 10000查询",null,",C,D,E",null,null,",F,H"},
    	{"红盾网/工商局网站查询",null,",C,D,E",null,null,",F,H"},
    	{"百度网查公司/个人信息",null,",C,D,E",null,null,",F,H"},
    	{"P2P网络逾期黑名单查询",null,",C,D,E",null,null,",F,H"},
    	{"全国法院被执行人信息查询",null,",C,D,E",null,null,",F,H"},
    	{"其他重要资料说明及风险点:,A,H"}
    };
    
    private static String[][] data_eight = {  //   41-42
    	
    	{"我司老客户,A,H"},
    	{"进件日期","借款金额","借款期限","约定月还款","是否已结清","提前期限","是否有逾期","有无重要信息变更"}
    	
    };
    
    private static String[][] data_nine = {  //  43-44
    	
    	{"审核建议   其它：,A,H"},
    	{"产品类别","是否本地人","信用情况","房产/车产","月均流水","外访费","建议额度","建议期限"}
    	
    };
    
    private static String[][] data_ten = {  //   45
    	
    	{"加急费",null,"共同借款人",null,"共借人身码",",F,H"}
    	
    };
    
    private static String[][] data_eleven = {  //   46-49
    	
    	{"DE1:,A,B",null,"DE2: ,C,D",null,"初 审：",null,"日 期：",null},
    	{"日 期：,A,B",null,"DR2: ,C,D",null,null,null,null,null},
    	{"LL1:,A,B",null,"LL2: ,C,D",null,null,null,null,null},
    	{"注：工薪类需计算以上数值,A,B",null,null,null,null,null,null,null}
    };
    
    private static String[][] data_twelve = {  //  50-58 企业详细信息（如有，自动提取）
    	
    	{"企业详细信息（如有，自动提取）,A,H,style:header"},
    	{"公司名称,A,B",null,",C,H"},
    	{"公司型式,A,B",null,"个体户 / 合伙公司 / 有限公司 / 其他:,C,H"},
    	{"公司经营地点,A,B",null,",C,H"},
    	{"成立时间,A,B",null,",C,D",null,"场地合同有效期,E,F",null,",G,H"},
    	{"注册/实收资本,A,B",null,",C,H"},
    	{"股东/股权比例,A,B",null,",C,H"},
    	{"变更情况,A,B",null,",C,H"},
    	{"业务经营情况,A,B",null,",C,H"}
    };
    
    private static String[][] data_thirteen = {  //  59-60 上游采购合同核实
    	
    	{"上游采购合同核实,A,H"},
    	{"公司名称","合同类型","合同金额","合同期限","结算方式","电话及来源,F,G",null,"电话核实情况"}
    };
    
    private static String[][] data_fourteen = {  //  61-62 下游销售合同核实
    	
    	{"下游销售合同核实,A,H"},
    	{"公司名称","合同类型","合同金额","合同期限","结算方式","电话及来源,F,G",null,"电话核实情况"}
    };
    
    private static String[][] data_fifteen = {  //  61-62 下游销售合同核实
    	
    	{"对公银行流水信息,A,H"},
    	{"银行名,A,D",null,null,null,"盖章（每个月）及余额加减是否正确:,E,H"},
    	{null," 月"," 月"," 月"," 月"," 月"," 月","月 均"},
    	{"流水存入",null,null,null,null,null,null,null},
    	{"备注：",null,null,null,null,null,null,null},
    	{"截止到       年      月      日尚有存款余额: ,A,H"}
    };
    
    
    
    
    
    
    
    
    
    
    
    
    
    private static String[][] empty_data={{}};
    
    public static void main(String[] args) {
//        LoanAuditXLS test = new LoanAuditXLS();
//        GenerateSpecialXLS specialXLS = new GenerateSpecialXLS("审批表");
//        specialXLS.createHeader();
//        test.setXls(specialXLS);
        
//        test.createBaseInfoOne();
//        test.createBaseInfoTwo();
//        test.createBaseInfoThree();
//        test.createBaseInfoFour();
//        test.createBaseInfoFive();
//        test.createBaseInfoSix();
//        test.createBaseInfoSeven();
//        test.createBaseInfoEight();
//        test.createBaseInfoNine();
//        test.createBaseInfoTen();
//        test.createBaseInfoEleven();
//        test.createBaseInfoTwelve();
//        test.createBaseInfoThirteen();
//        test.createBaseInfoFourteen();
//        test.createBaseInfoFifteen();
        
        
        
//        test.createBaseEmptyInfoOne();
//     
//        
//        
//        test.setStyle();
//         // Write the output to a file
//        test.saveFile("D:\\timesheet2.xls");
    }


	 
	private void setStyle() {
        specialXLS.setStyle();        
    }

    private void saveFile(String file) {
        specialXLS.saveFile(file);    
    }

    private void setXls(GenerateSpecialXLS specialXLS) {
        this.specialXLS = specialXLS;
    }


    private  void setCellValue(int rowIndex, int colIndex, String value) {
        int  rowPrevious = specialXLS.getRowPrevious();
        Cell cell = specialXLS.getCell(rowPrevious + rowIndex , colIndex);
        cell.setCellValue(value);
    }

}
