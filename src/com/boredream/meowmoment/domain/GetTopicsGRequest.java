package com.boredream.meowmoment.domain;

public class GetTopicsGRequest extends AuthGetRequest{
//	source			false	string	采用OAuth授权方式不需要此参数，其他授权方式为必填参数，数值为应用的AppKey。
//	access_token	false	string	采用OAuth授权方式为必填参数，其他授权方式不需要此参数，OAuth授权后获得。
//	q				true	string	搜索的话题关键字，必须进行URLencode，utf-8编码。
//	count			false	int	单页返回的记录条数，默认为10，最大为50。
//	page			false	int	返回结果的页码，默认为1。
	public String q;
	public int count;
	public int page;
}
