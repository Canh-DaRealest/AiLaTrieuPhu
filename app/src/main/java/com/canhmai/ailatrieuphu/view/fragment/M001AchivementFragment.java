package com.canhmai.ailatrieuphu.view.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.canhmai.ailatrieuphu.App;
import com.canhmai.ailatrieuphu.adapter.RecycleViewAdapter;
import com.canhmai.ailatrieuphu.databinding.M001ArchivementFragmentBinding;
import com.canhmai.ailatrieuphu.view.act.MainActivity;
import com.canhmai.ailatrieuphu.viewmodel.CommonVM;

public class M001AchivementFragment extends BaseFragment<CommonVM, M001ArchivementFragmentBinding> {
    public static final String TAG = M001AchivementFragment.class.getName();
    private RecycleViewAdapter recycleViewAdapter;

    @Override
    protected void initViews() {


        new Thread() {
            @Override
            public void run() {
                App.getInstance().getStorage().listHighScore = App.getInstance().getDb().getHighScoreDAO().getAll();
                Log.e(TAG, "run: Danh sach diem cao " + App.getInstance().getStorage().listHighScore.size());
                recycleViewAdapter = new RecycleViewAdapter(mContext, App.getInstance().getStorage().listHighScore);
                MainActivity mainActivity = (MainActivity) mContext;
                mainActivity.runOnUiThread(() -> binding.vpRecyclerView.setAdapter(recycleViewAdapter));
            }
        }.start();
        binding.ivBack.setOnClickListener(this);

    }

    @Override
    protected void clickView(View v) {
        super.clickView(v);
        MainActivity mainActivity = (MainActivity) mContext;
        mainActivity.onBackPressed();
    }

    @Override
    protected Class<CommonVM> getClassViewModel() {
        return CommonVM.class;
    }

    @Override
    protected M001ArchivementFragmentBinding initViewBinding(LayoutInflater inflater) {
        return M001ArchivementFragmentBinding.inflate(inflater);
    }
    //   private void upDateUI() {
    //
    //        HighScore hc2;
    //        HighScore hc3;
    //        int mone1;
    //        int mone2;
    //        int mone3;
    //        if (!App.getInstance().getStorage().listHighScore.isEmpty()) {
    //            Log.e(TAG, "upDateUI: LISTSIZE====" + App.getInstance().getStorage().listHighScore.size());
    //            HighScore hc1 = App.getInstance().getStorage().listHighScore.get(0);
    //            Log.e(TAG, "upDateUI: NAME==" + hc1.getName());
    //            mone1 = hc1.getScoreUser();
    //
    //            changeMoneyFromIntToString(mone1, hc1, binding.tvRank1Name, binding.tvRank1Money);
    //
    //
    //            if (App.getInstance().getStorage().listHighScore.size() >= 2) {
    //                hc2 = App.getInstance().getStorage().listHighScore.get(1);
    //                mone2 = hc2.getScoreUser();
    //
    //                changeMoneyFromIntToString(mone2, hc2, binding.tvRank2Name, binding.tvRank2Money);
    //            }
    //
    //            if (App.getInstance().getStorage().listHighScore.size() > 2) {
    //                hc3 = App.getInstance().getStorage().listHighScore.get(2);
    //                mone3 = hc3.getScoreUser();
    //
    //                changeMoneyFromIntToString(mone3, hc3, binding.tvRank3Name, binding.tvRank3Money);
    //            }
    //        }
    //    }
// private void changeMoneyFromIntToString(int mone, TextView tvMoney) {
//        String text;
//        if (mone == 150000000) {
//            text = "150,000,000";
//        } else if (mone == 22000000) {
//            text = "22,000,000";
//        } else if (mone == 20000000) {
//            text = "20,000,000";
//        } else if (mone == 2000000) {
//            text = "2,000,000";
//        } else if (mone == 200000) {
//            text = "200,000";
//        } else {
//
//            text = formatCurrency(hc.getScoreUser());
//        }
//
//        tvMoney.setText(text);
//    }
}
