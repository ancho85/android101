package com.example.blockdenotas;

import android.content.Context;
import android.content.Intent;

/**
 * Share a content using the user's installed apps
 *
 * Thanks to : http://labs.emich.be/2010/01/23/how-to-send-to-twitter-or-facebook-from-your-android-application/
 *
 * @author http://francho.org/lab/
 *
 */
public class Social {
    /**
     * Open a contextual Menu with the available applications to share
     * 
     * @param the
     *            Context (to open the menu and the new activity)
     * @param the
     *            subject
     * @param the
     *            text
     */
    public static void share(Context ctx, String subject,String text) {
        final Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_SUBJECT, text);
        intent.putExtra(Intent.EXTRA_TEXT, subject + ": " + text); //algunas aplicaciones no mmuestran el Subject

        ctx.startActivity(Intent.createChooser(intent, ctx.getString(R.string.tit_share)));
    }
}