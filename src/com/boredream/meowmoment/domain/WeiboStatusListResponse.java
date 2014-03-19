package com.boredream.meowmoment.domain;

import java.util.List;

public class WeiboStatusListResponse {

	public List<WeiboStatus> statuses;
	public long previous_cursor;
	public long next_cursor;
	public int total_number;
}
