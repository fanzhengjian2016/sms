package com.shenjun.collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shenjun.plugins.struts.Struts2Util;
import com.shenjun.util.SerializableUtil;
import com.shenjun.util.StringUtil;
import com.shenjun.web.thread.LocalContent;

public abstract class SplitPageAbstract{
	
	/**
	 * 
	 */

	public  Log log = LogFactory.getLog(SplitPageAbstract.class);
	
	private Object args[]=null;
	
	/**
	 * 开始（标识）
	 */
	private String startSign="start";
	
	/**
	 * 取多少（标识）
	 */
	private String limitSign="limit";
	
	/**
	 * 当前页(标识)
	 */
	private String pageSign;
	
	/**
	 * 开始
	 */
	private Integer start=0;
	
	/**
	 * 取多少
	 */
	private Integer limit=200;
	
	
	/**
	 * 是否序列化
	 */
	private  boolean isSerializable = false;
	
	public boolean isSerializable() {
		return isSerializable;
	}

	public void setSerializable(boolean isSerializable) {
		this.isSerializable = isSerializable;
	}

	/**
	 * 获取数据
	 * @param sign
	 * @return
	 */
	public abstract SuperList<Object[]> getData();
	
	public SplitPageAbstract(boolean isSerializable, Object...objects){
		this.isSerializable = isSerializable;
		this.args=objects;
	}
	
	public SplitPageAbstract(Object...objects){
		this.args=objects;
	}
	
	public boolean isSign() {
		return StringUtil.isNB(StringUtil.is(pageSign,Struts2Util.get("gid")));
	}
	
	/**
	 * 获取默认的过滤器
	 * @return
	 */
	public SuperListFilter getSuperListFilter(){
		return getSuperListFilter(null);
	}
	
	private SuperListFilter superListFilter=null;
	
	/**
	 * 获取默认的过滤器
	 * @return
	 */
	public SuperListFilter getSuperListFilter(SuperListFilter slf){
		if(slf==null){
			slf=new SuperListFilter() {
				
				@Override
				public Object doValue(Object o, int index, String columnName) {
					// TODO Auto-generated method stub
					return null;
				}
			};
			
			slf.setEnableDoValue(false);
		}
		superListFilter=slf;
		int xstart=this.getStart();
		int xlimit=this.getLimit();
		int toindex=xstart+xlimit;
		
		slf.setBeginEndNum(xstart*1L, toindex*1L);
		return slf;
	}
	
	/**
	 * 执行编译，如果不需要编译则传入False
	 * @param bool
	 * @return
	 */
	public SuperList<Object[]> execute(boolean bool){
		SuperList<Object[]> sl= null;
		try{
			String path="search/"+LocalContent.getUser().getUserNo()+"/";
			String serName=this.getPageSign()+".dat";
			
			if(this.isSerializable){
				if(!bool){
					sl=SerializableUtil.get(path, serName);
				}
			}else{
				sl= this.getData();
				if(bool){
					if(sl.size()>this.getLimit()){
						if(this.isSign()&&this.isSerializable){
							SerializableUtil.save(sl, path,serName);
						}
					}
				}
			}
			if(this.superListFilter==null){
				int xstart=this.getStart();
				int xlimit=this.getLimit();
				int toindex=xstart+xlimit;
				if(xstart >sl.size() ){
					throw new Exception("开始提取索引:"+xstart+",超过最大索引"+sl.size());
				}
				
				if(toindex>sl.size()){
					toindex=sl.size();
				}
				
				if(xstart==0&&toindex==sl.size()){
					//不需要重新整理集合数据
				}else{
					sl.setList(
							sl.subList(
									xstart, 
									toindex
							)
					);
				}
			}
		}catch (Exception e) {
			log.error("SplitPageAbstract execute error:"+e.getMessage(), e.getCause());
		}
		
		return sl;
	}
	
	/**
	 * 获取传入的参数
	 * @param <T>
	 * @param index
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getArgs(int index){
		return (T)(this.args[index]);
	}
	

	public Integer getLimit() {
		return Integer.valueOf(Struts2Util.get(this.getLimitSign(),this.limit+""));
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getLimitSign() {
		return limitSign;
	}

	public void setLimitSign(String limitSign) {
		this.limitSign = limitSign;
	}

	public String getPageSign() {
		return "temp"+StringUtil.is(pageSign,Struts2Util.get("gid"));
	}

	public void setPageSign(String pageSign) {
		this.pageSign = pageSign;
	}

	public Integer getStart() {
		return Integer.valueOf(Struts2Util.get(this.getStartSign(),this.start+""));
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public String getStartSign() {
		return startSign;
	}

	public void setStartSign(String startSign) {
		this.startSign = startSign;
	}

}
