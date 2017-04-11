package activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import cn.bosyun.anyou.R;
import utils.CommonUtils;
import view.LinePathView;

/**
 * Created by Jinpan on 2016/12/27 0027.
 */
public class HandWriteActivity extends Activity {
    private Button save;
    private Button clean;
    private LinePathView mPathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_write);
        save = ((Button) findViewById(R.id.save));
        clean = ((Button) findViewById(R.id.clean));
        mPathView = ((LinePathView) findViewById(R.id.view));
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPathView.getTouched()) {
                    try {
                        mPathView.save("/storage/emulated/0/Anyou/qm.png", true, 10);
                        setResult(100);
                        CommonUtils.showToast(HandWriteActivity.this, "图片已保存:" + "文件管理-本地-内部存储-Anyou");
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {

                    Toast.makeText(HandWriteActivity.this, "您没有签名~", Toast.LENGTH_SHORT).show();
                }
            }
        });
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathView.clear();
            }
        });
    }

}
