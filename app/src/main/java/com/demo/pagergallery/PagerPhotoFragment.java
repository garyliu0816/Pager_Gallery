package com.demo.pagergallery;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PagerPhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PagerPhotoFragment extends Fragment {
    private static final String TAG = "PagerPhotoFragment";
    ViewPager2 viewPager2;
    TextView photoTag;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PagerPhotoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PagerPhotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PagerPhotoFragment newInstance(String param1, String param2) {
        PagerPhotoFragment fragment = new PagerPhotoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pager_photo, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager2 = requireActivity().findViewById(R.id.viewPager2);
        photoTag = requireActivity().findViewById(R.id.photoTag);
        final Bundle bundle = requireArguments();



        final ArrayList<PhotoItem> photoList = bundle.getParcelableArrayList("photo_list");
        PagerPhotoAdapter adapter = new PagerPhotoAdapter();
        adapter.submitList(photoList);

        viewPager2.setAdapter(adapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                photoTag.setText(position + "/" + photoList.size());
            }
        });

        // 一定要在setAdapter之后再设置位置，否则无效
        int position = bundle.getInt("photo_position");
        // Log.d(TAG, "position: " + position);
        viewPager2.setCurrentItem(position, false);
    }
}