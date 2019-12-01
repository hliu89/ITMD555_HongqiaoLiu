package com.xk.CarRenting.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xk.CarRenting.R;
import com.xk.CarRenting.frontend.tools.MaterialProgressDrawable;


public class ViewUtils {

    public static <T extends View> T findViewById(@NonNull View v, @IdRes int id) {
        return (T) v.findViewById(id);
    }

    public static <T extends View> T findViewById(@NonNull Activity activity, @IdRes int id) {
        return (T) activity.findViewById(id);
    }

    public static <T extends View> T findViewById(@NonNull Dialog dialog, @IdRes int id) {
        return (T) dialog.findViewById(id);
    }

    public static int dip2px(@NonNull Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * ,
     *
     *
     * @param context         Dialog
     * @param message        ,
     * @param cancelable
     * @param cancelListener  Listener,
     * @return  AlertDialog
     */
    public static AlertDialog makeLoadingDialog(@NonNull Context context, CharSequence message, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppTheme_AlertDialog);

        View v = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);

        // Message which show in this dialog.
        TextView mMessage = ViewUtils.findViewById(v, R.id.tv_message);
        mMessage.setText(message);

        // Container of material progress bar.
        ImageView container = ViewUtils.findViewById(v, R.id.iv_progress_bar_container);

        // Setup material progress bar.
        MaterialProgressDrawable mProgress;
        mProgress = new MaterialProgressDrawable(context, container);
        mProgress.setBackgroundColor(Color.WHITE);
        mProgress.setColorSchemeColors(v.getResources().getColor(R.color.colorPrimary));
        mProgress.updateSizes(MaterialProgressDrawable.LARGE);
        mProgress.showArrow(false);
        mProgress.setAlpha(255);
        mProgress.start();

        container.setImageDrawable(mProgress);

        builder.setView(v);

        builder.setCancelable(cancelable);
        builder.setOnCancelListener(cancelListener);

        return builder.create();
    }

    /**
     * ,
     *
     *
     * @param context           Dialog
     * @param title            ,
     * @param message          ,
     * @param positiveListener ，
     * @param negativeListener ，
     * @return  AlertDialog
     */
    public static AlertDialog makeConfirmAlertDialog(
            @NonNull Context context,
            CharSequence title,
            CharSequence message,
            DialogInterface.OnClickListener positiveListener,
            DialogInterface.OnClickListener negativeListener) {

        return makeConfirmAlertDialog(context, title, message, positiveListener, negativeListener, null);
    }

    /**
     * ,
     *
     *
     * @param context           Dialog
     * @param title            ,
     * @param message          ,
     * @param positiveListener ，
     * @param negativeListener ，
     * @param cancelListener   ，
     * @return  AlertDialog
     */
    public static AlertDialog makeConfirmAlertDialog(
            @NonNull Context context,
            CharSequence title,
            CharSequence message,
            DialogInterface.OnClickListener positiveListener,
            DialogInterface.OnClickListener negativeListener,
            DialogInterface.OnCancelListener cancelListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setPositiveButton("ok", positiveListener);
        builder.setNegativeButton("cancel", negativeListener);

        if (null != title)
            builder.setTitle(title);

        if (null != message) {
            builder.setMessage(message);
        }

        if (null != cancelListener) {
            builder.setOnCancelListener(cancelListener);
        }

        return builder.create();
    }

    /**
     * TODO:
     * Sets ListView height dynamically based on the height of the items.
     *
     * @param listView to be resized
     * @return true if the listView is successfully resized, false otherwise
     */
    public static boolean setListViewHeightBasedOnItems(@NonNull ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);

            return true;
        } else {
            return false;
        }
    }

    /**
     *
     *
     * @param text
     * @return  SpannableString
     */
    public static SpannableString setUnderLine(CharSequence text) {

        SpannableString spannableString = new SpannableString(text);

        spannableString.setSpan(new UnderlineSpan(), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /**
     *  TextView
     *
     * @param textView  TextView
     */
    public static void setUnderLine(TextView textView) {
        textView.setText(setUnderLine(textView.getText()));
    }
}
