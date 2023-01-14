package com.evan.etan_mult;

import android.util.Log;

/**
 * Created by evan on 20.03.2018.
 */

public class func {
    final int flag_p = 0;
    final int flag_n = 1;
    final int flag_u = 2;
    final int flag_m = 3;
    final int flag_0 = 4;
    final int flag_K = 5;
    final int flag_M = 6;
    final int DC_plus = 0x11; // mode DC, plus
    final int DC_minus = 0x22; // mode DC, minus
    int flg;

    String tag = "TAG";
    double rez_meas;
    MainActivity mobj;

    public func(){
        mobj = new MainActivity();
    }
    String two_symbol_after_point(String symb){
        String res;
        int lenght_str;
        char[] s_buf = new char[100];
        char[] buf = new char[100];
        buf  = symb.toCharArray();      // получение из строки массива символов
        int count_sym_point = 0;
        int count_sym = 0;

        boolean flag_point = false;
        lenght_str = symb.length(); // количество символов в строке
        for(int  tmp = 0; tmp< 100; tmp++){s_buf[tmp] = ' ';}
        /////////////////////округление до 2 знаков после запятой
        for(int i = 0; i< lenght_str; i++){
            s_buf[i]= buf[i];
            count_sym++;
            if(buf[i]== '.'){
                flag_point = true;
            }

            if(flag_point){count_sym_point++;}
            if(count_sym_point >2) break;
        }
        ////////////////////////////
        res= "";
        for(int i = 0; i< count_sym; i++){res = res+s_buf[i];}
        return res;
    }
    //////////////////////////////
    public String prefix_meas(double mes) {
        String ss = "";
        flg = flag_p;
        mes = mes/1000000000.0;
        if (mes > 0.000001) {
            flg = flag_n;
            if (mes > 0.001) { flg = flag_u; }
            if (mes > 1) { flg = flag_m; };
            if (mes > 1000) { flg = flag_0; };
            if (mes > 1000000) { flg = flag_K; };
            if (mes > 1000000000) { flg = flag_M; };
        }

        switch (flg) {
            case flag_p:
                ss = "п";
                rez_meas = mes * 1000000000.0;
                break;
            case flag_n:
                ss = "н";
                rez_meas = mes * 1000000.0;
                break;
            case flag_u:
                rez_meas = mes * 1000.0;

                ss = "мк";
                break;
            case flag_m:
                rez_meas = mes ;
                ss = "м";
                break;
            case flag_0:
                rez_meas = mes / 1000.0;
                ss = "";
                break;
            case flag_K:
                rez_meas = mes / 1000000.0;
                ss = "к";
                break;
            case flag_M:
                rez_meas = mes / 1000000000.0;
                ss = "М";
                break;
        }
//        if(rez_meas > -1001 && rez_meas < 1001)
//            val_for_strelka = rez_meas;
        return ss;
    }
    //////////////////////////////
    public String prefix_meas_freq(double mes){
        String ss = "";
        flg= flag_0;
        if(mes>1000){
            flg= flag_K;
            if(mes>1000000) { flg= flag_M;}
        }
        switch(flg){
            case flag_K:
                ss = "к";
                rez_meas = mes/ 1000.0;
//				range_INDCAPFR = 1;
                break;
            case flag_M:
                ss = "М";
                rez_meas = mes/ 1000000.0;
//				range_INDCAPFR = 2;
                break;
            case flag_0:
                rez_meas = mes;
                ss = "";
//				range_INDCAPFR = 0;
                break;
        }
        return ss;
    }
    //////////////////////////////
    int filter_func (int data){
        int filt;
        if(data< 10000) {filt = 10;}
        else {filt = 30;}
        if(data< 5000) {filt = 5; }
        if(data< 1000) {filt = 2; }
        if(data< 500) {filt = 1; }
        if(data< 50) {filt = 0; }

        return filt;
    }
    //////////////////////////////
    public String[]  show_volt(double data, double k_korr, double b_korr, double k_korrm, double b_korrm){
        String[] rezult = new String[2];
        double mes , tmpp;
        double data_doub1 = 0, kor_K, kor_B;
        double k_vol = 0;
        String znak = "";
        String ff="";
        String prefix_volt;
        String volt_data;
        double miliVolt = 1000;

        try{

            if(mobj.adc_rez_minus) { kor_K = k_korrm; kor_B = b_korrm; }
            else { kor_K = k_korr; kor_B = b_korr; }
            if(mobj.delitel_freq == 0){
                data_doub1 = 1020.0;
                miliVolt = 1.0;
            }	//Kgain = 0.00294
            if(mobj.delitel_freq == 1){
                data_doub1 = 600.0;
                miliVolt = 1.0;
            }		//Kgain = 0.005
            if(mobj.delitel_freq == 2){
                data_doub1 = 60.0;
            }		//Kgain = 0.05
            if(mobj.delitel_freq == 3){
                data_doub1 = 6.0;
            }		//Kgain = 0.5
            if(mobj.delitel_freq == 4){
                data_doub1 = 0.6;
            }		//Kgain = 5
            if(mobj.delitel_freq == 5){
                data_doub1 = 0.06; //k_vol = 45000000000;  /// data_doub1 = Uref/Kgain  (3V/50)
            }		//Kgain = 50
            ;
            mes = ((data*data_doub1)/65535.0) ; //mes = ((data_doub*data_doub1)/65535.0)- k_vol;
//						if(mes<0) mes = 0.00000000001;
            mes = mes*miliVolt;
//////////////////////////////////////////////////////////////////////////////////корректировка с коэф. из структуры
            mes = mes*kor_K+kor_B;
//////////////////////////////////////////////////////////////////////////////////
//						if(mes<0){rezult = "0";}
//						else{
            if(mes<0){mes = mes*-1;}
            if(mobj.delitel_freq<2){	mes = mes*1000000000; mes = mes*1000; }
            else { mes = mes*1000000000; }
            prefix_volt = prefix_meas(mes);
            ff = Double.toString(rez_meas);
            volt_data = two_symbol_after_point(ff);  //
            if(mobj.znak_2<2){
                if(mobj.mode_work == DC_plus)znak = "";
                if(mobj.mode_work == DC_minus)znak = "-";
            }
            else {
                if(mobj.znak_2 == 2){
                    if(mobj.mode_work_znak2 == DC_plus)znak = "";
                    if(mobj.mode_work_znak2 == DC_minus)znak = "-";
                }
            }
            rezult[0] = znak+volt_data;
            rezult[1] = prefix_volt+"В";
            mobj.znak_2 = 0;
//						}
        }
        catch(Exception e){rezult[0] = "...";}
        return rezult;
    }
    //////////////////////////////
    public String [] show_cur(double [] dim){
        String[] rezult = new String[2];
        String znak = "";
        String ff="";
        String prefix_cur;
        String cur_data;
        double mes = 0;

        double data = dim[8], koef_A0 = dim[0], koef_A1 = dim[1], koef_A2 = dim[2], koef_A3 = dim[3],
                koef_A0m = dim[4], koef_A1m = dim[5], koef_A2m = dim[6], koef_A3m = dim[7];
        double A3, A2, A1, A0;
        double data_doub1;

        if(mobj.adc_rez_minus) {
            A3 = koef_A3m; A2 = koef_A2m; A1 = koef_A1m; A0 = koef_A0m;
        }
        else {
            A3 = koef_A3; A2 = koef_A2; A1 = koef_A1; A0 = koef_A0;
        }
        try{

            if(mobj.delitel_freq == 0){

                data_doub1 = (3.0*data)/(65535.0*15);
                mes = (data_doub1)/0.02;
            }//
            if(mobj.delitel_freq == 1){

                data_doub1 = (3.0*data)/(65535.0*15);
                mes = (data_doub1)/0.23;
            }	//
            if(mobj.delitel_freq == 2){
                data_doub1 = (3.0*(data))/(65535.0*15);
                mes = (data_doub1)/2.01;

            }	//
            if(mobj.delitel_freq == 3){

////////////////////////////////////////////////////////////////////////////////////
                data_doub1 = (3.0*(data))/(65535.0*150);
                mes = (data_doub1)/2.01;
                Log.d(tag, "data "+data+", K_korr = "+koef_A1+", K_korr_min = "+koef_A0);
            }
//////////////////////////////////////////////////////////////////////////////////корректировка с коэф. из структуры
            mes = mes*1000;									// переводим в милиамперы
//						mes = mes*kor_K+kor_B;
            mes = A3*mes*mes*mes+ A2*mes*mes+A1*mes+A0;

//////////////////////////////////////////////////////////////////////////////////

            mes = mes*1000000000;							// переводим в пикоамперы
            if(mes<0)mes = 0;
            prefix_cur = prefix_meas(mes);
            ff = Double.toString(rez_meas);
            cur_data = two_symbol_after_point(ff);

            if(mobj.znak_2<2){
                if(mobj.mode_work == DC_plus)znak = "";
                if(mobj.mode_work == DC_minus)znak = "- ";
            }
            else {
                if(mobj.znak_2 == 2){
                    if(mobj.mode_work_znak2 == DC_plus)znak = "";
                    if(mobj.mode_work_znak2 == DC_minus)znak = "- ";
                }
            }
            mobj.znak_2 = 0;
            rezult[0] = znak+cur_data;
            rezult[1] = prefix_cur+"А";
        }
        catch(Exception e){rezult[0] = "...";}
        return rezult;
    }
    //////////////////////////////
    public String[] show_res(double data, double k_korr, double b_korr){
        String[] rezult = new String[2];
        double mes;
        double rez_FUSE = 100; //16.25;//20.0;
        double rez_MOSFET = 0.88;
        double rez_RANGE = 0;
        double data_kor = 0;
        String prefix_res;
        String res_data;

        if(mobj.delitel_freq == 0){
            rez_RANGE = 40000000.0;
        }//
        if(mobj.delitel_freq == 1){
            rez_RANGE = 3900000.0;
        }	//
        if(mobj.delitel_freq == 2){
            rez_RANGE = 390000.0;
        }	//
        if(mobj.delitel_freq == 3){
            rez_RANGE = 39000.0;
        }	//
        if(mobj.delitel_freq == 4){
            rez_RANGE = 3900.0;
        }	//
        if(mobj.delitel_freq == 5){
            rez_RANGE = 390.0;
//							data_kor = 20;
        };;
        mes = (data*(rez_RANGE+rez_FUSE))/(131070.0-data); //
        mes = mes - rez_MOSFET;
        if(mes>0) {
//////////////////////////////////////////////////////////////////////////////////корректировка с коэф. из структуры
            mes = mes*k_korr+b_korr;
//////////////////////////////////////////////////////////////////////////////////
            mes = mes*1000000000; mes = mes*1000;
            prefix_res = prefix_meas(mes);
            res_data = Double.toString(rez_meas);
            res_data = two_symbol_after_point(res_data);
            rezult[0] = res_data;
            rezult[1] = prefix_res+"Ом";
//            flag_show_strelka = true;
            if (data > 65530.0) {
                rezult[0] = ".....";
//                flag_show_strelka = false;
            }
        }
        else{
            rezult[0] = "0";
//            flag_show_strelka = false;
        }
/////////////////////////////////////////////////////////////
        return rezult;
    }
    //////////////////////////////
    public double rez_induct(double data){
        double rez;
        double data_doub, mes, kor_ind_k = 1, kor_ind_b = 0, induct_series = 3640000.0;
        double cap_real = 3100.0;
/////////////////////////////////////////////////////////////////////////////////////////////
        data_doub = data*(double)mobj.delitel_freq;
        data_doub *= data_doub;
        data_doub = data_doub*cap_real;
        mes = 25331790086.0*1000000000000.0/(data_doub);
//						kor_ind_k = 1.22;
        mes = mes - induct_series;

/*
индуктивности по диапазонам
		0 диапазон 1мкГН-10мкГн			// k = 1.03806  b = -0.04844		12720000
эталон				измерено
1мкГн				1,01мкГн
10мкГн				10,68мкГн
		1 диапазон 10мкГН-100мкГн		// k = 1.01465  b = -0,83652		102340000
эталон				измерено
10мкГн				10,68мкГнмкГн
100мкГн				99,38мкГн
		2 диапазон 100мкГН-1000мкГн		// k = 0.99931  b = 0.68841			1000000000
эталон				измерено
100мкГн				99,38мкГн
1000мкГн			1000мкГн
		3 диапазон 1мГН-10мГн			// k = 0.87378  b = 126.21359		11310000000
эталон				измерено
1мГн				1000мкГн
10мГн				11300мкГн
		4 диапазон 10мГН-100мГн			// k = 0.79232  b = 1046.74706		124340000000
эталон				измерено
10мГн				11300мкГн
100мГн				122600мкГн
		5 диапазон 100мГН-1000мГн		// k = 0.80709  b = -798,12753		1240000000000.0
эталон				измерено
100мГн				122600мкГн
1000мГн				1240000мкГн
		6 диапазон 1ГН-5Гн				// k = 0.60331  b = 251,88536		7850000000000.0
эталон				измерено
1Гн					1240000мкГн
5Гн					7870000мкГн
		7 диапазон 5ГН-8Гн				// k = 0.42075  b = 1688,63955
эталон				измерено
5Гн					7870000мкГн
8Гн					15000000мкГн
		8 диапазон 8ГН-9Гн				// k = 0.37037  b = 2444,44444
эталон				измерено
8Гн					15000000мкГн
9Гн					17700000мкГн
		9 диапазон 9ГН-10Гн				// k = 0.30303  b = 3636,36363
эталон				измерено
9Гн					14500000мкГн
10Гн				17170000мкГн
*/
//////////////////////////////////////////////////////////////////////////////////////////////
        if(mes<0) rez = 0;
//							if(mes<0) ;
        else{
            if(mes< 15000000.0) {								//L < 10uHn
                mobj.range_INDCAPFR = 0;
            }
            else if (mes> 15000000.0 && mes< 102340000.0){							//L < 100uHn
                mobj.range_INDCAPFR = 1;
            }
            else if (mes > 102340000.0 && mes< 1000000000.0){						//L < 1000uHn
                mobj.range_INDCAPFR = 2;
            }  //
            else if (mes > 1000000000.0 && mes< 11310000000.0){						//L < 10mHn
                mobj.range_INDCAPFR = 3;
            }  //
            else if (mes > 11310000000.0 && mes< 124340000000.0){						//L < 100mHn
                mobj.range_INDCAPFR = 4;
            }	 //
            else if (mes > 124340000000.0 && mes< 1240000000000.0){						//L < 1000mHn
                mobj.range_INDCAPFR = 5;
            }
            else if (mes > 1240000000000.0 && mes< 7850000000000.0){					//1Hn < L < 5Hn
                mobj.range_INDCAPFR = 6;
            }
            else if (mes > 7850000000000.0 && mes< 14950000000000.0){					//5Hn < L < 8Hn
                mobj.range_INDCAPFR = 7;
            }
            else if (mes > 14950000000000.0 && mes < 17900000000000.0){					//8Hn < L < 9Hn
                mobj.range_INDCAPFR = 8;
            }
            else if (mes > 17900000000000.0){											//9Hn < L
                mobj.range_INDCAPFR = 9;
            }
//								mes = kor_ind_k*mes + kor_ind_b;
            if(mes<0)mes = 0;
            rez = mes;
            Log.d(tag, "induct = " +mes+" : "+mobj.range_INDCAPFR);
        }

        return rez;
    }
/////////////////////////////////////////////////////////////
    public String[] show_induct(double data, double k_korr, double b_korr){
        String[] rezult = new String[2];
        String prefix_ind;
        double ser_wire = mobj.induct_wire;
        double mes, kor_range = 1000000;
        if(!mobj.flag_editIND_wire)ser_wire = 0;;
        if(mobj.range_INDCAPFR > 5)kor_range = 1000000000;;
        mes = data*k_korr + b_korr*kor_range - ser_wire*1000;
//								mes = data - induct_wire*1000;



        if(mes<0) rezult[0] = "0";
        else{
            prefix_ind = prefix_meas(mes);    // вычисляется, в каких ед. будет отбражаться результат - в пико, нано, микро и т.д.
//            rezult[0] = Double.toString(rez_meas);
            rezult[0] = two_symbol_after_point(Double.toString(rez_meas));
            rezult[1] = prefix_ind+"Гн";
        }
        return rezult;
    }
    /////////////////////////////
    public String[] show_freq(double data, double k_korr, double b_korr){
        String[] rezult = new String[2];
        String prefix_freq;
        double rez;

        rez = data*mobj.delitel_freq;
//////////////////////////////////////////////////////////////////////////////////корректировка с коэф. из структуры
        rez = rez*k_korr+b_korr;
//////////////////////////////////////////////////////////////////////////////////
        prefix_freq = prefix_meas_freq(rez);
        if(rez<10){rezult[0] = "F < 10";}
        else{
//            rezult = Double.toString(rez_meas);
            rezult[0] = two_symbol_after_point(Double.toString(rez_meas));
        }
        rezult[1] = prefix_freq+"Гц";

        return rezult;
    }
    public String[] show_cap(double data, double k_korr, double b_korr, int range, double adc_stm){
        String[] rezult = new String[2];
        String prefix_cap;
        double rez = 0;
//double tmp;
        double Udd = 10.0;
        double UtreshH = 6.0;
        double UtreshL = 4.0;
        double res_in_mes_cap =	680;
        double R_charge = 100.0;
//////////////////////////////////////////////////////////////
        if(range == 0){
							/*
							rez = 1000000000000.0/(data*res_in_mes_cap * 0.85796); //формула для расчета ёмкости при Uпор.н. = 1,9718В, Uпор.в. = 5В и R = res_in_mes_cap -> Cпф = 10^12/(F*R*0.85796)
							rez = rez -1140.0;						// 1890пф - ёмкость цепи подключенной ко входу IN_C на плате
//						mes = (mes*1.70893) - 9.26795;			// корректировка измерения
							*/
            rez = -1000000000000.0/(data*res_in_mes_cap*(Math.log(1-(UtreshH/Udd))-Math.log(1-UtreshL/Udd)+Math.log(UtreshL/UtreshH)));

            //							rez = rez -3050.0;
        }
        if(range == 1){
            rez = (data)/(-1*R_charge*0.000001*(Math.log(1-(adc_stm/3.3))-Math.log(1-(0.001/3.3)))); //формула для расчета ёмкости при Uпор.н. = 0.001В, Uпор.в. = 1.6В и R = 100 -> Cпф = Tзар./(R*10^(-12)*(Ln(1- 1.6/1.6)-Ln(1- 0.001/1.6)))
//            Log.d(tag, "rez = "+rez);
            //							rez = rez*kor_C_big;
            if(data < 150) rez = 0;
        }
//////////////////////////////////////////////////////////////
        if(rez<0){rezult[0] = "0";}
        else{
//////////////////////////////////////////////////////////////////////////////////корректировка с коэф. из структуры
            rez = rez*k_korr+b_korr;
            Log.d(tag, "rez = "+rez);
//////////////////////////////////////////////////////////////////////////////////
            prefix_cap = prefix_meas(rez);
//            rezult = Double.toString(rez_meas);
            rezult[0] = two_symbol_after_point(Double.toString(rez_meas));
            rezult[1] = prefix_cap+"Ф";
        }

        return rezult;
    }
    /////////////////////////////
}
