package chnu.practice.movieadvicer.ui.fragments;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import chnu.practice.movieadvicer.R;


public class BaseFragment extends Fragment {
    private ProgressDialog progressDialog;

    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setIndeterminate(true);
        }
        progressDialog.show();
    }

    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}
