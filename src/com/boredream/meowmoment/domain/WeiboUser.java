package com.boredream.meowmoment.domain;

public class WeiboUser {
//	id	int64	用户UID
	public long id;
//	idstr	string	字符串型的用户UID
	public String idstr;
//	screen_name	string	用户昵称
	public String screen_name;
//	name	string	友好显示名称
	public String name;
//	province	int	用户所在省级ID
	public int province;
//	city	int	用户所在城市ID
	public int city;
//	location	string	用户所在地
	public String location;
//	description	string	用户个人描述
	public String description;
//	url	string	用户博客地址
	public String url;
//	profile_image_url	string	用户头像地址（中图），50×50像素
	public String profile_image_url;
//	profile_url	string	用户的微博统一URL地址
	public String profile_url;
//	domain	string	用户的个性化域名
	public String domain;
//	weihao	string	用户的微号
	public String weihao;
//	gender	string	性别，m：男、f：女、n：未知
	public String gender;
//	followers_count	int	粉丝数
	public int followers_count;
//	friends_count	int	关注数
	public int friends_count;
//	statuses_count	int	微博数
	public int statuses_count;
//	favourites_count	int	收藏数
	public int favourites_count;
//	created_at	string	用户创建（注册）时间
	public String created_at;
//	following	boolean	暂未支持
//	allow_all_act_msg	boolean	是否允许所有人给我发私信，true：是，false：否
	public boolean allow_all_act_msg;
//	geo_enabled	boolean	是否允许标识用户的地理位置，true：是，false：否
	public boolean geo_enabled;
//	verified	boolean	是否是微博认证用户，即加V用户，true：是，false：否
	public boolean verified;
//	verified_type	int	暂未支持
//	remark	string	用户备注信息，只有在查询用户关系时才返回此字段
	public String remark;
//	status	object	用户的最近一条微博信息字段 详细
	public WeiboStatus status;
//	allow_all_comment	boolean	是否允许所有人对我的微博进行评论，true：是，false：否
	public boolean allow_all_comment;
//	avatar_large	string	用户头像地址（大图），180×180像素
	public String avatar_large;
//	avatar_hd	string	用户头像地址（高清），高清头像原图
	public String avatar_hd;
//	verified_reason	string	认证原因
	public String verified_reason;
//	follow_me	boolean	该用户是否关注当前登录用户，true：是，false：否
	public boolean follow_me;
//	online_status	int	用户的在线状态，0：不在线、1：在线
	public int online_status;
//	bi_followers_count	int	用户的互粉数
	public int bi_followers_count;
//	lang	string	用户当前的语言版本，zh-cn：简体中文，zh-tw：繁体中文，en：英语
	public String lang;
}
