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
		TitlebarUtils.initTitlebar(this, R.drawable.transparent, "����", null, this);
		
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
			DialogUtils.showConfirmDialog(this, "��������", "��������һ�α��ݵ�����,ȷ�ϸ�����?", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					isBackupSuccess = FileUtils.backupDB(SettingActivity.this);
					showToast(isBackupSuccess?"���ݳɹ�":"����ʧ��");
				}
			});
			break;
		case R.id.setting_btn_restore:
			DialogUtils.showConfirmDialog(this, "�ָ�����", "�����ǵ�ǰӦ���ڵ�����,ȷ�ϸ�����?", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					isRestoreSuccess = FileUtils.restoreDB(SettingActivity.this);
					showToast(isRestoreSuccess?"�ָ��ɹ�":"�ָ�ʧ��");
				}
			});
			break;
		case R.id.setting_btn_readme:
			DialogUtils.showMsgDialog(this, "����˵��", 
					"����:\n���ݽ�������sd���µ�com.boredream.meowmoment/backup/meowmomentData.db,�ɽ��ļ����б�������������ؼ�\n\n" +
					"�ָ�:\n��sd���еı����ļ��ָ���Ӧ��,û�б����ļ�ʱ���޷��ָ�\n\n" +
					"ע��:\n�ָ�����ʱ,������Ӧ������������;�����ļ�Ҳ�Ḳ���ϴα��ݵ��ļ�");
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
