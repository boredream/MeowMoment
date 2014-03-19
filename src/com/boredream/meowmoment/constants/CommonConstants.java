package com.boredream.meowmoment.constants;

public class CommonConstants {
	public static final String SP_NAME = "meowmoment_config";
	public static final String SP_WEIBO_NAME = "com.baidu.cloudsdk.social.SESSION";
	public static final String BAIDU_API_KEY = "XVqTuaOkh1ItGnFm1OyKZRsy"; 
	public static final String WEIXIN_APP_ID = "wx34005dad370133de"; 
	
	public static final String WEIBO_OFFICIAL_NAME = "����ʱ��";
	public static final String WEIBO_OFFICAL_TOPIC = "#"+WEIBO_OFFICIAL_NAME+"#";
	public static final int WEIBO_COUNT_OF_PAGE = 20;
	
	public static final String EXTRA_KEY_IS_DATA_UPDATE = "isDataUpdate";
	public static final String EXTRA_KEY_MOMENT = "moment";
	
	/** ��ǰ DEMO Ӧ�õ� APP_KEY��������Ӧ��Ӧ��ʹ���Լ��� APP_KEY �滻�� APP_KEY */
	public static final String SINAWEIBO_APP_KEY = "579613669"; 

	public static final String ADD_SIGH = "#add_sigh#";
	
    /** 
     * ��ǰ DEMO Ӧ�õĻص�ҳ��������Ӧ�ÿ���ʹ���Լ��Ļص�ҳ��
     * 
     * <p>
     * ע��������Ȩ�ص�ҳ���ƶ��ͻ���Ӧ����˵���û��ǲ��ɼ��ģ����Զ���Ϊ������ʽ������Ӱ�죬
     * ����û�ж��彫�޷�ʹ�� SDK ��֤��¼��
     * ����ʹ��Ĭ�ϻص�ҳ��https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    /**
     * Scope �� OAuth2.0 ��Ȩ������ authorize �ӿڵ�һ��������ͨ�� Scope��ƽ̨�����Ÿ����΢��
     * ���Ĺ��ܸ������ߣ�ͬʱҲ��ǿ�û���˽�������������û����飬�û����� OAuth2.0 ��Ȩҳ����Ȩ��
     * ѡ����Ӧ�õĹ��ܡ�
     * 
     * ����ͨ������΢������ƽ̨-->��������-->�ҵ�Ӧ��-->�ӿڹ������ܿ�������Ŀǰ������Щ�ӿڵ�
     * ʹ��Ȩ�ޣ��߼�Ȩ����Ҫ�������롣
     * 
     * Ŀǰ Scope ֧�ִ����� Scope Ȩ�ޣ��ö��ŷָ���
     * 
     * �й���Щ OpenAPI ��ҪȨ�����룬��鿴��http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * ���� Scope ���ע�������鿴��http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE = 
            "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
    
}
