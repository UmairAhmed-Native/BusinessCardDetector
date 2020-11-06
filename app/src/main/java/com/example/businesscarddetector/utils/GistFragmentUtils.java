package com.example.businesscarddetector.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.businesscarddetector.R;


/**
 * Created by imran.pyarali on 8/12/2015.
 */
public class GistFragmentUtils {

    public static void switchFragmentAdd(final Activity activity, final Fragment fragment, final boolean isAddToStack, final boolean isAnimate) {

        try {
            if (activity != null && !activity.isFinishing() /*&& !fragment.getClass().getName().equalsIgnoreCase(getCurrentFragmentTag(activity))*/) {

                //GistUtils.hideSoftKey(activity);
                hideSoftKeyboard(activity);

                FragmentTransaction fragmentTransaction = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();

                if(isAnimate) {

                    /*fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                        R.anim.slide_in_left, R.anim.slide_out_right);*/

                    /*fragmentTransaction.setCustomAnimations(R.anim.open_translate, R.anim.close_scale,
                            R.anim.open_scale, R.anim.close_translate);*/

                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                }

                Fragment currentFragment = ((FragmentActivity)activity).getSupportFragmentManager().findFragmentById(R.id.container);

                if(currentFragment!= null) {

                    fragmentTransaction.hide(currentFragment);
                }

                Log.e("FragmentName",fragment.getClass().getName());

                if(isAddToStack){

                    fragmentTransaction.addToBackStack(fragment.getClass().getName());
                }

                fragmentTransaction.add(R.id.container, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
                //fragmentTransaction.replace(R.id.main_content, fragment, fragment.getClass().getName()).commit();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void switchFragmentReplace(final Activity activity, final Fragment fragment, final boolean isAddToStack, final boolean isAnimate) {

        switchFragmentReplace(activity, fragment, isAddToStack,isAnimate,R.id.container);
    }

    public static void switchFragmentReplace(final Activity activity, final Fragment fragment, final boolean isAddToStack, final boolean isAnimate, int containerId) {

        if (activity != null && !fragment.getClass().getName().equalsIgnoreCase(getCurrentFragmentTag(activity))) {

            //GistUtils.hideSoftKey(activity);
            hideSoftKeyboard(activity);

            FragmentTransaction fragmentTransaction = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();

            if(isAnimate) {

                /*fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                        R.anim.slide_in_left, R.anim.slide_out_right);*/

                /*fragmentTransaction.setCustomAnimations(R.anim.open_translate, R.anim.close_scale,
                        R.anim.open_scale, R.anim.close_translate);*/

                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            }

            if(isAddToStack){

                fragmentTransaction.addToBackStack(fragment.getClass().getName());
            }

            //fragmentTransaction.add(R.id.main_content, fragment, fragment.getClass().getName()).commit();
            fragmentTransaction.replace(containerId, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
        }
    }

    public static void switchFragmentTransition(Activity activity, Fragment startFragement, Fragment endFragment,
                                                View transitionImageView, String transition_name, boolean isAddToStack,
                                                boolean isAnimate) {

        //GistUtils.hideSoftKey(activity);
        hideSoftKeyboard(activity);

        // if device os is lollipop implement transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            // set transitions
            /*startFragement.setSharedElementReturnTransition(TransitionInflater.from(
                    activity).inflateTransition(R.transition.change_image_trans));
            startFragement.setExitTransition(TransitionInflater.from(
                    activity).inflateTransition(android.R.transition.fade));*/

//            endFragment.setSharedElementEnterTransition(TransitionInflater.from(
//                    activity).inflateTransition(R.transition.change_image_trans));

            /*endFragment.setEnterTransition(TransitionInflater.from(
                    activity).inflateTransition(android.R.transition.fade));*/

            // send bunddle string  of transition_name in endfragment
            /*Bundle bundle = new Bundle();
            bundle.putString(GistConstants.TRANSITION_NAME, transition_name);
            endFragment.setArguments(bundle);*/

            FragmentTransaction fragmentTransaction =  ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();

            Fragment currentFragment = ((FragmentActivity)activity).getSupportFragmentManager().findFragmentById(R.id.container);

            if(currentFragment!= null) {

                fragmentTransaction.hide(currentFragment);
            }

            if(isAddToStack){
                fragmentTransaction.addToBackStack(endFragment.getClass().getName());
            }

            fragmentTransaction.addSharedElement(transitionImageView, transition_name);

            fragmentTransaction.replace(R.id.container, endFragment, endFragment.getClass().getName());
            //fragmentTransaction.add(contentId, endFragment,endFragment.getClass().getName());
          //  fragmentTransaction.replace(R.id.main_content, endFragment);

            fragmentTransaction.commitAllowingStateLoss();

        } else {

            switchFragmentAdd(activity, endFragment, isAddToStack, isAnimate);
        }
    }


    public static void removeFragment(Activity activity, Fragment fragment) {
        if (activity != null) {

            //GistUtils.hideSoftKey(activity);
            hideSoftKeyboard(activity);

            FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commitAllowingStateLoss();
            manager.popBackStack();
        }
    }

    public static void removeFragmentt(Activity activity, Fragment fragment, ImageView transitionImageView, String transition_name) {
        if (activity != null) {

            //GistUtils.hideSoftKey(activity);
            hideSoftKeyboard(activity);

            FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();

           /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                // set transitions
                fragment.setSharedElementReturnTransition(TransitionInflater.from(
                        activity).inflateTransition(R.transition.change_image_trans));
                fragment.setExitTransition(TransitionInflater.from(
                        activity).inflateTransition(android.R.transition.fade));

                endFragment.setSharedElementEnterTransition(TransitionInflater.from(
                        activity).inflateTransition(R.transition.change_image_trans));
                endFragment.setEnterTransition(TransitionInflater.from(
                        activity).inflateTransition(android.R.transition.fade));

                // send bunddle string  of transition_name in endfragment
                Bundle bundle = new Bundle();
                bundle.putString(GistConstants.TRANSITION_NAME, transition_name);
                endFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction =  ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();

                Fragment currentFragment = ((FragmentActivity)activity).getSupportFragmentManager().findFragmentById(R.id.main_content);

                if(currentFragment!= null) {

                    fragmentTransaction.hide(currentFragment);
                }

                if(isAddToStack){
                    fragmentTransaction.addToBackStack(endFragment.getClass().getName());
                }

                fragmentTransaction.addSharedElement(transitionImageView, transition_name);

                //fragmentTransaction.add(R.id.main_content, fragment, fragment.getClass().getName()).commit();
                fragmentTransaction.add(R.id.main_content, endFragment,endFragment.getClass().getName());
                //  fragmentTransaction.replace(R.id.main_content, endFragment);

                fragmentTransaction.commit();

            }*/

            fragmentTransaction.remove(fragment);
            fragmentTransaction.commitAllowingStateLoss();
            manager.popBackStack();
        }
    }

    public static void homeFragment(Activity activity) {

        if (activity != null) {

            FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
            if (manager.getBackStackEntryCount() > 0) {
                FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
                manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }

    public static int getStackCount(Activity activity) {

        FragmentManager fragmentManager = ((AppCompatActivity)activity).getSupportFragmentManager();
        return fragmentManager.getBackStackEntryCount();
    }

    public static Fragment getCurrentFragment(Activity activity) {

        Fragment currentFragment = null;
        if (activity != null) {

            FragmentManager fragmentManager = ((AppCompatActivity)activity).getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {

                String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
                currentFragment = ((AppCompatActivity)activity).getSupportFragmentManager().findFragmentByTag(fragmentTag);

            } else {

                currentFragment = ((AppCompatActivity)activity).getSupportFragmentManager().findFragmentById(R.id.container);
            }
        }

        return currentFragment;
    }

    public static String getCurrentFragmentTag(Activity activity) {

        String currentTag = "";
        if (activity != null) {

            FragmentManager fragmentManager = ((AppCompatActivity)activity).getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {

                String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
                Fragment currentFragment = ((AppCompatActivity)activity).getSupportFragmentManager().findFragmentByTag(fragmentTag);
                if( currentFragment != null ){
                    currentTag = currentFragment.getTag();
                }

            } else {

                Fragment currentFragment = fragmentManager.findFragmentById(R.id.container);
                if (currentFragment != null) {
                    currentTag = currentFragment.getTag();
                }
            }
        }

        return currentTag;
    }

    public static void onBackPressed(Activity activity) {

        if (activity != null) {

            //GistUtils.hideSoftKey(activity);
            hideSoftKeyboard(activity);
            activity.onBackPressed();
        }
    }

    /**
     * Hide soft keyboard.
     *
     * @param activity the activity
     */
    public static void hideSoftKeyboard(Activity activity) {

        if( activity != null ) {
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    /**
     * Navigate To Specific Fragment which exist in stack
     *
     * @param activity Activity
     * @param fragmentClassName Fragment class Name should be the name of fragment
     *                          which exist after the targeted fragment
     */
    public static void navigateToFragment(Activity activity, String fragmentClassName){

        FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
        manager.popBackStack(fragmentClassName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
