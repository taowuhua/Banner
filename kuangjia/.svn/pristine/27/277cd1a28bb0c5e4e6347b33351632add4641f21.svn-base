package com.bank.quickpay.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.PhoneinfoUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 此类用于实现手写签名
 * @author xiepingping
 */
public class FingerPaintView extends LinearLayout {
	private TextView tv_shuoming, tv_clear,tv_ok,tv_time;
	FingerPaintClickListener rightclicklistenr;
	FingerPaintClickListener leftclicklistenr;
	LayoutParams p;
	FrameLayout frameLayout;
	static final int BACKGROUND_COLOR = Color.WHITE;
	static final int BRUSH_COLOR = Color.BLACK;
	PaintView mView;
	int mColorIndex;
	private boolean isConfirm = false;
	private boolean Signed = false;
	boolean isNeedTimer  = true;	//  订单确认的时候 设为ture  
	Timer timer ;
	Handler timehandler = new Handler() {
		@Override
        public void handleMessage(Message msg) {
			if (msg.what > 0) {
				tv_ok.setTextColor(getResources().getColor(R.color.white));
				tv_ok.setText("用户确认"+ "(" + msg.what + ")");
				tv_clear.setClickable(false);
//				getResources().getString(R.string.resend)
				tv_ok.setClickable(false);
			} else {
				timer.cancel();
				timer = null;
				tv_ok.setText("用户确认");
				tv_ok.setClickable(true);
				tv_clear.setClickable(true);
				tv_ok.setTextColor(getResources().getColor(R.color.white));
			}
		};
	};
	/**
	 * 开始倒计时60秒
	 */
	public void startCountdown() {
		timer = new Timer();
		TimerTask task = new TimerTask() {
			int secondsRremaining = 5;

			@Override
            public void run() {
				Message msg = new Message();
				msg.what = secondsRremaining--;
				timehandler.sendMessage(msg);
			}
		};
		timer.schedule(task, 1000, 1000);
	}
	
	public void stopCountdown(){
		if(isNeedTimer){
			if(timer!=null){
				timer.cancel();
			}
		}
		
	}
	

	public boolean isNeedTimer() {
		return isNeedTimer;
	}



	public void setNeedTimer(boolean isNeedTimer) {
		this.isNeedTimer = isNeedTimer;
	}



	public boolean isConfirm() {
		return isConfirm;
	}
	public void setConfirm(boolean isConfirm) {
		this.isConfirm = isConfirm;
	}
	public boolean isSigned() {
		return Signed;
	}
	public void setSigned(boolean signed) {
		Signed = signed;
	}
	public FingerPaintView(Context context, AttributeSet attrs) {
		super(context, attrs);
//		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);// 通过SystemService获得layout扩展服务
		inflater.inflate(R.layout.widget_fingerpaint, this);// 实例化xml布局文件

		tv_shuoming = (TextView) findViewById(R.id.tv_shuoming);
		tv_clear = (TextView) findViewById(R.id.tv_clear);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_ok = (TextView) findViewById(R.id.tv_ok);
		
		tv_clear.setOnClickListener(onclicklistener);// 绑定监听事件
		tv_ok.setOnClickListener(onclicklistener);// 绑定监听事件

		frameLayout = (FrameLayout) findViewById(R.id.finger_view);
		
		mView = new PaintView(context);
		mView.setBackgroundResource(R.drawable.lay_bg);
		frameLayout.addView(mView);
		mView.requestFocus();
	}
	public void initLayoutParams(int w, int h){
		p = new LayoutParams(w,h);
	}
	
	public void setQueRen(String text1,int rightbg){
		tv_ok.setText(text1);
		tv_ok.setBackgroundResource(rightbg);
	}

	public  void clearSign(){
		stopCountdown();
		setlockSignature(false);
		mView.clear();
		mView.setIscandraw(true);
	}
	public  void reDoSign(String lefttext, String righttext,int rightbg){
		setlockSignature(false);		
		tv_clear.setText(lefttext);
		tv_ok.setText(righttext);
		tv_ok.setBackgroundResource(rightbg);			
	}
	
	public  void setAfterConfirm(String lefttext, String righttext,int rightbg){
		setlockSignature(true);		
		tv_clear.setText(lefttext);
		tv_ok.setText(righttext);
		tv_ok.setBackgroundResource(rightbg);			
	}
	
	public  void setAfterConfirm(String lefttext,int leftbg, String righttext,int rightbg){
		setlockSignature(true);
		tv_clear.setText(lefttext);
		tv_clear.setBackgroundResource(leftbg);
		tv_ok.setText(righttext);
		tv_ok.setBackgroundResource(rightbg);			
	}
	
	public void setlockSignature(boolean  isLock){
		mView.setIscandraw(!isLock);
	}
	
	public Bitmap SaveAsBitmap(){
		return mView.getCachebBitmap();
	}
	private String formatTime(int t){
		  return t>=10? ""+t:"0"+t;//三元运算符 t>10时取 ""+t
	}
	public String SaveAsFile(){
		ByteArrayOutputStream baos = null;
		String _path = null;
		try {
			 Calendar c=Calendar.getInstance();
			  
			  String time=c.get(Calendar.YEAR)+"_"+                               //得到年
			  formatTime(c.get(Calendar.MONTH)+1)+"_"+//month加一    //月
			  formatTime(c.get(Calendar.DAY_OF_MONTH))+"_"+           //日
			  formatTime(c.get(Calendar.HOUR_OF_DAY))+"_"+              //时
			  formatTime(c.get(Calendar.MINUTE))+"_"+                           //分
			  formatTime(c.get(Calendar.SECOND));                               //秒
			
			String sign_dir = Environment.getExternalStorageDirectory() + File.separator+"imobpay"+ File.separator;			
			_path = sign_dir + time + ".jpg";
			baos = new ByteArrayOutputStream();
			mView.getCachebBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
			byte[] photoBytes = baos.toByteArray();
			if (photoBytes != null) {
				new FileOutputStream(new File(_path)).write(photoBytes);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
                    baos.close();
                }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return _path;

	}

	public void setMenuText(String text1,String time,String text2,String text3,
			int leftbg, int rightbg){
		tv_shuoming.setText(text1);
		tv_time.setText(time);
		tv_clear.setText(text2);
		tv_ok.setText(text3);
		tv_clear.setBackgroundResource(leftbg);
		tv_ok.setBackgroundResource(rightbg);
	}
	
	public interface FingerPaintClickListener {
		public void onClick(View iv);
	}
	
	public void SetRightClick(FingerPaintClickListener listener) {
		rightclicklistenr = listener;
	}
	
	public void SetLeftClick(FingerPaintClickListener listener) {
		leftclicklistenr = listener;

	}
	/**
	 * 设置右侧提交状态
	 * @param isCanClick
	 */
	public void SetRightClickable(boolean isCanClick){
			tv_ok.setEnabled(isCanClick);
			tv_ok.setClickable(isCanClick);
	}
	
	OnClickListener    onclicklistener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tv_clear:
				if (leftclicklistenr != null){
					leftclicklistenr.onClick(v);
					LogUtil.showLog("zuobian ");
				}	
				break;
			case R.id.tv_ok:
				if (rightclicklistenr != null){
					rightclicklistenr.onClick(v);
					LogUtil.showLog("youbian ");
				}		
				break;
			}
		}
	};
	
	/**
	 * 创建手写签名文件
	 * @author 田应中 
	 * @time 2014-05-29 0:29 增加锁定
	 * @time 2014-07-03 最后修改
	 * @return
	 */


	class PaintView extends View {
		
		private Paint paint;
		private Canvas cacheCanvas;
		private Bitmap cachebBitmap;
		private Path path;
		private boolean iscandraw ;
		static final int BACKGROUND_COLOR = Color.WHITE;

		static final int BRUSH_COLOR = Color.BLACK;
		public Bitmap getCachebBitmap() {
			return cachebBitmap;
		}

		public PaintView(Context context) {			
			super(context);
			iscandraw = true;
			init(context);
			
		}

		private void init(final Context context) {
			paint = new Paint();
			paint.setAntiAlias(true);
			paint.setStrokeWidth(3);
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(Color.BLACK);
			path = new Path();

			
			ViewTreeObserver vto = frameLayout.getViewTreeObserver(); 
			vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { 
				@Override
                public boolean onPreDraw() {
							frameLayout.getViewTreeObserver().removeOnPreDrawListener(this); 
							int height = frameLayout.getMeasuredHeight(); 
							int width = frameLayout.getMeasuredWidth(); 
							p=new LayoutParams(width, height);
					LogUtil.showLog("=========================LOG开始================================="+p.width+","+p.height);
							cachebBitmap = Bitmap.createBitmap(p.width==0? PhoneinfoUtils.getWindowsWidth(context):p.width, p.height==0?PhoneinfoUtils.getWindowsHight(context):p.height,Config.ARGB_8888);
					LogUtil.showLog("==========================LOG结束================================");
								cacheCanvas = new Canvas(cachebBitmap);
							cacheCanvas.drawColor(Color.WHITE);
							Rect rect = cacheCanvas.getClipBounds();
							Paint paint = new Paint();
							//设置边框颜色
							paint.setColor(Color.WHITE);
						LogUtil.showLog("setColor==WHITE");
//							paint.setColor(Color.parseColor("#dddddd"));
							paint.setStyle(Paint.Style.STROKE);
							paint.setStrokeWidth(1);
							cacheCanvas.drawRect(rect, paint);
							return true;
					} 
			}); 
		}

		public void clear() {
			if (cacheCanvas != null) {
				paint.setColor(BACKGROUND_COLOR);
				cacheCanvas.drawPaint(paint);
				paint.setColor(Color.BLACK);
				cacheCanvas.drawColor(Color.WHITE);
				invalidate();
			}
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			// canvas.drawColor(BRUSH_COLOR);			
			if(iscandraw){
				canvas.drawBitmap(cachebBitmap, 0, 0, null);
				canvas.drawPath(path, paint);
			}
		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			if(iscandraw){
				int curW = cachebBitmap != null ? cachebBitmap.getWidth() : 0;
				int curH = cachebBitmap != null ? cachebBitmap.getHeight() : 0;
				if (curW >= w && curH >= h) {
					return;
				}

				if (curW < w) {
                    curW = w;
                }
				if (curH < h) {
                    curH = h;
                }

				Bitmap newBitmap = Bitmap.createBitmap(curW, curH,
						Config.ARGB_8888);
				Canvas newCanvas = new Canvas();
				newCanvas.setBitmap(newBitmap);
				if (cachebBitmap != null) {
					newCanvas.drawBitmap(cachebBitmap, 0, 0, null);
				}
				cachebBitmap = newBitmap;
				cacheCanvas = newCanvas;
			}	
		}

		private float cur_x, cur_y;

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			if(iscandraw){
				float x = event.getX();
				float y = event.getY();
				
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN: {
						cur_x = x;
						cur_y = y;
						path.moveTo(cur_x, cur_y);
						break;
					}
	
					case MotionEvent.ACTION_MOVE: {
						path.quadTo(cur_x, cur_y, x, y);
						cur_x = x;
						cur_y = y;
						if(iscandraw){
							setSigned(true);
						}
						break;
					}
	
					case MotionEvent.ACTION_UP: {
						cacheCanvas.drawPath(path, paint);
						path.reset();
						break;
					}
				}
				invalidate();
			}
			return true;
		}

		public boolean isIscandraw() {
			return iscandraw;
		}

		public void setIscandraw(boolean iscandraw) {
			this.iscandraw = iscandraw;
		}
	}
	
}
