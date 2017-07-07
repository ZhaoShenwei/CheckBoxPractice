package com.example.checkbox;


import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.checkbox.MyAdapter.ViewHolder;

import java.util.ArrayList;



public class MainActivity extends Activity {
    private ListView listView;
    private MyAdapter mAdapter;
    private ArrayList<String> list;
    private Button btn_select_all;
    private Button btn_cancel_all;
    private Button btn_delete_selected;
    private Button btn_select_ok;
    private int checkNum; // 记录选中的条目数量
    private TextView tv_show;// 用于显示选中的条目数量

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		/* 实例化各个控件 */
        listView = (ListView) findViewById(R.id.list_view);
        btn_select_all = (Button) findViewById(R.id.btn_select_all);
        btn_cancel_all = (Button) findViewById(R.id.btn_cancel_all);
        btn_delete_selected = (Button) findViewById(R.id.btn_delete_selected);
        btn_select_ok=(Button)findViewById(R.id.btn_selected_ok);

        tv_show = (TextView) findViewById(R.id.tv);
        list = new ArrayList<String>();   //list用来存储所有选项
        // 为Adapter准备数据
        initDate();////相当于PID中的Scan
        // 实例化自定义的MyAdapter
        mAdapter = new MyAdapter(list, this);
        // 绑定Adapter
        listView.setAdapter(mAdapter);

        // 全选按钮的回调接口
        btn_select_all.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 遍历list的长度，将MyAdapter中的map值全部设为true
                for (int i = 0; i < list.size(); i++) {
                    MyAdapter.getIsSelected().put(i, true);
                }
                // 数量设为list的长度
                checkNum = list.size();
                // 刷新listview和TextView的显示
                dataChanged();
            }
        });

        // 反选按钮的回调接口
        btn_delete_selected.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 遍历list的长度，将已选的设为未选，未选的设为已选
                for (int i = 0; i < list.size(); i++) {
                    if (MyAdapter.getIsSelected().get(i)) {
                        MyAdapter.getIsSelected().put(i, false);
                        checkNum--;
                    } else {
                        MyAdapter.getIsSelected().put(i, true);
                        checkNum++;
                    }
                }
                // 刷新listView和TextView的显示
                dataChanged();
            }
        });

        // 取消按钮的回调接口
        btn_cancel_all.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 遍历list的长度，将已选的按钮设为未选
                for (int i = 0; i < list.size(); i++) {
                    if (MyAdapter.getIsSelected().get(i)) {
                        MyAdapter.getIsSelected().put(i, false);
                        checkNum--;// 数量减1
                    }
                }
                // 刷新listview和TextView的显示
                dataChanged();
            }
        });


        btn_select_ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(getApplicationContext(),"选定！",Toast.LENGTH_SHORT);
                toast.show();
                Toast toast1=Toast.makeText(getApplicationContext(),"跳转到DeviceControlActivity！",Toast.LENGTH_SHORT);

                //第一个参数：设置toast在屏幕中显示的位置。我现在的设置是居中靠顶
                //第二个参数：相对于第一个参数设置toast位置的横向X轴的偏移量，正数向右偏移，负数向左偏移
                //第三个参数：同的第二个参数道理一样
                //如果你设置的偏移量超过了屏幕的范围，toast将在屏幕内靠近超出的那个边界显示
                toast1.setGravity(Gravity.TOP|Gravity.CENTER, 0, 300);
                toast1.show();

            }
        });



        // 绑定listView的监听器
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
                ViewHolder holder = (ViewHolder) arg1.getTag();

                holder.checkBox.toggle();

                MyAdapter.getIsSelected().put(arg2, holder.checkBox.isChecked());

                if (holder.checkBox.isChecked()) {
                    checkNum++;
                } else {
                    checkNum--;
                }
                // 用TextView显示
                tv_show.setText("已选中" + checkNum + "项");
            }
        });
    }

    // 初始化数据
    private void initDate() {
        for (int i = 0; i < 35; i++) {
            list.add("data" + " " + i);
        }
        ;
    }
    // 刷新listview和TextView的显示
    private void dataChanged() {
        // 通知listView刷新
        mAdapter.notifyDataSetChanged();
        // TextView显示最新的选中数目
        tv_show.setText("已选中" + checkNum + "项");
    };
}
