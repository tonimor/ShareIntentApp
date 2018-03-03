package tonimor.vdkans.shareintentapp.share;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tonimor.vdkans.shareintentapp.R;

public class ShareHelper {
	Context context;
	String subject;
	String body;
	//Facebook facebook;

	public ShareHelper(Context context, String subject, String body) {
		this.context = context;
		this.subject = subject;
		this.body = body;
		//facebook = null;
	}
	
	//public Facebook share() {
	public void share() {
	Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
	sendIntent.setType("text/plain");
	//List activities = context.getPackageManager().queryIntentActivities(sendIntent, 0);
    List activities = getActivitiesList();
	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	builder.setTitle("Share with...");
	final ShareIntentListAdapter adapter = new ShareIntentListAdapter((Activity)context, R.layout.elemlist_of_my_listview, activities.toArray());
	builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			ResolveInfo info = (ResolveInfo) adapter.getItem(which);
			if(info.activityInfo.packageName.contains("facebook")) {
				//new PostToFacebookDialog(context, body).show();
				Toast.makeText(context, body, Toast.LENGTH_LONG).show();
			} else {
				Intent intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_SUBJECT, subject);
				intent.putExtra(Intent.EXTRA_TEXT, body);
				((Activity)context).startActivity(intent);
			}
		}
	});
	builder.create().show();
	//return facebook;
	}

    private ArrayList<ResolveInfo> getActivitiesList()
    {
        Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        ArrayList<ResolveInfo> list = (ArrayList<ResolveInfo>)context.getPackageManager().queryIntentActivities(sendIntent, 0);
        ArrayList<ResolveInfo> myList = new ArrayList<ResolveInfo>();
        for(ResolveInfo info : list) {

            String pkgName = info.activityInfo.packageName;

            if(pkgName.contains(".facebook") || pkgName.contains(".whatsapp") || pkgName.contains("android.gm"))
            {
                myList.add(info);
            }
            else
                continue;
        }
        return myList;
    }
}