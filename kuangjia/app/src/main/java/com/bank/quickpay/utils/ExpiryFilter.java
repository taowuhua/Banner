package com.bank.quickpay.utils;

/* ExpiryValidator.java
 * See the file "LICENSE.md" for the full license governing this code.
 */

import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

public class ExpiryFilter implements InputFilter {

	@Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {

		// Log.v(TAG, "filter( source:"+source
		// + " start:" + start
		// + " end:" + end
		// + " dest:" + dest
		// + " dstart:" + dstart
		// + " dend:" + dend);

		// do all in place edits

		SpannableStringBuilder result = new SpannableStringBuilder(source);

		if (dstart == 0 && result.length() > 0
				&& ('1' < result.charAt(0) && result.charAt(0) <= '9')) {
			result.insert(0, "0");
			end++;
		}

		int replen = dend - dstart;
		if (dstart - replen <= 2 && dstart + end - replen >= 2) {
			int loc = 2 - dstart;
			if (loc == end
					|| (0 <= loc && loc < end && result.charAt(loc) != '/')) {
				result.insert(loc, "/");
				end++;
			}
		}

		// look at what the updated string will be

		String updated = new SpannableStringBuilder(dest).replace(dstart, dend,
				result, start, end).toString();

		if (updated.length() >= 1) {
			if (updated.charAt(0) < '0' || '1' < updated.charAt(0)) {
                return "";
            }
		}

		if (updated.length() >= 2) {
			if (updated.charAt(0) != '0' && updated.charAt(1) > '2') {
                return "";
            }
			if (updated.charAt(0) == '0' && updated.charAt(1) == '0') {
                return "";
            }
		}

		if (updated.length() > 5) {
            return "";
        }

		return result;
	}

}
