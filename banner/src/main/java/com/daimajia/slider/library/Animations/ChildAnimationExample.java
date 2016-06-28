package com.daimajia.slider.library.Animations;

import android.view.View;

import com.daimajia.androidanimations.library.attention.StandUpAnimator;
import com.daimajia.slider.library.R;

/**
 * @author xiaocoder on 2016/6/9.
 * @modifier xiaocoder 2016/6/9 21:44.
 * @description
 */
public class ChildAnimationExample implements BaseAnimationInterface {

    private final static String TAG = "ChildAnimationExample";

    @Override
    public void onPrepareCurrentItemLeaveScreen(View current) {
        View descriptionLayout = current.findViewById(R.id.description_layout);
        if (descriptionLayout != null) {
            current.findViewById(R.id.description_layout).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onPrepareNextItemShowInScreen(View next) {
        View descriptionLayout = next.findViewById(R.id.description_layout);
        if (descriptionLayout != null) {
            next.findViewById(R.id.description_layout).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onCurrentItemDisappear(View view) {
    }

    @Override
    public void onNextItemAppear(View view) {

        View descriptionLayout = view.findViewById(R.id.description_layout);
        if (descriptionLayout != null) {
            view.findViewById(R.id.description_layout).setVisibility(View.VISIBLE);
//            ValueAnimator animator = ObjectAnimator.ofFloat(
//                    descriptionLayout, "y", -descriptionLayout.getHeight(),
//                    0).setDuration(500);
//            animator.start();
//            new BounceInAnimator().animate(descriptionLayout);
            new StandUpAnimator().animate(descriptionLayout);
        }
    }
}