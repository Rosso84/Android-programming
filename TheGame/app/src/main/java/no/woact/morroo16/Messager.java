package no.woact.morroo16;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/* By Roosbeh Morandi*/


public class Messager {


    /*resource: https://developer.android.com/guide/topics/ui/dialogs*/
    public void showMessageDialog(Context contex, String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(contex);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }


    public void toastMessage(Context context,  String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        //resources:
        //https://www.mkyong.com/android/android-toast-example/
    }

}
