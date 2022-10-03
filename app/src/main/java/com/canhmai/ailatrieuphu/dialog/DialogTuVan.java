package com.canhmai.ailatrieuphu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.canhmai.ailatrieuphu.App;
import com.canhmai.ailatrieuphu.MediaManager;
import com.canhmai.ailatrieuphu.Person;
import com.canhmai.ailatrieuphu.R;
import com.canhmai.ailatrieuphu.databinding.DialogToTuVanBinding;
import com.canhmai.ailatrieuphu.view.act.OnMainCallBack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DialogTuVan extends Dialog implements View.OnClickListener {

    private final DialogToTuVanBinding binding;
    private final Context context;

    int trueCase = 0;


    List<Person> four_ans;
    List<Person> subList;
    Person personTrue;
    private OnMainCallBack onMainCallBack;
    private String textAns;


    public DialogTuVan(@NonNull Context context) {
        super(context);
        this.context = context;
        binding = DialogToTuVanBinding.inflate(getLayoutInflater());
        this.personTrue = new Person();
        this.subList = new ArrayList<>();
        setContentView(binding.getRoot());

        initViews();
    }

    public void setTrueCase(int trueCase) {
        this.trueCase = trueCase;
    }

    private void initViews() {
        App.getInstance().getStorage().personList = new ArrayList<>();
        Window window = getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        setCancelable(false);
        setCanceledOnTouchOutside(false);


        binding.btClose2.setOnClickListener(this);

    }

    public void add4Ans() {
        four_ans = new ArrayList<>();
        four_ans.add(new Person("A", (MediaManager.TRO_GIUP_1)));
        four_ans.add(new Person("B", (MediaManager.TRO_GIUP_2)));
        four_ans.add(new Person("C", (MediaManager.TRO_GIUP_3)));
        four_ans.add(new Person("D", (MediaManager.TRO_GIUP_4)));


    }


    @Override
    public void onClick(View v) {
        dismiss();
    }

    public void anouceTrueAns() {
        Collections.shuffle(four_ans);

        Log.e("AAAAAAAAA", "anouceTrueAns: " + four_ans.get(0).getAnsText() + ", " + four_ans.get(0).getAnsSound().length);
        Log.e("AAAAAAAAA", "anouceTrueAns2: " + personTrue.getAnsText() + ", " + personTrue.getAnsSound().length);
        for (int i = 0; i < four_ans.size(); i++) {
            if (!Objects.equals(four_ans.get(i).getAnsText(), personTrue.getAnsText())) {
                subList.add(personTrue);
                subList.add(four_ans.get(i));
                subList.add(personTrue);

                Collections.shuffle(subList);

                Person per0 = subList.get(0);
                Person per1 = subList.get(1);
                Person per2 = subList.get(2);

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                binding.ivPersion1.setBackgroundResource(R.color.yellow);
                App.getInstance().getMediaManager().playGameSound(per0.getAnsSound(), new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        App.getInstance().getMediaManager().playGameSound(MediaManager.PING,null);
                        binding.tvPersion1.setText(per0.getAnsText());

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        binding.ivPersion1.setBackgroundResource(R.color.white);
                        binding.ivPersion2.setBackgroundResource(R.color.yellow);

                        App.getInstance().getMediaManager().playGameSound(per1.getAnsSound(), new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                App.getInstance().getMediaManager().playGameSound(MediaManager.PING,null);
                                binding.tvPersion2.setText(per1.getAnsText());

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                binding.ivPersion2.setBackgroundResource(R.color.white);
                                binding.ivPersion3.setBackgroundResource(R.color.yellow);
                                App.getInstance().getMediaManager().playGameSound(per2.getAnsSound(), new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                        App.getInstance().getMediaManager().playGameSound(MediaManager.PING,null);
                                        binding.tvPersion3.setText(per2.getAnsText());

                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        binding.ivPersion3.setBackgroundResource(R.color.white);

                                    }
                                });
                            }
                        });

                    }
                });

                return;
            }
        }

    }


    public void getNewTruePer(int trueCase) {
        if (trueCase == 1) {
            textAns = "A";

            personTrue.setAnsText(textAns);
            personTrue.setAnsSound(MediaManager.TRO_GIUP_1);
        } else if (trueCase == 2) {
            textAns = "B";

            personTrue.setAnsText(textAns);
            personTrue.setAnsSound(MediaManager.TRO_GIUP_2);

        } else if (trueCase == 3) {
            textAns = "C";
            personTrue.setAnsText(textAns);
            personTrue.setAnsSound(MediaManager.TRO_GIUP_3);


        } else if (trueCase == 4) {
            textAns = "D";
            personTrue.setAnsText(textAns);
            personTrue.setAnsSound(MediaManager.TRO_GIUP_4);

        }


    }


}
