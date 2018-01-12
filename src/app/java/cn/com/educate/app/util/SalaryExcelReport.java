package cn.com.educate.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.tool.hbm2x.StringUtils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 汇金端 业绩统计，按照excel模板生成统计报表
 * @author xjs
 *
 */
public class SalaryExcelReport {
	private  ActiveXComponent xl;
	private  Dispatch workbooks = null;
	private  Dispatch workbook = null;
	private  Dispatch sheet = null;
	private  Dispatch sheets = null;
	private  String filename = null;
	private  boolean readonly = false;
    private Dispatch document = null;
	/* 统计分界线 */
	private static final String PERIOD_KEY= "periodMoney";
	private static final String PERIOD_LOOP_KEY= "loopPeriodMoney";
	private static final String SUM_COUNT_KEY= "sumCount"; // 总数
	private static Map<String,String> productToColumn;     // 产品到列对应关系 6期 ->E 
	
	private static final String[] sumCols = {"E","F","G","H","I","J","K","L","M","N","O","P","Q","R","T","U"};
	static{
	    productToColumn = new HashMap<String,String>();
	    char firstColumn = 'F';
	    productToColumn.put(6+PERIOD_KEY, (char)(firstColumn++) +"");
	    productToColumn.put(6+PERIOD_LOOP_KEY, (char)(firstColumn++) +"");
	    productToColumn.put(9+PERIOD_KEY, (char)(firstColumn++) +"");
        productToColumn.put(9+PERIOD_LOOP_KEY, (char)(firstColumn++) +"");
        productToColumn.put(12+PERIOD_KEY, (char)(firstColumn++) +"");
        productToColumn.put(12+PERIOD_LOOP_KEY, (char)(firstColumn++) +"");
        productToColumn.put(18+PERIOD_KEY, (char)(firstColumn++) +"");
        productToColumn.put(18+PERIOD_LOOP_KEY, (char)(firstColumn++) +"");
        productToColumn.put(24+PERIOD_KEY, (char)(firstColumn++) +"");
        productToColumn.put(24+PERIOD_LOOP_KEY, (char)(firstColumn++) +"");
        productToColumn.put(36+PERIOD_KEY, (char)(firstColumn++) +"");
        productToColumn.put(36+PERIOD_LOOP_KEY, (char)(firstColumn++) +"");
	}

	// 打开Excel文档
	public  void openExcel(String file, boolean f) {
		
		try {
			ComThread.InitSTA();
			filename = file;
			if(xl==null)
				xl = new ActiveXComponent("Excel.Application");
			xl.setProperty("Visible", new Variant(f));
			if(workbooks==null)
				workbooks = xl.getProperty("Workbooks").toDispatch();
			workbook = Dispatch.invoke(
					(Dispatch) workbooks,
					"Open",
					Dispatch.Method,
					new Object[] { filename, new Variant(false),
							new Variant(readonly) },// 是否以只读方式打开
					new int[1]).toDispatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  void createNewDocument() {
		xl = new ActiveXComponent("Excel.Application");
		// Find the Documents collection object maintained by Word
		Dispatch documents = Dispatch.get(xl, "Documents").toDispatch();
		// Call the Add method of the Documents collection to create
		// a new document to edit
		document  = Dispatch.call(documents, "Add").toDispatch();
	}

	// 关闭Excel文档
	public  void closeExcel(String newFileName,boolean f) {
		try {
			Dispatch.call((Dispatch) workbook, "SaveAs",newFileName);
			Dispatch.call((Dispatch) workbook, "Close", new Variant(f));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			releaseSource();
		}
	}
	
	/**
     * 释放资源
     */
    public  void releaseSource(){
        if(xl!=null){
            xl.invoke("Quit", new Variant[0]);
            xl = null;
        }
        workbooks = null;
        ComThread.Release();
    }

	// 写入值
	public  void setValue(String position, String type, String value) {
		sheet = Dispatch.get((Dispatch) workbook, "ActiveSheet").toDispatch();
		Object cell = Dispatch.invoke((Dispatch) sheet, "Range", Dispatch.Get,
				new Object[] { position }, new int[1]).toDispatch();
		Dispatch.put((Dispatch) cell, type, value);
	}
	// 写入值
	public  void setValue(String position, String type, double value) {
		sheet = Dispatch.get((Dispatch) workbook, "ActiveSheet").toDispatch();
		Object cell = Dispatch.invoke((Dispatch) sheet, "Range", Dispatch.Get,
				new Object[] { position }, new int[1]).toDispatch();
		Dispatch.put((Dispatch) cell, type, value);
	}
	
	/**
	 * 
	 * @param rowNum
	 * @param templateTeamRow
	 * @param departRow
	 * @return
	 */
	public  int copyRow(int rowNum,int templateTeamRow,int departRow){
		sheet = Dispatch.get((Dispatch) workbook, "ActiveSheet").toDispatch();
		Dispatch row = Dispatch.call((Dispatch)sheet, "Rows", new Variant(templateTeamRow)).toDispatch();
		Dispatch.call(row, "Copy");
		//Dispatch row2 = Dispatch.call((Dispatch)sheet, "Rows", new Variant(templateTeamRow)).toDispatch();
		Dispatch.call(row, "Insert");
		//选择理财经理的插入位置,因为上面已经插入一行，所以插入位置为templateTeamRow+2
		Dispatch customerRow = Dispatch.call((Dispatch)sheet, "Rows", new Variant(templateTeamRow+2)).toDispatch();
		
		for(int i = 0; i < rowNum ; i++){
			Dispatch.call(customerRow, "Copy");
			Dispatch.call(row, "Insert");
		}

		changeSumFormat(templateTeamRow,sumCols,rowNum);
		changeDepartSumFormat(templateTeamRow,sumCols,departRow);
		return templateTeamRow + rowNum + 1;
   }
	
   /**
    * 修改门店经理所在行的excel公式
    * @param templateTeamRow
    * @param sumCols
    * @param departRow
    */
    private  void changeDepartSumFormat(int templateTeamRow,String[] sumCols, int departRow) {
    	
	   for(int i = 0 ; i < sumCols.length ; i++){
		   String cellPosition = sumCols[i] + departRow;
		   String formula =getFormula(cellPosition);
		   if(formula != null){
			   if(formula.startsWith("=")){
				   formula += "+" + sumCols[i]+templateTeamRow;
			   }else{
				   formula = "=" + sumCols[i]+templateTeamRow;
			   }
		   }else{
			   formula = "=" + sumCols[i]+templateTeamRow;
		   }
		   setAnother(cellPosition,"Value",formula);
	   }
	}

   /**
    * 修改团队经理所在行的计算公式
    * @param templateTeamRow
    * @param sumCols
    * @param rowNumsOfSum
    */
   private  void changeSumFormat(int templateTeamRow, String[] sumCols,int rowNumsOfSum) {
	   int begin = templateTeamRow + 1;
	   int end =   templateTeamRow + rowNumsOfSum;
	   for(int i = 0 ; i < sumCols.length ; i++){
		   String cellPosition =  sumCols[i] + templateTeamRow;
		   String sumFormula = "=SUM(" +  sumCols[i] + begin+":" + sumCols[i] + end +")";
		   setAnother(cellPosition, "Formula",sumFormula);
	   }
		
	}
	 /**
    *  得到sheets的集合对象
    * @return
    */
   public Dispatch getSheets() {
       if(sheets==null)
           sheets = Dispatch.get(workbook, "sheets").toDispatch();
       return sheets;
   }
   
   /**
    * 得到当前sheet
    * @return
    */
   public Dispatch getCurrentSheet() {
       sheet = Dispatch.get(workbook, "ActiveSheet").toDispatch();
       return sheet;
   }

   /**
    * 通过工作表名字得到工作表
    * @param name sheetName
    * @return
    */
   public Dispatch getSheetByName(String name) {
       return Dispatch.invoke(getSheets(), "Item", Dispatch.Get, new Object[]{name}, new int[1]).toDispatch();
   }
   
   /**
    * 通过工作表索引得到工作表(第一个工作簿index为1)
    * @param index
    * @return  sheet对象
    */
   public Dispatch getSheetByIndex(Integer index) {
       return Dispatch.invoke(getSheets(), "Item", Dispatch.Get, new Object[]{index}, new int[1]).toDispatch();
   }
   
   public  void deleteRow(int rowNum){
		sheet = Dispatch.get((Dispatch) workbook, "ActiveSheet").toDispatch();
		Dispatch row = Dispatch.call((Dispatch)sheet, "Rows", new Variant(rowNum)).toDispatch();
		Dispatch.call(row, "Delete");
   }
	
	private  void setAnother(String position, String type,String formula) {
		sheet = Dispatch.get((Dispatch) workbook, "ActiveSheet").toDispatch();
		Object cell = Dispatch.invoke((Dispatch) sheet, "Range", Dispatch.Get,
				new Object[] { position }, new int[1]).toDispatch();
		Dispatch.put((Dispatch) cell, type, formula);
	}
	
	private  void setAnother(Dispatch sheet, String position, String type,String formula) {
		Object cell = Dispatch.invoke((Dispatch) sheet, "Range", Dispatch.Get,
				new Object[] { position }, new int[1]).toDispatch();
		Dispatch.put((Dispatch) cell, type, formula);
	}

	// 读取值
	private  String getValue(String position) {
		Object cell = Dispatch.invoke((Dispatch) sheet, "Range", Dispatch.Get,
				new Object[] { position }, new int[1]).toDispatch();
		String value = Dispatch.get((Dispatch) cell, "Value").toString();
		return value;
	}
	private  String getFormula(String position) {
		Object cell = Dispatch.invoke((Dispatch) sheet, "Range", Dispatch.Get,
				new Object[] { position }, new int[1]).toDispatch();
		String value = Dispatch.get((Dispatch) cell, "Formula").toString();
		return value;
	}

    /**
     * 
     *
     * @param data                    数据
     * @param templateTeamRow         团队经理所在行数，模板中的行数
     * @param departRow               门店经理所在行数
     * @return
     * @author xjs
     * @date 2013-10-23 上午8:49:55
     */
	public int copyRow(Map<String, Object> data, int templateTeamRow, int departRow) {
		/**
		 * 取得门店经理
		 */
		String manager = "";
		try{
			List<Map<String,Object>> managerMap = (List<Map<String, Object>>) data.get("CTLIST");//门店经理列表
			for(int i = 0; i < managerMap.size() ; i++ ){
				Map<String,Object> item = managerMap.get(i);
				if(item != null ){
					String name = item.get("NAME") != null ? item.get("NAME").toString() :"";
					if(StringUtils.isNotEmpty(name)){
						manager = name;
						break;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		setValue("C6" ,"Value",manager);//设置门店经理
		
		Object lists = data.get("TMLIST");//团队经理列表
		if(lists != null ){
		  @SuppressWarnings("unchecked")
		  List<Map<String,Object>> itemsOfMap = (List<Map<String,Object>>)lists;
		  
		  //插入到业绩也表统计页面数据开始的行数为2，第一行为标题
		  int firstRowOfRecord = 2;
		  for(int index = 0 ; index < itemsOfMap.size() ; index++){
			    //取得团队下的客户经理列表
				Object customerLeaderItems = itemsOfMap.get(index).get("VALIST");
				String teamLeaderName = itemsOfMap.get(index).get("NAME") ==null ?"":itemsOfMap.get(index).get("NAME").toString();
				String teamLeaderNameEMPNO = itemsOfMap.get(index).get("EMP_NO") ==null ?"":itemsOfMap.get(index).get("EMP_NO").toString();
				@SuppressWarnings("unchecked")
				List<Map<String,Object>> items = (List<Map<String,Object>>)customerLeaderItems;
				Dispatch recordsSheet = getSheetByName("信贷业绩");
				firstRowOfRecord = copyRecordToSheet(recordsSheet,items,firstRowOfRecord);
				//根据记录进行统计
				List<Map<String,Object>> mergeItems = mergeItems(items);
				if(mergeItems.size() > 0 ){
					int rowNum = mergeItems.size();
					sheet = getSheetByName("绩效提成统计");
					Dispatch row = Dispatch.call((Dispatch)sheet, "Rows", new Variant(templateTeamRow)).toDispatch();
					Dispatch.call(row, "Copy");
					Dispatch.call(row, "Insert");
					setValue("A" + templateTeamRow, "Value", teamLeaderNameEMPNO);
					setValue("B" + templateTeamRow, "Value", teamLeaderName);
					
					//选择理财经理的插入位置,因为上面已经插入一行，所以插入位置为templateTeamRow+2
					Dispatch customerRow = Dispatch.call((Dispatch)sheet, "Rows", new Variant(templateTeamRow+2)).toDispatch();
					
					for(int i = 0; i < rowNum ; i++){
						Map<String,Object> singleValue = mergeItems.get(i);
						Dispatch.call(customerRow, "Copy");
						Dispatch.call(row, "Insert");	
						
						setValue("A"+(templateTeamRow + i + 1) ,"Value",singleValue.get("EMP_NO") == null ? "" :singleValue.get("EMP_NO").toString());
						setValue("B"+(templateTeamRow + i + 1) ,"Value",singleValue.get("NAME").toString());
						setValue("D"+(templateTeamRow + i + 1) ,"Value",teamLeaderName);
						setValue("U"+(templateTeamRow + i + 1) ,"Value",singleValue.get(SUM_COUNT_KEY) != null ? singleValue.get(SUM_COUNT_KEY).toString() :"0");
						
						for (Iterator<String> iterator = productToColumn.keySet().iterator(); iterator.hasNext();) {
						    String key = iterator.next();
                            if(singleValue.containsKey(key)){
                                setValue(productToColumn.get(key)+(templateTeamRow + i + 1)  ,"Value",singleValue.get(key).toString());
                            }
                            
                        }
					}
					changeSumFormat(templateTeamRow,sumCols,rowNum);
					changeDepartSumFormat(templateTeamRow,sumCols,departRow);
					templateTeamRow += rowNum + 1;
				}
		   }
		}
		return templateTeamRow;
	}

	private int copyRecordToSheet(Dispatch recordsSheet,List<Map<String, Object>> items, int firstRowOfRecord) {
		if(items != null ){
			for(int i = 0; i < items.size() ; i++){
				Map<String,Object> singleValue = items.get(i);
				if(singleValue.get("FKJE") != null && StringUtils.isNotEmpty(singleValue.get("FKJE").toString())){
					setAnother(recordsSheet,"A"+ firstRowOfRecord,"Value",singleValue.get("QDRQ").toString());
					setAnother(recordsSheet,"B"+ firstRowOfRecord,"Value",singleValue.get("JKRXM") != null ?singleValue.get("JKRXM").toString():"");//JKRXM
					setAnother(recordsSheet,"C"+ firstRowOfRecord,"Value",singleValue.get("JKTYPE") != null ?singleValue.get("JKTYPE").toString():"");//JKTYPE
					setAnother(recordsSheet,"D"+ firstRowOfRecord,"Value",singleValue.get("HTJE") != null ?singleValue.get("HTJE").toString():"");//HTJE
					setAnother(recordsSheet,"E"+ firstRowOfRecord,"Value",singleValue.get("HKQS") != null ?singleValue.get("HKQS").toString():"");//HKQS
					setAnother(recordsSheet,"F"+ firstRowOfRecord,"Value",singleValue.get("FKJE") != null ?singleValue.get("FKJE").toString():"");//FKJE
					setAnother(recordsSheet,"G"+ firstRowOfRecord,"Value",singleValue.get("NAME") != null ?singleValue.get("NAME").toString():"");//NAME
					if("1".equals(singleValue.get("LOOPAPPLY") != null ? singleValue.get("LOOPAPPLY").toString() : "0")){
					    setAnother(recordsSheet,"L"+ firstRowOfRecord,"Value","展期");//NAME
					}else{
					    setAnother(recordsSheet,"L"+ firstRowOfRecord,"Value","新增");//NAME
					}
					
					firstRowOfRecord++;
				}
			}
		}
		return firstRowOfRecord;
	}

	/**
	 * 将多条数据合并成一条数据，技术单子总额
	 *
	 * @param items
	 * @return
	 * @author xjs
	 * @date 2013-10-23 上午8:56:21
	 */
	private List<Map<String, Object>> mergeItems(List<Map<String, Object>> items) {
		List<Map<String, Object>> merge = new ArrayList<Map<String, Object>>();
		if(items != null){
			if(items.size() > 0){
				for(int i = 0; i < items.size() ; i++){
					Map<String,Object> singleValue = items.get(i);
					addToMergeItems(merge,singleValue);
				}
			}
		}
		return merge;
	}
	
    /**
     * 向集合中添加记录 
     * @param merge
     * @param singleValue
     */
	private void addToMergeItems(List<Map<String, Object>> merge,Map<String, Object> addValue) {
		
		Map<String,Object> existValue = findEquals(merge,addValue);
		
		if(existValue == null){
			countCycleCount(addValue);
			merge.add(addValue);
		}else{
			reCountExistItem(existValue,addValue);
		}
	}
	
	
	/**
	 * 
	 *
	 * @param existValue
	 * @param addValue
	 * @author xjs
	 * @date 2013-11-5 上午9:50:57
	 */
	private void reCountExistItem(Map<String, Object> existValue, Map<String, Object> addValue) {
	    //期数
        String period = addValue.get("HKQS") != null ? addValue.get("HKQS").toString() : "";
        //件数
        double money = Double.parseDouble(addValue.get("FKJE") != null ? addValue.get("FKJE").toString() : "0"); 
        
        //存在已有相关的记录，例如6期的
        if(existValue.containsKey(period + PERIOD_LOOP_KEY) || existValue.containsKey(period + PERIOD_KEY)){
            //循环借款
            if("1".equals(addValue.get("LOOPAPPLY") != null ? addValue.get("LOOPAPPLY").toString() : "0")){
               
                double beforeMoney = Double.parseDouble(existValue.get(period + PERIOD_LOOP_KEY) != null ? existValue.get(period + PERIOD_LOOP_KEY).toString() : "0");
                existValue.put(period + PERIOD_LOOP_KEY,beforeMoney  +money);
              
            }
            else{//正常借款，非续借
                double beforeMoney = Double.parseDouble(existValue.get(period + PERIOD_KEY) != null ? existValue.get(period + PERIOD_KEY).toString() : "0");
                existValue.put(period + PERIOD_KEY, beforeMoney + money);
                
            }
        }else{
            
            if("1".equals(addValue.get("LOOPAPPLY") != null ? addValue.get("LOOPAPPLY").toString() : "0")){
            
                existValue.put(period + PERIOD_LOOP_KEY,money);

            }else{
            
                existValue.put(period + PERIOD_KEY,money);
            
            }
        }
   
        existValue.put(SUM_COUNT_KEY, Integer.parseInt(existValue.get(SUM_COUNT_KEY).toString()) + 1 );
        
    }

    /**
	 * 计算期数与金额 
	 *
	 * @param item  --客户经理数据
	 * @author xjs
	 * @date 2013-11-5 上午9:18:53
	 */
    private void countCycleCount(Map<String, Object> item) {
        //期数
        String period = item.get("HKQS") != null ? item.get("HKQS").toString() : "";
        //件数
        double money = Double.parseDouble(item.get("FKJE") != null ? item.get("FKJE").toString() : "0"); 
        if("1".equals(item.get("LOOPAPPLY") != null ? item.get("LOOPAPPLY").toString() : "0"))
            item.put(period + PERIOD_LOOP_KEY, money);
        else
            item.put(period + PERIOD_KEY,money);
        if(money == 0) //为0代表 -- 或者用还款期数都可以，代表的是没有单子的客户 ----
            item.put(SUM_COUNT_KEY, 0);
        else
            item.put(SUM_COUNT_KEY, 1);
    }
    
    

    /**
     * 查找已经存在的数据,按照员工编号和姓名查询
     * @param merge
     * @param addValue
     * @return
     */
	private Map<String, Object> findEquals(List<Map<String, Object>> merge,	Map<String, Object> addValue) {
	    try{
    		for(int i = 0; i < merge.size() ; i++){
    			Map<String,Object> existValue = merge.get(i);
    			String existName = existValue.get("NAME").toString();
    			String addName   = addValue.get("NAME").toString();
    			String existEmpNo = existValue.get("EMP_NO").toString();
    			String addEmpNo = addValue.get("EMP_NO").toString();
    			if(StringUtils.equals(existName, addName) && StringUtils.equals(existEmpNo, addEmpNo)){
    			    return existValue;
    			}
    		}
	    }catch(Exception e){
	        e.printStackTrace();
	    }
		return null;
	}

	/**
	 * 判断是否都小于24期，或都大于等于24
	 * @param existPeriods
	 * @param addPeriods
	 * @return
	 
	private boolean inSamePosition(int existPeriods, int addPeriods) {
		if((existPeriods < STATIC_PERIOD && addPeriods < STATIC_PERIOD) || (existPeriods >= STATIC_PERIOD && addPeriods >= STATIC_PERIOD)){
			return true;
		}else{
			return false;
		}
	}
	*/
	
	public int copyRowCity(Map<String, Object> data, int templateTeamRow, int departRow) {
		int rownum = templateTeamRow + 1;
		String POSITION_LEVEL_CODE = data.get("POSITION_LEVEL_CODE")+"";
		if("CITYM".equals(POSITION_LEVEL_CODE)){
			String teamLeaderName = data.get("NAME") ==null ?"":data.get("NAME").toString();
			String rgani_name = data.get("RGANI_NAME") ==null ?"":data.get("RGANI_NAME").toString();
			Dispatch row = Dispatch.call((Dispatch)sheet, "Rows", new Variant(departRow)).toDispatch();
			Dispatch.call(row, "Copy");
			Dispatch.call(row, "Insert");
			setValue("C" + templateTeamRow, "Value", teamLeaderName);
			setValue("C" + templateTeamRow, "Value", teamLeaderName);
		}else{
			
		}
		
		Object lists = data.get("VALIST");
		if(lists != null ){
			List<Map<String,Object>> items = (List<Map<String,Object>>)lists;
			List<Map<String,Object>> mergeItems = mergeItems(items);
			if(mergeItems.size() > 0 ){
				String teamLeaderName = data.get("NAME") ==null ?"":data.get("NAME").toString();
				int rowNum = mergeItems.size();
				sheet = Dispatch.get((Dispatch) workbook, "ActiveSheet").toDispatch();
				Dispatch row = Dispatch.call((Dispatch)sheet, "Rows", new Variant(templateTeamRow)).toDispatch();
				Dispatch.call(row, "Copy");
				Dispatch.call(row, "Insert");
				setValue("C" + templateTeamRow, "Value", teamLeaderName);
				//选择理财经理的插入位置,因为上面已经插入一行，所以插入位置为templateTeamRow+2
				Dispatch customerRow = Dispatch.call((Dispatch)sheet, "Rows", new Variant(templateTeamRow+2)).toDispatch();
				
				for(int i = 0; i < rowNum ; i++){
					Map<String,Object> singleValue = mergeItems.get(i);
					Dispatch.call(customerRow, "Copy");
					Dispatch.call(row, "Insert");
					
					setValue("C"+(templateTeamRow + i + 1) ,"Value",singleValue.get("NAME").toString());
					setValue("E"+(templateTeamRow + i + 1) ,"Value",singleValue.get("FKJE") == null ? "" : singleValue.get("FKJE").toString());
					setValue("F"+(templateTeamRow + i + 1) ,"Value",singleValue.get("HKQS") == null ? "" : singleValue.get("HKQS").toString());
					setValue("H"+(templateTeamRow + i + 1) ,"Value",singleValue.get("COUNT") == null ? "" : singleValue.get("COUNT").toString());
				}

				changeSumFormat(templateTeamRow,sumCols,rowNum);
				changeDepartSumFormat(templateTeamRow,sumCols,departRow);
				templateTeamRow += rowNum + 1;
			}
		}
		return templateTeamRow;
	}
}
