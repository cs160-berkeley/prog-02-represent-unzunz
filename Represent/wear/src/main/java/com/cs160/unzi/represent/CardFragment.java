//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cs160.unzi.represent;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.wearable.R.id;
import android.support.wearable.R.layout;
import android.support.wearable.view.CardFrame;
import android.support.wearable.view.CardScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

@TargetApi(20)
public class CardFragment extends Fragment {
    private static final String CONTENT_SAVED_STATE = "CardScrollView_content";
    public static final int EXPAND_UP = -1;
    public static final int EXPAND_DOWN = 1;
    public static final String KEY_TITLE = "CardFragment_title";
    public static final String KEY_TEXT = "CardFragment_text";
    public static final String KEY_ICON_RESOURCE = "CardFragment_icon";
    private CardFrame mCard;
    private CardScrollView mCardScroll;
    private int mCardGravity = 80;
    private boolean mExpansionEnabled = true;
    private float mExpansionFactor = 10.0F;
    private int mExpansionDirection = 1;
    private boolean mScrollToTop;
    private boolean mScrollToBottom;
    private final Rect mCardMargins = new Rect(-1, -1, -1, -1);
    private Rect mCardPadding;
    private boolean mActivityCreated;

    public CardFragment() {
    }

    public static CardFragment create(CharSequence title, CharSequence description) {
        return create(title, description, 0);
    }

    public static CardFragment create(CharSequence title, CharSequence text, int iconRes) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        if(title != null) {
            args.putCharSequence("CardFragment_title", title);
        }

        if(text != null) {
            args.putCharSequence("CardFragment_text", text);
        }

        if(iconRes != 0) {
            args.putInt("CardFragment_icon", iconRes);
        }

        fragment.setArguments(args);
        return fragment;
    }

    public void setExpansionEnabled(boolean enabled) {
        this.mExpansionEnabled = enabled;
        if(this.mCard != null) {
            this.mCard.setExpansionEnabled(this.mExpansionEnabled);
        }

    }

    public void setExpansionDirection(int direction) {
        this.mExpansionDirection = direction;
        if(this.mCard != null) {
            this.mCard.setExpansionDirection(this.mExpansionDirection);
        }

    }

    public void setCardGravity(int gravity) {
        this.mCardGravity = gravity & 112;
        if(this.mActivityCreated) {
            this.applyCardGravity();
        }

    }

    private void applyCardGravity() {
        LayoutParams lp = (LayoutParams)this.mCard.getLayoutParams();
        lp.gravity = this.mCardGravity;
        this.mCard.setLayoutParams(lp);
    }

    public void setCardMargins(int left, int top, int right, int bottom) {
        this.mCardMargins.set(left, top, right, bottom);
        if(this.mActivityCreated) {
            this.applyCardMargins();
        }

    }

    public void setCardMarginTop(int top) {
        this.mCardMargins.top = top;
        if(this.mActivityCreated) {
            this.applyCardMargins();
        }

    }

    public void setCardMarginLeft(int left) {
        this.mCardMargins.left = left;
        if(this.mActivityCreated) {
            this.applyCardMargins();
        }

    }

    public void setCardMarginRight(int right) {
        this.mCardMargins.right = right;
        if(this.mActivityCreated) {
            this.applyCardMargins();
        }

    }

    public void setCardMarginBottom(int bottom) {
        this.mCardMargins.bottom = bottom;
        if(this.mActivityCreated) {
            this.applyCardMargins();
        }

    }

    private void applyCardMargins() {
        MarginLayoutParams lp = (MarginLayoutParams)this.mCard.getLayoutParams();
        if(this.mCardMargins.left != -1) {
            lp.leftMargin = this.mCardMargins.left;
        }

        if(this.mCardMargins.top != -1) {
            lp.topMargin = this.mCardMargins.top;
        }

        if(this.mCardMargins.right != -1) {
            lp.rightMargin = this.mCardMargins.right;
        }

        if(this.mCardMargins.bottom != -1) {
            lp.bottomMargin = this.mCardMargins.bottom;
        }

        this.mCard.setLayoutParams(lp);
    }

    private void applyPadding() {
        if(this.mCard != null) {
            this.mCard.setContentPadding(this.mCardPadding.left, this.mCardPadding.top, this.mCardPadding.right, this.mCardPadding.bottom);
        }

    }

    public void setContentPadding(int left, int top, int right, int bottom) {
        this.mCardPadding = new Rect(left, top, right, bottom);
        this.applyPadding();
    }

    public Rect getContentPadding() {
        return new Rect(this.mCardPadding);
    }

    public void setContentPaddingLeft(int leftPadding) {
        this.mCardPadding.left = leftPadding;
        this.applyPadding();
    }

    public int getContentPaddingLeft() {
        return this.mCardPadding.left;
    }

    public void setContentPaddingTop(int topPadding) {
        this.mCardPadding.top = topPadding;
        this.applyPadding();
    }

    public int getContentPaddingTop() {
        return this.mCardPadding.top;
    }

    public void setContentPaddingRight(int rightPadding) {
        this.mCardPadding.right = rightPadding;
        this.applyPadding();
    }

    public int getContentPaddingRight() {
        return this.mCardPadding.right;
    }

    public void setContentPaddingBottom(int bottomPadding) {
        this.mCardPadding.bottom = bottomPadding;
        this.applyPadding();
    }

    public int getContentPaddingBottom() {
        return this.mCardPadding.bottom;
    }

    public void setExpansionFactor(float factor) {
        this.mExpansionFactor = factor;
        if(this.mCard != null) {
            this.mCard.setExpansionFactor(factor);
        }

    }

    public void scrollToTop() {
        if(this.mCardScroll != null) {
            this.mCardScroll.scrollBy(0, this.mCardScroll.getAvailableScrollDelta(-1));
        } else {
            this.mScrollToTop = true;
            this.mScrollToBottom = false;
        }

    }

    public void scrollToBottom() {
        if(this.mCardScroll != null) {
            this.mCardScroll.scrollBy(0, this.mCardScroll.getAvailableScrollDelta(1));
        } else {
            this.mScrollToTop = true;
            this.mScrollToBottom = false;
        }

    }

    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mCardScroll = new CardScrollView(inflater.getContext());
        this.mCardScroll.setLayoutParams(new android.view.ViewGroup.LayoutParams(-1, -1));
        this.mCard = new CardFrame(inflater.getContext());
        this.mCard.setLayoutParams(new LayoutParams(-1, -2, this.mCardGravity));
        this.mCard.setExpansionEnabled(this.mExpansionEnabled);
        this.mCard.setExpansionFactor(this.mExpansionFactor);
        this.mCard.setExpansionDirection(this.mExpansionDirection);
        if(this.mCardPadding != null) {
            this.applyPadding();
        }

        this.mCardScroll.addView(this.mCard);
        if(this.mScrollToTop || this.mScrollToBottom) {
            this.mCardScroll.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    CardFragment.this.mCardScroll.removeOnLayoutChangeListener(this);
                    if(CardFragment.this.mScrollToTop) {
                        CardFragment.this.mScrollToTop = false;
                        CardFragment.this.scrollToTop();
                    } else if(CardFragment.this.mScrollToBottom) {
                        CardFragment.this.mScrollToBottom = false;
                        CardFragment.this.scrollToBottom();
                    }

                }
            });
        }

        Bundle contentSavedState = null;
        if(savedInstanceState != null) {
            contentSavedState = savedInstanceState.getBundle("CardScrollView_content");
        }

        View content = this.onCreateContentView(inflater, this.mCard, contentSavedState);
        if(content != null) {
            this.mCard.addView(content);
        }

        return this.mCardScroll;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mActivityCreated = true;
        this.applyCardMargins();
        this.applyCardGravity();
    }

    public void onDestroy() {
        this.mActivityCreated = false;
        super.onDestroy();
    }

    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(layout.watch_card_content, container, false);
        Bundle args = this.getArguments();
        if(args != null) {
            TextView title = (TextView)view.findViewById(id.title);
            if(args.containsKey("CardFragment_title") && title != null) {
                title.setText(args.getCharSequence("CardFragment_title"));
            }

            if(args.containsKey("CardFragment_text")) {
                TextView text = (TextView)view.findViewById(id.text);
                if(text != null) {
                    text.setText(args.getCharSequence("CardFragment_text"));
                }
            }

            if(args.containsKey("CardFragment_icon") && title != null) {
                title.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, args.getInt("CardFragment_icon"), 0);
            }
        }
        Log.i("HELLO SUCKERS", "HSDFLKJ");
        view.setOnClickListener(new RepOnClickListener(args.getCharSequence("CardFragment_title").toString()));

        return view;
    }

    public class RepOnClickListener implements View.OnClickListener {
        String rep_name;

        public RepOnClickListener(String name) {
            this.rep_name = name;
        }

        @Override
        public void onClick (View v) {
            Intent sendIntent = new Intent(getActivity(), WatchToPhoneService.class);
            sendIntent.putExtra("SELECTED_REP", rep_name);
            getActivity().startService(sendIntent);
            Log.i("TOUCHINGSLKFJ", "TOUHCHCHCH");
        }
    }
}
