package com.android.wool.view.constom;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.android.wool.R;
import java.util.ArrayList;
import java.util.List;
/**
 * @author J
 *一个自定义view 实现a-z的竖直绘制，和监听滑动事件
 */
public class SideBar extends View {
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	private List<String> mList = new ArrayList<>();
	private int choose = -1;
	private Paint paint = new Paint();

	private TextView mTextDialog;

	public void setTextView(TextView mTextDialog) {
		this.mTextDialog = mTextDialog;
	}

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SideBar(Context context) {
		super(context);
	}

	public void setData(List<String> list){
		if(list != null && list.size()>0){
			mList.addAll(list);
			invalidate();
		}
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int height = getHeight();
		int width = getWidth();
		if(mList.size() > 0) {
			int singleHeight = height / mList.size();

			for (int i = 0; i < mList.size(); i++) {
				paint.setColor(getContext().getResources().getColor(R.color.edit_phone));
				paint.setAntiAlias(true);
				paint.setTextSize(14 * getContext().getResources().getDisplayMetrics().density);
				if (i == choose) {
					paint.setColor(getContext().getResources().getColor(R.color.edit_phone));
					paint.setFakeBoldText(true);
				}
				float xPos = width / 2 - paint.measureText(mList.get(i)) / 2;
				float yPos = singleHeight * i + singleHeight;
				canvas.drawText(mList.get(i), xPos, yPos, paint);
				paint.reset();
			}
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * mList.size());

		switch (action) {
			case MotionEvent.ACTION_UP:
				setBackgroundDrawable(new ColorDrawable(0x00000000));
				choose = -1;//
				invalidate();
				if (mTextDialog != null) {
					mTextDialog.setVisibility(View.INVISIBLE);
				}
				break;

			default:
				// setBackgroundResource(R.drawable.sidebar_background);
				if (oldChoose != c) {
					if (c >= 0 && c < mList.size()) {
						if (listener != null) {
							listener.onTouchingLetterChanged(mList.get(c));
						}
						if (mTextDialog != null) {
							mTextDialog.setText(mList.get(c));
							mTextDialog.setVisibility(View.VISIBLE);
						}

						choose = c;
						invalidate();
					}
				}

				break;
		}
		return true;
	}

	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}
}