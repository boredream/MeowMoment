package com.boredream.meowmoment.domain;

public class WeiboStatus {
//	created_at	string	΢������ʱ��
	public String created_at;
//	id	int64	΢��ID
	public long id;
//	mid	int64	΢��MID
	public long mid;
//	idstr	string	�ַ����͵�΢��ID
	public String idstr;
//	text	string	΢����Ϣ����
	public String text;
//	source	string	΢����Դ
	public String source;
//	favorited	boolean	�Ƿ����ղأ�true���ǣ�false����
	public boolean favorited;
//	truncated	boolean	�Ƿ񱻽ضϣ�true���ǣ�false����
	public boolean truncated;
//	in_reply_to_status_id	string	����δ֧�֣��ظ�ID
//	in_reply_to_user_id	string	����δ֧�֣��ظ���UID
//	in_reply_to_screen_name	string	����δ֧�֣��ظ����ǳ�
//	thumbnail_pic	string	����ͼƬ��ַ��û��ʱ�����ش��ֶ�
	public String thumbnail_pic;
//	bmiddle_pic	string	�еȳߴ�ͼƬ��ַ��û��ʱ�����ش��ֶ�
	public String bmiddle_pic;
//	original_pic	string	ԭʼͼƬ��ַ��û��ʱ�����ش��ֶ�
	public String original_pic;
//	geo	object	������Ϣ�ֶ� ��ϸ
	public WeiboGeo geo;
//	user	object	΢�����ߵ��û���Ϣ�ֶ� ��ϸ
	public WeiboUser user;
//	retweeted_status	object	��ת����ԭ΢����Ϣ�ֶΣ�����΢��Ϊת��΢��ʱ���� ��ϸ
	public WeiboStatus retweeted_status;
//	reposts_count	int	ת����
	public int reposts_count;
//	comments_count	int	������
	public int comments_count;
//	attitudes_count	int	��̬��
	public int attitudes_count;
//	mlevel	int	��δ֧��
	public int mlevel;
//	visible	object	΢���Ŀɼ��Լ�ָ���ɼ�������Ϣ����object��typeȡֵ��0����ͨ΢����1��˽��΢����3��ָ������΢����4������΢����list_idΪ��������
	public Object visible;
//	pic_urls	object	΢����ͼ��ַ����ͼʱ���ض�ͼ���ӡ�����ͼ���ء�[]��
	public Object pic_urls;
//	ad	object array	΢�����ڵ��ƹ�΢��ID
	public Object ad;
}
