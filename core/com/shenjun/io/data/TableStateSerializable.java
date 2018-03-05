package com.shenjun.io.data;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shenjun.collection.SuperArrayList;
import com.shenjun.db.type.SqlColumnType;
import com.shenjun.util.SerializableUtil;

/**
 * ext gird colums state serializable
 * @author shenjun
 *
 */
public class TableStateSerializable{
	
	private static Log log = LogFactory.getLog(TableStateSerializable.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static void save(){
		String path="cache/table/";
		SerializableUtil.save(SuperArrayList.getAutoGridState(), path,"gid.dat");
	}
	
	public static void load(){
		String path="cache/table/";
		Map<String,SqlColumnType[]> map=(Map<String,SqlColumnType[]>)SerializableUtil.get(path,"gid.dat");
		if(map!=null){
			SuperArrayList.setAutoGridState(map);
		}else{
			log.info("cache/table/gid.dat is not find,data is null!");
		}
		
	}

}
