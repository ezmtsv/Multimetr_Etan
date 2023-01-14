package com.evan.etan_mult;


public class cmd {
    static boolean flag_ID = false;					// флаг подключения девайса с VID 0x0483
    static boolean flag_receiv = false;				// флаг принятых данных от МК
    static boolean flag_send_ready = false;			// флаг готовности к отправке в МК данных
    static boolean flag_cmd_OK = false;				// флаг успешно переданной команды
    static boolean flag_auto_range = true;			// флаг выставленного автодиапазона
    static boolean flag_coeff_read = false;			// флаг прочитанных коэффициентов корректировки
    static boolean flag_editIND_wire = false;
    static boolean adc_rez_minus = false;
    static boolean flag_show_strelka = true;

    char flag_cor_cap;
    int filter_dr;							// переменная фильтра сглаживания
    double rez_meas;
    double cap_real = 3100.0;				//2600.0
    double induct_wire = 300.0;

    double kor_C_big = 1.469;
    double R_charge = 100.0;

    char delitel_freq;
    char mode_work;

    char mode_work_znak2;
    char znak_2 = 0;
    char cmd_tmp;

    double koeff_K;							// коэффицент К для коррекции измеренной величины
    double koeff_B;							// коэффицент В для коррекции измеренной величины
    double val_for_strelka;


    int range_INDCAPFR = 0;					// переменная диапазона для частоты, ёмкости и индуктивности

    double step_Vdiscr = 0.000732600732;     //3.0/4095;
    double res_Fuse = 1.65;	//17.00;
    double res_charge_CAP = 100.0;
    static int  mod_work;


    static int Sel_range;
    static int Auto_range;

    MainActivity mobj;


public cmd(){
    mobj = new MainActivity();
 }
    public void init_struct_koeff() {
        for (int i = 0; i<2; i++) {
            mobj.korr_cap[i][0] = 1; mobj.korr_cap[i][1] = 0;
            mobj.korr_cap[i][2] = 1; mobj.korr_cap[i][3] = 0;
        }
        //		for(int i = 0; i<3; i++){
        for (int i = 0; i<8; i++) {
            mobj.korr_cur[i][0] = 1; mobj.korr_cur[i][1] = 0;
            mobj.korr_cur[i][2] = 0; mobj.korr_cur[i][3] = 0;
        }
        for (int i = 0; i<3; i++) {
            mobj.korr_freq[i][0] = 1; mobj.korr_freq[i][1] = 0;
            mobj.korr_freq[i][2] = 0; mobj.korr_freq[i][3] = 0;
        }
        for (int i = 0; i<10; i++) {
            mobj.korr_ind[i][0] = 1; mobj.korr_ind[i][1] = 0;
            mobj.korr_ind[i][2] = 1; mobj.korr_ind[i][3] = 0;
        }
        for (int i = 0; i<6; i++) {
            mobj.korr_res[i][0] = 1; mobj.korr_res[i][1] = 0;
            mobj.korr_res[i][2] = 0; mobj.korr_res[i][3] = 0;
        }
        //		for(int i = 0; i<6; i++){
        for (int i = 0; i<12; i++) {
            mobj.korr_vol[i][0] = 1; mobj.korr_vol[i][1] = 0;
            mobj.korr_vol[i][2] = 0; mobj.korr_vol[i][3] = 0;
        }
    }

}
