package cn.com.educate.app.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class Java2Word {
	private boolean saveOnExit;
	/**
	 * word文档
	 */
	private Dispatch doc = null;
	/**
	 * word运行程序对象
	 */
	private ActiveXComponent word;
	/**
	 * 所有word文档
	 */
	private Dispatch documents;

	/**
	 * 构造函数
	 */
	public Java2Word() {
		saveOnExit = false;
		word = new ActiveXComponent("Word.Application");
		word.setProperty("Visible", new Variant(false));
		documents = word.getProperty("Documents").toDispatch();

	}

	/**
	 * 设置参数：退出时是否保存
	 * 
	 * @param saveOnExit
	 *            true-退出时保存文件，false-退出时不保存文件
	 */
	public void setSaveOnExit(boolean saveOnExit) {
		this.saveOnExit = saveOnExit;
	}

	/**
	 * 得到参数：退出时是否保存
	 * 
	 * @return boolean true-退出时保存文件，false-退出时不保存文件
	 */
	public boolean getSaveOnExit() {
		return saveOnExit;
	}

	/**
	 * 打开文件
	 * 
	 * @param inputDoc
	 *            要打开的文件，全路径
	 * @return Dispatch 打开的文件
	 */
	public Dispatch open(String inputDoc) {
	    
		return Dispatch.call(documents, "Open", inputDoc).toDispatch();
	}
	
	/**
     * 打开文件
     * 
     * @param inputDoc
     *            要打开的文件，全路径
     * @return Dispatch 打开的文件
     */
    public Dispatch openTemp(String inputDoc) {
        Dispatch doc = Dispatch.invoke(documents, "Open", Dispatch.Method,
                new Object[] { inputDoc, new Variant(false), new Variant(true)},//参数３,false:可写，true:只读
                new int[1]).toDispatch();//打开文档
        return doc;
    }

	/**
	 * 选定内容
	 * 
	 * @return Dispatch 选定的范围或插入点
	 */
	public Dispatch select() {
		return word.getProperty("Selection").toDispatch();
	}

	/**
	 * 把选定内容或插入点向上移动
	 * 
	 * @param selection
	 *            要移动的内容
	 * @param count
	 *            移动的距离
	 */
	public void moveUp(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveUp");
	}

	/**
	 * 把选定内容或插入点向下移动
	 * 
	 * @param selection
	 *            要移动的内容
	 * @param count
	 *            移动的距离
	 */
	public void moveDown(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveDown");
	}

	/**
	 * 把选定内容或插入点向左移动
	 * 
	 * @param selection
	 *            要移动的内容
	 * @param count
	 *            移动的距离
	 */
	public void moveLeft(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveLeft");
	}

	/**
	 * 把选定内容或插入点向右移动
	 * 
	 * @param selection
	 *            要移动的内容
	 * @param count
	 *            移动的距离
	 */
	public void moveRight(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveRight");
	}

	/**
	 * 把插入点移动到文件首位置
	 * 
	 * @param selection
	 *            插入点
	 */
	public void moveStart(Dispatch selection) {
		Dispatch.call(selection, "HomeKey", new Variant(6));
	}

	/**
	 * 从选定内容或插入点开始查找文本
	 * 
	 * @param selection
	 *            选定内容
	 * @param toFindText
	 *            要查找的文本
	 * @return boolean true-查找到并选中该文本，false-未查找到文本
	 */
	public boolean find(Dispatch selection, String toFindText) {
		// 从selection所在位置开始查询
		Dispatch find = Dispatch.call(selection, "Find").toDispatch();
		// 设置要查找的内容
		Dispatch.put(find, "Text", toFindText);
		// 向前查找
		Dispatch.put(find, "Forward", "True");
		// 设置格式
		Dispatch.put(find, "Format", "True");
		// 大小写匹配
		Dispatch.put(find, "MatchCase", "True");
		// 全字匹配
		Dispatch.put(find, "MatchWholeWord", "True");
		// 查找并选中
		return Dispatch.call(find, "Execute").getBoolean();
	}

	/**
	 * 把选定内容替换为设定文本
	 * 
	 * @param selection
	 *            选定内容
	 * @param newText
	 *            替换为文本
	 */
	public void replace(Dispatch selection, String newText) {
		// 设置替换文本
		Dispatch.put(selection, "Text", newText);
	}

	/**
	 * 替换页脚
	 */
	public void repSeekView(Dispatch selection, String oldText, String newText) {
		Dispatch activeWindow = word.getProperty("ActiveWindow").toDispatch();
		Dispatch activePane = Dispatch.get(activeWindow, "ActivePane")
				.toDispatch();
		Dispatch view = Dispatch.get(activePane, "View").toDispatch();
		Dispatch.put(view, "SeekView", "10");// 10是设置页脚，9是设置页眉
		while (find(selection, oldText)) {
			Dispatch.put(selection, "Text", newText);
		}
	}

	/**
	 * 全局替换
	 * 
	 * @param selection
	 *            选定内容或起始插入点
	 * @param oldText
	 *            要替换的文本
	 * @param newText
	 *            替换为文本
	 */
	public void replaceAll(Dispatch selection, String oldText, Object replaceObj) {
		// 移动到文件开头
		moveStart(selection);
		if (oldText.startsWith("table") || replaceObj instanceof List) {
			replaceTable(selection, oldText, (List) replaceObj);
			// } else if (oldText.startsWith("ctable") || replaceObj instanceof
			// List){
			// createTable(selection, oldText, (List) replaceObj);
		} else {

			// String newText = (String) replaceObj;
			String newText = replaceObj.toString();
			if (oldText.indexOf("image") != -1
					|| newText.lastIndexOf(".bmp") != -1
					|| newText.lastIndexOf(".jpg") != -1
					|| newText.lastIndexOf(".gif") != -1)
				while (find(selection, oldText)) {
					replaceImage(selection, newText);
					Dispatch.call(selection, "MoveRight");
				}
			else
				while (find(selection, oldText)) {
					replace(selection, newText);
					Dispatch.call(selection, "MoveRight");
				}
		}
	}

	/**
	 * 替换图片
	 * 
	 * @param selection
	 *            图片的插入点
	 * @param imagePath
	 *            图片文件（全路径）
	 */
	public void replaceImage(Dispatch selection, String imagePath) {
		Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
				"AddPicture", imagePath);
	}

	/**
	 * 替换表格
	 * 
	 * @param selection
	 *            插入点
	 * @param tableName
	 *            表格名称，形如table$1@1、table$2@1...table$R@N，R代表从表格中的第R行开始填充，
	 *            N代表word文件中的第N张表(RN都从1开始)
	 * @param dataList
	 *            表格中要替换的字段与数据的对应表 ,list[0]数组为所替列，如[1,2,3,4],list[1]之后为数值
	 */
	public void replaceTable(Dispatch selection, String tableName, List dataList) {
		if (dataList.size() <= 1) {
			System.out.println("Empty table!");
			return;
		}
		// 要填充的列
		String[] cols = (String[]) dataList.get(0);
		// 表格序号
		String tbIndex = tableName.substring(tableName.lastIndexOf("@") + 1);
		// 从第几行开始填充
		int fromRow = Integer.parseInt(tableName.substring(
				tableName.lastIndexOf("$") + 1, tableName.lastIndexOf("@")));
		// 所有表格
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tbIndex))
				.toDispatch();
		// 表格的所有行
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		// 填充表格
		for (int i = 1; i < dataList.size(); i++) {
			// 某一行数据
			String[] datas = (String[]) dataList.get(i);
			// 在表格中添加一行
			if (Dispatch.get(rows, "Count").getInt() < fromRow + i - 1)
				Dispatch.call(rows, "Add");
			// 填充该行的相关列
			for (int j = 0; j < datas.length; j++) {
				// 得到单元格
				Dispatch cell = Dispatch.call(table, "Cell",
						Integer.toString(fromRow + i - 1), cols[j])
						.toDispatch();
				// 选中单元格
				Dispatch.call(cell, "Select");
				// 设置格式
				Dispatch font = Dispatch.get(selection, "Font").toDispatch();
				Dispatch.put(font, "Bold", "0");
				Dispatch.put(font, "Italic", "0");
				// 输入数据
				Dispatch.put(selection, "Text", datas[j]);
			}
		}
	}

	public void replaceTable(int tbIndex, int fromRow, int fromCol,
			List dataList) throws Exception {
		if (dataList.size() < 1) {
			System.out.println("Empty table!");
			return;
		}
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tbIndex))
				.toDispatch();
		// 读取列数
		Dispatch cols = Dispatch.get(table, "Columns").toDispatch();
		int colsCount = Dispatch.get(cols, "Count").toInt();
		if (fromCol < 1 || fromCol > colsCount)
			fromCol = 1;
		// 读取行数
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		int rowsCount = Dispatch.get(rows, "Count").getInt();
		if (fromRow < 1 || fromRow > rowsCount)
			fromRow = rowsCount + 1;
		for (int i = 0; i < dataList.size(); i++) {
			String datas[] = (String[]) dataList.get(i);
			if (fromRow + i > rowsCount)
				Dispatch.call(rows, "Add");
			for (int j = 0; j < datas.length; j++) {
				if (fromCol + j > colsCount)
					break;
				Dispatch cell = Dispatch.call(table, "Cell", fromRow + i + "",
						fromCol + j + "").toDispatch();
				Dispatch.call(cell, "Select");
				Dispatch range = Dispatch.get(cell, "Range").toDispatch();
				/*
				 * Dispatch font = Dispatch.get(doc, "Font").toDispatch();
				 * Dispatch.put(font, "Bold", "0"); Dispatch.put(font, "Italic",
				 * "0");
				 */
				Dispatch.put(range, "Text", datas[j]);
			}
		}
	}

	/** */
	/**
	 * 合并单元格
	 * 
	 * @param tableIndex
	 * @param fstCellRowIdx
	 * @param fstCellColIdx
	 * @param secCellRowIdx
	 * @param secCellColIdx
	 */
	public void mergeCell(int tableIndex, int fstCellRowIdx, int fstCellColIdx,
			int secCellRowIdx, int secCellColIdx) {

		// 所有表格
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch fstCell = Dispatch.call(table, "Cell",
				new Variant(fstCellRowIdx), new Variant(fstCellColIdx))
				.toDispatch();
		Dispatch secCell = Dispatch.call(table, "Cell",
				new Variant(secCellRowIdx), new Variant(secCellColIdx))
				.toDispatch();
		Dispatch.call(fstCell, "Merge", secCell);
	}

	/**
	 * 保存文件
	 * 
	 * @param outputPath
	 *            输出文件（包含路径）
	 */
	public void save(String outputPath) {
		// Dispatch.call(Dispatch.call(word,
		// "WordBasic").getDispatch(),"FileSaveAs", outputPath);
	}

	public void saveWordFile(final String filePath) {
		File file = new File(filePath);
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}

		Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {
				filePath, new Variant(0) }, new int[1]);
		// 作为word格式保存到目标文件
	}
	
	public void saveWord(final String filePath) {

        Dispatch.invoke(doc, "Save", Dispatch.Method, new Object[] {
                 new Variant(0) }, new int[1]);
//        Dispatch.call(doc, "Save");
        // 作为word格式保存到目标文件
    }

	/**
	 * 关闭文件
	 * 
	 * @param document
	 *            要关闭的文件
	 */
	public void close(Dispatch doc) {
		Dispatch.call(doc, "Close", new Variant(saveOnExit));
	}

	/**
	 * 退出程序
	 */
	public void quit() {
		word.invoke("Quit", new Variant[0]);
		ComThread.Release();
	}

	/**
	 * 根据模板、数据生成word文件
	 * 
	 * @param inputPath
	 *            模板文件（包含路径）
	 * @param outPath
	 *            输出文件（包含路径）
	 * @param data
	 *            数据包（包含要填充的字段、对应的数据）
	 */
	public void toWord(String inputPath, String outPath, HashMap data) {
		String oldText;
		Object newValue;
//		String tempFile = outPath.substring(0,outPath.lastIndexOf(".")) +"temp"+ outPath.substring(outPath.lastIndexOf("."));
		String tempFile = outPath;
		try {
			doc = openTemp(inputPath);
			saveWordFile(tempFile);
			//close(doc);
			//doc = open(tempFile);
			Dispatch selection = select();
			Iterator keys = data.keySet().iterator();
			while (keys.hasNext()) {
				oldText = (String) keys.next();
				newValue = data.get(oldText);
				if (newValue == null) {
					newValue = "";
				}
				replaceAll(selection, oldText, newValue);
			}
			Iterator keys2 = data.keySet().iterator();
			while (keys2.hasNext()) {
				oldText = (String) keys2.next();
				newValue = data.get(oldText);
				if (oldText.startsWith("seek")) {
					oldText = oldText.substring(4, oldText.length());
					// System.out.println("oldText===" + oldText);
					repSeekView(selection, oldText, newValue.toString());
				}
			}
			saveWordFile(outPath);
			
		} catch (Exception e) {
			System.out.println("toword[Java2Word]------------操作word文件失败！");
			e.printStackTrace();
		} finally {
			if (doc != null)
				close(doc);
			//File file = new File(tempFile);
            //file.delete();
		}
	}

	/**
	 * 根据模板、数据生成word文件,包含生成后合并单元格处理
	 * 
	 * @param inputPath
	 *            模板文件（包含路径）
	 * @param outPath
	 *            输出文件（包含路径）
	 * @param data
	 *            数据包（包含要填充的字段、对应的数据）
	 * @param mergerData
	 *            数据包（包含要合并的单元格信息）
	 */
	public void toWord(String inputPath, String outPath, HashMap data,
			List<String> mergerData) {
		String oldText;
		Object newValue;
		String tempFile = outPath;
		try {
			doc = openTemp(inputPath);
			saveWordFile(tempFile);
			Dispatch selection = select();
			Iterator keys = data.keySet().iterator();
			while (keys.hasNext()) {
				oldText = (String) keys.next();
				newValue = data.get(oldText);
				if (newValue == null) {
					newValue = "";
				}
				replaceAll(selection, oldText, newValue);
			}

			if (mergerData != null) {
				String str[];
				for (String merger : mergerData) {
					str = merger.split("@");
					mergeCell(Integer.parseInt(str[0]),
							Integer.parseInt(str[1]), Integer.parseInt(str[2]),
							Integer.parseInt(str[3]), Integer.parseInt(str[4]));
				}
			}

			saveWordFile(tempFile);
		} catch (Exception e) {
			System.out.println("toword[Java2Word]------------操作word文件失败！"
					+ e.getMessage());

		} finally {
			if (doc != null)
				close(doc);
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Java2Word j = new Java2Word();
		HashMap data = new HashMap();
		// data.put("（1）", "2012111111");
		// data.put("（2）", "2012");
		// data.put("（3）", "12");
		// data.put("（4）", "4");
		List l = new ArrayList();
		String[] a = new String[3];
		a[0] = "1";
		a[1] = "2";
		a[2] = "4";
		l.add(a);
		a = new String[3];
		a[0] = "000";
		a[1] = "";
		a[2] = "2221";
		l.add(a);
		a = new String[3];
		a[0] = "0000";
		a[1] = "1111";
		a[2] = "22222";
		l.add(a);
		a = new String[3];
		a[0] = "0000";
		a[1] = "";
		a[2] = "22223";
		l.add(a);
		a = new String[3];
		a[0] = "0000";
		a[1] = "1111";
		a[2] = "22224";
		l.add(a);
		a = new String[3];
		a[0] = "0000";
		a[1] = "1111";
		a[2] = "22225";
		l.add(a);
		data.put("ctable$2@1", l);
		List ll = new ArrayList();
		ll.add("1@2@1@2@2");
		ll.add("1@4@1@4@2");
		// j.toWord("C://hkglfwsms.doc", "C://hkglfwsms111.doc", data,ll);
		// j.toWord("C://hkglfwsms111.doc", "C://hkglfwsms222.doc", data,ll);

		j.quit();
		// List l1 = new ArrayList();
		// String[] a = new String[9];
		// a[0] = "2";
		// a[1] = "3";
		// a[2] = "4";
		// a[3] = "6";
		// a[4] = "7";
		// a[5] = "8";
		// a[6] = "10";
		// a[7] = "11";
		// a[8] = "12";
		// l1.add(a);
		// a = new String[9];
		// for (int i = 0; i < 9; i++) {
		// a[i%3*3] = i+"p";
		// a[i%3*3+1] = i+"x";
		// a[i%3*3+2] = i+"s";
		// if ((i+1)%3==0){
		// l1.add(a);
		// a = new String[9];
		// }
		// }
		// if (9%3!=0){
		// for (int i = 0; i < a.length; i++) {
		// if (a[i]==null){
		// a[i]="";
		// }
		// }
		// l1.add(a);
		// }
		// System.out.println(l1.size());

		// j.wordToSwf("d:\\tools\\借款协议.doc", "d:\\tools\\借款协议.swf");
		// j.wordToSwf("d:\\tools\\借款协议.doc", "d:\\tools\\借款协议.pdf");
	}
}