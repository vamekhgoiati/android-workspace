package com.example.modernartui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ModernArtUI extends Activity {

	private DialogFragment mDialog;
	private RectFragment mRectFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modern_art_ui);

		mRectFragment = new RectFragment();

		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.add(R.id.rect_fragment_container, mRectFragment);
		transaction.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, R.string.info_item);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case Menu.FIRST:
			
			mDialog = new AlertDialogFragment().newInstance();
			mDialog.show(getFragmentManager(), "Alert");
			
			return true;
		default:
			super.onOptionsItemSelected(item);
			return true;
		}

	}

	// Class that creates the AlertDialog
	public static class AlertDialogFragment extends DialogFragment {

		public static AlertDialogFragment newInstance() {
			return new AlertDialogFragment();
		}

		// Build AlertDialog using AlertDialog.Builder
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return new AlertDialog.Builder(getActivity())
					.setTitle(R.string.dialog_title)
					.setMessage(R.string.dialog_message)
					.setCancelable(false)
					.setNegativeButton(R.string.dialog_not_now,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dismiss();
								}
							})
					.setPositiveButton(R.string.dialog_visit_moma,
							new DialogInterface.OnClickListener() {
								public void onClick(final DialogInterface dialog, int id) {
									Uri uri = Uri.parse("http://www.moma.org");
									Intent intent = new Intent(Intent.ACTION_VIEW, uri);
									startActivity(intent);
								}
							}).create();
		}
	}
}
