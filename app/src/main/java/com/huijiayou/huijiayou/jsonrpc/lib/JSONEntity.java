package com.huijiayou.huijiayou.jsonrpc.lib;

import com.huijiayou.huijiayou.xxtea.ParamHanding;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Provides a HttpEntity for json content
 */
class JSONEntity extends StringEntity
{
	/**
	 * Basic constructor
	 * @param jsonObject		
	 * @throws UnsupportedEncodingException
	 */
	public JSONEntity(JSONObject jsonObject) throws UnsupportedEncodingException
	{
//		super(jsonObject.toString());
		super(ParamHanding.paramJsonHanding(jsonObject));
	}

	/**
	 * Constructor with encoding specified
	 * @param jsonObject
	 * @param encoding	Chosen encoding from HTTP.UTF_8, HTTP.UTF_16 or ISO_8859_1 or any other supported format
	 * @throws UnsupportedEncodingException
	 */
	public JSONEntity(JSONObject jsonObject, String encoding) throws UnsupportedEncodingException
	{
//		super(jsonObject.toString(), encoding);
		super(ParamHanding.paramJsonHanding(jsonObject), encoding);
		setContentEncoding(encoding);
	}

	@Override
	public Header getContentType()
	{
		return new BasicHeader(HTTP.CONTENT_TYPE, "application/json");
	}	
}
