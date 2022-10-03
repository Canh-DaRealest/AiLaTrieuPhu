package com.canhmai.ailatrieuphu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.canhmai.ailatrieuphu.App;
import com.canhmai.ailatrieuphu.databinding.DialogHightscoreBinding;
import com.canhmai.ailatrieuphu.db.entities.HighScore;

public class DialogHighScore extends Dialog implements View.OnClickListener {
    private final DialogHightscoreBinding binding;
    private final Context context;
    private String name;
    private int score;

    public DialogHighScore(@NonNull Context context) {
        super(context);
        this.context = context;
        setCanceledOnTouchOutside(false);
        binding = DialogHightscoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        initViews();
    }

    private void initViews() {
        doClick();
    }

    private void doClick() {
        binding.btSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        clickView(v);
    }

    private void clickView(View v) {
        v.setAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
        String newName = binding.edtName.getText().toString().trim();
        String tien = "";


        if (newName.isEmpty()) {
            Toast.makeText(context, "Vui lòng nhập tên của bạn", Toast.LENGTH_SHORT).show();
            return;
        }
//        binding.tvTotalMoney.setText(tien);

        new Thread() {
            @Override
            public void run() {
                App.getInstance().getDb().getHighScoreDAO().insert(new HighScore(newName, score));
            }
        }.start();
        //App.getInstance().getStorage().listHighScore.add(new HighScore(newName, score));
        dismiss();
    }


    public void setScore(String money) {
        binding.tvTotalMoney.setText(money);
        this.score = Integer.parseInt(money.replace(",", ""));
    }
}
