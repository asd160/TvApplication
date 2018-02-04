package com.example.ubuntu.bick.tvapplication.custom;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


import com.example.ubuntu.bick.tvapplication.R;
import com.example.ubuntu.bick.tvapplication.utils.DrawableToBitmap;
import com.example.ubuntu.bick.tvapplication.utils.screenUtils.ScreenGet;

import java.util.ArrayList;

/**
 * Created by 白2 鹏 on 2018/1/30.
 */

public class LineGraphicView extends View {
    /**
     * 公共部分  圆点的大小 每天数据的大小
     */
    private static final int CIRCLE_SIZE = 0;
    private int heightProeryPx; //屏幕高度
    private int widthProeryPx; //屏幕宽度

    //计算宽度
    private int jianjuTop = 60;
    private int bgWidth = 100;
    private int bgHeight = 600;
    //新添加
    private int sizeMax;//最大书
    private int sizeLow;//最小数


    // Rect类主要用于表示坐标系中的一块矩形区域
    private Rect mRect;
    // GradientDrawable支持使用渐变色来绘制图形，通常可以用作Button或是背景图形
    private GradientDrawable mDrawable;
    private Drawable drawable;
    private int bottomKeyboardHeight;
    private int marginTop2;

    private static enum Linestyle {
        Line, Curve
    }

    private Context mContext;
    private Paint mPaint;
    private Paint mPaintLow;
    private Resources res; //资源
    private DisplayMetrics dm;

    /**
     * data
     */
    private Linestyle mStyle = Linestyle.Curve;

    private int canvasHeight;
    private int canvasWidth;
    private int bheight = 0;
    private int blwidh;
    private boolean isMeasure = true;
    /**
     * Y轴最大值
     */
    private int maxValue;

    /**
     * Y轴间距值
     */
    private int averageValue;
    private int marginTop = 144+(bgWidth - 50)  +35;
    private int marginBottom = 360;

    /**
     * 曲线上总点数
     */
    private Point[] mPoints;
    private Point[] mPointslow; //**
    /**
     * 纵坐标值
     */
    private ArrayList<Double> yRawData;
    private ArrayList<Double> yRawDataLow;//**
    /**
     * 横坐标值
     */
    private ArrayList<Integer> xList = new ArrayList<Integer>();// 记录每个x的值
    private ArrayList<Integer> xListlow = new ArrayList<Integer>();// 记录每个x的值
    private int spacingHeight;

    /**
     * 图片间距
     *
     * @param context
     */
    int space = 10;
    private int singleWidth;
    private Activity activity;

    public LineGraphicView(Context context) {
        this(context, null);
    }

    public LineGraphicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        this.res = mContext.getResources();
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dm = new DisplayMetrics();
        setFocusable(true);///***************

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (isMeasure) {
            this.canvasHeight = getHeight();
            this.canvasWidth = getWidth();
            if (bheight == 0)
                bheight = (int) (canvasHeight - marginBottom);
            blwidh = dip2px(30);
            isMeasure = false;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        heightProeryPx = DrawableToBitmap.getproeryHeightPx(getContext());
        widthProeryPx = DrawableToBitmap.getproeryWidthPx(getContext());
        //计算虚拟键的高度
        bottomKeyboardHeight = new ScreenGet(activity).getBottomKeyboardHeight();
        //最低气温的表格 高度
        marginTop2 = marginTop+bheight+(35);
        jisuan();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void jisuan() {
        singleWidth = (widthProeryPx - (space * 6)) / 7;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawImageView(canvas);//bg
        //表格的颜色
        mPaint.setColor(res.getColor(R.color.search_opaque));
        mPaint.setStrokeWidth(dip2px(0.5f));

        drawAllXLine(canvas);
        // 画直线（纵向）
        drawAllYLine(canvas);

        drawAllXLine2(canvas);
        // 画直线（纵向）
        drawAllYLine2(canvas);

        /**
         * 获取位置
         */
        // 点的操作设置
        mPoints = getPoints();
        //*******
        mPointslow = getPointslow();
        for (int i = 0; i < mPointslow.length; i++) {
            System.out.println(mPointslow[i].x);
        }

        /**
         *  线条的颜色
         */
//        Paint mPaint=new Paint();
//        setLayerType( LAYER_TYPE_SOFTWARE , null);
//        mPaint.setColor(Color.GREEN);
//        mPaint.setTextSize(25);
//        mPaint.setShadowLayer(1, 10, 10, Color.GRAY);
        LinearGradient lg1 = new LinearGradient(0, 0, 1000, 1000, Color.RED, Color.BLUE, Shader.TileMode.MIRROR);
        mPaint.setShadowLayer(10, 0, 10, Color.BLACK); //偏移阴影
        mPaint.setShader(lg1);//渐变
        mPaint.setStrokeWidth(dip2px(7f));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Style.STROKE);
        if (mStyle == Linestyle.Curve) {
            drawScrollLine(canvas);//弧线
        } else {
            drawLine(canvas); //表格
        }
        /**
         * 画点
         */
        mPaint.setStyle(Style.FILL);
        for (int i = 0; i < mPoints.length; i++) {
            canvas.drawCircle(mPoints[i].x, mPoints[i].y, CIRCLE_SIZE / 2, mPaint);
        }
        /**
         * /*****
         * //参数一为渐变起初点坐标x位置，参数二为y轴位置，参数三和四分辨对应渐变终点，最后参数为平铺方式，这里设置为镜像
         */
        mPaintLow = new Paint(Paint.ANTI_ALIAS_FLAG);
        LinearGradient lg2 = new LinearGradient(0, 0, 1000, 1000, Color.YELLOW, Color.BLUE, Shader.TileMode.MIRROR);

        mPaintLow.setStrokeCap(Paint.Cap.ROUND);//圆角
        mPaintLow.setShader(lg2);//渐变
        mPaintLow.setShadowLayer(10, 0, 10, Color.BLACK); //偏移阴影
        mPaintLow.setStrokeWidth(dip2px(6f));
        mPaintLow.setStyle(Style.STROKE);
        if (mStyle == Linestyle.Curve) {
            //弧线
            drawScrollLinleow(canvas);
        } else {
            // 画线
            drawLine(canvas);
        }
        /**
         * 画点
         */
        mPaint.setStyle(Style.FILL);
        for (int i = 0; i < mPointslow.length; i++) {
            canvas.drawCircle(mPointslow[i].x, mPointslow[i].y, CIRCLE_SIZE / 2, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            //创建和按钮一样位置的Rect
//            Rect rect = new Rect(0, 0, drawable.getWidth(), drawable.getHeight());
//            if (rect.contains((int) x, (int) y)) {
//                System.out.println("按钮范围之内");
                Toast.makeText(getContext(),"sss",Toast.LENGTH_SHORT).show();
//            }
        }
        return super.onTouchEvent(event);
    }
    /**
     * 画背景图
     *
     * @param canvas
     */
    private void drawImageView(Canvas canvas) {

        int qidianY = 0;//起点X坐标
        int zhongidianY = 0;
        int rectButtom = bgHeight + 60-bottomKeyboardHeight;//bg buttom
        int rectRight= bgWidth;//bg right
        //画背景图
        for (int i = 0; i <7 ; i++) {

            //*************** 画北京 *************
            mRect = new Rect(qidianY,jianjuTop,rectRight,rectButtom);
            // orientation指定了渐变的方向，int[]colors指定渐变的颜色由colors数组指定，数组中的每个值为一个颜色。
            mDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, new int[] { 0xF0636363,
                    0xFFFF000}); //从上倒下 阴影设置
            // 设置Drawable的形状为矩形
            mDrawable.setShape(GradientDrawable.RECTANGLE);
            // 设置渐变的半径
            mDrawable.setGradientRadius((float) (Math.sqrt(2) * 120));

            mDrawable.setBounds(mRect);
            float r = 6;
            canvas.save();
            //canvas.translate(10, 10);//画面平移
            // 设置渐变模式：线性渐变
            mDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            setCornersRadii(mDrawable, r, r, r, r);
            mDrawable.draw(canvas);
            canvas.restore();
            qidianY += (bgWidth + 10);//计算 每一次Y轴 起点坐标
            rectRight=bgWidth+10+bgWidth;//右边距离 bg right
        }
         qidianY = 0;//起点X坐标
         zhongidianY = 0;
         rectButtom = bgHeight + 60-bottomKeyboardHeight;//bg buttom
         rectRight= bgWidth;//bg right

        for (int i = 0; i < 7; i++) {
            drawable = res.getDrawable(R.drawable.tall_color);
            //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tall_color);

            //背景矩形
            //System.out.println("bottomKeyboardHeight"+bottomKeyboardHeight);
            //drawImage2(canvas, DrawableToBitmap.drawableToBitmap(drawable), qidianY, jianjuTop, bgWidth, bgHeight - bottomKeyboardHeight, 0, 0);
            /**
             * 画星期
             */
            mPaint.setColor(Color.WHITE);
            mPaint.setTextSize(16);//设置字体大小
            //根据路径得到Typeface
            //Typeface tf=Typeface.createFromAsset(mgr, "fonts/pocknum.ttf");
            //mPaint.setTypeface(tf);//设置字体类型
            // canvas.drawText(,qidianY,90,mPaint);
            canvas.drawText("今天", qidianY + 30, 100, mPaint);

            /**
             * 画天气
             */
            Drawable tianqi = res.getDrawable(R.drawable.duoyun);
            //mPaint.setColor(Color.WHITE);
            //画天气bitmap
            drawImage2(canvas, DrawableToBitmap.drawableToBitmap(tianqi), qidianY + 20, 100 + 20, bgWidth - 50, bgWidth - 50, 0, 0);
            //晚上天气
            drawImage2(canvas, DrawableToBitmap.drawableToBitmap(tianqi), qidianY + 20, heightProeryPx-(148+(bgWidth - 50)), bgWidth - 50, bgWidth - 50, 0, 0);
            //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.duoyun),100,100,mPaint);
            qidianY += (bgWidth + 10);//计算 每一次Y轴 起点坐标

        }

    }


    public void drawImage2(Canvas canvas, Bitmap blt, int x, int y,
                           int w, int h, int bx, int by) {
        Rect src = new Rect();// 图片 >>原矩形
        Rect dst = new Rect();// 屏幕 >>目标矩形

        src.left = bx;
        src.top = by;
        src.right = bx + w;
        src.bottom = by + h;

        dst.left = x;
        dst.top = y;
        dst.right = x + w;
        dst.bottom = y + h;
        Paint mPaint = new Paint();
        // 这个方法的意思就像 将一个位图按照需求重画一遍，画后的位图就是我们需要的了
//        mPaint.setAlpha(0);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setStyle(Paint.Style.STROKE);
//        //使Paint的笔触更加圆滑一点
        mPaint.setStrokeJoin(Paint.Join.ROUND);
//        //使Paint的连接处更加圆滑一点
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(false);
        canvas.drawBitmap(blt, src, dst, mPaint);
        src = null;
        dst = null;
    }


    /**
     * 画所有横向表格，包括X轴
     */
    private void drawAllXLine(Canvas canvas) {
        for (int i = 0; i < spacingHeight + 1; i++) {
            canvas.drawLine(blwidh, bheight - (bheight / spacingHeight) * i + marginTop, (canvasWidth - blwidh),
                    bheight - (bheight / spacingHeight) * i + marginTop, mPaint);// Y坐标

            drawText(String.valueOf(averageValue * i), blwidh / 2, bheight - (bheight / spacingHeight) * i + marginTop,
                    canvas);
        }
    }

    private void drawAllXLine2(Canvas canvas) {

        //0 - 30 数
        //bheight 表格的高度
        for (int i = 0; i < spacingHeight + 1; i++) {
            canvas.drawLine(blwidh, bheight - (bheight / spacingHeight) * i + marginTop2, (canvasWidth - blwidh),
                    bheight - (bheight / spacingHeight) * i + marginTop2, mPaint);// Y坐标

            drawText(String.valueOf(averageValue * i), blwidh / 2, bheight - (bheight / spacingHeight) * i + marginTop2,
                    canvas);
        }
    }


    /**
     * 画所有纵向表格，包括Y 轴
     */
    private void drawAllYLine(Canvas canvas)
    {
        for (int i = 0; i < yRawData.size(); i++)
        {
            /**
             * 记录数据 每个点
             */
            xList.add(blwidh + (canvasWidth - blwidh) / yRawData.size() * i);

            xListlow.add(blwidh + (canvasWidth - blwidh) / yRawDataLow.size() * i);

            /**
             * 最高气温
             */
            canvas.drawLine(blwidh + (canvasWidth - blwidh) / yRawData.size() * i, marginTop, blwidh
                    + (canvasWidth - blwidh) / yRawData.size() * i, bheight + marginTop, mPaint);


            /**
             * 最低气温
             */
            canvas.drawLine(blwidh + (canvasWidth - blwidh) / yRawDataLow.size() * i, marginTop2, blwidh
                    + (canvasWidth - blwidh) / yRawDataLow.size() * i, bheight + marginTop2, mPaint);

        }
    }
    private void drawAllYLine2(Canvas canvas) {

    }




    private void drawScrollLine(Canvas canvas)
    {
        Point startp = new Point();
        Point endp = new Point();
        for (int i = 0; i < mPoints.length - 1; i++)
        {
            startp = mPoints[i];
            endp = mPoints[i + 1];
            int wt = (startp.x + endp.x) / 2;
            Point p3 = new Point();
            Point p4 = new Point();
            p3.y = startp.y;
            p3.x = wt;
            p4.y = endp.y;
            p4.x = wt;

            Path path = new Path();
            path.moveTo(startp.x, startp.y);
            path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
            canvas.drawPath(path, mPaint);
        }
    }

    /**
     * 最低温度线条
     * @param canvas
     */
    private void drawScrollLinleow(Canvas canvas)
    {
        //画点
        Point startp = new Point();
        Point endp = new Point();
        for (int i = 0; i < mPointslow.length - 1; i++)
        {
            startp = mPointslow[i];//开始
            endp = mPointslow[i + 1];//结束位置
            int wt = (startp.x + endp.x) / 2;
            Point p3 = new Point();
            Point p4 = new Point();
            p3.y = startp.y;
            p3.x = wt;
            p4.y = endp.y;
            p4.x = wt;

            Path path = new Path();
            path.moveTo(startp.x, startp.y);
            path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
           // path.quadTo( p4.x, p4.y, endp.x, endp.y);
            canvas.drawPath(path, mPaintLow);
        }
    }

    /**
     * 表格
     * @param canvas
     */
    private void drawLine(Canvas canvas)
    {
        Point startp = new Point();
        Point endp = new Point();
        for (int i = 0; i < mPoints.length - 1; i++)
        {
            startp = mPoints[i];
            endp = mPoints[i + 1];
            canvas.drawLine(startp.x, startp.y, endp.x, endp.y, mPaint);
        }
    }

    private void drawText(String text, int x, int y, Canvas canvas)
    {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextSize(dip2px(10));
        p.setColor(res.getColor(R.color.lb_default_search_color));
        p.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text, x, y, p);
    }

    /**
     * 获取Y 每个点
     * @return
     */
    private Point[] getPoints()
    {
        Point[] points = new Point[yRawData.size()];
        for (int i = 0; i < yRawData.size(); i++)
        {
            int ph = bheight - (int) (bheight * (yRawData.get(i) / maxValue));
            points[i] = new Point(xList.get(i), ph + marginTop);
        }
        return points;
    }

    /**
     * 获取Y 轴  //*****
     * @return
     */
    private Point[] getPointslow()
    {
        Point[] points = new Point[yRawDataLow.size()];
        for (int i = 0; i < yRawDataLow.size(); i++)
        {
            int ph = bheight - (int) (bheight * (yRawDataLow.get(i) / maxValue));
            points[i] = new Point(xListlow.get(i), ph + marginTop2);
        }
        return points;
    }



































    public void setData(Activity activity,ArrayList<Double> yRawData, ArrayList<Double> yRawDataLow,int maxValue, int averageValue
    ,int sizeMax,int sizeLow,int size)
    {

        //自己添加
        this.sizeMax=sizeMax;//最大表格数
        this.sizeLow=sizeLow;

        this.maxValue = maxValue;
        this.averageValue = averageValue;

        //this.mPoints = new Point[yRawData.size()];//数量
        this.mPoints = new Point[yRawData.size()];
        this.activity=activity;

        this.mPointslow = new Point[yRawDataLow.size()];//最低温度//*** 点

        this.yRawData = yRawData;
        this.yRawDataLow = yRawDataLow;//***

        this.spacingHeight = maxValue / averageValue; //表格X轴 间距 起点
    }

    public void setTotalvalue(int maxValue)
    {
        this.maxValue = maxValue;
    }

    public void setPjvalue(int averageValue)
    {
        this.averageValue = averageValue;
    }

    public void setMargint(int marginTop)
    {
        this.marginTop = marginTop;
    }

    public void setMarginb(int marginBottom)
    {
        this.marginBottom = marginBottom;
    }

    public void setMstyle(Linestyle mStyle)
    {
        this.mStyle = mStyle;
    }

    /**
     * Bheight =bheight (表格的高度)
     * @param bheight
     */
    public void setBheight(int bheight)
    {
        this.bheight = bheight;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(float dpValue)
    {
        return (int) (dpValue * dm.density + 0.5f);
    }
    /**
     * 设置矩形的 角度 radius
     * @param drawable
     * @param r0
     * @param r1
     * @param r2
     * @param r3
     */
    static void setCornersRadii(GradientDrawable drawable, float r0,
                                float r1, float r2, float r3) {
        // 设置图片四个角圆形半径：1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
        drawable.setCornerRadii(new float[] { r0, r0, r1, r1, r2, r2, r3,
                r3 });
    }
}