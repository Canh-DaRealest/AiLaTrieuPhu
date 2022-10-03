package com.canhmai.ailatrieuphu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.canhmai.ailatrieuphu.R;
import com.canhmai.ailatrieuphu.databinding.M001DialogRateMeBinding;

public class DialogRatingMe extends Dialog implements View.OnClickListener, RatingBar.OnRatingBarChangeListener {
    public static final String OPEN_FB_APP = "OPEN_FB_APP";
    private final Context context;
    private final M001DialogRateMeBinding binding;

    public DialogRatingMe(@NonNull Context context) {
        super(context);
        this.context = context;
        binding = M001DialogRateMeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
    }

    private void initViews() {
        binding.ratingbar.setStepSize(1);
        binding.ratingbar.setNumStars(5);
        binding.ratingbar.setOnRatingBarChangeListener(this);

        binding.btRating.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (binding.ratingbar.getRating() > 0) {
            Toast toast = new Toast(context);
            toast.setText("Cảm ơn bạn đã đánh giá chúng tôi ♥");
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, Gravity.CENTER, Gravity.CENTER);
            toast.show();
        }
        dismiss();
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        float counttingStar = binding.ratingbar.getRating();
        float numberStar = binding.ratingbar.getNumStars();
        if (counttingStar < numberStar && counttingStar >= 3) {  //tu 3 sao tro len
            binding.iconRate.setImageResource(R.drawable.neutral);
            binding.ratingText.setText("Bình thường");
        } else if (counttingStar < 3) {  //duoi 3 sao
            binding.iconRate.setImageResource(R.drawable.angry);
            binding.ratingText.setText("Không hài lòng");
        } else if (counttingStar >= numberStar) {  //5 sao tro len
            binding.iconRate.setImageResource(R.drawable.happy);
            binding.ratingText.setText("Rất hài lòng");
        }

    }
}
