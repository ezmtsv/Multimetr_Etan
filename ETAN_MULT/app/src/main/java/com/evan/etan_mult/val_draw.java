package com.evan.etan_mult;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class val_draw extends Drawable {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //        Paint mPaint = new Paint();
    private Path mPath = new Path();
    MainActivity mobj;
    float scX, scY;
    float x_strelka, y_strelka, w_strelka, h_strelka, val_one_del, move_x;
    float startX_INF_val, startY_INF_val, startY_INF_valAMP, startY_INF_val_, startY_INF_valmin, startX_INF_mili, startY_INF_mili;
    float startX_scale, startY_scale, shift_symb, shift_symb1, shift_symb1_0, shift_symb1_1, shift_symb1_2;
    float shift_symb2, shift_symb2_0, shift_symb2_1, shift_symb2_2, shift_symb10, shift_symb10_0, shift_symb10_1, shift_symb10_2;

    int ww, hh;
    int size_txt, size_txt_scal, size_txtAMPL;

    final static int flag_mod_RMS = 57;
    final static int flag_mod_Average = 58;
    final static int flag_mod_Average_rectified = 59;
    final static int flag_mod_Amplitude = 60;

    String [][] scale = new String[3][11];

    frag Frg;
    String INF_val, INF_mili, INF_valmin;
    String tag = "TAG";

    public val_draw(int ww_, int hh_, float scX_, float scY_){
/*        INF_val = INF_;
        INF_mili = mili_;
        if(INF_val == null){ INF_val ="00.00"; }
        if(INF_mili == null){ INF_mili = ""; }
        INF_val = INF_val+INF_mili;*/

        scX= scX_;                              // масштаб экрана по х от исходной картинки
        scY= scY_;                              // масштаб экрана по у от исходной картинки
        ww = ww_;                               // ширина экрана
        hh = hh_;                               // высота экрана
        x_strelka =  90*scX;                   // начальная координата х подвижной стрелки
        y_strelka = 133*scY;                    // начальная координата у подвижной стрелки
        w_strelka = 9*scX;                      // ширина подвижной стрелки
        h_strelka = 152*scY;                    // высота подвижной стрелки
        val_one_del = (ww - 179*scX)/100;           // цена деления в пикселях 1% от шкалы
        startX_INF_val = 770*scX;
        startY_INF_val = 500*scY;           //460//координата "у" основного тестового поля
        startY_INF_valmin = 520*scY;        //460//координата "у" мин. значения
        startY_INF_valAMP = 400*scY;        //350//координата "у" макс. значения
        size_txt = (int)(250*scY);
        size_txt_scal = (int)(60*scY);
        size_txtAMPL = (int)(120*scY);
        startX_scale = (int)(78*scX);
        startY_scale = (int)(110*scY);
        shift_symb = (int)(239*scX);
//        shift_symb1 = (int)(220*scX);
        shift_symb1_0 = (int)(220*scX);
        shift_symb1_1 = shift_symb1_0;
        shift_symb1_2 = (int)(200*scX);
//        shift_symb2 = (int)(460*scX);
        shift_symb2_0 = (int)(460*scX);
        shift_symb2_1 = (int)(445*scX);
        shift_symb2_2 = shift_symb2_1;
//        shift_symb10 = (int)(2340*scX);
        shift_symb10_0 = (int)(2340*scX);
        shift_symb10_1 = (int)(2340*scX);
        shift_symb10_2 = (int)(2360*scX);
////////////////////////////////////////////
//        mobj.screen_range = 2;
//        mobj.frag_select_but = flag_mod_Amplitude;
///////////////////////////////////////////////
        int j = 0;
        for(int i = 0; i<11; i++){
            scale[0][i] = Integer.toString(j);
            j+=10;
        } j = 0;
        for(int i = 0; i<11; i++){
            scale[1][i] = Integer.toString(j);
            j+=50;
        } j = 0;
        for(int i = 0; i<10; i++){
            scale[2][i] = Integer.toString(j);
            j+=100;
        } scale[2][10] = "1K";
//        startX_INF_mili = startX_INF_val+300*scX_;
//        startY_INF_mili = startY_INF_val;
    }

    @Override
    public void draw(Canvas canvas) {

        mPath.reset();
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.RED);
//        move_x = x_strelka + (float)Frg.val_info*val_one_del;
        move_x = x_strelka + (float)mobj.val_info*val_one_del;
        mPath.moveTo(move_x,y_strelka);
        mPath.lineTo(move_x,y_strelka+h_strelka);
        mPath.lineTo(move_x+w_strelka,y_strelka+h_strelka);
        mPath.lineTo(move_x+w_strelka,y_strelka);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
        ///////////////////////////////
        ////выводим текстовую информацию на диаграмме//////////
        try {
            mPath.reset();
//        mPaint.setColor(Color.rgb(100,50,50));
            mPaint.setColor(Color.GREEN);
            if(mobj.frag_select_but_RMS == flag_mod_Amplitude){
                if(mobj.maxV!=null){
                    INF_val = "max. "+mobj.maxV +" "+mobj.pref_max; INF_valmin = "min.  "+ mobj.minV +" "+mobj.pref_min;
                }else{
                    INF_val = "max. "+"00.00 "; INF_valmin = "min.  "+ "00.00 ";
                }
                startY_INF_val_ = startY_INF_valAMP;
                mPaint.setTextSize(size_txtAMPL);
//                Log.d(tag, "DRAW frag_select_AMPL");
            }else {
                INF_val = mobj.INFO;
                INF_mili = mobj.INF_mili;
                INF_valmin ="";
                if (INF_val == null) {
                    INF_val = "00.00";
                }
                if (INF_mili == null) {
                    INF_mili = "";
                }
                INF_val = INF_val + INF_mili;
                startY_INF_val_ = startY_INF_val;
                mPaint.setTextSize(size_txt);
//                Log.d(tag, "DRAW frag_select = "+ mobj.frag_select_but_RMS);
            }
            switch(mobj.screen_range){
                case 0:
                    shift_symb1 = shift_symb1_0;
                    shift_symb2 = shift_symb2_0;
                    shift_symb10 = shift_symb10_0;
                    break;
                case 1:
                    shift_symb1 = shift_symb1_1;;
                    shift_symb2 = shift_symb2_1;
                    shift_symb10 = shift_symb10_1;
                    break;
                case 2:
                    shift_symb1 = shift_symb1_2;
                    shift_symb2 = shift_symb2_2;
                    shift_symb10 = shift_symb10_2;
                    break;
            }
//        mPaint.setStrokeWidth(18);
            canvas.drawText(INF_valmin, startX_INF_val, startY_INF_valmin, mPaint);
            canvas.drawText(INF_val, startX_INF_val, startY_INF_val_, mPaint);

            mPath.reset();
            mPaint.setColor(Color.GREEN);
            mPaint.setTextSize(size_txt_scal);

            canvas.drawText(scale[mobj.screen_range][0], startX_scale, startY_scale, mPaint);
            canvas.drawText(scale[mobj.screen_range][1], startX_scale+shift_symb1, startY_scale, mPaint);
            canvas.drawText(scale[mobj.screen_range][2], startX_scale+shift_symb2, startY_scale, mPaint);
            for(int i = 1; i<8; i++){
                canvas.drawText(scale[mobj.screen_range][i+2], startX_scale+shift_symb2+i*shift_symb, startY_scale, mPaint);
            }
            canvas.drawText(scale[mobj.screen_range][10], startX_scale+shift_symb10, startY_scale, mPaint);

        }catch(Exception e){ Log.d(tag, "Exception draw(Canvas canvas)");}
        invalidateSelf();               // метод обеспечивающий перерисовку графики
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }
}
