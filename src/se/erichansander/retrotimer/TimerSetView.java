package se.erichansander.retrotimer;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class TimerSetView extends TimerView {

	private static final String DEBUG_TAG = "TimerSetView";

	private TimerSetListener mListener;
	private GestureDetector mGestures;

	private boolean mBeingChanged = false;
	private long mMillisLeftBefore = 0;

	public interface TimerSetListener {
		abstract void onTimerTempValue(long millis);
		abstract void onTimerSetValue(long millis);
	}

	public TimerSetView (Context context) {
		super(context);

		mGestures = new GestureDetector(context,
				new TimerGestureListener(this));
		mGestures.setIsLongpressEnabled(false);
	}

    public void setTimerSetListener(TimerSetListener listener) {
        mListener = listener;
    }

	private void onTurn(float dx) {
		final float scale = 
			getContext().getResources().getDisplayMetrics().density;

		float w = this.getWidth()*scale;
		dx *= scale;

//		Log.d(DEBUG_TAG, "onTurn(dx=" + dx + "), mBeingChanged=" + mBeingChanged);
//		Log.d(DEBUG_TAG, "getWidth()=" + w);

		if (mBeingChanged == false) {
			mMillisLeftBefore = mMillisLeft;
			mBeingChanged = true;
		}

		mMillisLeft = mMillisLeftBefore +
				Math.round((-dx / w) * 20f * 60000f);

		// Round to the closest full minute
		mMillisLeft = Math.round((float) mMillisLeft / 60000f)*60000;

		if (mMillisLeft <= 0) {
    		mMillisLeft = 0;
    	} else if (mMillisLeft > TIMER_MAX_MINS*60000) {
    		mMillisLeft = TIMER_MAX_MINS*60000;
    	}
		
		mListener.onTimerTempValue(mMillisLeft);
	}
	
	private void onSet() {
		mListener.onTimerSetValue(mMillisLeft);
		mBeingChanged = false;
		mMillisLeftBefore = 0;
	}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	boolean retVal = mGestures.onTouchEvent(event);
    	
    	int action = event.getAction();
        if (action == MotionEvent.ACTION_UP ||
        		action == MotionEvent.ACTION_CANCEL) {
            // Helper method to detect when scrolling is finished
            onSet();
            retVal = true;
        }

        return retVal;
    }

	private class TimerGestureListener 
			extends GestureDetector.SimpleOnGestureListener {

		private TimerSetView mView;
		
		public TimerGestureListener (TimerSetView view) {
			this.mView = view;
		}
		
//		For some reason, this method must return true for onScroll to be called?!
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2,
				final float velocityX, final float velocityY) {
//			Log.d(DEBUG_TAG, "onFling");
//			TODO: call callback for flinging
			return true;
		}

		@Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                float distanceX, float distanceY) {
//			Log.d(DEBUG_TAG, "onScroll");
			mView.onTurn(e2.getX() - e1.getX());
            return true;
		}
	}
}
