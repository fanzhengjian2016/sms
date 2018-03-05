package com.shenjun.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shenjun.db.type.SqlColumnType;
import com.shenjun.enums.ExtjsTypeEnum;
import com.shenjun.io.data.TableStateSerializable;
import com.shenjun.plugins.jackson.JSON;
import com.shenjun.plugins.struts.Struts2Util;
import com.shenjun.util.ClassUtil;
import com.shenjun.util.JSONUtils;
import com.shenjun.util.MapUtils;
import com.shenjun.util.SqlUtil;
import com.shenjun.util.StringUtil;

public class SuperArrayList<E> extends ArrayList<E> implements SuperList<E> {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4650197414900526738L;
	
	public  Log log = LogFactory.getLog(SuperArrayList.class);
	
	//private boolean isSerializable=false;
	public SqlColumnType[] sct;
	
/*********存储临时List原对象*****************************************************************************/
	private List<Object[]> list;
	
	@SuppressWarnings("rawtypes")
	public List getList() {
		return (this.list==null||this.list.size()==0)?this:this.list;
	}

	@Override
	public void setList(List<Object[]> list) {
		this.list = list;
	}
/*********************************************************/
	private SuperListFilter listFilter;
	
	

	public SuperListFilter getListFilter() {
		return listFilter;
	}
	
	@Override
	public void setListFilter(SuperListFilter listFilter) {
		this.listFilter = listFilter;
	}

/*********base************************************************************************************************/
	public SqlColumnType[] getSqlColumnTypes() {
		return this.sct;
	}

	public void setSqlColumnTypes(SqlColumnType[] sct) {
		this.sct=sct;
	}
	
	public Map<String,SqlColumnType> getSqlColumnTypeMap(){
		Map<String,SqlColumnType> map = new HashMap<String,SqlColumnType>();
		for(SqlColumnType sc:sct){
			map.put(sc.getColumnName().toLowerCase(), sc);
		}
		return map;
	}
	
	public String[] getColumnNames(){
		if(this.sct==null)
			return null;
		
		String []columns=new String[this.getSqlColumnTypes().length];
		
		for(int i=0,len=this.getSqlColumnTypes().length;i<len;i++){
			columns[i]=this.getSqlColumnTypes()[i].getColumnName().toLowerCase();
		}
		return columns;
	}

	@SuppressWarnings("unchecked")
	public <T> T getT(int index){
		return (T)super.get(index);
	}
	
	
	private Map<String,Integer> columnIndexMap=null;
	
	public int getIndexByName(String name) {
		// TODO Auto-generated method stub
		if(columnIndexMap==null){
			columnIndexMap=new HashMap<String,Integer>();
		}
		
		if(columnIndexMap.get(name)==null){
			String[] names = this.getColumnNames();
			for(int i = 0, len=names.length;i<len;i++){
				if((""+names[i]).equals(name)){
					columnIndexMap.put(name, i);
					break;
				}
			}
		}
		return columnIndexMap.get(name);
	}

	public Integer getCount(){
		return this.getList().size();
	}
	
	@Override
	public Integer getTotalCount(){
		if(this.getListFilter()!=null){
			return this.getListFilter().getTotalCount();
		}else{
			return this.size();
		}
	}

	public List toClass(Class cls){
		// TODO Auto-generated method stub
		return this.toClass(cls,null);
	}

	public List toClass(Class cls, String[] cols) {
		if(cols==null){
			cols=this.getColumnNames();
		}
		// TODO Auto-generated method stub
		List<Object> ls = new ArrayList<Object>();
		
		for(int i=0,len=this.getCount();i<len;i++){
			Object obj=ClassUtil.classLoad(cls,cols,(Object[]) this.getList().get(i));
			ls.add(obj);
		}
		return ls;
	}	

	@Override
	public String toJSONArrayString() {
		return toJSONArrayString(this.getColumnNames());
	}

	@Override
	public String toJSONArrayString(String[] columns) {
		List<Map<String, Object>> ls=JSONUtils.listToListMap((List<Object[]>)(this.getList()), columns);
		return JSONUtils.toJson(ls);
	}

	@Override
	public String toFristRowJSONString() {
		// TODO Auto-generated method stub
		return toFristRowJSONString(this.getColumnNames());
	}

	@Override
	public String toFristRowJSONString(String[] columns) {
		Map<String, Object> map=MapUtils.arraysToJsonMap(columns,
				(this.getCount()>0?
						(Object[])(this.getList().get(0)):
						new Object[columns.length]
				)
			);
		return JSONUtils.toJson(map);
	}

	@Override
	public String toSqlColumnJSONArrayString() {
		return JSONUtils.toJson(SqlUtil.sqlColumnsToJson(this.getSqlColumnTypes()));
	}

	@Override
	public String getListArrayString() {
		// TODO Auto-generated method stub
		return JSONUtils.toJson(this.getList());
	}
	
	@Override
	public <T> T get(int rowsIndex, int cellIndex) {
		// TODO Auto-generated method stub
		Object obj=null;
		try{
			Object[] objs = (Object[]) this.getList().get(rowsIndex);
			obj=objs[cellIndex];
		}catch (Exception e) {
			log.warn(e.getMessage());
		}
		return (T) obj;
	}
	
	@Override
	public <T> T get(int rowsIndex,String columnName){
		int cellIndex=this.getIndexByName(columnName);
		return this.get(rowsIndex, cellIndex);
	}
	
	@Override
	public <T> T get(String columnName){
		return this.get(0,columnName);
	}
	

	
/************************************************************extjs**********************************************************/
	public String toExtjs(ExtjsTypeEnum ete, String[] objs) {
		// TODO Auto-generated method stub
		return this.toExtjs(ete, objs, null, null);
	}
	
	/**
	 * objs is table column as
	 * dataName default value is data
	 * totalProperty default value is totalProperty
	 */
	public String toExtjs(ExtjsTypeEnum ete, String[] objs,String dataName,
			String totalName) {
		// TODO Auto-generated method stub
		String [] cls = null;
		if(objs!=null)
			cls=objs;
		else
			cls=this.getColumnNames();
		
		if(ete==null)
			ete=ExtjsTypeEnum.MULTI_DATA_STORE;
		
		if(ExtjsTypeEnum.MULTI_DATA_STORE==ete){
			
			if(this.sct==null){
				SqlColumnType[] sct = new SqlColumnType[cls.length];
				for(int i = 0,len=cls.length; i < len; i++){
					sct[i]=new SqlColumnType(cls[i]);
				}
				this.setSqlColumnTypes(sct);
			}else if(objs!=null){
				SqlColumnType[] sct =this.getSqlColumnTypes();
				for(int i = 0,len=cls.length; i < len; i++){
					if(StringUtil.isNB(cls[i])){
						sct[i].setColumnName(cls[i]);
					}
				}
			}
			
			String columns=this.getSqlColumnJSONArray().toJSONString();
			
			String gid=Struts2Util.get("gid");
			if(StringUtil.isNB(gid)){
				setGridItem(gid,this.getSqlColumnTypes());
			}
			
			if(!StringUtil.isNB(dataName))
				dataName=globalDataName;
			
			if(!StringUtil.isNB(totalName))
				totalName=globalTotalName;
			
			StringBuilder res=new StringBuilder("{\"columns\":");
			res.append(columns);
			res.append(",\""+totalName+"\":"+this.getTotalCount()+",");
			res.append("\""+dataName+"\":");
			res.append(this.getListJSONArray(cls).toJSONString());
			res.append("}");
			
			return res.toString();
		}else if(ExtjsTypeEnum.SINGLE_DATA_JSON==ete){
			return this.getListJSONObject(cls).toJSONString();
		}
		return "";
	}

	public String toExtjs(String[] objs) {
		// TODO Auto-generated method stub
		return this.toExtjs(ExtjsTypeEnum.MULTI_DATA_STORE, objs);
	}
	
	////////////grid/////////////////////////////////////////////////////
	private static Map<String,SqlColumnType[]> autoGrid = new HashMap<String,SqlColumnType[]>();
	
	public static Map<String,SqlColumnType[]> getAutoGridState(){
		return autoGrid;
	}
	
	public static void setAutoGridState(Map<String,SqlColumnType[]> map){
		for(Map.Entry<String,SqlColumnType[]> entry : map.entrySet()){
			autoGrid.put(entry.getKey(), entry.getValue());
		}
	}
	
	public static SqlColumnType[] getExtjsColumn(String gid){
		return autoGrid.get(gid);
	}
	
	protected static void setGridItem(String key,SqlColumnType[] scts){
		
		SqlColumnType[] savescts=getExtjsColumn(key);
		if(savescts==null||!SqlUtil.sqlColumnTypeToText(scts).equals(
				SqlUtil.sqlColumnTypeToText(savescts)
		)){
			//System.out.println(SqlUtil.sqlColumnTypeToText(scts));
			//System.out.println(SqlUtil.sqlColumnTypeToText(savescts));
			autoGrid.put(key, scts);
			TableStateSerializable.save();
		}
	}
	
	private static final String globalDataName="data";
	private static final String globalTotalName="totalProperty";
	
	public String toExtjs() {
		// TODO Auto-generated method stub
		return this.toExtjs(ExtjsTypeEnum.MULTI_DATA_STORE, null);
	}

	public String toExtjs(ExtjsTypeEnum ete) {
		// TODO Auto-generated method stub
		return this.toExtjs(ete, null);
	}
	
	
/***********过时对象*************************************************************************************************************/
	@Deprecated
	public JSON getListJSONArray() {
		return JSONUtils.listToJson((List<Object[]>)this, this.getColumnNames());
	}
	
	@Deprecated
	@SuppressWarnings("unchecked")
	public JSON getSqlColumnJSONArray() {
		return new JSON(SqlUtil.sqlColumnsToJson(this.getSqlColumnTypes()));
	}
	
	@Deprecated
	public JSON getListJSONObject() {
		if(this.getCount()>0)
			return JSONUtils.objectjsToJson((Object[])(this.getList().get(0)), this.getColumnNames());
		else
			return JSONUtils.objectjsToJson(new Object[0],this.getColumnNames());
	}
	@Deprecated
	public JSON getListJSONArray(String[] columns) {
		return JSONUtils.listToJson(this.getList(), columns);
	}
	@Deprecated
	public JSON getListJSONObject(String[] columns) {
		if(this.getCount()>0)
			return JSONUtils.objectjsToJson((Object[])(this.getList().get(0)), columns);
		else
			return JSONUtils.objectjsToJson(new Object[0],columns);
	}
}
