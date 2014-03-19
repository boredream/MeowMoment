package com.boredream.meowmoment.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.boredream.meowmoment.BaseActivity;
import com.boredream.meowmoment.R;
import com.boredream.meowmoment.constants.CommonConstants;
import com.boredream.meowmoment.util.DialogUtils;
import com.boredream.meowmoment.util.FileUtils;
import com.boredream.meowmoment.util.TitlebarUtils;

public class SettingActivity extends BaseActivity implements OnClickListener {
	
	public static final int RESULT_CODE_DATA_UPDATE = 10501;
	
	private Button btnbackup;
	private Button btnRestore;
	private Button btnReadme;
	
	private boolean isBackupSuccess;
	private boolean isRestoreSuccess;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		initView();
	}

	private void initView() {
		TitlebarUtils.initTitlebar(this, R.drawable.transparent, "设置", null, this);
		
		btnbackup = (Button) findViewById(R.id.setting_btn_backup);
		btnRestore = (Button) findViewById(R.id.setting_btn_restore);
		btnReadme = (Button) findViewById(R.id.setting_btn_readme);
		
		btnbackup.setOnClickListener(this);
		btnRestore.setOnClickListener(this);
		btnReadme.setOnClickListener(this);
	}

	private void setDataUpdateResult() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(CommonConstants.EXTRA_KEY_IS_DATA_UPDATE, isBackupSuccess | isRestoreSuccess);
		setResult(RESULT_CODE_DATA_UPDATE, intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titlebar_left:
			setDataUpdateResult();
			this.finish();
			break;
		case R.id.setting_btn_backup:
			DialogUtils.showConfirmDialog(this, "备份数据", "将覆盖上一次备份的数据,确认覆盖吗?", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					isBackupSuccess = FileUtils.backupDB(SettingActivity.this);
					showToast(isBackupSuccess?"备份成功":"备份失败");
				}
			});
			break;
		case R.id.setting_btn_restore:
			DialogUtils.showConfirmDialog(this, "恢复数据", "将覆盖当前应用内的数据,确认覆盖吗?", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					isRestoreSuccess = FileUtils.restoreDB(SettingActivity.this);
					showToast(isRestoreSuccess?"恢复成功":"恢复失败");
				}
			});
			break;
		case R.id.setting_btn_readme:
			DialogUtils.showMsgDialog(this, "备份说明", 
					"备份:\n数据将备份至sd卡下的com.boredream.meowmoment/backup/meowmomentData.db,可将文件自行保存至其他储蓄控件\n\n" +
					"恢复:\n将sd卡中的备份文件恢复至应用,没有备份文件时将无法恢复\n\n" +
					"注意:\n恢复备份时,将覆盖应用下已有数据;备份文件也会覆盖上次备份的文件");
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		setDataUpdateResult();
		super.onBackPressed();
	}
}
