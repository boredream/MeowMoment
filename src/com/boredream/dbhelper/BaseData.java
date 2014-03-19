package com.boredream.dbhelper;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class BaseData implements Serializable {
	private long _id;

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}
	
}
