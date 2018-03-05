package com.shenjun.system;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shenjun.db.type.SqlColumnType;

public class VarUtils {
	
	private static final Log log = LogFactory.getLog(VarUtils.class);
	
	private static Map<Object,Boolean> vars = new HashMap<Object,Boolean>();
	
	public static Map<Object,Boolean> getVars(){
		return vars;
	}
	
	/**
	 * 
	 * @param cls 构建的Class对象名
	 * @param cleanSc isClean is object 清理 , is null 获取
	 * @param isRemove true 删除，false 清理
	 * @param params 实例化的参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized static <T> T VarContent(Class<T> cls,Object cleanSc,boolean isRemove,Object... params){
		Object var=null;
		if(cleanSc==null){
			for(Map.Entry<Object,Boolean> entry : vars.entrySet()){
				if(entry.getValue() && entry.getKey().getClass() == cls){ 
					var=entry.getKey();
					break;
				}
			}
			if(var==null){
				try {
					var = cls.newInstance();
				} catch (InstantiationException e) {
					log.error("InstantiationException VarUtils.VarContent create "+cls.getName()+" error:"+e.getMessage(),e.getCause());
				} catch (IllegalAccessException e) {
					log.error("IllegalAccessException VarUtils.VarContent create "+cls.getName()+" error:"+e.getMessage(),e.getCause());
				}
				vars.put(var, true);
			}
			vars.put(var, false);
		}else{
			if(isRemove){
				vars.remove(cleanSc);
			}else{
				vars.put(cleanSc, true);
			}
		}
		
//		StringBuilder debug = new StringBuilder();
//		for(Map.Entry<Object,Boolean> entry : vars.entrySet()){
//			debug.append("["+entry.getValue()+":"+entry.getKey()+"]");
//		}
//		System.out.println(debug.toString());
		
		return ((T)var);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	private static Map<String,Boolean> lockvars = new HashMap<String,Boolean>();
	/**
	 * 获取与锁定参数
	 * @param lockName
	 * @param state
	 * @return 如果没有存储值，将返回false
	 */
	public synchronized static Boolean lockVal(String lockName,boolean... state){
		if(state.length>0){
			lockvars.put(lockName, state[0]);
		}
		return lockvars.get(lockName)==null?false:lockvars.get(lockName);
	}
	
	public static void main(String[] args) {
//		lockVal("aa",true);
//		lockVal("aa",false);
//		lockVal("aa",true);
		if(lockVal("aa"))
		System.out.println(lockVal("aa")+":");
		SqlColumnType sct = VarUtils.VarContent(SqlColumnType.class, null, false);
		System.out.println(sct);
		VarUtils.VarContent(SqlColumnType.class, sct, false);
		System.out.println(VarUtils.VarContent(SqlColumnType.class, null, false));
		System.out.println(VarUtils.VarContent(SqlColumnType.class, null, false));
		System.out.println(VarUtils.VarContent(Date.class, null, false));
		System.out.println(VarUtils.VarContent(Date.class, null, false));
		System.out.println(VarUtils.VarContent(Date.class, null, false));
		System.out.println(VarUtils.getVars().size());
	}
}
