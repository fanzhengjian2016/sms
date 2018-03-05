package com.commons.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shenjun.db.type.SqlColumnType;
import com.shenjun.manager.CommonManager;
import com.shenjun.util.StringUtil;
import com.shenjun.web.thread.Lc;

public class LocalTools {
	
	private static Map<String,String> wdbk = new HashMap<String, String>();
	
	public static SqlColumnType[] convertLocalText(SqlColumnType[] scts,String table){
		return convertLocalText(scts,Lc.getConn(),table);
	}
	
	private static void initLocal(CommonManager cm){
		List<Object[]> wds = cm.createSQLQuery("select wbname,wbVal1,wbVal2 from tb_sys_wordbook where wbType='本地化' and state=1 ");
		for(Object[] wd : wds){
			if(StringUtil.isNB(wd[2])){
				wdbk.put(wd[1]+"|"+wd[2], wd[0]+"");
			}
			
			wdbk.put(wd[1]+"", wd[0]+"");
		}
	}
	
	public static void reload(CommonManager cm){
		initLocal(cm);
	}
	
	public static String getLocalText(CommonManager cm,String table,String col){
		//Log.info(""+table+":"+ col);
		if(wdbk.size()==0){
			initLocal(cm);
		}
		String local=null;
		if(table!=null){
			local=wdbk.get(col+"|"+table);
		}
		if(local==null){
			local=wdbk.get(col);
		}
		if(!StringUtil.isNB(local)){
			local=col;
		}
		//System.out.println(col+"|"+table+"->"+local);
		
		return local;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public static SqlColumnType[] convertLocalText(SqlColumnType[] scts,CommonManager cm,String table){
		
//		Object[] obj=new Object[scts.length];
//		Map<String, Integer> map=new HashMap<String, Integer>();
//		for(int i=0,len=scts.length;i<len;i++){
//			String rtable=table;
//			if(StringUtil.isNB(scts[i].getTableName())){
//				rtable=scts[i].getTableName();
//			}
//			
//			String key=scts[i].getColumnName();
//			
//			obj[i]=key;
//			map.put(key, i);
//		}
//		
//		List<Object[]> rs=cm.createSQLQuery("select wbname,wbVal1,wbVal2,wbVal3,wbno from tb_sys_wordbook where wbType='本地化' and " +
//				" wbVal1 in("+
//				StringUtil.createSameChar("?",obj.length,",")+
//				") order by wbval2 desc", obj);
//		
//		
//		for(Object[] row : rs){
//			String key=row[1]+"";
//			if(map.get(key)!=null){
//				SqlColumnType sct = scts[map.get(key)];
//				sct.setLocalText(row[0]+"");
//				sct.setParams(row[3]+"");
//				sct.setSign(row[4]+"");
//				
//				String rtable=table;
//				if(StringUtil.isNB(sct.getTableName())){
//					rtable=sct.getTableName();
//				}
//				
//				if((""+row[2]).equals(rtable)){
//					map.remove(row[1]+"");
//				}
//			}
//		}
		
		for(SqlColumnType sct:scts){
			String localText=getLocalText(cm,table,sct.getColumnName());
			if(localText!=null){
				sct.setLocalText(localText);
			}
		}
		return scts;
	}
}
