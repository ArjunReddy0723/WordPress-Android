package org.wordpress.android.ui.accounts.login;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import org.wordpress.android.R;
import org.wordpress.android.WordPress;
import org.wordpress.android.fluxc.store.AccountStore;
import org.wordpress.android.fluxc.store.SiteStore;
import org.wordpress.android.ui.accounts.LoginMode;
import org.wordpress.android.util.HelpshiftHelper;

import javax.inject.Inject;

public class LoginSiteAddressHelpDialogFragment extends DialogFragment {
    public static final String TAG = "login_site_address_help_dialog_fragment_tag";

    private LoginListener mLoginListener;

    @Inject SiteStore mSiteStore;
    @Inject AccountStore mAccountStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((WordPress) getActivity().getApplication()).component().inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginListener) {
            mLoginListener = (LoginListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement LoginListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(R.string.login_site_address_help_title);

        alert.setView(getActivity().getLayoutInflater().inflate(R.layout.login_alert_site_address_help, null));
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        alert.setNeutralButton(R.string.login_site_address_more_help, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HelpshiftHelper.getInstance().showConversation(getActivity(), mSiteStore,
                        HelpshiftHelper.chooseHelpshiftLoginTag(
                                mLoginListener.getLoginMode() == LoginMode.JETPACK_STATS, true),
                        mAccountStore.getAccount().getUserName());
            }
        });

        return alert.create();
    }
}
