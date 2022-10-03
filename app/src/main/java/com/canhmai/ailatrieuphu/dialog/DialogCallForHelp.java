package com.canhmai.ailatrieuphu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.canhmai.ailatrieuphu.App;
import com.canhmai.ailatrieuphu.MediaManager;
import com.canhmai.ailatrieuphu.R;
import com.canhmai.ailatrieuphu.databinding.DialogCallForHelpBinding;
import com.canhmai.ailatrieuphu.view.act.OnMainCallBack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DialogCallForHelp extends Dialog implements View.OnClickListener {

    private final DialogCallForHelpBinding binding;
    private final Context context;
    LinearLayout person1;
    TextView ans;
    LinearLayout person2;
    LinearLayout person3;
    LinearLayout person4;
    ImageView person1Photo;
    ImageView person2Photo;
    ImageView person3Photo;
    ImageView person4Photo;
    String textAns = "";
    TextView person1Name;
    TextView person2Name;
    TextView person3Name;
    TextView person4Name;

    View view;
    ImageView choosePerson;
    ImageView telephone;
    TextView choosePersonName;
    TextView calling;
    TextView myAnswerIs;
    Button close;
    private OnMainCallBack onMainCallBack;
    private String trueAnswer;
    private String tvAnswer;

    public DialogCallForHelp(@NonNull Context context) {
        super(context);
        this.context = context;
        this.onMainCallBack = onMainCallBack;
        binding = DialogCallForHelpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(context).inflate(R.layout.view_list_person, null);
        binding.frAddView.addView(view);

        person1 = view.findViewById(R.id.ln_person1);
        person2 = view.findViewById(R.id.ln_person2);
        person3 = view.findViewById(R.id.ln_person3);
        person4 = view.findViewById(R.id.ln_person4);

        person1Photo = person1.findViewById(R.id.iv_person1);
        person2Photo = person2.findViewById(R.id.iv_person2);
        person3Photo = person3.findViewById(R.id.iv_person3);
        person4Photo = person4.findViewById(R.id.iv_person4);

        person1Name = person1.findViewById(R.id.tv_person1_name);
        person2Name = person2.findViewById(R.id.tv_person2_name);
        person3Name = person3.findViewById(R.id.tv_person3_name);
        person4Name = person4.findViewById(R.id.tv_person4_name);

        person1.setOnClickListener(this);
        person2.setOnClickListener(this);
        person3.setOnClickListener(this);
        person4.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (person1.equals(v)) {
            setUICalling(person1, R.drawable.ic_ronaldo, person1Name);

        } else if (person2.equals(v)) {
            setUICalling(person2, R.drawable.ic_trump, person2Name);

        } else if (person3.equals(v)) {
            setUICalling(person3, R.drawable.ic_thuybatluc, person3Name);
        } else if (person4.equals(v)) {
            setUICalling(person4, R.drawable.ic_satthudatinh, person4Name);
        }

        anouceTrueAns();
    }

    private void anouceTrueAns() {
        App.getInstance().getMediaManager().playGameSound(MediaManager.KET_NOI_DIEN_THOAI, new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                calling.setText("Đã kết nối.....");
                calling.setTextColor(App.getInstance().getResources().getColor(R.color.yellow));
                telephone.setImageResource(R.drawable.oncall);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myAnswerIs.setVisibility(View.VISIBLE);
                        close.setClickable(true);
                        close.setVisibility(View.VISIBLE);
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dismiss();
                            }
                        });
                    }
                }, 1500);

            }
        });
    }

    private void setUICalling(LinearLayout personPhoto, int photoId, TextView personName) {

        binding.frAddView.removeAllViews();
        view = LayoutInflater.from(context).inflate(R.layout.view_callling_person, null);
        choosePerson = view.findViewById(R.id.iv_choosePerson);
        choosePersonName = view.findViewById(R.id.tv_choosePersonName);
        calling = view.findViewById(R.id.tv_onCall);
        myAnswerIs = view.findViewById(R.id.tv_ans_is);
        close = view.findViewById(R.id.bt_close2);
        telephone = view.findViewById(R.id.iv_telephone);
        close.setClickable(false);
        close.setVisibility(View.INVISIBLE);
        List<String> voicelist = new ArrayList<>();

        voicelist.add("Đáp án đúng là " + textAns);
        voicelist.add("Câu trả lời là " + textAns);
        voicelist.add(textAns + " là đáp án đúng");
        voicelist.add("Chọn " + textAns + " nhé");
        voicelist.add("Đáp án của em là " + textAns);
        voicelist.add("Chốt " + textAns);
        voicelist.add("Chọn " + textAns + " đi anh");
        voicelist.add("Chọn " + textAns + " đi em");

        Collections.shuffle(voicelist);

        myAnswerIs.setText(voicelist.get(0));
        myAnswerIs.setVisibility(View.INVISIBLE);

        calling.setText("Đang kết nối.....");
        binding.frAddView.addView(view);
        choosePerson.setImageResource(photoId);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.rotate_bg_circle_anim);
        animation.setDuration(20000);
        animation.setRepeatMode(Animation.INFINITE);
        animation.setRepeatCount(Animation.INFINITE);
        choosePerson.startAnimation(animation);
        choosePersonName.setText(personName.getText().toString());
    }

    public String setTrueAnswer(int trueAnswer) {

        if (trueAnswer == 1) {
            textAns = "A";
        } else if (trueAnswer == 2) {
            textAns = "B";
        } else if (trueAnswer == 3) {
            textAns = "C";
        } else {
            textAns = "D";
        }
        return textAns;
    }

    public void setClickable(boolean state) {
        if (!state) {
            person1.setClickable(false);
            person2.setClickable(false);
            person3.setClickable(false);
            person4.setClickable(false);
        } else {
            person1.setClickable(true);
            person2.setClickable(true);
            person3.setClickable(true);
            person4.setClickable(true);
        }
    }
}
