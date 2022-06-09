package clqwq.press.qycopenfiles;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView view;
    private static final int FILECHOOSER_RESULTCODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.openFile);
        view = findViewById(R.id.HashResult);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");   // 可以通过这个属性设置过滤出地文件，如 'image/*'只保存图片
                startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            // 获取到Uri
            Uri result = data == null || resultCode != MainActivity.RESULT_OK ? null : data.getData();
            try {
                InputStream in = this.getContentResolver().openInputStream(result);
                Hash[] hashes = new Hash[2];
                hashes[0] = new Md5();
                hashes[1] = new SHA1();
                StringBuilder res = new StringBuilder();
                for (Hash it : hashes) {
                    res.append(it.out(in));
                    res.append("\n");
                }
                view.setText(res);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Java 原生算法，不支持以字节流方式处理
        } else if (MainActivity.RESULT_OK != 0){
            Toast.makeText(this.getApplicationContext(), "打开文件失败", Toast.LENGTH_LONG).show();
        }
    }




}