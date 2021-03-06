/*
 * Copyright (C) 2010-2011  Eric Hansander
 *
 *  This file is part of Retro Timer.
 *
 *  Retro Timer is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Retro Timer is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Retro Timer.  If not, see <http://www.gnu.org/licenses/>.
 */

package se.erichansander.retrotimer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

/** Special view for displaying the timer, and handling touch events. */
public class TimerAlertView extends TimerView implements OnClickListener {

	private TimerAlertListener mListener = null;

	public interface TimerAlertListener {
		abstract void onAlertDismissed();
	}

	public TimerAlertView (Context context, AttributeSet attrs) {
		super(context, attrs);

		this.setOnClickListener(this);
	}

    public void setTimerAlertListener(TimerAlertListener listener) {
        mListener = listener;
    }

    public void onClick(View v) {
    	mListener.onAlertDismissed();
    }
}
