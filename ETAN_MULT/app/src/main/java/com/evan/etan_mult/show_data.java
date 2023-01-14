package com.evan.etan_mult;

import android.util.Log;

/**
 * Created by evan on 20.03.2018.
 */

public class show_data {
    final static int flag_mod_ind = 0;
    final static int flag_mod_freq = 1;
    final static int flag_mod_cap = 2;
    final static int flag_mod_vol = 3;
    final static int flag_mod_cur = 4;
    final static int flag_mod_res = 5;
    String tag = "TAG";
    func fn;
    MainActivity mobj;
    public show_data(){

    }
    public String[] show(int mod_work, int data, int data1, int data3) {
        double mes;
        double data_doub;
        double stm_adc;
        double K_korr, B_korr, K_korrm, B_korrm;
        String Genri = "Гн";
        String[] ff = new String[2];
        String[] result = new String[6];
        String lab_value ="";
        int filter_dr;							// переменная фильтра сглаживания
        int i_filtr = 0;
        fn = new func();
        mobj = new MainActivity();
        switch (mod_work) {
            case flag_mod_ind:
                if (data > 0) {
                    filter_dr = fn.filter_func(data);
                    if (data>i_filtr) {
                        if ((data - i_filtr)>filter_dr) { data_doub = (double)data; i_filtr = data; }
                        else { data_doub = (double)i_filtr; }
                    }
                    else {
                        if ((i_filtr - data)>filter_dr) { data_doub = (double)data; i_filtr = data; }
                        else { data_doub = (double)i_filtr; }
                    }

                    data_doub = data;
                    mes = fn.rez_induct(data_doub);
                    K_korr = mobj.korr_ind[mobj.range_INDCAPFR][0];
                    B_korr = mobj.korr_ind[mobj.range_INDCAPFR][1];
                    Log.d(tag, "induct = " +mes+" : "+K_korr+" : "+B_korr);
                    ff = fn.show_induct(mes, K_korr, B_korr);
                    result[0] = ff[0]; result[1] = ff[1];
                }

                else {
/*                    flag_show_strelka = false;
                    this->lab_value->Text = "0,00";*/
                }

                break;
            ////////////////////////////////////////////////////////
            case flag_mod_freq:
                if (data > 0) {

                    filter_dr = fn.filter_func(data);
                    if (data>i_filtr) {
                        if ((data - i_filtr)>filter_dr) { data_doub = (double)data; i_filtr = data; }
                        else { data_doub = (double)i_filtr; }
                    }
                    else {
                        if ((i_filtr - data)>filter_dr) { data_doub = (double)data; i_filtr = data; }
                        else { data_doub = (double)i_filtr; }
                    }
                    mes = data_doub*mobj.delitel_freq;
                    if (mes < 1000) { mobj.range_INDCAPFR = 0; }
                    if (mes > 1000 && mes < 1000000) { mobj.range_INDCAPFR = 1; }
                    if (mes > 1000000) { mobj.range_INDCAPFR = 2; }
                    K_korr = mobj.korr_freq[mobj.range_INDCAPFR][0];
                    B_korr = mobj.korr_freq[mobj.range_INDCAPFR][1];
                    ff = fn.show_freq(data_doub, K_korr, B_korr);
                    result[0] = ff[0]; result[1] = ff[1];
                }
//                else { this->lab_value->Text = "0,00"; flag_show_strelka = false; }
                break;
            case flag_mod_cap:
                data = data * mobj.delitel_freq;
                filter_dr = fn.filter_func(data);
                data_doub = (double)data;
                if (mobj.delitel_freq != 1) {
                    mobj.range_INDCAPFR = 0;
                }
                else {
                    mobj.range_INDCAPFR = 1;
                }
                K_korr = mobj.korr_cap[mobj.range_INDCAPFR][0];
                B_korr = mobj.korr_cap[mobj.range_INDCAPFR][1];
                stm_adc = (3.0*(double)data3) / 4095.0;
                ff = fn.show_cap(data_doub, K_korr, B_korr, mobj.range_INDCAPFR, stm_adc);
                result[0] = ff[0]; result[1] = ff[1];
                break;
            case flag_mod_vol:
                K_korr = mobj.korr_vol[mobj.delitel_freq][0];
                B_korr = mobj.korr_vol[mobj.delitel_freq][1];

                K_korrm = mobj.korr_vol[mobj.delitel_freq + 6][0];
                B_korrm = mobj.korr_vol[mobj.delitel_freq + 6][1];
                ////////////////////////////////////////////////////////
//                K_korr = 1; K_korrm = 1; mobj.delitel_freq = 5;
                ////////////////////////////////////////////////////////
                if (mobj.Flag_algoritm_mod == mobj.flag_mod_Amplitude) {
                    mobj.znak_2 = 1;
                    String vol_max, vol_min, pr_max, pr_min;
                    data_doub = (double)mobj.max_ADC_rez;
                    ff = fn.show_volt(data_doub, K_korr, B_korr, K_korrm, B_korrm);
                    result[2] = ff[0]; result[4] = ff[1];
                    vol_max = "max. "+ff[0]; pr_max = ff[1];


                    mobj.znak_2 = 2;
                    data_doub = (double)mobj.min_ADC_rez;
                    ff = fn.show_volt(data_doub, K_korr, B_korr, K_korrm, B_korrm);
                    result[3] = ff[0]; result[5] = ff[1];
                    vol_min = "min. "+ff[0]; pr_min = ff[1];
                    ff[0] = vol_max + "\n"+ vol_min; ff[1] = pr_max+"\n"+" "+pr_min;
//                    Log.d(tag, "vol_max = "+ff[0]+" volpr = "+ff[1]);
                    result[0] = ff[0]; result[1] = ff[1];
                }
                else {
                    data_doub = (double)data;
                    ff = fn.show_volt(data_doub, K_korr, B_korr, K_korrm, B_korrm);
                    result[0] = ff[0]; result[1] = ff[1];
                }
                /////////////////////////////////////////////////////
//                Log.d(tag, "data_doub "+data_doub+", K_korr = "+K_korr+", K_korr_min = "+K_korrm);
                ////////////////////////////////////////////////////
                break;
            case flag_mod_cur:
                String cur_max, cur_min, pr_max, pr_min;
                double[] cur_dim = new double[9];
                cur_dim[0] = mobj.korr_cur[mobj.delitel_freq][1];		// заносим А0 по плюсу
                cur_dim[1] = mobj.korr_cur[mobj.delitel_freq][0];		// заносим А1 по плюсу
                cur_dim[2] = mobj.korr_cur[mobj.delitel_freq][2];		// заносим А2 по плюсу
                cur_dim[3] = mobj.korr_cur[mobj.delitel_freq][3];		// заносим А3 по плюсу

                cur_dim[4] = mobj.korr_cur[mobj.delitel_freq + 4][1];		// заносим А0 по минусу
                cur_dim[5] = mobj.korr_cur[mobj.delitel_freq + 4][0];		// заносим А1 по минусу
                cur_dim[6] = mobj.korr_cur[mobj.delitel_freq + 4][2];		// заносим А2 по минусу
                cur_dim[7] = mobj.korr_cur[mobj.delitel_freq + 4][3];		// заносим А3 по минусу

                if (mobj.Flag_algoritm_mod == mobj.flag_mod_Amplitude) {
                    mobj.znak_2 = 1;
                    data_doub = (double)mobj.max_ADC_rez;
                    cur_dim[8] = data_doub;
                    ff = fn.show_cur(cur_dim);
                    result[2] = ff[0]; result[4] = ff[1];
                    cur_max = "max. "+ff[0]; pr_max = ff[1];

                    mobj.znak_2 = 2;
                    data_doub = (double)mobj.min_ADC_rez;
                    cur_dim[8] = data_doub;
                    ff = fn.show_cur(cur_dim);
                    result[3] = ff[0]; result[5] = ff[1];
                    cur_min = "min. "+ff[0]; pr_min = ff[1];
                    ff[0] = cur_max + "\n"+ cur_min; ff[1] = pr_max+"\n"+" "+pr_min;
                    result[0] = ff[0]; result[1] = ff[1];
                }
                else {
                    data_doub = (double)data;
                    cur_dim[8] = data_doub;
                    ff = fn.show_cur(cur_dim);
                    result[0] = ff[0]; result[1] = ff[1];
//                    Log.d(tag, "data_doub "+data_doub+", K_korr = "+cur_dim[1]+", K_korr_min = "+cur_dim[5]);
                }
                break;
            case flag_mod_res:

                K_korr = mobj.korr_res[mobj.delitel_freq][0];
                B_korr = mobj.korr_res[mobj.delitel_freq][1];

                data_doub = (double)data;
                ff = fn.show_res(data_doub, K_korr, B_korr);
                result[0] = ff[0]; result[1] = ff[1];
                break;
        }

//        draw_val(val_for_strelka);
        return  result;
    }
}

