package com.boredream.meowmoment.domain;

public class WeiboUser {
//	id	int64	�û�UID
	public long id;
//	idstr	string	�ַ����͵��û�UID
	public String idstr;
//	screen_name	string	�û��ǳ�
	public String screen_name;
//	name	string	�Ѻ���ʾ����
	public String name;
//	province	int	�û�����ʡ��ID
	public int province;
//	city	int	�û����ڳ���ID
	public int city;
//	location	string	�û����ڵ�
	public String location;
//	description	string	�û���������
	public String description;
//	url	string	�û����͵�ַ
	public String url;
//	profile_image_url	string	�û�ͷ���ַ����ͼ����50��50����
	public String profile_image_url;
//	profile_url	string	�û���΢��ͳһURL��ַ
	public String profile_url;
//	domain	string	�û��ĸ��Ի�����
	public String domain;
//	weihao	string	�û���΢��
	public String weihao;
//	gender	string	�Ա�m���С�f��Ů��n��δ֪
	public String gender;
//	followers_count	int	��˿��
	public int followers_count;
//	friends_count	int	��ע��
	public int friends_count;
//	statuses_count	int	΢����
	public int statuses_count;
//	favourites_count	int	�ղ���
	public int favourites_count;
//	created_at	string	�û�������ע�ᣩʱ��
	public String created_at;
//	following	boolean	��δ֧��
//	allow_all_act_msg	boolean	�Ƿ����������˸��ҷ�˽�ţ�true���ǣ�false����
	public boolean allow_all_act_msg;
//	geo_enabled	boolean	�Ƿ������ʶ�û��ĵ���λ�ã�true���ǣ�false����
	public boolean geo_enabled;
//	verified	boolean	�Ƿ���΢����֤�û�������V�û���true���ǣ�false����
	public boolean verified;
//	verified_type	int	��δ֧��
//	remark	string	�û���ע��Ϣ��ֻ���ڲ�ѯ�û���ϵʱ�ŷ��ش��ֶ�
	public String remark;
//	status	object	�û������һ��΢����Ϣ�ֶ� ��ϸ
	public WeiboStatus status;
//	allow_all_comment	boolean	�Ƿ����������˶��ҵ�΢���������ۣ�true���ǣ�false����
	public boolean allow_all_comment;
//	avatar_large	string	�û�ͷ���ַ����ͼ����180��180����
	public String avatar_large;
//	avatar_hd	string	�û�ͷ���ַ�����壩������ͷ��ԭͼ
	public String avatar_hd;
//	verified_reason	string	��֤ԭ��
	public String verified_reason;
//	follow_me	boolean	���û��Ƿ��ע��ǰ��¼�û���true���ǣ�false����
	public boolean follow_me;
//	online_status	int	�û�������״̬��0�������ߡ�1������
	public int online_status;
//	bi_followers_count	int	�û��Ļ�����
	public int bi_followers_count;
//	lang	string	�û���ǰ�����԰汾��zh-cn���������ģ�zh-tw���������ģ�en��Ӣ��
	public String lang;
}
