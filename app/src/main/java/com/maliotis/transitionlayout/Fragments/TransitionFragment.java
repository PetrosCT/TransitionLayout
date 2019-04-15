package com.maliotis.transitionlayout.Fragments;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TransitionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TransitionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransitionFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RelativeLayout mainLayout;
    private RelativeLayout mLayoutA;
    private RelativeLayout mLayoutB;
    private HashMap<String, Integer> viewIds;
    private int widthOfDevice;
    private int heightOfDevice;

    private OnFragmentInteractionListener mListener;

    public TransitionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransitionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransitionFragment newInstance(String param1, String param2) {
        TransitionFragment fragment = new TransitionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        heightOfDevice = displayMetrics.heightPixels;
        widthOfDevice = displayMetrics.widthPixels;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewIds = new HashMap<>();
        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mainLayout = new RelativeLayout(getActivity());
        mainLayout.setLayoutParams(lp);
        mLayoutA = createLayoutA();
        mLayoutB = createLayoutB();
        mainLayout.addView(mLayoutA);
        return mainLayout;
    }

    public RelativeLayout createLayoutA() {
        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout relativeLayout;

        relativeLayout= new RelativeLayout(getActivity());
        relativeLayout.setLayoutParams(lp);

        Button nextScene = createButtonNext();
        relativeLayout.addView(nextScene);

        return relativeLayout;
    }

    private Button createButtonNext() {
        Button button = new Button(getActivity());
        button.setText("Next Scene");
        int id = View.generateViewId();
        viewIds.put("next", id);
        button.setId(id);
        button.setOnClickListener(this);
        return button;
    }

    private RelativeLayout createLayoutB() {
        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);



        mLayoutB = new RelativeLayout(getActivity());
        mLayoutB.setLayoutParams(lp);
        mLayoutB.setX(widthOfDevice);
        mLayoutB.setBackgroundColor(Color.WHITE);
        mLayoutB.setElevation(14f);
        Button button = createButtonPrevious();
        mLayoutB.addView(button);

        return mLayoutB;
    }

    private Button createButtonPrevious() {
        Button button = new Button(getActivity());
        button.setText("Previous Scene");
        int id = View.generateViewId();
        viewIds.put("previous", id);
        button.setId(id);
        button.setOnClickListener(this);
        return button;
    }


    @Override
    public void onClick(View view) {
        for (Map.Entry map: viewIds.entrySet()) {
            if ( (int) map.getValue() == view.getId()) {
                switch ((String) map.getKey()) {
                    case "next":
                        changeLayouts(mLayoutB, "in");
                        break;
                    case "previous":
                        changeLayouts(mLayoutB, "out");
                        break;
                    default:
                        break;
                }
            }
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void changeLayouts(final RelativeLayout nextLayout, String id) {

        if (id.equals("in")) {
            mainLayout.addView(nextLayout);
            animateLayout(nextLayout, 0);

        } else if (id.equals("out")){
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            animateLayout(nextLayout, width);

            Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    mainLayout.removeView(nextLayout);
                }
            };
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(runnable);
                }
            },500);

        }
    }

    private void animateLayout(RelativeLayout nextLayout, int width) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(nextLayout, "x", width);
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
