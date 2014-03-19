package com.boredream.meowmoment.domain;

public class WeiboStatus {
//	created_at	string	微博创建时间
	public String created_at;
//	id	int64	微博ID
	public long id;
//	mid	int64	微博MID
	public long mid;
//	idstr	string	字符串型的微博ID
	public String idstr;
//	text	string	微博信息内容
	public String text;
//	source	string	微博来源
	public String source;
//	favorited	boolean	是否已收藏，true：是，false：否
	public boolean favorited;
//	truncated	boolean	是否被截断，true：是，false：否
	public boolean truncated;
//	in_reply_to_status_id	string	（暂未支持）回复ID
//	in_reply_to_user_id	string	（暂未支持）回复人UID
//	in_reply_to_screen_name	string	（暂未支持）回复人昵称
//	thumbnail_pic	string	缩略图片地址，没有时不返回此字段
	public String thumbnail_pic;
//	bmiddle_pic	string	中等尺寸图片地址，没有时不返回此字段
	public String bmiddle_pic;
//	original_pic	string	原始图片地址，没有时不返回此字段
	public String original_pic;
//	geo	object	地理信息字段 详细
	public WeiboGeo geo;
//	user	object	微博作者的用户信息字段 详细
	public WeiboUser user;
//	retweeted_status	object	被转发的原微博信息字段，当该微博为转发微博时返回 详细
	public WeiboStatus retweeted_status;
//	reposts_count	int	转发数
	public int reposts_count;
//	comments_count	int	评论数
	public int comments_count;
//	attitudes_count	int	表态数
	public int attitudes_count;
//	mlevel	int	暂未支持
	public int mlevel;
//	visible	object	微博的可见性及指定可见分组信息。该object中type取值，0：普通微博，1：私密微博，3：指定分组微博，4：密友微博；list_id为分组的组号
	public Object visible;
//	pic_urls	object	微博配图地址。多图时返回多图链接。无配图返回“[]”
	public Object pic_urls;
//	ad	object array	微博流内的推广微博ID
	public Object ad;
}
