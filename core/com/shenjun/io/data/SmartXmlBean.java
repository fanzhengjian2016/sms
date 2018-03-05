package com.shenjun.io.data;

public abstract class SmartXmlBean implements SXmlBean{
	public boolean isLoad = false;

	public abstract int load(byte[] xml);

	public abstract byte[] getReqXml();

	public abstract byte[] getResXml();
}
