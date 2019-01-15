package hr.tgazica.bakingapp.ui.details;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import hr.tgazica.bakingapp.R;
import hr.tgazica.bakingapp.model.Step;
import hr.tgazica.bakingapp.ui.recipe.RecipeActivity;

public class RecipeDetailsFragment extends Fragment {

    @BindView(R.id.video_player)
    PlayerView videoPlayer;
    @BindView(R.id.recipe_step_description)
    TextView recipeStepDescription;
    @BindView(R.id.button_next)
    @Nullable
    Button buttonNext;
    @Nullable
    @BindView(R.id.button_previous)
    Button buttonPrevious;
    @Nullable
    @BindView(R.id.recipe_details_land)
    RelativeLayout recipeDetailsLand;
    @Nullable
    @BindView(R.id.button_steps)
    Button showStepsButton;

    private Step step;
    private SimpleExoPlayer player;

    private int currentWindow = 0;
    private long playbackPosition= 0;
    private boolean playWhenReady = true;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null){
            step = (Step) getArguments().getSerializable(RecipeActivity.RECIPE_EXTRA);
        }

        if (step != null) {
            if (step.getVideoURL().equals("")) {
                videoPlayer.setVisibility(View.GONE);
            }else {
                initializePlayer();
            }

            recipeStepDescription.setText(step.getDescription());

        }
    }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultRenderersFactory(getContext()),new DefaultTrackSelector(),new DefaultLoadControl());

        videoPlayer.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse(step.getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer")).createMediaSource(uri);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }
}
