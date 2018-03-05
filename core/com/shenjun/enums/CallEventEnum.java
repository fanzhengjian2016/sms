package com.shenjun.enums;
/**
 * 电话事件
 * @author 沈军
 *
 */
@SuppressWarnings("unused")
public class CallEventEnum {
	/**
	 * //注册
	 */
	public final static int SERVER_REGISTER=0x0009;
	/**
	 * //挂机
	 */
	public final static int HANDUP=0x0001;
	/**
	 * //拨号
	 */
	public final static int MARK_CALL=0x0002;
	/**
	 * //播放文件
	 */
	public final static int PLAY_FILE=0x0003;
	/**
	 * //停止播放
	 */
	public final static int STOP_PLAY_FILE=0x0004;
	/**
	 * //添加会议
	 */
	public final static int ADD_CONF=0x0005;
	/**
	 * //通出会议
	 */
	public final static int EXIT_CONF=0x0006;
	/**
	 * //转接其它通道
	 */
	public final static int FORWARD_CHAN=0x0007;
	/**
	 * //转向Ivr流程
	 */
	public final static int FORWARD_IVR=0x0008;
	/**
	 * //退出服务器
	 */
	public final static int EXIT_SERVER=0x000A;
	
	/**
	 * //获取信息
	 */
	public final static int MSG_TYPE_GET=0x000B;
	/**
	 * //设置信息
	 */
	public final static int MSG_TYPE_SET=0x000C;
	/**
	 * //退出线程
	 */
	public final static int MSG_TYPE_EXIT=0x000D;

}
