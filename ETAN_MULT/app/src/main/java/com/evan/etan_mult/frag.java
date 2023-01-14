package com.evan.etan_mult;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class frag extends Fragment {
    String tag = "TAG";

    ImageView bg_lay;
    ImageView img_scr;
    ImageView info_connect;
    ImageView change_time;
    TextView txt_edit;
    TextView info_param_in;
//    Button buttt;
//    TextView v_menu;
    final static int flag_mod_ind = 0;
    final static int flag_mod_freq = 1;
    final static int flag_mod_cap = 2;
    final static int frag_com_OFF = 65;
    final static int frag_com_TIME = 64;

    boolean change_statusUSB = false;
    boolean mRUNcheckUSB = true;

    static double val_info =0;

    MainActivity mobj;
    val_draw drV;
    cmd CMD;
    check_statusUSB checkUSB;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment, null);
//        buttt = (Button)v.findViewById(R.id.button);
        bg_lay = (ImageView) v.findViewById(R.id.imag_bg);
        img_scr = (ImageView)v.findViewById(R.id.imag_screen);
        info_connect = (ImageView)v.findViewById(R.id.info_connect);
        txt_edit = (TextView)v.findViewById(R.id.txt_edit);
        change_time = (ImageView)v.findViewById(R.id.change_time);
        info_param_in = (TextView)v.findViewById(R.id.info_param_in);
//        v_menu = (TextView)v.findViewById(R.id.v_menu);

        try {
            init_lay();
        }catch(Exception e){Log.d(tag, "init_lay()");}
//        v_menu.setHeight((int)(138*mobj.scale_Y)); v_menu.setWidth((int)(160*mobj.scale_X)); // устанавливаем высоту и ширину кнопки меню
//        set_pos_but(v_menu, (int)(2400*mobj.scale_X), 0);   // располагаем виев для вывода кнопки меню.
//        bg_lay.setBackground(createLayerDrawable(R.drawable.bg_screen2, 1, 1)); /// установка бэкграунда нужного разрешения
//        img_scr.setBackground(createLayerDrawable(R.drawable.scr_val100, 1, (float)0.4));

        /////////////////////////////////
        drV = new val_draw(mobj.widht, mobj.hight, mobj.scale_X, mobj.scale_Y);  // получаем объект класса val_draw в нужном разрешении
        img_scr.setImageDrawable(drV);

//       buttt.setOnClickListener(oclB);
        bg_lay.setOnClickListener(oclB);        // освобождает от ложных срабатываний на главном активити во время работы на фрагменте
        img_scr.setOnClickListener(oclB);
        txt_edit.setOnClickListener(oclB);
        change_time.setOnClickListener(oclB);

//        v_menu.setOnClickListener(oclB);
        checkUSB = new check_statusUSB();

        return v;
    }
    ////////////////////////////////////
    View.OnClickListener oclB = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // по id определеяем кнопку, вызвавшую этот обработчик
            switch (v.getId()) {
/*                case R.id.v_menu:
                    mobj.frag_select_but = 1;
                    break;*/
                case R.id.imag_screen:
                    mRUNcheckUSB = false;
                    getActivity().onBackPressed();        /// возврат назад к активити!!!!!
                    break;
/*                case R.id.button:
                    Log.d(tag, "кнопка BUT");
                    val_info+=10;
                   // buttt.setText("BUT "+ val_info);
                    buttt.setText("change_statusUSB "+change_statusUSB+", mobj.USB_connect "+ mobj.USB_connect);
                    show_txt_toast("push!");
                    mobj.frag_com_exe = frag_com_OFF;
                    break;*/
                case R.id.txt_edit:
                    dialog_show(1);
                    break;
                case R.id.change_time:
                    read_flag_INDCAP_wire(mobj.frag_mode_work, true);
                    mobj.frag_com_exe = frag_com_TIME;
                    break;
            }
        }
    };
    ////////////////////////////////////////////////////////
    void set_pos_but(View v, float x, float y){                 // если не выполнить эту функцию сначала, то функция v.set.X(float x) работает некорректно
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(

                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        lp.setMargins((int)x,(int)y,0,0);
        //                       lp.setMargins(x, y, 0, 0);
        v.setLayoutParams(lp);
    }
//////////////////////////////////////////////////////////////
    /*
private Drawable createLayerDrawable(int ID_drw, float x, float y) {
    float xx = (float)mobj.widht*x;
    float yy = (float)mobj.hight*y;
    Bitmap bitm = BitmapFactory.decodeResource(getResources(), ID_drw);
    Bitmap btm = bitm.createScaledBitmap(bitm, (int)xx, (int)yy, true);
    BitmapDrawable drawable0 = new BitmapDrawable(getResources(), btm);
//    BitmapDrawable drawable0 = (BitmapDrawable) getResources().getDrawable(
//            R.drawable.bg_main1920);
    Log.d(tag, "widht "+btm.getWidth()+" hight "+btm.getHeight());

    return drawable0;
}
*/
void init_lay(){

//    buttt.setWidth(600);
//    set_pos_but(buttt, 10,800);
    set_pos_but(img_scr, 0,140*mobj.scale_Y);
    set_pos_but(bg_lay, 0,140*mobj.scale_Y);
    set_pos_but(info_connect, (int)(157*mobj.scale_X), (int)(1294*mobj.scale_Y));

    bg_lay.setBackground(mobj.draw_bg_lay_screen2);
    img_scr.setBackground(mobj.draw_bg_screen2);
    info_connect.setImageBitmap(mobj.btm_usb_connect);

    if(mobj.frag_mode_work != flag_mod_freq) {
        int time;
        int shift = (int) (380 * mobj.scale_X);
        int posY_txt = (int) (1116 * mobj.scale_Y);
        int posX_txt = (int)(140 * mobj.scale_X);
        txt_edit.setWidth(shift); //
        txt_edit.setHeight((int) (115 * mobj.scale_Y));
        txt_edit.setTextSize(TypedValue.COMPLEX_UNIT_PX, 80 * mobj.scale_Y);            // размер текста в пикселях
        if (mobj.frag_mode_work == flag_mod_ind) {
            time = (int)mobj.induct_wire;
            info_param_in.setText("С УЧЕТОМ ПРОВОДОВ(нГн)");
            info_param_in.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60*mobj.scale_Y);            // размер текста в пикселях
            set_pos_but(info_param_in, (int) (180 * mobj.scale_X), (int) (1016 * mobj.scale_Y));
        } else {
            time = (int)mobj.cap_wire;
            info_param_in.setText("ЕМКОСТЬ ВХОДА(пФ)");
            info_param_in.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60*mobj.scale_Y);            // размер текста в пикселях
            set_pos_but(info_param_in, (int) (250 * mobj.scale_X), (int) (1016 * mobj.scale_Y));
        }
        txt_edit.setText(Integer.toString(time));
        set_pos_but(txt_edit, posX_txt, posY_txt);

//        change_time.setImageBitmap(mobj.btm_but_grey_change);
        read_flag_INDCAP_wire(mobj.frag_mode_work, false);
        set_pos_but(change_time, posX_txt+shift, (int)(posY_txt*0.996));
        /////////////////////////////////////////////

        /////////////////////////////////////////////
    } else{
        txt_edit.setVisibility(View.GONE);
        change_time.setVisibility(View.GONE);
    }
}
////////////////////////////////////////////////////////////////
void delayMs(int time){
    try {

        Thread.sleep(time);
    } catch (Exception ee) {
        Log.d(tag, "Exception sleep");
    }
}

    ///////////////////////////////////////////////////////////////////////////////
    public void read_flag_INDCAP_wire(int mod, boolean state_change){
        if(state_change) {
            if (mod == flag_mod_ind) {
                if (!mobj.flag_editIND_wire) {
                    mobj.flag_editIND_wire = true;
                    change_time.setImageBitmap(mobj.btm_but_green_change_lay2);
                } else {
                    mobj.flag_editIND_wire = false;
                    change_time.setImageBitmap(mobj.btm_but_grey_change_lay2);
                }
            }
            if (mod == flag_mod_cap) {
                if (!mobj.flag_editCAP_wire) {
                    mobj.flag_editCAP_wire = true;
                    change_time.setImageBitmap(mobj.btm_but_green_change_lay2);
                } else {
                    mobj.flag_editCAP_wire = false;
                    change_time.setImageBitmap(mobj.btm_but_grey_change_lay2);
                }
            }
        }else{
            if (mod == flag_mod_ind) {
                if (!mobj.flag_editIND_wire) {
                    change_time.setImageBitmap(mobj.btm_but_grey_change_lay2);
                } else {
                    change_time.setImageBitmap(mobj.btm_but_green_change_lay2);
                }
            }
            if (mod == flag_mod_cap) {
                if (!mobj.flag_editCAP_wire) {
                    change_time.setImageBitmap(mobj.btm_but_grey_change_lay2);
                } else {
                    change_time.setImageBitmap(mobj.btm_but_green_change_lay2);
                }
            }
        }
    }
////////////////////////////////////////////////
    public class check_statusUSB extends Thread {
        // Конструктор
        check_statusUSB() {
            // Создаём новый поток
            super("check USB");
            start(); // Запускаем поток
        }
        public void run() {
            while(mRUNcheckUSB) {
                try {
                    if (change_statusUSB != mobj.USB_connect) {
                        if (mobj.USB_connect) {
                            try{
                                info_connect.setImageBitmap(mobj.btm_usb_on); Log.d(tag, "load bitmap usb_on");
                            }catch(Exception t){Log.d(tag, "Exception usb_on");}
                        } else {
                            try{
                                info_connect.setImageBitmap(mobj.btm_usb_off); Log.d(tag, "load bitmap usb_off");
                            }catch(Exception t){Log.d(tag, "Exception usb_off");}
//                            mobj.frag_com_exe = true;
                        }
                        change_statusUSB = mobj.USB_connect;
                    }
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Log.d(tag, "поток прерван");
                }
                if(mobj.close_frag){
                    mRUNcheckUSB = false;
                    mobj.frag_com_exe = frag_com_OFF;
                }
            }
            Log.d(tag, "stop Thread!");

        }
    }
/////////////////////////////////////////////////////
void show_txt_toast(String str){
    Toast.makeText(getActivity(),str, Toast.LENGTH_SHORT).show();
}
/////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////
void dialog_show(int dialog){
    final AlertDialog alert;
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom);
//    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    switch(dialog){
        case 1:
            final EditText gist_v = new EditText(getActivity());
            gist_v.setTextColor(Color.BLACK);
            gist_v.setHintTextColor(Color.GRAY);
            if(txt_edit.getText()!= null){gist_v.setHint(txt_edit.getText());}
            else {gist_v.setHint("100");}
            gist_v.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setTitle("Мультиметр ETANxx")
//                    .setMessage("Установите время выборки в мс")
                    .setView(gist_v)
                    .setNegativeButton("ВЫХОД", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .setPositiveButton("ПРИМЕНИТЬ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if(gist_v.getText().length() != 0) {
                                txt_edit.setText(gist_v.getText().toString());
                            }
                            else{
                                if(gist_v.getHint().length() != 0){txt_edit.setText(gist_v.getHint().toString());}
                                else txt_edit.setText("300");
                            }
                            int a;
                            a = Integer.parseInt(txt_edit.getText().toString());
                            if (mobj.frag_mode_work == flag_mod_ind) {mobj.induct_wire = a; }
                            else{ mobj.cap_wire = a; };
                            mobj.frag_com_exe = frag_com_TIME;
                        }
                    });
            alert = builder.create();
            alert.show();
            break;
        case 2:
            break;
        case 3:

            break;
        case 4:
            break;
        case 5:
            break;
        case 6:

            break;
    }
}
    /////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////
public void onStart() {
    super.onStart();
    Log.d(tag, "Fragment1 onStart");
}

    public void onResume() {
        super.onResume();
        Log.d(tag, "Fragment1 onResume");
    }

    public void onPause() {
        super.onPause();
        Log.d(tag, "Fragment1 onPause");
    }

    public void onStop() {
        super.onStop();
        Log.d(tag, "Fragment1 onStop");
    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.d(tag, "Fragment1 onDestroyView");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(tag, "Fragment1 onDestroy");
    }

    public void onDetach() {
        super.onDetach();
        Log.d(tag, "Fragment1 onDetach");
    }
/////////////////////////////////////////////////////////////////    
}