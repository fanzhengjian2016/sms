package com.shenjun.db.structure;

import com.shenjun.db.type.SqlColumnType;
import com.shenjun.db.type.Table;
import com.shenjun.enums.SqlServerType;
import com.shenjun.manager.CommonManager;
import com.shenjun.web.thread.Lc;
/**
 * 数据库自动操作类，主要进行数据表的自动创建，数据的自动导入、删除、增加列的操作。
 * @author jbox_user
 *
 */
public class Operation {
	
	/**
	 * 在Table表中添加列
	 * @param cm
	 * @param tableName 表名
	 * @param columnName 列表
	 * @param columnType 列类型
	 * @return
	 */
	public static boolean tableAddColumn(CommonManager cm,String tableName,String columnName,String columnType){
		return Lc.getConn().createSql(
				String.format("alter table %s add content %s",tableName,columnName)
				);
	}
	
	/**
	 * 修改Table表中的列。【这一个方法不进行列名的修改】
	 * @param cm
	 * @param tableName
	 * @param columnName
	 * @param columnType
	 * @param columnDisplaySize
	 * @return
	 */
	public static boolean tableAterColumn(CommonManager cm,String tableName,String columnName,String columnType,Integer columnDisplaySize){
		
		String dialect="alter";
		if(cm.getDBType() == SqlServerType.MYSQL){
			dialect="modify";
		}
		
		return cm.createSql(
				String.format("alter table %s %s column %s %s(%s)",tableName,dialect,columnName,columnType,columnDisplaySize)
				);
	}
	
	/**
	 * 自动创建一张表
	 * @param cm
	 * @param table
	 * @return
	 */
	public static int makeTable(CommonManager cm,Table table){
		String tableName=table.getTableName();
		StringBuilder sb = new StringBuilder("create table ");
		sb.append(tableName).append(" (");
		
		for(SqlColumnType sct: table.getColumns()){
			sb.append(sct.getColumnName()).append(" ")
			.append(sct.getColumnTypeName()+"("+sct.getColumnDisplaySize()+"),");
		}
		if(table.getColumns().size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		
		sb.append(")");
		
		return cm.createSql(sb.toString())?0:-1;
	}
	
	/*test 1
	 * public static void main(String[] args) {
		List<SqlColumnType> columns = new ArrayList<SqlColumnType>();
		columns.add(new SqlColumnType("cc", SqlType.VARCHAR, "varchar", 30));
		columns.add(new SqlColumnType("mm", SqlType.VARCHAR, "varchar", 30));
		columns.add(new SqlColumnType("dd", SqlType.VARCHAR, "varchar", 30));
		
		Table tb = new Table();
		tb.setColumns(columns);
		tb.setTableName("tb_test2");
		
		Operation.makeTable(tb, null);
	}*/
	
	/**
	 * String tableName=table.getTableName();
		try{
			cm.createSQLQuery("select * from "+tableName+" where 1=2");
		}catch(Exception e){
			System.out.println("-------------------");
		}
	 */
	
}
