package com.maliotis.transitionlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.transition.Scene;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.maliotis.transitionlayout.Fragments.TransitionFragment;
import com.maliotis.transitionlayout.Layouts.LayoutA;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        relativeLayout = new RelativeLayout(this);
        relativeLayout.setId(View.generateViewId());
        relativeLayout.setLayoutParams(lp);
        setContentView(relativeLayout);

        createFragment();
    }

    private void createFragment() {
        TransitionFragment fragment = TransitionFragment.newInstance("This","That");
        getSupportFragmentManager().beginTransaction()
                .add(relativeLayout.getId(), fragment).commit();
    }

}
