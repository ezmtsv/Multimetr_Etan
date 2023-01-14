package com.evan.etan_mult;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class frag_vol extends Fragment {

    ImageView bg_lay;
    ImageView img_scr;
    ImageView info_connect;
    ImageView but_range0;
    ImageView but_range1;
    ImageView but_range2;
    ImageView but_range3;
    ImageView but_range4;
    ImageView but_range5;
    ImageView but_range_auto;
    ImageView but_rms;
    ImageView but_arif;
    ImageView but_rect;
    ImageView but_amp;
    ImageView change_time;
    TextView txt_edit;
    TextView info_param_in;

    String tag = "TAG";

    final static int null_range = 0;
    final static int one_range = 1;
    final static int two_range = 2;
    final static int three_range = 3;
    final static int four_range = 4;
    final static int five_range = 5;
    final static int   auto_range_ON = 23;
    final static int   auto_range_OFF = 24;
    final static int flag_mod_RMS = 57;
    final static int flag_mod_Average = 58;
    final static int flag_mod_Average_rectified = 59;
    final static int flag_mod_Amplitude = 60;

    final static int frag_com_RANGE = 62;
    final static int frag_com_RMS = 63;
    final static int frag_com_TIME = 64;
    final static int frag_com_OFF = 65;

    static double val_info =0;

    boolean change_statusUSB = false;
    boolean mRUNcheckUSB = true;

    MainActivity mobj;
    val_draw drV;
    cmd CMD;
    check_statusUSB checkUSB;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_lay_vol, null);
        bg_lay = (ImageView) v.findViewById(R.id.imag_bg);
        img_scr = (ImageView)v.findViewById(R.id.imag_screen);
        info_connect = (ImageView)v.findViewById(R.id.info_connect);
        txt_edit = (TextView)v.findViewById(R.id.txt_edit);
        info_param_in = (TextView)v.findViewById(R.id.info_param_in);

        but_range0 = (ImageView)v.findViewById(R.id.but_range0);
        but_range1 = (ImageView)v.findViewById(R.id.but_range1);
        but_range2 = (ImageView)v.findViewById(R.id.but_range2);
        but_range3 = (ImageView)v.findViewById(R.id.but_range3);
        but_range4 = (ImageView)v.findViewById(R.id.but_range4);
        but_range5 = (ImageView)v.findViewById(R.id.but_range5);
        but_range_auto = (ImageView)v.findViewById(R.id.but_range_auto);

        but_rms = (ImageView)v.findViewById(R.id.but_rms);
        but_arif = (ImageView)v.findViewById(R.id.but_arif);
        but_rect = (ImageView)v.findViewById(R.id.but_rect);
        but_amp = (ImageView)v.findViewById(R.id.but_amp);

        try {
            init_lay();
        }catch(Exception e){Log.d(tag, "init_lay()");}
        show_range(mobj.frag_select_but);
        show_RMS(mobj.frag_select_but_RMS);

        drV = new val_draw(mobj.widht, mobj.hight, mobj.scale_X, mobj.scale_Y);
        img_scr.setImageDrawable(drV);

///////////////////////////////////////////////////////////////////
        bg_lay.setOnClickListener(oclB);        // освобождает от ложных срабатываний на главном активити во время работы на фрагменте
        img_scr.setOnClickListener(oclB);
        but_range_auto.setOnClickListener(oclB);
        but_range0.setOnClickListener(oclB);
        but_range1.setOnClickListener(oclB);
        but_range2.setOnClickListener(oclB);
        but_range3.setOnClickListener(oclB);
        but_range4.setOnClickListener(oclB);
        but_range5.setOnClickListener(oclB);
        but_rms.setOnClickListener(oclB);
        but_arif.setOnClickListener(oclB);
        but_rect.setOnClickListener(oclB);
        but_amp.setOnClickListener(oclB);
        txt_edit.setOnClickListener(oclB);
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
                case R.id.imag_screen:
                    mRUNcheckUSB = false;
                    getActivity().onBackPressed();        /// возврат назад к активити!!!!!
                    break;
                case R.id.but_range_auto:
                    mobj.frag_select_but = auto_range_ON;
                    show_range(auto_range_ON); mobj.frag_com_exe = frag_com_RANGE;
                    break;
                case R.id.but_range0:
                    mobj.frag_select_but = null_range;
                    show_range(null_range); mobj.frag_com_exe = frag_com_RANGE;
                    break;
                case R.id.but_range1:
                    mobj.frag_select_but = one_range;
                    show_range(one_range); mobj.frag_com_exe = frag_com_RANGE;
                    break;
                case R.id.but_range2:
                    mobj.frag_select_but = two_range;
                    show_range(two_range); mobj.frag_com_exe = frag_com_RANGE;
                    break;
                case R.id.but_range3:
                    mobj.frag_select_but = three_range;
                    show_range(three_range); mobj.frag_com_exe = frag_com_RANGE;
                    break;
                case R.id.but_range4:
                    mobj.frag_select_but = four_range;
                    show_range(four_range); mobj.frag_com_exe = frag_com_RANGE;
                    break;
                case R.id.but_range5:
                    mobj.frag_select_but = five_range;
                    show_range(five_range); mobj.frag_com_exe = frag_com_RANGE;
                    break;
                case R.id.but_rms:
                    mobj.frag_select_but_RMS = flag_mod_RMS;
                    show_RMS(flag_mod_RMS);
                    but_rms.setImageBitmap(mobj.btm_but_green_rms_lay2); mobj.frag_com_exe = frag_com_RMS;
                    break;
                case R.id.but_arif:
                    mobj.frag_select_but_RMS = flag_mod_Average;
                    show_RMS(flag_mod_Average);
                    but_arif.setImageBitmap(mobj.btm_but_green_arif_lay2); mobj.frag_com_exe = frag_com_RMS;
                    break;
                case R.id.but_rect:
                    mobj.frag_select_but_RMS = flag_mod_Average_rectified;
                    show_RMS(flag_mod_Average_rectified);
                    but_rect.setImageBitmap(mobj.btm_but_green_rect_lay2); mobj.frag_com_exe = frag_com_RMS;
                    break;
                case R.id.but_amp:
                    mobj.frag_select_but_RMS = flag_mod_Amplitude;
                    show_RMS(flag_mod_Amplitude);
                    but_amp.setImageBitmap(mobj.btm_but_green_amp_lay2); mobj.frag_com_exe = frag_com_RMS;
                    break;
                case R.id.txt_edit:

                    dialog_show(1);
                    break;
            }
        }
    };
    ////////////////////////////////////////////////////////
/*    void gray_lay3_but(){
        but_rms.setImageBitmap(mobj.btm_grey_rms);
        but_arif.setImageBitmap(mobj.btm_grey_arif);
        but_rect.setImageBitmap(mobj.btm_grey_rect);
        but_amp.setImageBitmap(mobj.btm_grey_amp);
    }*/
    ////////////////////////////////////////////////////////
    private void show_RMS(int rms){
        try {
            but_rms.setImageBitmap(mobj.btm_but_grey_rms_lay2);
            but_arif.setImageBitmap(mobj.btm_but_grey_arif_lay2);
            but_rect.setImageBitmap(mobj.btm_but_grey_rect_lay2);
            but_amp.setImageBitmap(mobj.btm_but_grey_amp_lay2);
            switch (rms) {
                case flag_mod_RMS:
                    but_rms.setImageBitmap(mobj.btm_but_green_rms_lay2);
                    break;
                case flag_mod_Amplitude:
                    but_amp.setImageBitmap(mobj.btm_but_green_amp_lay2);
                    break;
                case flag_mod_Average:
                    but_arif.setImageBitmap(mobj.btm_but_green_arif_lay2);
                    break;
                case flag_mod_Average_rectified:
                    but_rect.setImageBitmap(mobj.btm_but_green_rect_lay2);
                    break;
            }
        }catch(Exception e){Log.d(tag, "Exception show_RMS()");}

    }
    private void show_range(int range) {
        try {
            but_range0.setImageBitmap(mobj.btm_grey_range_lay2[3][0]);
            but_range1.setImageBitmap(mobj.btm_grey_range_lay2[3][1]);
            but_range2.setImageBitmap(mobj.btm_grey_range_lay2[3][2]);
            but_range3.setImageBitmap(mobj.btm_grey_range_lay2[3][3]);
            but_range4.setImageBitmap(mobj.btm_grey_range_lay2[3][4]);
            but_range5.setImageBitmap(mobj.btm_grey_range_lay2[3][5]);
            but_range_auto.setImageBitmap(mobj.btm_butgrey_auto_range_lay2);

            if (mobj.frag_select_but != auto_range_ON) {
                switch (range) {
                    case null_range:
                        but_range0.setImageBitmap(mobj.btm_green_range_lay2[3][range]);
                        break;
                    case one_range:
                        but_range1.setImageBitmap(mobj.btm_green_range_lay2[3][range]);
                        break;
                    case two_range:
                        but_range2.setImageBitmap(mobj.btm_green_range_lay2[3][range]);
                        break;
                    case three_range:
                        but_range3.setImageBitmap(mobj.btm_green_range_lay2[3][range]);
                        break;
                    case four_range:
                        but_range4.setImageBitmap(mobj.btm_green_range_lay2[3][range]);
                        break;
                    case five_range:
                        but_range5.setImageBitmap(mobj.btm_green_range_lay2[3][range]);
                        break;
                }
            } else {
                but_range_auto.setImageBitmap(mobj.btm_butgreen_auto_range_lay2);
            }
        }catch(Exception e){Log.d(tag, "Exception show_range()");}
    }
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
    void init_lay(){
        int i = 0;
        float posX = 7* mobj.scale_X;
        float posY = 787* mobj.scale_Y;
        float deltaX = 364*mobj.scale_X;

        set_pos_but(img_scr, 0,140*mobj.scale_Y);
        set_pos_but(bg_lay, 0,140*mobj.scale_Y);
        set_pos_but(info_connect, (int)(157*mobj.scale_X), (int)(1294*mobj.scale_Y));

        bg_lay.setBackground(mobj.draw_bg_lay_screen2);
        img_scr.setBackground(mobj.draw_bg_screen2);
        info_connect.setImageBitmap(mobj.btm_usb_connect);

/////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////

        but_range_auto.setImageBitmap(mobj.btm_butgreen_auto_range_lay2);
        set_pos_but(but_range_auto, posX+i*deltaX, posY);
        i++;
        but_range0.setImageBitmap(mobj.btm_grey_range_lay2[3][0]);
        set_pos_but(but_range0, posX+i*deltaX, posY);
        i++;
        but_range1.setImageBitmap(mobj.btm_grey_range_lay2[3][1]);
        set_pos_but(but_range1, posX+i*deltaX, posY);
        i++;
        but_range2.setImageBitmap(mobj.btm_grey_range_lay2[3][2]);
        set_pos_but(but_range2, posX+i*deltaX, posY);
        i++;
        but_range3.setImageBitmap(mobj.btm_grey_range_lay2[3][3]);
        set_pos_but(but_range3, posX+i*deltaX, posY);
        i++;
        but_range4.setImageBitmap(mobj.btm_grey_range_lay2[3][4]);
        set_pos_but(but_range4, posX+i*deltaX, posY);
        i++;
        but_range5.setImageBitmap(mobj.btm_grey_range_lay2[3][5]);
        set_pos_but(but_range5, posX+i*deltaX, posY);
        i++;
//////////////////////////////////////////////////////////////////////////////
/*        posX = 980* mobj.scale_X;
        posY = 1000* mobj.scale_Y;
        deltaX = 790*mobj.scale_X;
        float deltaY = 210*mobj.scale_Y;*/
        posX = 972* mobj.scale_X;
        posY = 1016* mobj.scale_Y;
        deltaX = 790*mobj.scale_X;
        float deltaY = 209*mobj.scale_Y;

        but_rms.setImageBitmap(mobj.btm_but_green_rms_lay2);
        set_pos_but(but_rms, posX, posY);

        but_arif.setImageBitmap(mobj.btm_but_grey_arif_lay2);
        set_pos_but(but_arif, posX+deltaX, posY);

        but_rect.setImageBitmap(mobj.btm_but_grey_rect_lay2);
        set_pos_but(but_rect, posX, posY+deltaY);

        but_amp.setImageBitmap(mobj.btm_but_grey_amp_lay2);
        set_pos_but(but_amp, posX+deltaX, posY+deltaY);

        txt_edit.setWidth((int)(700*mobj.scale_X)); //
        txt_edit.setHeight((int)(115*mobj.scale_Y));
        txt_edit.setTextSize(TypedValue.COMPLEX_UNIT_PX, 80*mobj.scale_Y);            // размер текста в пикселях
        txt_edit.setText(Integer.toString(mobj.time_select));
        set_pos_but(txt_edit, 170*mobj.scale_X, (int)(1116*mobj.scale_Y));
        ////////////////////////////////////////
        info_param_in.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60*mobj.scale_Y);            // размер текста в пикселях
        set_pos_but(info_param_in, (int) (250 * mobj.scale_X), (int) (1016 * mobj.scale_Y));
        ////////////////////////////////////////
    }
    ////////////////////////////////////////////////////////////////
    void delayMs(int time){
        try {

            Thread.sleep(time);
        } catch (Exception ee) {
            Log.d(tag, "Exception sleep");
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
                                    else txt_edit.setText("100");
                                }
                                int a;
                                a = Integer.parseInt(txt_edit.getText().toString());
                                if (a>0 && a<5001) {
                                    mobj.time_select = a;
                                    mobj.frag_com_exe = frag_com_TIME;
                                }
                                else{
                                    txt_edit.setText("100");
                                    show_txt_toast("Введите время выборки от '1' до '5000' мсек");
                                }

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
    void show_txt_toast(String str){
        Toast.makeText(getActivity(),str, Toast.LENGTH_SHORT).show();
    }
/////////////////////////////////////////////////////////////////

}