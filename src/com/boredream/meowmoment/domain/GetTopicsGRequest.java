package com.boredream.meowmoment.domain;

public class GetTopicsGRequest extends AuthGetRequest{
//	source			false	string	����OAuth��Ȩ��ʽ����Ҫ�˲�����������Ȩ��ʽΪ�����������ֵΪӦ�õ�AppKey��
//	access_token	false	string	����OAuth��Ȩ��ʽΪ���������������Ȩ��ʽ����Ҫ�˲�����OAuth��Ȩ���á�
//	q				true	string	�����Ļ���ؼ��֣��������URLencode��utf-8���롣
//	count			false	int	��ҳ���صļ�¼������Ĭ��Ϊ10�����Ϊ50��
//	page			false	int	���ؽ����ҳ�룬Ĭ��Ϊ1��
	public String q;
	public int count;
	public int page;
}
