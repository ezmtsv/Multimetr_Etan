package com.evan.etan_mult;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;

import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

import android.view.MenuItem;
import android.support.v7.widget.PopupMenu;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import static java.util.Arrays.fill;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private View item_img = null;
    ImageView but_cur, but_curG;
    ImageView but_vol, but_volG;
    ImageView but_res, but_resG;
    ImageView but_ind, but_indG;
    ImageView but_cap, but_capG;
    ImageView but_freq, but_freqG;

    ImageView but_rms;
    ImageView but_arif;
    ImageView but_rect;
    ImageView but_amp;

    ImageView screen1;
    ImageView but_range0;
    ImageView but_range1;
    ImageView but_range2;
    ImageView but_range3;
    ImageView but_range4;
    ImageView but_range5;
    ImageView but_range_auto;
    ImageView change_time;

    ImageView info_connect;

    TextView txt_time;
    TextView v_menu;
    TextView info_param_in;
    TextView txt_debug;
    TextView txt_edit;
    TextView info_value;
    TextView info_mili;

    Bitmap btm_grey_cur, btm_grey_vol, btm_grey_res, btm_grey_ind, btm_grey_cap, btm_grey_freq;                     // битмапы серых кнопок 1ряд
    Bitmap btm_green_cur, btm_green_vol, btm_green_res, btm_green_ind, btm_green_cap, btm_green_freq;               // битмапы зеленых кнопок 1ряд
    static Bitmap btm_green_rms, btm_green_arif, btm_green_rect, btm_green_amp;                                     // битмапы серых кнопок 3ряд
    static Bitmap btm_but_green_amp_lay2, btm_but_green_rms_lay2,btm_but_green_arif_lay2, btm_but_green_rect_lay2;  // битмапы зеленых кнопок RMS lay2
    static Bitmap btm_but_grey_amp_lay2, btm_but_grey_rms_lay2,btm_but_grey_arif_lay2, btm_but_grey_rect_lay2;      // битмапы серых кнопок RMS lay2
    static Bitmap btm_grey_rms, btm_grey_arif, btm_grey_rect, btm_grey_amp;                                         // битмапы зеленых кнопок 3ряд
    Bitmap btm_sreen1;
    Bitmap btm_butgrey_auto_range, btm_butgreen_auto_range;

    static Bitmap btm_but_green_change, btm_but_grey_change;
    static Bitmap btm_but_green_change_lay2, btm_but_grey_change_lay2;
    static Bitmap btm_usb_off, btm_usb_on, btm_usb_connect;

    Bitmap [][] btm_grey_range = new Bitmap[6][6];
    Bitmap [][] btm_green_range = new Bitmap[6][6];

    static Bitmap [][] btm_grey_range_lay2 = new Bitmap[6][6];
    static Bitmap [][] btm_green_range_lay2 = new Bitmap[6][6];
    static Bitmap btm_butgrey_auto_range_lay2, btm_butgreen_auto_range_lay2;
    static Bitmap btm_butgrey_autoCUR_range_lay2, btm_butgreen_autoCUR_range_lay2;
    Bitmap btm_screenshot_main, btm_screenshot_dop, btm_screenshot_tap;

    ImageView img_1, img_2, img_13, img_4, img_5, img_6;
    RelativeLayout ml;

    static int widht;
    static int hight;
    int id_img;
    int eX, eY;
    private int offset_x = 0;
    private int offset_y = 0;

    final int sizebuffer = 64;
    final static int flag_mod_ind = 0;
    final static int flag_mod_freq = 1;
    final static int flag_mod_cap = 2;
    final static int flag_mod_vol = 3;
    final static int flag_mod_cur = 4;
    final static int flag_mod_res = 5;

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

    final static int cmd_ind =              0x11;
    final static int cmd_cap =			    0x12;
    final static int cmd_res =			    0x13;
    final static int cmd_vol =			    0x14;
    final static int cmd_cur =			    0x15;
    final static int cmd_freq =		        0x16;
    final static int cmd_data =		        0x17;
    final static int cmd_debug =		    0x18;
    final static int cmd_sel_range =	    0x19;
    final static int OK =				    0x77;
    final static int NO	=			        0x99;
    final static int cmd_read_koeff =       0x33;
    final static int cmd_read_koeff_ALL =   0x34;
    final static int end_read_koeff	=		0x35;
    final static int start_read_koeff =		0x36;
    final static int cmd_read_koeff_lost = 		0x37;
    final static int Number_pak_corr = 41;		//общее число пакетов для коэфф.кор. //30//37

    final static int DC_plus =		0x11; // mode DC, plus
    final static int DC_minus =	    0x22; // mode DC, minus
    final static int DC_minus_L =	0x43; //минус левого 4-байтного слова
    final static int DC_minus_T =	0x53; //минус правого и левого 4-байтных слов

    final static int frag_select_menu = 12;

    static int Flag_algoritm_mod;
    static int max_ADC_rez = 0;
    static int min_ADC_rez = 0;
    static int frag_select_but = 0;
    static int frag_select_but_RMS = 0;
    static int screen_range = 0;
    static int frag_mode_work;

    static double [][] korr_res = new double[6][4];
    static double [][] korr_vol = new double[12][4];
    static double [][] korr_cur = new double[8][4];
    static double [][] korr_cap = new double[2][4];
    static double [][] korr_ind = new double[10][4];
    static double [][] korr_freq = new double[3][4];

    static double val_info = 0;

    byte[] bufIN = new byte[sizebuffer];
    byte[] bufOUT = new byte[sizebuffer];

    int [] bufINint = new int[sizebuffer];
    int [] bufOUTint = new int[sizebuffer];
    int bufferDataLength;
    int rec_data_f1, rec_data_f2;
    int adc_stm = 0;
    int data_tmp = 0;
    int count_answer;


    static int range_INDCAPFR = 0;					// переменная диапазона для частоты, ёмкости и индуктивности
    static int time_select = 100;
    static int frag_com_exe = 0;


    static float scale_X;
    static float scale_Y;
    float sizetxt_rms;
    float sizetxt_amp;
    double tmp_X, tmp_Y;
    static double induct_wire = 300.0;
    static double cap_wire = 700.0;

    boolean USB_RUN = true;
    boolean touchFlag = false;
    boolean menu4_check = false;
    boolean flag_receiv = false;				// флаг принятых данных от МК
    boolean read_kor_success = false;
    boolean flag_cmd_OK = false;				// флаг успешно переданной команды
    boolean readCORR_flg = true;
    static boolean adc_rez_minus;
    static boolean flag_editIND_wire = false;
    static boolean flag_editCAP_wire = false;
    static boolean flag_ALARM = false;
    static boolean USB_connect = false;
    static boolean close_frag = false;

    static byte delitel_freq;
    static byte mode_work_znak2;
    static byte mode_work;
    static byte znak_2 = 0;

    String tag = "TAG";
    String status_USB, USB_tag;
    String debug_txt= "";
    String debug_txt1 = "";
    static String INFO, INF_mili, maxV, minV, pref_max, pref_min;
    static String sys_time;

    USB_hid hiddev;
    private UsbManager mUsbManager;

    Timer timer_res;
    TimerTask mTimerreset;

    cmd CMD;
    show_data SHOW_d = new show_data();
    Timer timerOnesec, timerSHVAL;
    TimerTask tasksek, taskshow;

    FragmentTransaction fTrans;
    static frag     frag1;
    static frag_cur fragcur;
    static frag_vol fragvol;
    static frag_res fragres;
    Fragment Frg;
    static Drawable draw_bg_lay_screen2;
    static Drawable draw_bg_screen2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);  // убирание системной строки с экрана
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        ml = (RelativeLayout) findViewById(R.id.main_L);

        but_cur = (ImageView)findViewById(R.id.but_cur);
        but_vol = (ImageView)findViewById(R.id.but_vol);
        but_res = (ImageView)findViewById(R.id.but_res);
        but_ind = (ImageView)findViewById(R.id.but_ind);
        but_cap = (ImageView)findViewById(R.id.but_cap);
        but_freq = (ImageView)findViewById(R.id.but_freq);

        but_rms = (ImageView)findViewById(R.id.but_rms);
        but_arif = (ImageView)findViewById(R.id.but_arif);
        but_rect = (ImageView)findViewById(R.id.but_rect);
        but_amp = (ImageView)findViewById(R.id.but_amp);

        screen1 = (ImageView)findViewById(R.id.screen1);
        but_range0 = (ImageView)findViewById(R.id.but_range0);
        but_range1 = (ImageView)findViewById(R.id.but_range1);
        but_range2 = (ImageView)findViewById(R.id.but_range2);
        but_range3 = (ImageView)findViewById(R.id.but_range3);
        but_range4 = (ImageView)findViewById(R.id.but_range4);
        but_range5 = (ImageView)findViewById(R.id.but_range5);
        but_range_auto = (ImageView)findViewById(R.id.but_range_auto);
        change_time = (ImageView)findViewById(R.id.change_time);
        info_connect = (ImageView)findViewById(R.id.info_connect);

        info_param_in = (TextView)findViewById(R.id.info_param_in);
        txt_time = (TextView)findViewById(R.id.txt_time);
        v_menu = (TextView)findViewById(R.id.v_menu);
        info_value = (TextView)findViewById(R.id.info_value);
        txt_debug = (TextView)findViewById(R.id.txt_debug);
        txt_edit = (TextView) findViewById(R.id.txt_edit);
        info_mili = (TextView)findViewById(R.id.info_mili);

        // получение ширины текущего разрешения
        WindowManager w = getWindowManager();// объект менеджер окна
        Display d = w.getDefaultDisplay();
        widht = d.getWidth();
        hight = d.getHeight();
        tmp_X = (double)widht;
        tmp_Y = (double)hight;
        scale_X = (float)(tmp_X/2560);
        scale_Y = (float)(tmp_Y/1440);

        ml.setBackground(createLayerDrawable(R.drawable.bg_main, 1, 1)); /// установка бэкграунда нужного разрешения
//        ml.setBackgroundResource(R.drawable.bg_main1280); если применить стандартную процедуру бэкграунда, то приложение начинает тормозить

        draw_bg_lay_screen2 = createLayerDrawable(R.drawable.bg_screen2, 1, (float)0.8916);
        draw_bg_screen2 = createLayerDrawable(R.drawable.scr_val100, 1, (float)0.44);

        multimetr_start();
        create_but();
        find_bitm_lay2();
        /////////////////////////////
        CMD.init_struct_koeff();
        /////////////инициализация USB //////////////////////////////////
        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        workUSBThread usb_thread = new workUSBThread();
        usb_thread.start();

        /////////////детектирование отключение устройства от USB ///////////////////
        //Регистрация broadcast receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        registerReceiver(mUsbReceiver, filter);

        View root = findViewById(android.R.id.content).getRootView();
        root.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (touchFlag) {
                    System.err.println("Display If  Part ::->" + touchFlag);

                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:
                            Log.d(tag, "onTouch DOWN oncreate");
                            show_mode(item_img);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            eX = (int) event.getX();
                            eY = (int) event.getY();
//                            int x = (int) event.getX() - offset_x;
//                            int y = (int) event.getY() - offset_y;
//                            item_img.setY(y);item_img.setX(x);
                            break;
                        case MotionEvent.ACTION_UP:
                            Log.d(tag, "onTouch UP oncreate");
                            show_mode_UP(item_img);
                            touchFlag = false;

                            break;
                    }
                }
                return true;
            }
        });

        timer_res = new Timer();
        mTimerreset = new Timerreset();
        try{timer_res.schedule(mTimerreset, 1000, 200);}catch(Exception c){;}
//////////////////////////////////////////////////////
/*        readCORR_flg = false;
        timerSHVAL = new Timer();
        taskshow = new showVAL();
        try{timerSHVAL.schedule(taskshow, 1000, 20);}catch(Exception c){;}
*/
//////////////////////////////////////////////////////

//        try{timerSHVAL.schedule(taskshow, 1000, 20);}catch(Exception c){;}
    }
    ///////////////////////END_ON_CREATE////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    void multimetr_start(){
        CMD = new cmd();
        CMD.Auto_range = auto_range_ON;
        Flag_algoritm_mod = flag_mod_RMS;
        frag_select_but = auto_range_ON;
        frag_select_but_RMS = flag_mod_RMS;
        CMD.Sel_range = null_range;
        CMD.mod_work = flag_mod_vol;
        gray_lay1_but();
        txt_edit.setText(Integer.toString(time_select));
        but_vol.setImageBitmap(btm_green_vol);
        show_ampl(false);
        CMD.mod_work = flag_mod_vol;
        show_range(null_range);
        gray_lay3_but();
        but_rms.setImageBitmap(btm_green_rms);
        send_MULT(cmd_vol);
    }
    /////////////////////////////////////////////////////////////////////
    private Drawable createLayerDrawable(int ID_drw, float x, float y) {     //получаем объект Drawable из ресурсов (id = "ID_drw") нужной ширины "x"  и высоты "y"
        float xx = (float)widht*x;
        float yy = (float)hight*y;
        Bitmap bitm = BitmapFactory.decodeResource(getResources(), ID_drw);
        Bitmap btm = bitm.createScaledBitmap(bitm, (int)xx, (int)yy, true);
        BitmapDrawable drawable0 = new BitmapDrawable(getResources(), btm);
//    BitmapDrawable drawable0 = (BitmapDrawable) getResources().getDrawable(
//            R.drawable.bg_main1920);
        Log.d(tag, "widht "+btm.getWidth()+" hight "+btm.getHeight());

        return drawable0;
    }
    /////////////////////////////////////////////////////////////////////
    public void on_click(View v) {
//        show_mode(item_img);
    }
    ////////////////////////////////////////////////////////////////////
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                item_img = v;
                offset_x = (int) event.getX();
                offset_y = (int) event.getY();
                touchFlag = true;

                Log.d(tag, "onTouch DOWN");
                break;
            case MotionEvent.ACTION_UP:
                touchFlag = false;
                Log.d(tag, "onTouch UP");
                break;
            default:
                break;
        }
        return false;
    }
    /////////////////////////////////////////////////////////

    //////////////////////////////////////////////// обработчик отключения от USB
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action))
            {
                USB_connect = false;
                info_connect.setImageBitmap(btm_usb_off);
                btm_usb_connect = btm_usb_off;
                readCORR_flg = true;
                timerSHVAL.cancel();
                INFO = "00.00"; INF_mili = " ";
                show_txt_toast("Мультиметр отключен!");
                show_ampl(false);
                handlvalue.sendEmptyMessage(0);
            }
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action))
            {
//                show_txt_toast("Мультиметр подключен!");
                close_frag = true;
            }
        }
    };
    ///////////////////USB /////////////////////////////////
    public class workUSBThread extends Thread {
        public void run() {

            while(USB_RUN) {
                if (!USB_connect) {                  // в случае потери соединения метод BroadcastReceiver выставит USB_connect = false, выполняем попытку повторного подключения
                    try {
                        HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
                        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
                        UsbDevice dev = null;
                        hiddev = new USB_hid(deviceList, deviceIterator, mUsbManager);
                        if (hiddev.search()) {
                            USB_tag = "Мультиметр подключен";
                            USB_connect = true; bufferDataLength = hiddev.bufferDataLength;
//                            cmd_send = synchro;
//                            statusUSB = OK;
////////////////////////
                            info_connect.setImageBitmap(btm_usb_on);
                            btm_usb_connect = btm_usb_on;
////////////////////////
                            INFO = ".....";
//                            info_value.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizetxt_amp);
//                            INFO = "Загрузка...";
                            INF_mili = "";
                            handlvalue.sendEmptyMessage(0);
//                            readCORR_flg = true;
                            read_init RD_init = new read_init();
//
                        } else {
                            USB_tag = "Подключите Мультиметр!!!";
//                            statusUSB = NO;
                        }
                        //         status_USB = status_USB + "\n" + hiddev.status_USB;
                        status_USB = hiddev.status_USB;
                    } catch (Exception e) {
                        Log.d(tag, e.toString() + "Exception thread");
                        status_USB = "Exception search!"; USB_connect = false;
//                        statusUSB = NO;
//                        handlstatus.sendEmptyMessage(0);
                    }

//                handltxt.sendEmptyMessage(0);
                } else {
                    read_USB();
                }
                try {

                    Thread.sleep(100);
                } catch (Exception ee) {
                    Log.d(tag, "Exception sleep");
                }
            }
        }
    }
    public class read_init extends Thread{
        read_init(){
            super("read_init");
            start(); // Запускаем поток
        }
        public void run() {
            timerOnesec = new Timer();
            tasksek = new TimerTask(){
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
//                                read_MULT_koeff();
                                read_MULT_koeff_ALL();
/*                                int k = 0;
                                while (!read_MULT_koeff_ALL()){
                                    k++; if(k>7){ break; }
                                }*/
                            }catch(Exception e){Log.d(tag, " TimerSEC");}

                        }
                    });
                }
            };
            try{timerOnesec.schedule(tasksek, 100);}catch(Exception c){;}// одноразовый запуск таймера
        }
    }
    public class USBThread_send extends Thread {
        // Конструктор
        USBThread_send() {
            // Создаём новый поток
            super("send USB");
            start(); // Запускаем поток
        }
        public void run() {
//            boolean suc = false;
//            int count = 0;
//            while(!suc) {
//                count++;
                try {
                    hiddev.send_USB(bufOUT);
                    status_USB = hiddev.status_USB;
                    Thread.sleep(50);
//                    suc = true;
                } catch (InterruptedException e) {
                    Log.d(tag, "Второй поток прерван");
//                    if(count>5)break;
                }
//            }
        }
    }
    void read_USB(){
        String ss="";
        if(hiddev.read(bufIN, bufINint)){
            for(int a=0; a<bufferDataLength; a++){ss =ss + " : "+bufINint[a];}
////////////////////////////////////////////////////////////////////////////////
            if(!readCORR_flg) {
                rec_data_f1 = bufINint[11] | (bufINint[12] << 8) | (bufINint[13] << 16) | (bufINint[14] << 24);
                rec_data_f2 = bufINint[16] | (bufINint[17] << 8) | (bufINint[18] << 16) | (bufINint[19] << 24);
                //					flag_cor_cap = buf_receiv[20];
                delitel_freq = (byte) bufINint[20];
                //					select_range = delitel_freq;
                //////////////////////////////////////////////////////////
                if (bufINint[2] == OK) {
                    flag_ALARM = true;
                } else {
                    flag_ALARM = false;
                }
                //////////////////////////////////////////////////////////
                mode_work = (byte) bufINint[21];
                mode_work_znak2 = (byte) bufINint[22];
                if (bufINint[27] == DC_minus) {
                    adc_rez_minus = true;
                } else {
                    adc_rez_minus = false;
                }
                max_ADC_rez = (bufINint[7] << 8) | (bufINint[8]);
                min_ADC_rez = (bufINint[9] << 8) | (bufINint[10]);
                adc_stm = (bufINint[26] << 8) | bufINint[25];
                if (read_kor_success && !flag_ALARM) {
                    if (CMD.mod_work == flag_mod_cap) {
//						ESR_cap->Visible = true;
                    }

                } else {
                    if (flag_ALARM) {
//                    lab_value->Text = "защита вкл.";
                    } else {
//                    lab_value->Text = "/-----/";
                    }
                }
                ////////////////////////////////////////////////////////////////////////////////////////
                data_tmp = bufINint[60] | (bufINint[61] << 8);
//				if (delitel_freq == 64 || delitel_freq == 4)ESR_tmp = 0;
//				this->ESR_cap->Text = show_ESR(data_tmp);
                ////////////////////////////////////////////////////////////////////////////////////////
//                fill(bufINint, 0);
            }
////////////////////////////////////////////////////////////////////////////////
            flag_receiv = true;
        }else{
////////////////////////////////////////////////////////
////////////////////////////////////////////////////////
        }
        status_USB = hiddev.status_USB;
//        handlstatus.sendEmptyMessage(0);

        /////////////////////////////////////////////
    }

     void USB_sendDEV(){                     /// отправка 64 байт из буфера bufOUT по USB
        if(USB_connect) {
            for (int i = 0; i < bufOUT.length; i++) {
                bufOUT[i] = (byte) bufOUTint[i];
            }  // предварительно переводим данные из массива bufOUTint в массив bufOUT(больше 127 числа при этом для типа byte получаются отрицательными в дополненом коде)
            USBThread_send send_USB = new USBThread_send();
        }
    }
    void read_MULT_koeff(){
        int num_send = 0;
        int count_main_while = 0;
        count_answer = 0;
        koeff_require(num_send);
        try {
            int count_while = 0;
            int count_pak = Number_pak_corr;
            //read_cmd:
            while (count_pak != 0) {
                while (flag_receiv == false) {		// ожидание ответа МК
                    delayMs(1);
                     count_answer++;
                    if(count_answer>300)break;
                }
                if (flag_receiv) {
//                    debug_txt1 = debug_txt1+":"+Integer.toString(count_answer);
                    count_answer = 0;
                    ///////////////////////////////////////////////////////////////////////////////////////////////
                    if (bufINint[51] == cmd_read_koeff && bufINint[4] == num_send) { // проверка возвращенной МК команды(buf_receiv[51]) и номера пакета(buf_receiv[4])
                        read_save_coeff(num_send);
                        num_send++;													   // номер отправляемого пакета
                        count_pak--;												   // количество оставшихся пакетов для приёма
                    };
                    ///////////////////////////////////////////////////////////////////////////////////////////////
                }
                fill(bufINint, 0);
                if (!flag_receiv) { count_main_while++; }
                flag_receiv = false;
                koeff_require(num_send);
                if (count_main_while> 100)break;
            }

        }
        catch (Exception e) { ; }
        INFO = "00.00";
        timerSHVAL = new Timer();
        taskshow = new showVAL();
        try{timerSHVAL.schedule(taskshow, 1000, 20);}catch(Exception c){;}
        multimetr_start();                  //начальная инициализация в режиме изм. напряжения
        close_frag = false;
        handlvalue.sendEmptyMessage(0);
        readCORR_flg = false;
        ////////////////////////////////////////////////////////////////////////////////
//	this->label15->Text = Convert::ToString(count_main_while) + "  " + Convert::ToString(num_send);
        if (num_send == Number_pak_corr) {
            read_kor_success = true;    //проверка валидности чтения коэф.
            show_txt_toast("Чтение кор.коэф. успешно!");
        }
        else { read_kor_success = false; }
    }
///////////////////////////////////////////////////////////////////////////////
boolean read_MULT_koeff_ALL(){

    boolean rr = false;
    boolean [] lost_coef = new boolean [50];
    fill(lost_coef, false);
    count_answer = 0;
    int recieved_pak =0, count_wait = 0, loaded_pak = 0, wait_main = 0;
    int end_read = 0, start_read = 0;
    require_get_koef(0, 150);
    try {
        debug_txt = "";
        while (end_read != end_read_koeff) {
            while (flag_receiv == false) {		// ожидание ответа МК
                delayMs(1);
                count_answer++;
                if(count_answer>300){ count_answer = 0; break; }
            }
            if (flag_receiv) {
                count_answer = 0;
                end_read = bufINint[51];
                start_read = bufINint[51];
//                if(loaded_pak == bufINint[4] && start_read == start_read_koeff){
                if(start_read == start_read_koeff){
                    read_save_coeff(bufINint[4]);
                    loaded_pak++;
                    lost_coef[bufINint[4]]= true;
                }
                recieved_pak++;
                count_wait++;
                debug_txt = debug_txt+ " : "+ Integer.toString(bufINint[51])+"_"+Integer.toString(bufINint[4])+"_"+Integer.toString(bufINint[52]);
                if(count_wait>3 && start_read!= start_read_koeff && loaded_pak == 0){			// если через 20 чтений не получили подтверждение команды старта чтения коэф. дублируем команду
                    require_get_koef(0, 150); count_wait = 0;
                };
                if (count_wait> 100)break;
            }
            fill(bufINint, 0);                          // заполняем приемный буфер нулями
//            if (!flag_receiv) { count_wait++; }
            wait_main++;
            if(wait_main>50)break;
            flag_receiv = false;

        }

    }
    catch (Exception e) { ; }

    if (loaded_pak == Number_pak_corr) {
        read_kor_success = true;    //проверка валидности чтения коэф.
        rr = true;
//        show_txt_toast("Чтение кор.коэф. успешно!"+recieved_pak+" loaded_pak = "+loaded_pak);
        for(int i = 0; i <50; i++){
            delayMs(20); send_comand(2, end_read_koeff, 0,0);
            if(flag_receiv) break;		// ожидание ответа МК
        }
        show_txt_toast("Устройство готово !");
    }
    else {
/////////////////////////////////////////////////////////////
        count_answer = 0;
        boolean suc = false;
        String ddeb = "", dd = "";
        int send_rep = 0;
        for(int i = 0; i <Number_pak_corr; i++){
            if(!lost_coef[i]){                      // номера потерянных пакетов в совпадают с номерами членов массива lost_coef[i] со значением false
                send_comand(2, cmd_read_koeff_lost, 50,i*6);        // команда МК, 2-ым байтом идет команда "cmd_read_koeff_lost", 50-ым байтом данные
                dd = dd+" : "+ i; suc = false;
                while(!suc) {
                    while (flag_receiv == false) {        // ожидание ответа МК
                        delayMs(1);
                        count_answer++;
                        if (count_answer > 300) break;
                    }
                    if (flag_receiv) {
                        count_answer = 0;
                        ///////////////////////////////////////////////////////////////////////////////////////////////
                        if (bufINint[51] == cmd_read_koeff_lost && bufINint[4] == i) { // проверка возвращенной МК команды(buf_receiv[51]) и номера пакета(buf_receiv[4])
                            read_save_coeff(i);
                            loaded_pak++;
                            suc = true;
                        } else{ send_comand(2, cmd_read_koeff_lost, 50,i*6); }            // если не получили запрошенного пакета дублируем команду
                        send_rep++;
                        flag_receiv = false;
                        fill(bufINint, 0);
                    }
                    if(send_rep> 500)break;
                }
            }
        }
/////////////////////////////////////////////////////////////
        if (loaded_pak == Number_pak_corr) {
            read_kor_success = true;    //проверка валидности чтения коэф.
            rr = true;
            debug_txt = debug_txt+"\n"+" send_rep = "+send_rep+"\n"+ddeb+"\n"+"send_pak "+dd;
            for(int i = 0; i <50; i++){
                delayMs(20); send_comand(2, end_read_koeff, 0,0);
                if(flag_receiv) break;		// ожидание ответа МК
            }
            show_txt_toast("Устройство готово!");

        }else{
            read_kor_success = false;
//            debug_txt = debug_txt+"\n"+" send_rep = "+send_rep+"\n"+ddeb+"\n"+"send_pak "+dd;
//            show_txt_toast("Ошибка чтения коэф."+recieved_pak+" loaded_pak = "+loaded_pak+" send_rep = "+send_rep);
            show_txt_toast("Ошибка чтения коэф.");
        }
    }
    ///////////////////////////////////////////////////////
    flag_receiv = false;
    INFO = "00.00";
    timerSHVAL = new Timer();
    taskshow = new showVAL();
    try{timerSHVAL.schedule(taskshow, 1000, 20);}catch(Exception c){;}
    multimetr_start();                  //начальная инициализация в режиме изм. напряжения
    close_frag = false;
    handlvalue.sendEmptyMessage(0);
    readCORR_flg = false;
    ////////////////////////////////////////
//    debug_txt = ""+show_koef_induct();   // вывод загруженных коэффициентов

    ////////////////////////////////////////////////////////////////////////////////
    return  rr;
}
String show_koef_induct(){
    String koef = "";
    koef =
            "korr_res_K-0="+ korr_ind[0][0]+
            ", korr_res_B-0="+ korr_ind[0][1]+"\n"+
            "korr_res_K-1="+ korr_ind[1][0]+
            ", korr_res_B-1="+ korr_ind[1][1]+"\n"+
            "korr_res_K-2="+ korr_ind[2][0]+
            ", korr_res_B-2="+ korr_ind[2][1]+"\n"+
            "korr_res_K-3="+ korr_ind[3][0]+
            ", korr_res_B-3="+ korr_ind[3][1]+"\n"+
            "korr_res_K-4="+ korr_ind[4][0]+
            ", korr_res_B-4="+ korr_ind[4][1]+"\n"+
            "korr_res_K-5="+ korr_ind[5][0]+
            ", korr_res_B-5="+ korr_ind[5][1]+"\n"+
            "korr_res_Km-6="+ korr_ind[6][0]+
            ", korr_res_Bm-6="+ korr_ind[6][1]+"\n"+
            "korr_res_Km-7="+ korr_ind[7][0]+
            ", korr_res_Bm-7="+ korr_ind[7][1]+"\n"+
            "korr_res_Km-8="+ korr_ind[8][0]+
            ", korr_res_Bm-8="+ korr_ind[8][1]+"\n"+
            "korr_res_Km-9="+ korr_ind[9][0]+
            ", korr_res_Bm-9="+ korr_ind[9][1]+"\n";
    return koef;
}
String show_koef_volt(){
    String koef = "";
    koef =
            "korr_A3-0="+korr_vol[0][3]+
            ", korr_A2-0="+korr_vol[0][2]+
            ", korr_A1-0="+korr_vol[0][0]+
            ", korr_A0-0="+ korr_vol[0][1]+"\n"+

            "korr_A3-1="+ korr_vol[1][3]+
            ", korr_A2-1="+ korr_vol[1][2]+
            ", korr_A1-1="+ korr_vol[1][0]+
            ", korr_A0-1="+ korr_vol[1][1]+"\n"+

            "korr_A3-2="+ korr_vol[2][3]+
            ", korr_A2-2="+ korr_vol[2][2]+
            ", korr_A1-2="+ korr_vol[2][0]+
            ", korr_A0-2="+ korr_vol[2][1]+"\n"+

            "korr_A3-3="+ korr_vol[3][3]+
            ", korr_A2-3="+ korr_vol[3][2]+
            ", korr_A1-3="+ korr_vol[3][0]+
            ", korr_A0-3="+ korr_vol[3][1]+"\n"+

            "korr_A3-4="+ korr_vol[4][3]+
            ", korr_A2-4="+ korr_vol[4][2]+
            ", korr_A1-4="+ korr_vol[4][0]+
            ", korr_A0-4="+ korr_vol[4][1]+"\n"+

    "korr_A3-5="+ korr_vol[5][3]+
            ", korr_A2-5="+ korr_vol[5][2]+
            ", korr_A1-5="+ korr_vol[5][0]+
            ", korr_A0-5="+ korr_vol[5][1]+"\n"+"\n"+


            "korr_A3m-0="+ korr_vol[6][3]+
            ", korr_A2m-0="+ korr_vol[6][2]+
            ", korr_A1m-0="+ korr_vol[6][0]+
            ", korr_A0m-0="+ korr_vol[6][1]+"\n"+

            "korr_A3m-1="+ korr_vol[7][3]+
            ", korr_A2m-1="+ korr_vol[7][2]+
            ", korr_A1m-1="+ korr_vol[7][0]+
            ", korr_A0m-1="+ korr_vol[7][1]+"\n"+

            "korr_A3m-2="+ korr_vol[8][3]+
            ", korr_A2m-2="+ korr_vol[8][2]+
            ", korr_A1m-2="+ korr_vol[8][0]+
            ", korr_A0m-2="+ korr_vol[8][1]+"\n"+

            "korr_A3m-3="+ korr_vol[9][3]+
            ", korr_A2m-3="+ korr_vol[9][2]+
            ", korr_A1m-3="+ korr_vol[9][0]+
            ", korr_A0m-3="+ korr_vol[9][1]+"\n"+

            "korr_A3m-4="+ korr_vol[10][3]+
            ", korr_A2m-4="+ korr_vol[10][2]+
            ", korr_A1m-4="+ korr_vol[10][0]+
            ", korr_A0m-4="+ korr_vol[10][1]+"\n"+

            "korr_A3m-5="+ korr_vol[11][3]+
            ", korr_A2m-5="+ korr_vol[11][2]+
            ", korr_A1m-5="+ korr_vol[11][0]+
            ", korr_A0m-5="+ korr_vol[11][1]+"\n";
    return koef;
}
///////////////////////////////////////////////////////////////////////////////
    void send_comand(int place_com, int comand, int place_data, int data){
        bufOUTint[0] = 1;						 // репорт ID
        bufOUTint[place_com] = comand;
        bufOUTint[place_data] = data;						// номер запрашиваемого пакета
        USB_sendDEV();
    }
    void require_get_koef(int pak, int delay){
        bufOUTint[0] = 1;						 // репорт ID
        bufOUTint[2] = cmd_read_koeff_ALL;
        bufOUTint[4] = pak;						// номер запрашиваемого пакета
        bufOUTint[50] = Number_pak_corr;
        bufOUTint[52] = delay;
        USB_sendDEV();
    }
    void koeff_require(int num){
        byte num_send = 0;
        int count_main_while = 0;
        bufOUTint[0] = 1;						 // репорт ID
        bufOUTint[2] = cmd_read_koeff;
        bufOUTint[4] = num;						// номер запрашиваемого пакета
        bufOUTint[50] = num * 6;					// адрес, откуда начинать читать пакет
        USB_sendDEV();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    void read_save_coeff(int num_puk) {
	long kk_L, bb_L, aa3, aa2;
        double kk_D, bb_D, aa3d, aa2d;
        kk_L = (long)(bufINint[30]) | ((long)(bufINint[31]) << 8) | ((long)(bufINint[32]) << 16) | ((long)(bufINint[33]) << 24);
        aa3 = (long)(bufINint[34]) | ((long)(bufINint[35]) << 8) | ((long)(bufINint[36]) << 16) | ((long)(bufINint[37]) << 24);
        kk_D = (double)kk_L; aa3d = (double)aa3;
        kk_D = kk_D / 1000000; aa3d = aa3d / 1000000;
        //		if(buf_receiv[38] == DC_minus){kk_D = kk_D*(-1);}
        //		data_debug->Text = Convert::ToString(yy1d);
        bb_L = (long)(bufINint[40]) | ((long)(bufINint[41]) << 8) | ((long)(bufINint[42]) << 16) | ((long)(bufINint[43]) << 24);
        aa2 = (long)(bufINint[44]) | ((long)(bufINint[45]) << 8) | ((long)(bufINint[46]) << 16) | ((long)(bufINint[47]) << 24);
        bb_D = (double)bb_L; aa2d = (double)aa2;
        bb_D = bb_D / 1000000; aa2d = aa2d / 1000000;
        //		if(buf_receiv[38] == DC_minus){kk_D = kk_D*(-1);}
        //		if(buf_receiv[39] == DC_minus){bb_D = bb_D*(-1);}
        switch (bufINint[38]) {
            case DC_minus:
                kk_D = kk_D*(-1);
                break;
            case DC_minus_L:
                aa3d = aa3d*(-1);
                break;
            case DC_minus_T:
                kk_D = kk_D*(-1); aa3d = aa3d*(-1);
                break;
        }
        switch (bufINint[39]) {
            case DC_minus:
                bb_D = bb_D*(-1);
                break;
            case DC_minus_L:
                aa2d = aa2d*(-1);
                break;
            case DC_minus_T:
                bb_D = bb_D*(-1); aa2d = aa2d*(-1);
                break;
        }
        if (bufINint[49] == 0xff) {
            kk_D = 1.0; aa3d = 0;
            bb_D = 0; aa2d = 0;
        }
        //// заполняем структуру, А3 в [3], А2 во [2], А0 в [1] и А1 в [0]
        switch (bufINint[48]) {
            case flag_mod_ind:
                korr_ind[bufINint[49]][0] = kk_D;
                korr_ind[bufINint[49]][1] = bb_D;
                korr_ind[bufINint[49]][2] = aa2d;
                korr_ind[bufINint[49]][3] = aa3d;
                break;
            case flag_mod_cap:
                korr_cap[bufINint[49]][0] = kk_D;
                korr_cap[bufINint[49]][1] = bb_D;
                korr_cap[bufINint[49]][2] = aa2d;
                korr_cap[bufINint[49]][3] = aa3d;
                break;
            case flag_mod_res:
                korr_res[bufINint[49]][0] = kk_D;
                korr_res[bufINint[49]][1] = bb_D;
                korr_res[bufINint[49]][2] = aa2d;
                korr_res[bufINint[49]][3] = aa3d;
                break;
            case flag_mod_vol:
                if (num_puk > 14 && num_puk <21) {
                    korr_vol[bufINint[49]][0] = kk_D;
                    korr_vol[bufINint[49]][1] = bb_D;
                    korr_vol[bufINint[49]][2] = aa2d;
                    korr_vol[bufINint[49]][3] = aa3d;
                }
                else {
                    korr_vol[bufINint[49] + 6][0] = kk_D;
                    korr_vol[bufINint[49] + 6][1] = bb_D;
                    korr_vol[bufINint[49] + 6][2] = aa2d;
                    korr_vol[bufINint[49] + 6][3] = aa3d;
                }
                break;
            case flag_mod_cur:
                if (num_puk > 26 && num_puk <31) {
                    korr_cur[bufINint[49]][0] = kk_D;
                    korr_cur[bufINint[49]][1] = bb_D;
                    korr_cur[bufINint[49]][2] = aa2d;
                    korr_cur[bufINint[49]][3] = aa3d;
                }
                else {
                    korr_cur[bufINint[49] + 4][0] = kk_D;
                    korr_cur[bufINint[49] + 4][1] = bb_D;
                    korr_cur[bufINint[49] + 4][2] = aa2d;
                    korr_cur[bufINint[49] + 4][3] = aa3d;
                }
                break;
            case flag_mod_freq:
                korr_freq[bufINint[49]][0] = kk_D;
                korr_freq[bufINint[49]][1] = bb_D;
                korr_freq[bufINint[49]][2] = aa2d;
                korr_freq[bufINint[49]][3] = aa3d;
                break;
        }
    }
    //////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////
    private void send_MULT(int cmd) {

        int err_wrt, a;

        bufOUTint[0] = 1;						 // репорт ID
        bufOUTint[2] = cmd;
        bufOUTint[7] = CMD.Auto_range;

        if (cmd == cmd_vol || cmd == cmd_cur) {
            try { a = Integer.parseInt(txt_edit.getText().toString()); }
            catch (Exception ee) {a = 100;}
            if (a>0 && a<5001) { ; }
            else { a = 100; }
            bufOUTint[3] = a >> 8;		// передаем время выборки старший байт
            bufOUTint[4] = a;			// передаем время выборки младший байт
            bufOUTint[6] = Flag_algoritm_mod;
        }
        //st_send:
        USB_sendDEV();
        ////////////////////////////////////////////////////////////
/*        try {

            int count_while = 0;
            int err_read;
            //read_cmd:
            while (flag_receiv == false) {		// ожидание ответа МК

            }
            if (flag_receiv == true && bufINint[3] == OK) {
                flag_receiv = false;
                flag_cmd_OK = true;
            }
            fill(bufOUTint,0);
        }
        catch (Exception er) { ; }*/
    }
    private void send_MULT(int cmd, int data) {

        int err_wrt;
        String ss;
        bufOUTint[0] = 1;						 // репорт ID
        bufOUTint[2] = cmd;
        bufOUTint[7] = CMD.Auto_range;
        bufOUTint[30] = data;
        bufOUTint[31] = data >> 8;
        USB_sendDEV();
        //////////////////////====================================////////////////////////
/*        try {

            int count_while = 0;
            int err_read;
            //read_cmd:
            while (flag_receiv == false) {		// ожидание ответа МК

            }
            if (flag_receiv == true && bufINint[3] == OK) {
                flag_receiv = false;
                flag_cmd_OK = true;
            }
            fill(bufOUTint,0);
        }
        catch (Exception er) { ; }*/
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////
    void show_mode_UP(View v){
        switch(v.getId()){
            case R.id.change_time:
//                change_time.setImageBitmap(btm_but_grey_change);
                break;
        }
    }
    void show_mode(View v){

        switch(v.getId()){
            case R.id.but_cur:
                gray_lay1_but();
                but_cur.setImageBitmap(btm_green_cur);
                if(CMD.Sel_range>3){ CMD.Sel_range = three_range;}
                CMD.mod_work = flag_mod_cur;
                show_range(CMD.Sel_range);
                if(Flag_algoritm_mod == flag_mod_Amplitude)show_ampl(true);
                txt_edit.setText(Integer.toString(time_select));
                send_MULT(cmd_cur);
//                show_range();
//                but_curG.setVisibility(View.VISIBLE);
                break;
            case R.id.but_vol:
                gray_lay1_but();
                but_vol.setImageBitmap(btm_green_vol);
                CMD.mod_work = flag_mod_vol;
                show_range(CMD.Sel_range);
                if(Flag_algoritm_mod == flag_mod_Amplitude)show_ampl(true);
                txt_edit.setText(Integer.toString(time_select));
                send_MULT(cmd_vol);
//                show_range();
                break;
            case R.id.but_res:
                gray_lay1_but();
                but_res.setImageBitmap(btm_green_res);
                CMD.mod_work = flag_mod_res; show_ampl(false);
                show_range(CMD.Sel_range);
                send_MULT(cmd_res);
 //               show_range();
                break;
            case R.id.but_ind:
                gray_lay1_but(); show_ampl(false);
                but_ind.setImageBitmap(btm_green_ind);
                CMD.mod_work = flag_mod_ind;
                show_centre(false);
                read_flag_INDCAP_wire(CMD.mod_work, false);
                txt_edit.setText(Integer.toString((int)induct_wire));
                send_MULT(cmd_ind);
                break;
            case R.id.but_cap:
                gray_lay1_but(); show_ampl(false);
                but_cap.setImageBitmap(btm_green_cap);
                CMD.mod_work = flag_mod_cap;
                show_centre(false);
                read_flag_INDCAP_wire(CMD.mod_work, false);
                txt_edit.setText(Integer.toString((int)cap_wire));
                send_MULT(cmd_cap);
                break;
            case R.id.but_freq:
                gray_lay1_but(); show_ampl(false);
                but_freq.setImageBitmap(btm_green_freq);
                CMD.mod_work = flag_mod_freq;
                show_centre(false);
                send_MULT(cmd_freq);
                break;
            case R.id.but_rms:
                push_but_RMS();
                break;
            case R.id.but_arif:
                push_but_ARIF();
                break;
            case R.id.but_rect:
                push_but_RECT();
//                reboot_serv();
                break;
            case R.id.but_amp:
                push_but_AMP();
                break;
            case R.id.v_menu:
//                show_txt_toast("MENU");
                showPopupMenu(v);
                break;
            case R.id.but_range_auto:
                push_but_rangeAUTO();
                break;
            case R.id.but_range0:
                push_but_range0();
                break;
            case R.id.but_range1:
                push_but_range1();
                break;
            case R.id.but_range2:
                push_but_range2();
                break;
            case R.id.but_range3:
                push_but_range3();
                break;
            case R.id.but_range4:
                push_but_range4();
                break;
            case R.id.but_range5:
                push_but_range5();
                break;
            case R.id.change_time:

//               read_MULT_koeff();
//                send_MULT(cmd_vol);
//                dialog_show(1);
                read_flag_INDCAP_wire(CMD.mod_work, true);
                break;
            case R.id.txt_edit:
                dialog_show(1);
                break;
            case R.id.screen1:
                fTrans = getSupportFragmentManager().beginTransaction();
                switch(CMD.mod_work){
                    case flag_mod_freq:
                    case flag_mod_cap:
                    case flag_mod_ind:
                        frag_mode_work = CMD.mod_work;
                        Frg = new frag();
                        break;
                    case flag_mod_cur:
                        Frg = new frag_cur();
                        break;
                    case flag_mod_vol:
                        Frg = new frag_vol();
                        break;
                    case flag_mod_res:
                        Frg = new frag_res();
                        break;
                }
                fTrans.add(R.id.cont, Frg);
                fTrans.addToBackStack("frag_cont");
                fTrans.commit();
                break;
        }
    }
///////////////////////////////////////////////////////////////////////////////
    private void push_but_RMS(){
        Flag_algoritm_mod = flag_mod_RMS;
        show_RMS(flag_mod_RMS); show_ampl(false);
        frag_select_but_RMS = flag_mod_RMS;
        if (CMD.mod_work == flag_mod_vol) { send_MULT(cmd_vol); }
        if (CMD.mod_work == flag_mod_cur) { send_MULT(cmd_cur); }
    }
    private void push_but_ARIF(){
        Flag_algoritm_mod = flag_mod_Average;
        show_RMS(flag_mod_Average); show_ampl(false);
        frag_select_but_RMS = flag_mod_Average;
        if (CMD.mod_work == flag_mod_vol) { send_MULT(cmd_vol); }
        if (CMD.mod_work == flag_mod_cur) { send_MULT(cmd_cur); }
    }
    private void push_but_RECT(){
        Flag_algoritm_mod = flag_mod_Average_rectified;
        show_RMS(flag_mod_Average_rectified); show_ampl(false);
        frag_select_but_RMS = flag_mod_Average_rectified;
        if (CMD.mod_work == flag_mod_vol) { send_MULT(cmd_vol); }
        if (CMD.mod_work == flag_mod_cur) { send_MULT(cmd_cur); }
    }
    private void push_but_AMP(){
        Flag_algoritm_mod = flag_mod_Amplitude;
        show_RMS(flag_mod_Amplitude); show_ampl(true);
        frag_select_but_RMS = flag_mod_Amplitude;
        if (CMD.mod_work == flag_mod_vol) { send_MULT(cmd_vol); }
        if (CMD.mod_work == flag_mod_cur) { send_MULT(cmd_cur); }
    }
    private void push_but_rangeAUTO(){
        CMD.Auto_range = auto_range_ON;
        show_range(CMD.Sel_range);
        frag_select_but = auto_range_ON;
        send_MULT(cmd_sel_range, CMD.Sel_range);
    }
    private void push_but_range0(){
        CMD.Auto_range = auto_range_OFF;
        CMD.Sel_range = null_range;
        show_range(CMD.Sel_range);
        frag_select_but = null_range;
        send_MULT(cmd_sel_range, CMD.Sel_range);
    }
    private void push_but_range1(){
        CMD.Auto_range = auto_range_OFF;
        CMD.Sel_range = one_range;
        show_range(CMD.Sel_range);
        frag_select_but = one_range;
        send_MULT(cmd_sel_range, CMD.Sel_range);
    }
    private void push_but_range2(){
        CMD.Auto_range = auto_range_OFF;
        CMD.Sel_range = two_range;
        show_range(CMD.Sel_range);
        frag_select_but = two_range;
        send_MULT(cmd_sel_range, CMD.Sel_range);
    }
    private void push_but_range3(){
        CMD.Auto_range = auto_range_OFF;
        CMD.Sel_range = three_range;
        show_range(CMD.Sel_range);
        frag_select_but = three_range;
        send_MULT(cmd_sel_range, CMD.Sel_range);
    }
    private void push_but_range4(){
        CMD.Auto_range = auto_range_OFF;
        CMD.Sel_range = four_range;
        show_range(CMD.Sel_range);
        frag_select_but = four_range;
        send_MULT(cmd_sel_range, CMD.Sel_range);
    }
    private void push_but_range5(){
        CMD.Auto_range = auto_range_OFF;
        CMD.Sel_range = five_range;
        show_range(CMD.Sel_range);
        frag_select_but = five_range;
        send_MULT(cmd_sel_range, CMD.Sel_range);
    }
///////////////////////////////////////////////////////////////////////////////
private void show_RMS(int rms){
    try {
        gray_lay3_but();
        switch (rms) {
            case flag_mod_RMS:
                but_rms.setImageBitmap(btm_green_rms);
                break;
            case flag_mod_Amplitude:
                but_amp.setImageBitmap(btm_green_amp);
                break;
            case flag_mod_Average:
                but_arif.setImageBitmap(btm_green_arif);
                break;
            case flag_mod_Average_rectified:
                but_rect.setImageBitmap(btm_green_rect);
                break;
        }
    }catch(Exception e){Log.d(tag, "Exception show_RMS()");}

}
///////////////////////////////////////////////////////////////////////////////
    public void read_flag_INDCAP_wire(int mod, boolean state_change){
        if(state_change) {
            if (mod == flag_mod_ind) {
                if (!flag_editIND_wire) {
                    flag_editIND_wire = true;
                    change_time.setImageBitmap(btm_but_green_change);
                } else {
                    flag_editIND_wire = false;
                    change_time.setImageBitmap(btm_but_grey_change);
                }
            }
            if (mod == flag_mod_cap) {
                if (!flag_editCAP_wire) {
                    flag_editCAP_wire = true;
                    change_time.setImageBitmap(btm_but_green_change);
                } else {
                    flag_editCAP_wire = false;
                    change_time.setImageBitmap(btm_but_grey_change);
                }
            }
        }else{
            if (mod == flag_mod_ind) {
                if (!flag_editIND_wire) {
                    change_time.setImageBitmap(btm_but_grey_change);
                } else {
                    change_time.setImageBitmap(btm_but_green_change);
                }
            }
            if (mod == flag_mod_cap) {
                if (!flag_editCAP_wire) {
                    change_time.setImageBitmap(btm_but_grey_change);
                } else {
                    change_time.setImageBitmap(btm_but_green_change);
                }
            }
        }
    }
    void show_ampl(boolean en){
        if(en) {
            set_pos_but(info_value, 925 * scale_X, 250 * scale_Y);
            info_value.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizetxt_amp);
            set_pos_but(info_mili, (912 + 523) * scale_X, 270 * scale_Y);
        }else{
            info_value.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizetxt_rms);
            set_pos_but(info_value, 925*scale_X, 250*scale_Y);
            set_pos_but(info_mili, (912+523)*scale_X, 358*scale_Y);
        }
    }
    void gray_lay1_but(){
        but_cur.setImageBitmap(btm_grey_cur);
        but_vol.setImageBitmap(btm_grey_vol);
        but_res.setImageBitmap(btm_grey_res);
        but_ind.setImageBitmap(btm_grey_ind);
        but_cap.setImageBitmap(btm_grey_cap);
        but_freq.setImageBitmap(btm_grey_freq);
    }
    void gray_lay3_but(){
        but_rms.setImageBitmap(btm_grey_rms);
        but_arif.setImageBitmap(btm_grey_arif);
        but_rect.setImageBitmap(btm_grey_rect);
        but_amp.setImageBitmap(btm_grey_amp);
    }
/////////////////////////////////////////////////////////////////////////////////////////////
Handler handlstatus = new Handler() {
    @Override
    public void handleMessage(Message msg) {
        show_txt_toast(USB_tag);
    }
};///////////////////////////////////////////


    Handler handltime = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        txt_time.setText(sys_time);
        }
    };
    Handler handlvalue = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            info_value.setText(INFO); info_mili.setText(INF_mili);
        }
    };
    Handler handldebug = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            txt_debug.setText(debug_txt);
        }
    };
///////////////////////////////////////////////////////////
class Timerreset extends TimerTask {
    @Override
    public void run() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss");

        final String strDate = simpleDateFormat.format(calendar.getTime());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    sys_time = strDate;
                    handltime.sendEmptyMessage(0);
/////////////////////////////////////////////////////////////////////////////
/*                    debug_txt =  "korr_vol K="+korr_vol[delitel_freq][0]+
                            ", korr_vol B="+korr_vol[delitel_freq][1]+
                            ", korr_vol Kmin="+korr_vol[delitel_freq+6][0]+
                            ", korr_vol Bmin="+korr_vol[delitel_freq+6][1]+
                            ", rec_data_f1 = "+rec_data_f1+
                            "readCORR_flg "+readCORR_flg; //+"\n"+debug_txt1;*/
                    handldebug.sendEmptyMessage(0);
///////////////////////////////////////////////////////////////debug
 /*                   show_ampl(true);
                  String []tmp = new String[2];
                    korr_vol[delitel_freq][0] = 1;
                    korr_vol[delitel_freq+6][0] = 1;
                    max_ADC_rez = 1000; min_ADC_rez = 500;
                    tmp = SHOW_d.show(CMD.mod_work, rec_data_f1, rec_data_f2, adc_stm);
                    INFO = tmp[0]; INF_mili = " "+tmp[1];
                    handlvalue.sendEmptyMessage(0);
                    Log.d(tag, "tmp ="+ tmp[0]+" "+tmp[1]);*/
                    ////////////////////////////////////
                   /* String []tmp = new String[2];
                    delitel_freq = 1;
                    CMD.mod_work = flag_mod_cap;
                    korr_cap[0][0] = 1; korr_cap[1][0] = 1;
                    korr_vol[0][1] = 0; korr_vol[1][1] = 0;
                    adc_stm = 1000; rec_data_f1 = 1000;
                    tmp = SHOW_d.show(CMD.mod_work, rec_data_f1, rec_data_f2, adc_stm);
                    INFO = tmp[0]; INF_mili = " "+tmp[1];
                    handlvalue.sendEmptyMessage(0);*/
                   ///////////////////////////////////////
 /*                   CMD.mod_work = flag_mod_ind;
                    delitel_freq = 4;
                    String []tmp = new String[2];
                    rec_data_f1 = 100;
                    tmp = SHOW_d.show(CMD.mod_work, rec_data_f1, rec_data_f2, adc_stm);
                    INFO = tmp[0]; INF_mili = " "+tmp[1];
                    handlvalue.sendEmptyMessage(0);*/
                    /////////////////////////
/*                    INFO = "max. -999.99"+"\n"+"min. +999.99"; INF_mili = " мкГн";
                    if (INFO.length() > 6) {
                        INFO = INFO.substring(0, 6);
                    }
                    handlvalue.sendEmptyMessage(0);*/
                    /////////////////////////////////
/*                    CMD.mod_work = flag_mod_cur;
                    delitel_freq = 3;
                    String []tmp = new String[2];
                    rec_data_f1 = 53;
                    tmp = SHOW_d.show(CMD.mod_work, rec_data_f1, rec_data_f2, adc_stm);
                    INFO = tmp[0]; INF_mili = " "+tmp[1];
                    handlvalue.sendEmptyMessage(0);*/
                    if(frag_com_exe!=0) {
                        switch (frag_com_exe) {
                            case frag_com_RANGE:
                                switch (frag_select_but) {
                                    case auto_range_ON:
                                        push_but_rangeAUTO();
                                        break;
                                    case null_range:
                                        push_but_range0();
                                        break;
                                    case one_range:
                                        push_but_range1();
                                        break;
                                    case two_range:
                                        push_but_range2();
                                        break;
                                    case three_range:
                                        push_but_range3();
                                        break;
                                    case four_range:
                                        push_but_range4();
                                        break;
                                    case five_range:
                                        push_but_range5();
                                        break;

                                }
                                Log.d(tag, "frag_com_exe  = frag_com_RANGE!");
                                break;
                            case frag_com_RMS:
                                switch (frag_select_but_RMS) {
                                    case flag_mod_RMS:
                                        push_but_RMS();
                                        break;
                                    case flag_mod_Amplitude:
                                        push_but_AMP();
                                        break;
                                    case flag_mod_Average:
                                        push_but_ARIF();
                                        break;
                                    case flag_mod_Average_rectified:
                                        push_but_RECT();
                                        break;
                                }
                                Log.d(tag, "frag_com_exe  = frag_com_RMS");
                                break;
                            case frag_com_TIME:
                                txt_edit.setText(Integer.toString(time_select));
                                if(CMD.mod_work == flag_mod_vol)send_MULT(cmd_vol);
                                if(CMD.mod_work == flag_mod_cur)send_MULT(cmd_cur);
                                if(CMD.mod_work == flag_mod_ind){
                                    read_flag_INDCAP_wire(CMD.mod_work, false);
                                    txt_edit.setText(Integer.toString((int)induct_wire));
                                }
                                if(CMD.mod_work == flag_mod_cap){
                                    read_flag_INDCAP_wire(CMD.mod_work, false);
                                    txt_edit.setText(Integer.toString((int)cap_wire));
                                }
                                break;
                        }
                        if (close_frag) {
                            onBackPressed();
                            close_frag = false;
                        }
                        frag_com_exe = 0;
                        Log.d(tag, "frag_com_exe RUN!");
                    }
//////////////////////////////////////////////////////////

                }catch(Exception e){Log.d(tag, " TimerRESET");}
            }
        });
    }
}
    class showVAL extends TimerTask {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(!readCORR_flg) {
                            ////////////////////////////////////////////////
//                            rec_data_f1 = 5000;
                            ////////////////////////////////////////////////
                            String []tmp = new String[6];

                            tmp = SHOW_d.show(CMD.mod_work, rec_data_f1, rec_data_f2, adc_stm);
                            INFO = tmp[0]; INF_mili = " "+tmp[1];
                            maxV = tmp[2]; minV = tmp[3]; pref_max = tmp[4]; pref_min = tmp[5];
 //                           Log.d(tag, "vol_max = "+tmp[0]+" volpr = "+tmp[1]);
                            if(Flag_algoritm_mod != flag_mod_Amplitude) {
                                if (INFO.length() > 6) {
                                    INFO = INFO.substring(0, 6);
                                }
                            }
                            handlvalue.sendEmptyMessage(0);
                            ///////////////////////////////////////////////
                            double rez;
                            if(frag_select_but == flag_mod_Amplitude){
                                rez = Double.parseDouble(maxV);
                            }else {
                                rez = Double.parseDouble(INFO);
                            }

                            if (rez < 0) { rez = rez * -1; }
                            if (rez < 1000) { screen_range = 2; };
                            if (rez < 500) { screen_range = 1; };
                            if (rez < 100) { screen_range = 0; };
                            switch (screen_range) {
                                case 0:
                                    val_info = rez;
                                    break;
                                case 1:
                                    val_info = 0.2 * rez;
                                    break;
                                case 2:
                                    val_info = 0.1 * rez;
                                    break;
                            }
                            ////////////////////////////////////////////////
                        }
                    }catch(Exception e){Log.d(tag, " Timer showVAL");}

                }
            });
        }
    }
/*
class mTimersek extends TimerTask{
    @Override
    public void run() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    read_MULT_koeff();
                }catch(Exception e){Log.d(tag, " TimerSEC");}

            }
        });
    }
}*/
void delayMs(int time){
    try {

        Thread.sleep(time);
    } catch (Exception ee) {
        Log.d(tag, "Exception sleep");
    }
}
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
    private void show_range(int range) {

        show_centre(true); Log.d(tag, " show_centre  true");
        but_range0.setImageBitmap(btm_grey_range[CMD.mod_work][0]);
        but_range1.setImageBitmap(btm_grey_range[CMD.mod_work][1]);
        but_range2.setImageBitmap(btm_grey_range[CMD.mod_work][2]);
        but_range3.setImageBitmap(btm_grey_range[CMD.mod_work][3]);
        but_range4.setImageBitmap(btm_grey_range[CMD.mod_work][4]);
        but_range5.setImageBitmap(btm_grey_range[CMD.mod_work][5]);
        but_range_auto.setImageBitmap(btm_butgrey_auto_range);

        if(CMD.mod_work == flag_mod_cur){but_range4.setEnabled(false); but_range5.setEnabled(false);}
        else{but_range4.setEnabled(true); but_range5.setEnabled(true);}


        if(CMD.Auto_range == auto_range_OFF) {
            switch (range) {
                case null_range:
                    but_range0.setImageBitmap(btm_green_range[CMD.mod_work][range]);
                    break;
                case one_range:
                    but_range1.setImageBitmap(btm_green_range[CMD.mod_work][range]);
                    break;
                case two_range:
                    but_range2.setImageBitmap(btm_green_range[CMD.mod_work][range]);
                    break;
                case three_range:
                    but_range3.setImageBitmap(btm_green_range[CMD.mod_work][range]);
                    break;
                case four_range:
                    but_range4.setImageBitmap(btm_green_range[CMD.mod_work][range]);
                    break;
                case five_range:
                    but_range5.setImageBitmap(btm_green_range[CMD.mod_work][range]);
                    break;
            }
        }else{but_range_auto.setImageBitmap(btm_butgreen_auto_range);}
    }
    void show_centre(boolean show){
        if(show){
            but_range_auto.setVisibility(View.VISIBLE);
            but_range0.setVisibility(View.VISIBLE); but_range1.setVisibility(View.VISIBLE); but_range2.setVisibility(View.VISIBLE);
            but_range3.setVisibility(View.VISIBLE); but_range4.setVisibility(View.VISIBLE); but_range5.setVisibility(View.VISIBLE);
            but_range0.setEnabled(true); but_range1.setEnabled(true);but_range2.setEnabled(true); but_range_auto.setEnabled(true);
            but_range3.setEnabled(true); but_range4.setEnabled(true); but_range5.setEnabled(true);
            if(CMD.mod_work == flag_mod_cur || CMD.mod_work == flag_mod_vol) {
                info_param_in.setText("ВРЕМЯ ВЫБОРКИ(мс)"); txt_edit.setWidth((int) (763 * scale_X)); //763//380
                but_arif.setVisibility(View.VISIBLE); but_rms.setVisibility(View.VISIBLE); but_rect.setVisibility(View.VISIBLE); but_amp.setVisibility(View.VISIBLE);
                but_arif.setEnabled(true);but_rms.setEnabled(true); but_rect.setEnabled(true); but_amp.setEnabled(true);
                change_time.setVisibility(View.GONE); change_time.setEnabled(false); txt_edit.setEnabled(true); txt_edit.setVisibility(View.VISIBLE);
            }else{
                info_param_in.setText("");
                but_arif.setVisibility(View.GONE); but_rms.setVisibility(View.GONE); but_rect.setVisibility(View.GONE); but_amp.setVisibility(View.GONE);
                but_arif.setEnabled(false);but_rms.setEnabled(false); but_rect.setEnabled(false); but_amp.setEnabled(false);
                change_time.setVisibility(View.GONE); change_time.setEnabled(false); txt_edit.setEnabled(false); txt_edit.setVisibility(View.GONE);
            }
        }else{
            but_range_auto.setVisibility(View.GONE);
            but_range0.setVisibility(View.GONE); but_range1.setVisibility(View.GONE); but_range2.setVisibility(View.GONE);
            but_range3.setVisibility(View.GONE); but_range4.setVisibility(View.GONE); but_range5.setVisibility(View.GONE);
            but_arif.setVisibility(View.GONE); but_rms.setVisibility(View.GONE); but_rect.setVisibility(View.GONE); but_amp.setVisibility(View.GONE);

            but_arif.setEnabled(false);but_rms.setEnabled(false); but_rect.setEnabled(false); but_amp.setEnabled(false);
            but_range0.setEnabled(false); but_range1.setEnabled(false);but_range2.setEnabled(false); but_range_auto.setEnabled(false);
            but_range3.setEnabled(false); but_range4.setEnabled(false); but_range5.setEnabled(false);
            switch(CMD.mod_work){
                case flag_mod_cap:
                    info_param_in.setText("ЕМКОСТЬ ВХОДА(пФ)"); txt_edit.setWidth((int) (380 * scale_X)); //763//380
                    change_time.setVisibility(View.VISIBLE); change_time.setEnabled(true); txt_edit.setEnabled(true); txt_edit.setVisibility(View.VISIBLE);
                    break;
                case flag_mod_ind:
                    info_param_in.setText("С УЧЕТОМ ПРОВОДОВ(нГн)"); txt_edit.setWidth((int) (380 * scale_X)); //763//380
                    change_time.setVisibility(View.VISIBLE); change_time.setEnabled(true); txt_edit.setEnabled(true); txt_edit.setVisibility(View.VISIBLE);
                    break;
                case flag_mod_freq:
                    info_param_in.setText("");
                    change_time.setVisibility(View.GONE); change_time.setEnabled(false); txt_edit.setEnabled(false); txt_edit.setVisibility(View.GONE);
                    break;
            }
        }
    }
void create_but() {
    /////////////////////////////////////////////////////////
//        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(new ViewGroup.MarginLayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        lp1.setMargins(0, 0, 0, 0);
//        main_L3.setLayoutParams(lp1);
/*
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0,0,0,0);
        main_L1.setLayoutParams(layoutParams);
        main_L2.setLayoutParams(layoutParams);
        main_L3.setLayoutParams(layoutParams);*/
    try {
        find_bitm();
    }catch(Exception e){Log.d(tag, "Exception find_bitm()" );}
    ///////////////////////////////////////////////////////////
    try {
        sizetxt_rms = 180*scale_Y;
        sizetxt_amp = 95*scale_Y;
        int posX_2column = (int) (908 * scale_X);          // координата Х среднего ряда кнопок
        screen1.setImageBitmap(btm_sreen1);
        set_pos_but(screen1, posX_2column, (int) (192 * scale_Y));
        ///////////////////// создаем кнопки первого ряда
//    Log.d(tag, "tmp_X "+scale_X+" tmp_Y "+scale_Y);
        float shiftX_but_lay1 = 42 * scale_X;
        float shiftY_but_lay1 = (310 * scale_Y);
        float delta_but_L1 = 184 * scale_Y;

        float shiftY_txt_time = 60 * scale_Y;
        int i = 0;

        but_cur.setImageBitmap(btm_grey_cur);
        set_pos_but(but_cur, shiftX_but_lay1, shiftY_but_lay1);
        i++;


        but_vol.setImageBitmap(btm_green_vol);
        set_pos_but(but_vol, shiftX_but_lay1, shiftY_but_lay1 + i * (int) delta_but_L1);
        Log.d(tag, " shiftY_but_lay1 " + shiftY_but_lay1 + " i " + i + " delta_but_L1 " + delta_but_L1);
        Log.d(tag, "shiftY_but_lay1 " + Integer.toString((int) shiftY_but_lay1 + i * (int) delta_but_L1));
        i++;

        but_res.setImageBitmap(btm_grey_res);
        set_pos_but(but_res, shiftX_but_lay1, shiftY_but_lay1 + i * (int) delta_but_L1);
        Log.d(tag, " shiftY_but_lay1 " + shiftY_but_lay1 + " i " + i + " delta_but_L1 " + delta_but_L1);
        Log.d(tag, "shiftY_but_lay1 " + Integer.toString((int) shiftY_but_lay1 + i * (int) delta_but_L1));
        i++;

        but_ind.setImageBitmap(btm_grey_ind);
        set_pos_but(but_ind, shiftX_but_lay1, shiftY_but_lay1 + i * (int) delta_but_L1);
        Log.d(tag, " shiftY_but_lay1 " + shiftY_but_lay1 + " i " + i + " delta_but_L1 " + delta_but_L1);
        Log.d(tag, "shiftY_but_lay1 " + Integer.toString((int) shiftY_but_lay1 + i * (int) delta_but_L1));
        i++;

        but_cap.setImageBitmap(btm_grey_cap);
        set_pos_but(but_cap, shiftX_but_lay1, shiftY_but_lay1 + i * (int) delta_but_L1);
        i++;

        but_freq.setImageBitmap(btm_grey_freq);
        set_pos_but(but_freq, shiftX_but_lay1, shiftY_but_lay1 + i * (int) delta_but_L1);
        i++;
/////////////////////////////////////////////////////
        ///////////////////// создаем кнопки третьего ряда
        shiftX_but_lay1 = 1722 * scale_X;
        shiftY_but_lay1 = (525 * scale_Y);
        delta_but_L1 = 230 * scale_Y;
        int posX_txt_edit = (int) shiftX_but_lay1;
        i = 0;
        but_rms.setImageBitmap(btm_green_rms);
        set_pos_but(but_rms, shiftX_but_lay1, shiftY_but_lay1 + i * (int) delta_but_L1);
        i++;

        but_arif.setImageBitmap(btm_grey_arif);
        set_pos_but(but_arif, shiftX_but_lay1, shiftY_but_lay1 + i * (int) delta_but_L1);
        i++;

        but_rect.setImageBitmap(btm_grey_rect);
        set_pos_but(but_rect, shiftX_but_lay1, shiftY_but_lay1 + i * (int) delta_but_L1);
        i++;

        but_amp.setImageBitmap(btm_grey_amp);
        set_pos_but(but_amp, shiftX_but_lay1, shiftY_but_lay1 + i * (int) delta_but_L1);
        i++;
///////////////////// создаем кнопки центрального ряда
////posX_2column
        shiftX_but_lay1 = 362 * scale_X;
        shiftY_but_lay1 = (604 * scale_Y);
        delta_but_L1 = (139 * scale_Y);
        i = 0;
        but_range_auto.setImageBitmap(btm_butgreen_auto_range);
        set_pos_but(but_range_auto, posX_2column, shiftY_but_lay1 + i * (int) delta_but_L1);
        i++;
        but_range0.setImageBitmap(btm_grey_range[3][0]);
        set_pos_but(but_range0, posX_2column, shiftY_but_lay1 + i * (int) delta_but_L1);
        but_range1.setImageBitmap(btm_grey_range[3][1]);
        set_pos_but(but_range1, posX_2column + shiftX_but_lay1, shiftY_but_lay1 + i * (int) delta_but_L1);
        i++;
        but_range2.setImageBitmap(btm_grey_range[3][2]);
        set_pos_but(but_range2, posX_2column, shiftY_but_lay1 + i * (int) delta_but_L1);
        but_range3.setImageBitmap(btm_grey_range[3][3]);
        set_pos_but(but_range3, posX_2column + shiftX_but_lay1, shiftY_but_lay1 + i * (int) delta_but_L1);
        i++;
        but_range4.setImageBitmap(btm_grey_range[3][4]);
        set_pos_but(but_range4, posX_2column, shiftY_but_lay1 + i * (int) delta_but_L1);
        but_range5.setImageBitmap(btm_grey_range[3][5]);
        set_pos_but(but_range5, posX_2column + shiftX_but_lay1, shiftY_but_lay1 + i * (int) delta_but_L1);
        i++;
///////////////////////////////////////////////////////
        txt_time.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60*scale_Y);            // размер текста в пикселях
        set_pos_but(txt_time, (widht * 5) / 8, hight / 25);   // располагаем виев для вывода системного времени
        v_menu.setHeight((int) (138 * scale_Y));
        v_menu.setWidth((int) (160 * scale_X)); // устанавливаем высоту и ширину кнопки меню
        set_pos_but(v_menu, (int) (2400 * scale_X), 0);   // располагаем виев для вывода кнопки меню.

        info_connect.setImageBitmap(btm_usb_off);
        btm_usb_connect = btm_usb_off;
        set_pos_but(info_connect, (int) (900 * scale_X), (int) (1260 * scale_Y));
        info_param_in.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60*scale_Y);            // размер текста в пикселях
        set_pos_but(info_param_in, (int) (1720 * scale_X), (int) (190 * scale_Y));        //

//    txt_edit.setHeight((int)(104*scale_Y));
        txt_edit.setWidth((int) (763 * scale_X)); //763//380
        txt_edit.setHeight((int) (115 * scale_Y));
        txt_edit.setTextSize(TypedValue.COMPLEX_UNIT_PX, 80*scale_Y);            // размер текста в пикселях
        set_pos_but(txt_edit, posX_txt_edit + 2, (int) (320 * scale_Y));

        info_value.setWidth((int) (523 * scale_X));
//        info_value.setTextSize(47);         // текст в SP
//        info_value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 47);
        info_value.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizetxt_rms);            // размер текста в пикселях
//        info_mili.setTextSize(25*scale_Y);
        info_mili.setTextSize(TypedValue.COMPLEX_UNIT_PX, 70*scale_Y);            // размер текста в пикселях
        set_pos_but(info_value, 925 * scale_X, 250 * scale_Y);
        set_pos_but(info_mili, (912 + 523) * scale_X, 358 * scale_Y);

        change_time.setImageBitmap(btm_but_grey_change);
        set_pos_but(change_time, 2110 * scale_X, 311 * scale_Y);
///////////////////////////////////////////////////////
        but_cur.setOnTouchListener(this);
        but_vol.setOnTouchListener(this);
        but_res.setOnTouchListener(this);
        but_ind.setOnTouchListener(this);
        but_cap.setOnTouchListener(this);
        but_freq.setOnTouchListener(this);

        but_rms.setOnTouchListener(this);
        but_arif.setOnTouchListener(this);
        but_rect.setOnTouchListener(this);
        but_amp.setOnTouchListener(this);

        but_range_auto.setOnTouchListener(this);
        but_range0.setOnTouchListener(this);
        but_range1.setOnTouchListener(this);
        but_range2.setOnTouchListener(this);
        but_range3.setOnTouchListener(this);
        but_range4.setOnTouchListener(this);
        but_range5.setOnTouchListener(this);
        screen1.setOnTouchListener(this);
        change_time.setOnTouchListener(this);
        txt_edit.setOnTouchListener(this);

        v_menu.setOnTouchListener(this);
    }catch (Exception ee){Log.d(tag, "Exception create_but()");}
    /////////////////////////////////////////////////////////////
    Log.d(tag, "posX = "+ but_cur.getX()+" posY = "+ but_cur.getY());
}
    /*
        void set_pos_but(View v, float x, float y){                 // если не выполнить эту функцию сначала, то функция v.set.X(float x) работает некорректно
            AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    (int)x, // begin x coord //
                    (int)y // begin y coord //);
            //                       lp.setMargins(x, y, 0, 0);
            v.setLayoutParams(lp);
        }
     */
    void set_pos_but(View v, float x, float y){                 // если не выполнить эту функцию сначала, то функция v.set.X(float x) работает некорректно
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(

                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        lp.setMargins((int)x,(int)y,0,0);
        //                       lp.setMargins(x, y, 0, 0);
        v.setLayoutParams(lp);
    }
    void find_bitm(){
        float h_but_cur = 157*scale_Y;          // находим размеры кнопок первого ряда для текущего разрешения в пикселях
        float w_but_cur = 771*scale_X;          // находим размеры кнопок первого ряда для текущего разрешения в пикселях

        Bitmap bitmap;

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.screen1);
        btm_sreen1 = Bitmap.createScaledBitmap(bitmap, (int)(719*scale_X), (int)(380*scale_Y), true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_grey_cur);
        float h_img = (float)bitmap.getHeight(), w_img = (float)bitmap.getWidth();      // находим фактический размер изображения кнопки
        Log.d(tag, "bitmap.getHeight() "+h_img+" bitmap.getWidth() "+w_img);
/////////////////////// битмапы серых кнопок первого ряда////////////////////////////////////
        btm_grey_cur = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_grey_vol);
        btm_grey_vol = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_grey_res);
        btm_grey_res = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_grey_ind);
        btm_grey_ind = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_grey_cap);
        btm_grey_cap = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_grey_freq);
        btm_grey_freq = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);

        ///////////////////битмапы зеленых кнопок первого ряда////////////////////////////////////
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_green_cur);
        btm_green_cur = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_green_vol);
        btm_green_vol = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_green_res);
        btm_green_res = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_green_ind);
        btm_green_ind = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_green_cap);
        btm_green_cap = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_green_freq);
        btm_green_freq = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        ///////////////////////////////////////////////////////////////////////////////////
        ///////////////////битмапы кнопок третьего ряда////////////////////////////////////
        h_but_cur = 194*scale_Y;          // находим размеры кнопок третьего ряда для текущего разрешения в пикселях
        w_but_cur = 775*scale_X;          // находим размеры кнопок третьего ряда для текущего разрешения в пикселях

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_green_rms);
        btm_green_rms = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_green_arif);
        btm_green_arif = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_green_rect);
        btm_green_rect = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_green_amp);
        btm_green_amp = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_grey_rms);
        btm_grey_rms = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_grey_arif);
        btm_grey_arif = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_grey_rect);
        btm_grey_rect = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_grey_amp);
        btm_grey_amp = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////
        ///////////////////битмапы кнопок центрального ряда////////////////////////////////////
        h_but_cur = 139*scale_Y;          // находим размеры кнопок центрального ряда для текущего разрешения в пикселях
        w_but_cur = 725*scale_X;          // находим размеры кнопок центрального ряда для текущего разрешения в пикселях

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_grey_auto);
        btm_butgrey_auto_range = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_green_auto);
        btm_butgreen_auto_range = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);

        h_but_cur = 139*scale_Y;          //
        w_but_cur = 362*scale_X;
///////////////////////////////////////voltage///////////////
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_grey_range0);
        btm_grey_range[3][0] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_grey_range1);
        btm_grey_range[3][1] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_grey_range2);
        btm_grey_range[3][2] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_grey_range3);
        btm_grey_range[3][3] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        h_but_cur = 184*scale_Y;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_grey_range4);
        btm_grey_range[3][4] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_grey_range5);
        btm_grey_range[3][5] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);

        h_but_cur = 139*scale_Y;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_green_range0);
        btm_green_range[3][0] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_green_range1);
        btm_green_range[3][1] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_green_range2);
        btm_green_range[3][2] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_green_range3);
        btm_green_range[3][3] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        h_but_cur = 184*scale_Y;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_green_range4);
        btm_green_range[3][4] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_green_range5);
        btm_green_range[3][5] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        h_but_cur = 139*scale_Y;
///////////////////////////resistance//////////////////////////////
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_grey_range0);
        btm_grey_range[5][0] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_grey_range1);
        btm_grey_range[5][1] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_grey_range2);
        btm_grey_range[5][2] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_grey_range3);
        btm_grey_range[5][3] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        h_but_cur = 184*scale_Y;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_grey_range4);
        btm_grey_range[5][4] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_grey_range5);
        btm_grey_range[5][5] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        h_but_cur = 139*scale_Y;

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_green_range0);
        btm_green_range[5][0] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_green_range1);
        btm_green_range[5][1] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_green_range2);
        btm_green_range[5][2] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_green_range3);
        btm_green_range[5][3] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        h_but_cur = 184*scale_Y;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_green_range4);
        btm_green_range[5][4] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_green_range5);
        btm_green_range[5][5] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        h_but_cur = 139*scale_Y;
//////////////////////////////////////current//////////////////////////////////////////
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.curbut_grey_range0);
        btm_grey_range[4][0] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.curbut_grey_range1);
        btm_grey_range[4][1] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        h_but_cur = 184*scale_Y;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.curbut_grey_range2);
        btm_grey_range[4][2] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.curbut_grey_range3);
        btm_grey_range[4][3] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        h_but_cur = 139*scale_Y;

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.curbut_green_range0);
        btm_green_range[4][0] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.curbut_green_range1);
        btm_green_range[4][1] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        h_but_cur = 184*scale_Y;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.curbut_green_range2);
        btm_green_range[4][2] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.curbut_green_range3);
        btm_green_range[4][3] = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
//////////////////////////////////////////////////////////////////////////////////////
        h_but_cur = 120*scale_Y;          //
        w_but_cur = 732*scale_X;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_usb_off);
        btm_usb_off = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_usb_on);
        btm_usb_on = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);

        h_but_cur = 172*scale_Y;          //
        w_but_cur = 383*scale_X;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_grey_change);
        btm_but_grey_change = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_green_change);
        btm_but_green_change = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);

//////////////////////////////////////////////////////////////////////////////////////
        String info =
                String.format("Info: size = %s x %s, bytes = %s (%s), config = %s",
                        btm_green_freq.getWidth(),
                        btm_green_freq.getHeight(),
                        btm_green_freq.getByteCount(),
                        btm_green_freq.getRowBytes(),
                        btm_green_freq.getConfig());
        Log.d(tag, info);

        bitmap = null;
    }
    void find_bitm_lay2(){

        Bitmap bitmap;

        float h_but_cur = 222*scale_Y;          // находим размеры кнопок для текущего разрешения в пикселях
        float w_but_cur = 364*scale_X;          // находим размеры кнопок для текущего разрешения в пикселях
///////////////////////////////////////voltage///////////////
        try {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_grey_range_auto_lay2);
            btm_butgrey_auto_range_lay2 = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_green_range_auto_lay2);
            btm_butgreen_auto_range_lay2 = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);

            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_grey_range0_lay2);
            btm_grey_range_lay2[3][0] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_grey_range1_lay2);
            btm_grey_range_lay2[3][1] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_grey_range2_lay2);
            btm_grey_range_lay2[3][2] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_grey_range3_lay2);
            btm_grey_range_lay2[3][3] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_grey_range4_lay2);
            btm_grey_range_lay2[3][4] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_grey_range5_lay2);
            btm_grey_range_lay2[3][5] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);

            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_green_range0_lay2);
            btm_green_range_lay2[3][0] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_green_range1_lay2);
            btm_green_range_lay2[3][1] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_green_range2_lay2);
            btm_green_range_lay2[3][2] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_green_range3_lay2);
            btm_green_range_lay2[3][3] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_green_range4_lay2);
            btm_green_range_lay2[3][4] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volbut_green_range5_lay2);
            btm_green_range_lay2[3][5] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
///////////////////////////resistance//////////////////////////////
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_grey_range0_lay2);
            btm_grey_range_lay2[5][0] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_grey_range1_lay2);
            btm_grey_range_lay2[5][1] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_grey_range2_lay2);
            btm_grey_range_lay2[5][2] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_grey_range3_lay2);
            btm_grey_range_lay2[5][3] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_grey_range4_lay2);
            btm_grey_range_lay2[5][4] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_grey_range5_lay2);
            btm_grey_range_lay2[5][5] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);

            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_green_range0_lay2);
            btm_green_range_lay2[5][0] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_green_range1_lay2);
            btm_green_range_lay2[5][1] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_green_range2_lay2);
            btm_green_range_lay2[5][2] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_green_range3_lay2);
            btm_green_range_lay2[5][3] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_green_range4_lay2);
            btm_green_range_lay2[5][4] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resbut_green_range5_lay2);
            btm_green_range_lay2[5][5] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
//////////////////////////////////////current//////////////////////////////////////////
            w_but_cur = 510 * scale_X;
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.curbut_grey_range_auto_lay2);
            btm_butgrey_autoCUR_range_lay2 = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.curbut_green_range_auto_lay2);
            btm_butgreen_autoCUR_range_lay2 = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);

            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.curbut_grey_range0_lay2);
            btm_grey_range_lay2[4][0] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.curbut_grey_range1_lay2);
            btm_grey_range_lay2[4][1] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.curbut_grey_range2_lay2);
            btm_grey_range_lay2[4][2] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.curbut_grey_range3_lay2);
            btm_grey_range_lay2[4][3] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);

            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.curbut_green_range0_lay2);
            btm_green_range_lay2[4][0] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.curbut_green_range1_lay2);
            btm_green_range_lay2[4][1] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.curbut_green_range2_lay2);
            btm_green_range_lay2[4][2] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.curbut_green_range3_lay2);
            btm_green_range_lay2[4][3] = Bitmap.createScaledBitmap(bitmap, (int) w_but_cur, (int) h_but_cur, true);
//////////////////////////////////////////////////////////////////////////////////////
            h_but_cur = 193*scale_Y;          // находим размеры кнопок третьего ряда для текущего разрешения в пикселях
            w_but_cur = 775*scale_X;          // находим размеры кнопок третьего ряда для текущего разрешения в пикселях
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_green_amp_lay2);
            btm_but_green_amp_lay2 = Bitmap.createScaledBitmap(bitmap, (int) (w_but_cur), (int) (h_but_cur), true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_green_rms_lay2);
            btm_but_green_rms_lay2 = Bitmap.createScaledBitmap(bitmap, (int) (w_but_cur), (int) (h_but_cur), true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_green_arif_lay2);
            btm_but_green_arif_lay2 = Bitmap.createScaledBitmap(bitmap, (int) (w_but_cur), (int) (h_but_cur), true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_green_rect_lay2);
            btm_but_green_rect_lay2 = Bitmap.createScaledBitmap(bitmap, (int) (w_but_cur), (int) (h_but_cur), true);

            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_grey_amp_lay2);
            btm_but_grey_amp_lay2 = Bitmap.createScaledBitmap(bitmap, (int) (w_but_cur), (int) (h_but_cur), true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_grey_rms_lay2);
            btm_but_grey_rms_lay2 = Bitmap.createScaledBitmap(bitmap, (int) (w_but_cur), (int) (h_but_cur), true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_grey_arif_lay2);
            btm_but_grey_arif_lay2 = Bitmap.createScaledBitmap(bitmap, (int) (w_but_cur), (int) (h_but_cur), true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_grey_rect_lay2);
            btm_but_grey_rect_lay2 = Bitmap.createScaledBitmap(bitmap, (int) (w_but_cur), (int) (h_but_cur), true);
//////////////////////////////////////////////////////////////////////////////////////
            h_but_cur = 157*scale_Y;          //
            w_but_cur = 383*scale_X;
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_grey_change_lay2);
            btm_but_grey_change_lay2 = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.but_green_change_lay2);
            btm_but_green_change_lay2 = Bitmap.createScaledBitmap(bitmap, (int)w_but_cur, (int)h_but_cur, true);
//////////////////////////////////////////////////////////////////////////////////////
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.screenshot_main);
            btm_screenshot_main = Bitmap.createScaledBitmap(bitmap, (int) (0.5 * widht), (int) (0.5 * hight), true);
            bitmap = null;
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.screenshot_dop);
            btm_screenshot_dop = Bitmap.createScaledBitmap(bitmap, (int) (0.5 * widht), (int) (0.5 * hight), true);
            bitmap = null;
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.screenshot_tap);
            btm_screenshot_tap = Bitmap.createScaledBitmap(bitmap, (int) (0.5 * widht), (int) (0.5 * hight), true);
            bitmap = null;
        }catch (Exception e){Log.d(tag, "Exception create BITMAP!");}
    }
/////////////////////////////////////////////
void reboot_serv(){
    // Reboot
    try {
        Process proc = Runtime.getRuntime()
                .exec(new String[]{ "su", "-c", "reboot" });
        proc.waitFor();
    } catch (Exception ex) {
        ex.printStackTrace(); Log.d(tag, "ex: " + ex);
    }

}
    void pow_of_serv(){

        //// Power OFF
        try {
            Process proc = Runtime.getRuntime()
                    .exec(new String[]{ "su", "-c", "reboot -p" });
            proc.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    void show_txt_toast(String str){
        Toast.makeText(this,str, Toast.LENGTH_SHORT).show();
    }
//////////////////////////////////////////////////////////////////
private void showPopupMenu(View v) {
    PopupMenu popupMenu = new PopupMenu(this, v);
    popupMenu.inflate(R.menu.pop_menu); // Для Android 4.0
    // для версии Android 3.0 нужно использовать длинный вариант
    // popupMenu.getMenuInflater().inflate(R.menu.popupmenu,
    // popupMenu.getMenu());
/*    MenuItem it = popupMenu.getMenu().getItem(3);
    it.setCheckable(true);
    if(menu4_check){ it.setChecked(true); }
    else{ it.setChecked(false); }*/

    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    // Toast.makeText(PopupMenuDemoActivity.this,
                    // item.toString(), Toast.LENGTH_LONG).show();
                    // return true;
                    switch (item.getItemId()) {

                        case R.id.menu1:
                            dialog_show(3);
                            break;
                        case R.id.menu2:
                            dialog_show(2);
                            break;
                        case R.id.menu3:
                            dialog_show(4);
/*                            Toast.makeText(getApplicationContext(),
                                    "Вы выбрали Menu 3",
                                    Toast.LENGTH_SHORT).show();*/
                            break;
                        case R.id.menu4:

/*                            if(menu4_check){ menu4_check = false; item.setChecked(false);}
                            else{menu4_check = true; item.setChecked(true);}*/
                            break;
                        case R.id.menu5:
                            finish();
                            break;
                        default:  break;
                    }
                    return true;
                }
            });
    MenuPopupHelper menuHelper = new MenuPopupHelper(this, (MenuBuilder) popupMenu.getMenu(), v);
    menuHelper.setForceShowIcon(true);
    menuHelper.setGravity(Gravity.END); menuHelper.show();
    /*
    popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {

        @Override
        public void onDismiss(PopupMenu menu) {
            Toast.makeText(getApplicationContext(), "onDismiss",
                    Toast.LENGTH_SHORT).show();
        }
    });
    popupMenu.show();
    */
}
//////////////////////////////////////////////////////////////////
void dialog_show(int dialog){
    final AlertDialog alert;
    AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
//    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    switch(dialog){
        case 1:
            final EditText gist_v = new EditText(MainActivity.this);
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
                            switch(CMD.mod_work){
                                case flag_mod_vol:
                                    if (a>0 && a<5001) { send_MULT(cmd_vol); time_select = a;}
                                    else{
                                        txt_edit.setText("100");
                                        show_txt_toast("Введите время выборки от '1' до '5000' мсек");
                                    }
                                    break;
                                case flag_mod_cur:
                                    if (a>0 && a<5001) {send_MULT(cmd_cur); time_select = a;}
                                    else{
                                        txt_edit.setText("100");
                                        show_txt_toast("Введите время выборки от '1' до '5000' мсек");
                                    }
                                    break;
                                case flag_mod_cap:
                                    cap_wire =  (double)a;
                                    break;
                                case flag_mod_ind:
                                    induct_wire = (double)a;
                                    break;
                            }

                        }
                    });
            alert = builder.create();
            alert.show();
            break;
        case 2:
            View view_about;
            view_about = (RelativeLayout)getLayoutInflater().inflate(R.layout.help_mult, null);
            TextView txt_title = view_about.findViewById(R.id.textView2);
            TextView txt_mes = view_about.findViewById(R.id.textView5);
            txt_title.setText("ETAN_Mult v.xx");
//            txt_mes.setText(getResources().getIdentifier("about_", "string", getPackageName()));
            txt_mes.setText(getResources().getString(R.string.about_));
            builder.setView(view_about);
            alert = builder.create();
            alert.show();
            break;
        case 3:
            View view_help;
            view_help = (RelativeLayout)getLayoutInflater().inflate(R.layout.help_mult, null);
            TextView txt_help0 = view_help.findViewById(R.id.textView5);
            txt_help0.setText(getResources().getString(R.string.help_0));
            TextView txt_help1 = view_help.findViewById(R.id.help1);
            txt_help1.setText(getResources().getString(R.string.help_1));
            TextView txt_help2 = view_help.findViewById(R.id.help2);
            txt_help2.setText(getResources().getString(R.string.help_2));
            TextView txt_help3 = view_help.findViewById(R.id.help3);
            txt_help3.setText(getResources().getString(R.string.help_3));
            try {
                ImageView img_help0 = view_help.findViewById(R.id.img_help0);
                ImageView img_help1 = view_help.findViewById(R.id.img_help1);
                ImageView img_help2 = view_help.findViewById(R.id.img_help2);
                img_help0.setImageBitmap(btm_screenshot_main);
                img_help1.setImageBitmap(btm_screenshot_dop);
                img_help2.setImageBitmap(btm_screenshot_tap);
            }catch(Exception e){Log.d(tag, "Exception out Screenshot_!"); }

            builder.setView(view_help);
            alert = builder.create();
            alert.show();
/*            builder.setTitle("ТЕРМОСТАТ").setMessage("Помощь оказана!");
            alert = builder.create();
            alert.show();*/
            break;
        case 4:
            View view_param;
            view_param = (RelativeLayout)getLayoutInflater().inflate(R.layout.help_mult, null);
            TextView txt_t = view_param.findViewById(R.id.textView2);
            TextView txt_m = view_param.findViewById(R.id.textView5);
            txt_t.setText("Характеристики устройства:");
//            txt_mes.setText(getResources().getIdentifier("about_", "string", getPackageName()));
            txt_m.setText(getResources().getString(R.string.param_));
            builder.setView(view_param);
            alert = builder.create();
            alert.show();
            break;
        case 5:
            break;
        case 6:

            break;
    }
}
//////////////////////////////////////////////////////////////////
}
