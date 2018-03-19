package com.wjl.reviewdemo.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.base.BaseActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * author: WuJinLi
 * time  : 18/3/19
 * desc  :文件的存储和读取
 */

public class FileSaveActivity extends BaseActivity implements View.OnClickListener {

    EditText et_contant;
    Button btn_save, btn_read;
    TextView tv_file_content;
    String fileContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_storage);
        et_contant = findViewById(R.id.et_contant);
        btn_save = findViewById(R.id.btn_save);
        btn_read = findViewById(R.id.btn_read);
        tv_file_content = findViewById(R.id.tv_file_content);

        btn_save.setOnClickListener(this);
        btn_read.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                String inputText = et_contant.getText().toString();
                if (TextUtils.isEmpty(et_contant.getText().toString())) {
                    Toast.makeText(this, "填写内容为空，不能进行储存", LENGTH_SHORT).show();
                    return;
                }
                try {
                    saveContantInFile(inputText);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_read:
                readFile();
                break;
            default:
                break;
        }
    }

    /**
     * 读取文件内容并显示
     * 为避免文件内容较大，需要在子线程进行，避免ANR
     */
    private void readFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                fileContent = readFileContants();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(fileContent)) {
                            tv_file_content.setText(fileContent);
                        } else {
                            tv_file_content.setText("读取文件的内容");
                            Toast.makeText(FileSaveActivity.this, "文件内容为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 向文件中写入指定内容
     *
     * @param inputText
     * @throws IOException
     */
    @SuppressLint("WrongConstant")
    private void saveContantInFile(String inputText) throws IOException {

        FileOutputStream fileOutputStream = null;
        BufferedWriter writer = null;

        /**
         * Context.MODE_APPEND:内容不会被覆盖，追加存储
         * Context.MODE_PRIVATE:每次操作都会覆盖上一次写入的内容
         */
        try {
            fileOutputStream = openFileOutput("fileSave", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            writer.write(inputText);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            et_contant.setText("");
            Toast.makeText(this, "文件存储完毕", 0).show();
        }
    }


    /**
     * 读取文件中的内容
     *
     * @return
     */
    public String readFileContants() {
        FileInputStream fileInputStream = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();

        try {
            fileInputStream = openFileInput("fileSave");
            reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (content == null) {
            return "";
        } else {
            return content.toString();
        }
    }
}
