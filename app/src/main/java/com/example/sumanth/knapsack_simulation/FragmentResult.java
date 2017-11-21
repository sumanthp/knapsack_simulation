package com.example.sumanth.knapsack_simulation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentResult.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentResult#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentResult extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int k[][];
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentResult() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentResult.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentResult newInstance(String param1, String param2) {
        FragmentResult fragment = new FragmentResult();
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
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        //int result = getActivity().getIntent().getIntExtra("result",0);
        int result = Integer.parseInt(getArguments().getString("result"));
        int weights_count = Integer.parseInt(getArguments().getString("weights_count"));
        int max_weight = Integer.parseInt(getArguments().getString("max_weight"));
        String array[] = getArguments().getStringArray("array");
        String[] weights_array = getArguments().getStringArray("weights_array");
        String[] values_array = getArguments().getStringArray("values_array");
        int[] weights = convertStringToIntArray(weights_array);
        int[] values = convertStringToIntArray(values_array);
        k = new int[weights_count+1][max_weight+1];
       // convertarrayto2d(array,k,weights_count+1,max_weight+1);
        TextView result_text = (TextView)view.findViewById(R.id.result);
        simulate(weights,values,max_weight,view);
        result_text.setText("Maximum value that can fit into maximum weight is: "+result);
        return view;
    }
    public int[] convertStringToIntArray(String array[])
    {
        int[] storeArray = new int[array.length];
        for(int i=0;i<array.length;i++)
        {
            storeArray[i]=Integer.parseInt(array[i]);
        }
        return storeArray;
    }
    public void simulate(int[] weights,int[] values,int max_weight,View view)
    {
        int value=0;
        int weight=0;
        String w="";
        LinearLayout l = (LinearLayout)view.findViewById(R.id.result_layout);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for(int i=0;i<(1<<weights.length);i++)
        {
            for(int j=0;j<values.length;j++)
            {
               /* if(i==j) ;
                else if(weights[i]==max_weight)
                {
                    value = values[i]+values[j];
                    TextView subset = new TextView(getActivity());
                    subset.setLayoutParams(layoutParams);
                    subset.setTextSize(getResources().getDimension(R.dimen.textsize));
                    subset.setText("("+weights[i]+", "+weights[j]+") = "+max_weight+"==> Value: "+value);
                    l.addView(subset);
                }
                else if(weights[i]+weights[j]<max_weight)
                {
                    value = values[i]+values[j];
                    TextView subset = new TextView(getActivity());
                    subset.setLayoutParams(layoutParams);
                    subset.setTextSize(getResources().getDimension(R.dimen.textsize));
                    subset.setText("("+weights[i]+", "+weights[j]+") < "+max_weight+"==> Value: "+value);
                    l.addView(subset);
                }
                else if(weights[i]+weights[j]==max_weight)
                {
                    value = values[i]+values[j];
                    TextView subset = new TextView(getActivity());
                    subset.setLayoutParams(layoutParams);
                    subset.setTextSize(getResources().getDimension(R.dimen.textsize));
                    subset.setText("("+weights[i]+", "+weights[j]+") = "+max_weight+"==> Value: "+value);
                    l.addView(subset);
                }*/

                if ((i & (1 << j)) > 0) {
                    weight+=weights[j];
                    value+=values[j];
                    w += weights[j]+",";
                }

            }
            TextView subset = new TextView(getActivity());
            subset.setLayoutParams(layoutParams);
            subset.setTextSize(getResources().getDimension(R.dimen.textsize));
            if(weight==max_weight)
                subset.setText("("+w+") = "+max_weight+"==> Value: "+value);
            else if(weight<max_weight)
                subset.setText("("+w+") < "+max_weight+"==> Value: "+value);
            else if(weight>max_weight)
                subset.setText("("+w+") > "+max_weight+"==> Value: "+value);
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_to_right);
            subset.startAnimation(animation);
            l.addView(subset);
            w="";
            value=0;
            weight=0;
        }
        TextView result = (TextView)view.findViewById(R.id.result);
        result.setVisibility(View.VISIBLE);
    }
    public void convertarrayto2d(String[] array,int k[][],int rows,int cols)
    {
        int m=0,n=0;
        for(int i=0;i<array.length;i++)
        {
            if(iIsmultipleOfRows(i,rows))
            {
                k[m][n]=Integer.parseInt(array[i]);
                m++;
                n++;
            }
            else
            {
                k[m][n]=Integer.parseInt(array[i]);
                n++;
            }

        }
    }
    public boolean iIsmultipleOfRows(int i, int rows)
    {
        if(i%rows==0)
            return true;
        else
            return false;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
