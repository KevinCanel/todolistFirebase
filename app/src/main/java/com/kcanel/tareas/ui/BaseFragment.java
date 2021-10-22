package com.kcanel.tareas.ui;

import android.content.Context;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.FirebaseDatabase;

public class BaseFragment extends Fragment {
    public OnFragmentInteractionListener mListener;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    public interface OnFragmentInteractionListener {
        boolean isConnected();
    }

    public boolean isConnected() {
        if(isAdded()){
            return mListener.isConnected();
        }else{
            return false;
        }
    }

}
