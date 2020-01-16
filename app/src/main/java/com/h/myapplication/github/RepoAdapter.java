package com.h.myapplication.github;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.h.myapplication.R;
import com.hiking.common.bean.login.UserRepo;
import com.hiking.common.glide.GlideApp;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class RepoAdapter extends PagedListAdapter<UserRepo, RepoAdapter.RepoPagedViewHolder> {


    protected RepoAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RepoPagedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_repo, parent, false);
        return new RepoPagedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoPagedViewHolder holder, int position) {
        holder.bind(position, getItem(position));
    }

    private static DiffUtil.ItemCallback<UserRepo> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<UserRepo>() {
                // UserRepo details may have changed if reloaded from the database,
                // but ID is fixed.
                @Override
                public boolean areItemsTheSame(UserRepo oldUserRepo, UserRepo newUserRepo) {
                    return oldUserRepo.id == newUserRepo.id;
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(UserRepo oldUserRepo,
                                                  UserRepo newUserRepo) {
                    return ((oldUserRepo.id == newUserRepo.id) && TextUtils.equals(oldUserRepo.cloneUrl, newUserRepo.cloneUrl));
                }
            };

    static class RepoPagedViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvOwnerName;
        TextView tvLanguage;
        TextView tvRepoName;
        TextView tvRepoDesc;
        TextView tvStar;
        TextView tvIssue;
        TextView tvFork;
        ImageView ivLanguageColor;

        public RepoPagedViewHolder(@NonNull View v) {

            super(v);
            ivAvatar = v.findViewById(R.id.ivAvatar);
            tvOwnerName = v.findViewById(R.id.tvOwnerName);
            tvLanguage = v.findViewById(R.id.tvLanguage);
            tvRepoName = v.findViewById(R.id.tvRepoName);
            tvRepoDesc = v.findViewById(R.id.tvRepoDesc);
            tvStar = v.findViewById(R.id.tvStar);
            tvIssue = v.findViewById(R.id.tvIssue);
            tvFork = v.findViewById(R.id.tvFork);
            ivLanguageColor = v.findViewById(R.id.ivLanguageColor);
        }

        public void bind(int position, UserRepo item) {
            GlideApp.with(ivAvatar.getContext())
                    .load(item.owner.avatarUrl)
                    .apply(new RequestOptions().circleCrop())
                    .into(ivAvatar);
            GlideApp.with(ivLanguageColor.getContext())
                    .load(getLanguageColor(item.language))
                    .apply(new RequestOptions().circleCrop())
                    .into(ivLanguageColor);

            tvOwnerName.setText(item.owner.login);
            tvLanguage.setText(item.language);
            tvRepoName.setText(item.fullName);
            tvRepoDesc.setText(item.description);
            tvStar.setText(item.stargazersCount+"");
            tvIssue.setText(item.openIssuesCount+"");
            tvFork.setText(item.forksCount+"");

            ivLanguageColor.setImageDrawable(getLanguageColor(item.language));
            ivLanguageColor.setVisibility(TextUtils.isEmpty(item.language) ? View.GONE : View.VISIBLE);
        }

        private Drawable getLanguageColor(String language) {
            if (TextUtils.isEmpty(language)) return new ColorDrawable(Color.TRANSPARENT);
            return new ColorDrawable(ContextCompat.getColor(itemView.getContext(), getColor(language)));

        }

        private int getColor(String language) {
            switch (language) {
                case "Kotlin":
                    return R.color.color_language_kotlin;
                case "Java":
                    return R.color.color_language_java;
                case "JavaScript":
                    return R.color.color_language_js;
                case "Python":
                    return R.color.color_language_python;
                case "HTML":
                    return R.color.color_language_html;
                case "CSS":
                    return R.color.color_language_css;
                case "Shell":
                    return R.color.color_language_shell;
                case "C++":
                    return R.color.color_language_cplus;
                case "C":
                    return R.color.color_language_c;
                case "ruby":
                    return R.color.color_language_ruby;
                default:
                    return R.color.color_language_other;

            }
        }


    }
}
