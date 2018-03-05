package com.shenjun.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shenjun.enums.SearchEnum;
import com.shenjun.web.thread.User;

public class ClassUtil {
	public static  Log log = LogFactory.getLog(ClassUtil.class);
	
	/**
	 * 寻找所有的父接口
	 * @param classList
	 * @param cls
	 * @param depth 寻找的深度，大于0
	 */
	@SuppressWarnings("rawtypes")
	public static List<Class> findAllInterfaces(List<Class> classList,Class cls,Integer depth){
		if(cls!=null){
			Class[] iClass =cls.getInterfaces();
			if(iClass!=null){
				for(Class c : iClass){
					classList.add(c);
				}
			}
			
			Class superClass = cls.getSuperclass();
			if(superClass!=null&&depth>0){
				depth--;
				findAllInterfaces(classList,superClass,depth);
			}
			
		}
		
		return classList;
	}
	/**
	 * 寻找所有的父接口
	 * @param cls
	 * @param depth 寻找的深度，大于0
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	public static List<Class> findAllInterfaces(Class cls,Integer depth){
		return findAllInterfaces(new ArrayList<Class>(),cls,depth);
	}
	
	
	/**
	 * 将Obj对象进行尝试性转成为指定对象
	 * @param obj 任意的常用数据类型
	 * @param type 对象类型
	 * @return Obj的Type类型对象
	 */
	@SuppressWarnings("unchecked")
	public static Object caseValue(Object obj,Type type){
		if(obj==null){
			return null;
		}
		//String 型按格式进行换
		if(obj instanceof String){
			obj = StringUtil.stringToValue(obj+"", type);
		}
		//按类型强转
		if(obj.getClass()!=type){
			if(type==String.class){
				obj=obj+""; 
			}else if(Double.class==type){
				obj = Double.parseDouble(obj+"");
			}else if(Float.class==type){
				obj = Float.parseFloat(obj+"");
			}else if(Integer.class==type){
				obj = Integer.parseInt(obj+"");
			}
		}
		return obj;
	}
	/**
	 * 获取一个Class类中指定的方法
	 * @param name 方法名
	 * @param cls class类名
	 * @param searchEnum 【SearchEnum.NOT_DIS_UPPER_LOWER|SearchEnum.DIS_UPPER_LOWER】是否区分大小写
	 * @return
	 */
	public static Method getMethod(String name,Class cls,SearchEnum searchEnum){
		try{
			if(searchEnum==SearchEnum.NOT_DIS_UPPER_LOWER){
				Method[] methods=cls.getMethods();
				for(Method method:methods){
					if(name.toLowerCase().equals(method.getName().toLowerCase())){
						return method;
					} 
				}
				return null;
			}else if(searchEnum==SearchEnum.DIS_UPPER_LOWER){
				try {
					return cls.getMethod(name, new Class[]{});
				} catch (SecurityException e) {
					log.error(e.getMessage(),e.getCause());
				} catch (NoSuchMethodException e) {
					log.error(e.getMessage(),e.getCause());
				}
			}
		}catch(Exception e){
			return null;
		}
		
		return null;
		
	}
	/**
	 * 将Field[]字段转为成String[]
	 * @param f Field[]字段
	 * @return String[]
	 */
	public static String[] field2Name(Field[] f){   
		String[] name = new String[f.length];   
		for(int i = 0;i <f.length;i++){   
			name[i] = f[i].getName();   
		}   
		return name;   
	}   
	/**
	 *  获取Field[]对象属性值
	 * @param f Field[]
	 * @param o 对象
	 * @return Object[]对象数组
	 * @throws Exception
	 */    
	public static Object[] field2Value(Field[] f,Object o)throws Exception{  
		Object[] value =new Object[f.length];   
		for(int i = 0;i < f.length;i++){  
			f[i].setAccessible(true);
			value[i]=f[i].get(o);   
		}   
		return value;   
	}
	
	/**
	 * 将一个对象Bean中的值拷贝到另一个对象中。取值与设置都通过get/set
	 * @param source 源对象Bean
	 * @param target 目标对象Bean
	 */
	public static void copy(Object source ,Object target){
		try{
			Field[] fields=getFields(target.getClass());
			
			for(Field field:fields){
				Method method_target=ClassUtil.getMethod("set"+field.getName(), target.getClass(),SearchEnum.NOT_DIS_UPPER_LOWER);
				Method method_source=ClassUtil.getMethod("get"+field.getName(), source.getClass(),SearchEnum.NOT_DIS_UPPER_LOWER);
				
				if(method_source!=null&&method_target!=null){
					method_target.invoke(target, method_source.invoke(source,null));
				}
			}
		}catch(Exception e){
			log.error("classUtil copy."+e,e.getCause());
		}
	}
	
	/**
	 * 获取一个Class对象的所有的 Field
	 * @param cls Class对象
	 * @return Field数组
	 */
	public static Field[] getFields(Class cls){
		
		List<Field> ls = new ArrayList<Field>();
		
		for(int i=0;i<10;i++){
			Field[] fields=cls.getDeclaredFields();
			
			for(Field field : fields){
				ls.add(field);
			}
			
			cls=cls.getSuperclass();
			
			if("java.lang.Object".equals(cls.getName())){
				 break;
			}
			
		}
		
		return ls.toArray(new Field[]{});
	}
	
	/**
	 * 转Map对象中的值依次通过传递的Class对象构建一个实例化对象
	 * @param cls Class对象
	 * @param map 值的Map对象【对象的属性对应】
	 * @return Class的实例化对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T classLoad(Class cls,Map<String,String> map){
		try {
			Object obj =cls.newInstance();
			
			Field[] fields=getFields(cls);
			
			for(Field field:fields){
				String v=map.get(field.getName());
				if(StringUtil.isNB(v)){
					Method method=ClassUtil.getMethod("set"+field.getName(), cls,SearchEnum.NOT_DIS_UPPER_LOWER);
					if(method!=null){
						Type[] type = method.getGenericParameterTypes();
						if(type.length==1){
							method.invoke(obj, ClassUtil.caseValue(v, type[0]));
						}
						
					}
				}
			}
			return (T)obj;
		} catch (Exception e) {
			log.error(e+"castexception.",e.getCause());
		}
		return null;
	}
	
	/**
	 * 把fieldNames与values装载到Class类中，并实例化【fieldNames与values应依次对应】
	 * @param cls Class对象类型
	 * @param fieldNames Class对象的属性名数组
	 * @param values Class对象的值数组
	 * @return 返回Class的实例为对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T classLoad(Class cls,String[] fieldNames,Object[] values){
		try {
			Object obj =cls.newInstance();
			
			for(int i=0,len=fieldNames.length;i<len;i++){
				Object value = values[i];
				Method method=ClassUtil.getMethod("set"+fieldNames[i], cls,SearchEnum.NOT_DIS_UPPER_LOWER);
				
				if(method!=null){
					Type[] type = method.getGenericParameterTypes();
					
					if(type.length==1){
						method.invoke(obj, ClassUtil.caseValue(value, type[0]));
					}
				}
				
			}
			return (T)obj;
		} catch (Exception e) {
			log.error(e+"castexception.",e.getCause());
		}
		return null;
	}
	
/*	public static void main(String[] args) {
		
		User l=classLoad(User.class,new String[]{"userNo"},new Object[]{"11111"});
		System.out.println(l.getUserNo());
	}*/
}
