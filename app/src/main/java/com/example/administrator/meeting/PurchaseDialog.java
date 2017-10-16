package com.example.administrator.meeting;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017-02-24.
 */
public class PurchaseDialog extends Dialog {

    private Context context;
    private int amount;
    private double price, allprice;
    EditText editamount;
    TextView all_price;
    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {
        public void doPay();
        public void doClose();

    }

    public PurchaseDialog(Context context, Double price) {
        super(context);
        this.context = context;
        this.price = price;

    }

    public double getAllprice(){
        return allprice;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }
    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_purchase, null);
        setContentView(view);
        TextView textprice = (TextView) view.findViewById(R.id.text_price);
        all_price = (TextView) view.findViewById(R.id.text_price_all);
        TextView pay = (TextView) view.findViewById(R.id.text_pay);
        editamount = (EditText) view.findViewById(R.id.edit_amount);
        ImageButton close = (ImageButton) view.findViewById(R.id.btn_close);
        ImageButton sub = (ImageButton) view.findViewById(R.id.btn_sub);
        ImageButton plus = (ImageButton) view.findViewById(R.id.btn_plus);
        close.setOnClickListener(new clickListener());
        sub.setOnClickListener(new clickListener());
        plus.setOnClickListener(new clickListener());
        pay.setOnClickListener(new clickListener());


        amount = 0;
        allprice = 0;

        textprice.setText(String.valueOf(price));
        editamount.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        editamount.setText(String.valueOf(amount));
        amount = Integer.parseInt(editamount.getText().toString());
        allprice = amount * price;
        all_price.setText(String.valueOf(allprice));
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        dialogWindow.setWindowAnimations(R.style.dialogstyle);  //添加动画
    }


    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int id = v.getId();
            switch (id) {
                case R.id.btn_close:
                    clickListenerInterface.doClose();
                    break;
                case R.id.text_pay:
                    clickListenerInterface.doPay();
                    break;
                case R.id.btn_sub:
                    amount = Integer.parseInt(editamount.getText().toString());
                    if (--amount < 0) {
                        amount++;
                        editamount.setText("0");
                    } else {
                        editamount.setText(String.valueOf(amount));
                    }
                    amount = Integer.parseInt(editamount.getText().toString());
                    allprice = amount * price;
                    all_price.setText(String.valueOf(allprice));
                    break;
                case R.id.btn_plus:
                    amount = Integer.parseInt(editamount.getText().toString());
                    amount++;
                    editamount.setText(String.valueOf(amount));
                    amount = Integer.parseInt(editamount.getText().toString());
                    allprice = amount * price;
                    all_price.setText(String.valueOf(allprice));
                    break;
            }
        }
    }
}
