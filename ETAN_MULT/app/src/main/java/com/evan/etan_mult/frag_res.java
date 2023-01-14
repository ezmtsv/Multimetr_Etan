package com.evan.etan_mult;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class frag_res extends Fragment {

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

    String tag = "TAG";

    final static int null_range = 0;
    final static int one_range = 1;
    final static int two_range = 2;
    final static int three_range = 3;
    final static int four_range = 4;
    final static int five_range = 5;
    final static int   auto_range_ON = 23;
    final static int   auto_range_OFF = 24;

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
        View v = inflater.inflate(R.layout.frag_lay_res, null);
        bg_lay = (ImageView) v.findViewById(R.id.imag_bg);
        img_scr = (ImageView)v.findViewById(R.id.imag_screen);
        info_connect = (ImageView)v.findViewById(R.id.info_connect);
        but_range0 = (ImageView)v.findViewById(R.id.but_range0);
        but_range1 = (ImageView)v.findViewById(R.id.but_range1);
        but_range2 = (ImageView)v.findViewById(R.id.but_range2);
        but_range3 = (ImageView)v.findViewById(R.id.but_range3);
        but_range4 = (ImageView)v.findViewById(R.id.but_range4);
        but_range5 = (ImageView)v.findViewById(R.id.but_range5);
        but_range_auto = (ImageView)v.findViewById(R.id.but_range_auto);

        try {
            init_lay();
        }catch(Exception e){Log.d(tag, "init_lay()");}
        show_range(mobj.frag_select_but);

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
        //////////////////////////////////////////////////////////////////////////////

        but_range_auto.setImageBitmap(mobj.btm_butgreen_auto_range_lay2);
        set_pos_but(but_range_auto, posX+i*deltaX, posY);
        i++;
        but_range0.setImageBitmap(mobj.btm_grey_range_lay2[5][0]);
        set_pos_but(but_range0, posX+i*deltaX, posY);
        i++;
        but_range1.setImageBitmap(mobj.btm_grey_range_lay2[5][1]);
        set_pos_but(but_range1, posX+i*deltaX, posY);
        i++;
        but_range2.setImageBitmap(mobj.btm_grey_range_lay2[5][2]);
        set_pos_but(but_range2, posX+i*deltaX, posY);
        i++;
        but_range3.setImageBitmap(mobj.btm_grey_range_lay2[5][3]);
        set_pos_but(but_range3, posX+i*deltaX, posY);
        i++;
        but_range4.setImageBitmap(mobj.btm_grey_range_lay2[5][4]);
        set_pos_but(but_range4, posX+i*deltaX, posY);
        i++;
        but_range5.setImageBitmap(mobj.btm_grey_range_lay2[5][5]);
        set_pos_but(but_range5, posX+i*deltaX, posY);
        i++;
//////////////////////////////////////////////////////////////////////////////
    }
    ////////////////////////////////////////////////////////////////
    private void show_range(int range) {
        try {
            but_range0.setImageBitmap(mobj.btm_grey_range_lay2[5][0]);
            but_range1.setImageBitmap(mobj.btm_grey_range_lay2[5][1]);
            but_range2.setImageBitmap(mobj.btm_grey_range_lay2[5][2]);
            but_range3.setImageBitmap(mobj.btm_grey_range_lay2[5][3]);
            but_range4.setImageBitmap(mobj.btm_grey_range_lay2[5][4]);
            but_range5.setImageBitmap(mobj.btm_grey_range_lay2[5][5]);
            but_range_auto.setImageBitmap(mobj.btm_butgrey_auto_range_lay2);

            if (mobj.frag_select_but != auto_range_ON) {
                switch (range) {
                    case null_range:
                        but_range0.setImageBitmap(mobj.btm_green_range_lay2[5][range]);
                        break;
                    case one_range:
                        but_range1.setImageBitmap(mobj.btm_green_range_lay2[5][range]);
                        break;
                    case two_range:
                        but_range2.setImageBitmap(mobj.btm_green_range_lay2[5][range]);
                        break;
                    case three_range:
                        but_range3.setImageBitmap(mobj.btm_green_range_lay2[5][range]);
                        break;
                    case four_range:
                        but_range4.setImageBitmap(mobj.btm_green_range_lay2[5][range]);
                        break;
                    case five_range:
                        but_range5.setImageBitmap(mobj.btm_green_range_lay2[5][range]);
                        break;
                }
            } else {
                but_range_auto.setImageBitmap(mobj.btm_butgreen_auto_range_lay2);
            }
        }catch(Exception e){Log.d(tag, "Exception show_range()");}
    }
    ////////////////////////////////////////////////////////
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
    void show_txt_toast(String str){
        Toast.makeText(getActivity(),str, Toast.LENGTH_SHORT).show();
    }
/////////////////////////////////////////////////////////////////

}