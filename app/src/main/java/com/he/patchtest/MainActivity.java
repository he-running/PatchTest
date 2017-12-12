package com.he.patchtest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String mPath= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator;
    private final String newPath=mPath+"app2.apk";
    private final String patchPath=mPath+"appPatch.patch";

    private final File newApkFile=new File(newPath);
    private final File patchFile=new File(patchPath);

    private Toast toast;

    private Button btn_diff;
    private Button btn_merge;
    private TextView tv_diff;
    private TextView tv_merge;
    private TextView tv_tip;

    private RelativeLayout rl_progress;

    private DiffAsyncTask diffAsyncTask;
    private MergeAsyncTask mergeAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_diff= (Button) findViewById(R.id.btn_diff);
        btn_diff.setOnClickListener(this);
        btn_merge= (Button) findViewById(R.id.btn_merge);
        btn_merge.setOnClickListener(this);

        tv_diff= (TextView) findViewById(R.id.tv_diff);
        tv_merge= (TextView) findViewById(R.id.tv_merge);

        rl_progress= (RelativeLayout) findViewById(R.id.rl_progress);

        diffAsyncTask=new DiffAsyncTask();
        mergeAsyncTask=new MergeAsyncTask();

        tv_tip= (TextView) findViewById(R.id.tv_tip);
        if ("1.0.1".equals(ApkUtil.getApkVersionName(this))){
            tv_tip.setVisibility(View.VISIBLE);
        }else {
            tv_tip.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        rl_progress.setVisibility(View.VISIBLE);
        switch (v.getId()){
            case R.id.btn_diff:
                showToast("开始生成差分包，请稍候...");
                btn_diff.setEnabled(false);
                diffAsyncTask.execute();
                break;
            case R.id.btn_merge:
                showToast("开始合并差分包，请稍候...");
                btn_merge.setEnabled(false);
                mergeAsyncTask.execute();
                break;
        }
    }

    private void showToast(String s){
        if (toast==null){
            toast= Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT);
            toast.show();
        }
        toast.setText(s);
        toast.show();
    }

    class DiffAsyncTask extends AsyncTask<Object,Object,Boolean>{


        @Override
        protected Boolean doInBackground(Object[] params) {
            if (!newApkFile.exists()){
                showToast("没有新的apk");
                return false;
            }
            int diff=-1;
            diff=PatchUtil.generateDiffApk(ApkUtil.getApkPath(MainActivity.this),newPath,patchPath);
            if (diff==0){
                return true;
            }else{
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean o) {
            rl_progress.setVisibility(View.GONE);
            btn_diff.setEnabled(true);
            if (o){
                tv_diff.setText("diff succeed");
            }else {
                tv_diff.setText("diff failed");
            }
        }
    }

    class MergeAsyncTask extends AsyncTask<Object,Object,Boolean>{

        @Override
        protected Boolean doInBackground(Object[] params) {
            if (!patchFile.exists()){
                showToast("找不到差分包");
                return false;
            }
            int merge=-1;
            merge=PatchUtil.mergeDiffApk(ApkUtil.getApkPath(MainActivity.this),newPath,patchPath);
            if (merge==0){
                return true;
            }else{
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean o) {
            rl_progress.setVisibility(View.GONE);
            btn_merge.setEnabled(true);
            if (o){
                if (newApkFile.exists()){
                    ApkUtil.installApk(MainActivity.this,newPath);
                }
                tv_merge.setText("merge succeed");
            }else {
                tv_merge.setText("merge failed");
            }
        }
    }

}
