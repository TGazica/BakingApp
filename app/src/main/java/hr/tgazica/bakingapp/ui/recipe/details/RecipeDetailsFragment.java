package hr.tgazica.bakingapp.ui.recipe.details;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import hr.tgazica.bakingapp.R;
import hr.tgazica.bakingapp.model.Recipe;
import hr.tgazica.bakingapp.model.Step;
import hr.tgazica.bakingapp.ui.recipe.OnRecipeListener;
import hr.tgazica.bakingapp.ui.recipe.RecipeActivity;

public class RecipeDetailsFragment extends Fragment {

    private static final String RECIPE_SAVE = "recipe_save";
    private static final String STEP_SAVE = "step_save";
    private static final String PLAYBACK_SAVE = "playback_save";
    private static final String IS_PLAYING_SAVE = "is_playing";

    @BindView(R.id.video_player)
    PlayerView videoPlayer;
    @Nullable
    @BindView(R.id.recipe_step_description)
    TextView recipeStepDescription;
    @Nullable
    @BindView(R.id.recipe_details_land)
    RelativeLayout recipeDetailsLand;

    private Step step;
    private Recipe recipe;
    private SimpleExoPlayer player;
    private long playbackPosition = 0;
    private boolean playWhenReady = true;
    private OnRecipeListener recipeListener;
    private boolean isTablet;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        recipeListener = (OnRecipeListener) context;
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

        if (savedInstanceState != null) {
            recipe = (Recipe) savedInstanceState.getSerializable(RECIPE_SAVE);
            playbackPosition = savedInstanceState.getLong(PLAYBACK_SAVE);
            step = (Step) savedInstanceState.getSerializable(STEP_SAVE);
            playWhenReady = savedInstanceState.getBoolean(IS_PLAYING_SAVE);
            isTablet = savedInstanceState.getBoolean(RecipeActivity.IS_TABLET);
        } else {
            if (getArguments() != null) {
                step = (Step) getArguments().getSerializable(RecipeActivity.STEP_EXTRA);
                recipe = (Recipe) getArguments().getSerializable(RecipeActivity.RECIPE_EXTRA);
                isTablet = getArguments().getBoolean(RecipeActivity.IS_TABLET);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (recipeDetailsLand == null) {
            recipeListener.onDetailsFragmentResumed(true);
        } else {
            recipeListener.onDetailsFragmentResumed(false);
        }

        if (step != null) {
            if (step.getVideoURL().equals("")) {
                videoPlayer.setVisibility(View.GONE);
            } else {
                initializePlayer();

                if (recipeDetailsLand != null) {
                    if (getActivity() != null) {
                        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
                        if (appCompatActivity.getSupportActionBar() != null) {
                            if (!isTablet) {
                                appCompatActivity.getSupportActionBar().hide();
                            }
                        }
                        if (!isTablet) {
                            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                        }
                    }
                }
            }

            if (recipeStepDescription != null) {
                recipeStepDescription.setText(step.getDescription());
            }

        }
    }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultRenderersFactory(getContext()), new DefaultTrackSelector(), new DefaultLoadControl());

        videoPlayer.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);

        Uri uri = Uri.parse(step.getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);

        player.seekTo(playbackPosition);
        player.prepare(mediaSource, false, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer")).createMediaSource(uri);
    }

    @Override
    public void onPause() {
        if (recipeDetailsLand != null && getActivity() != null) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            if (appCompatActivity.getSupportActionBar() != null) {
                appCompatActivity.getSupportActionBar().show();
            }
        }
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STEP_SAVE, step);
        outState.putSerializable(RECIPE_SAVE, recipe);
        outState.putLong(PLAYBACK_SAVE, playbackPosition);
        outState.putBoolean(RecipeActivity.IS_TABLET, isTablet);
        if (player != null) {
            outState.putBoolean(IS_PLAYING_SAVE, player.getPlayWhenReady());
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }
}
