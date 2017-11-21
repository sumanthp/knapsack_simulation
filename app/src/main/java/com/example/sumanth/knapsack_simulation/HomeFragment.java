package com.example.sumanth.knapsack_simulation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int k[][];
    private OnFragmentInteractionListener mListener;
    Button submit;
    int weights_count,values_count,max_weight,weights_array[],values_array[];
    int knapsack_matrix[][];
    int array[];
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        final View v = inflater.inflate(R.layout.fragment_home, container, false);
        submit = (Button)v.findViewById(R.id.submit);
        final Bundle bundle = new Bundle();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value1 = ((EditText) v.findViewById(R.id.weights_count)).getText().toString().trim();
                String value2 = ((EditText) v.findViewById(R.id.values_count)).getText().toString().trim();
                String value3 = ((EditText) v.findViewById(R.id.max_weight)).getText().toString();
                String value4 = ((EditText) v.findViewById(R.id.weights_array)).getText().toString();
                String value5 = ((EditText) v.findViewById(R.id.values_array)).getText().toString();
                if (value1.isEmpty() || value2.isEmpty() || value3.isEmpty() || value4.isEmpty() || value5.isEmpty()) {
                    Toast.makeText(getActivity(), "One of the fields is empty", Toast.LENGTH_SHORT).show();
                } else {
                    weights_count = Integer.parseInt(value1);
                    values_count = Integer.parseInt(value2);
                    max_weight = Integer.parseInt(value3);
                    String array[] = value4.split(" ");
                    String[] weights_array_string = array;
                    weights_array = new int[array.length];
                    for(int i=0;i<array.length;i++)
                    {
                        weights_array[i] = Integer.parseInt(array[i]);
                    }
                    //convertStringToIntArray(array, weights_array);
                    array = value5.split(" ");
                    String[] values_array_string = array;
                    values_array = new int[array.length];
                    for(int j=0;j<array.length;j++)
                    {
                        values_array[j] = Integer.parseInt(array[j]);
                    }
                    //convertStringToIntArray(array, values_array);
                    if (weights_count != values_count) {
                        Toast.makeText(getActivity(), "Number of values and number of weights do not match", Toast.LENGTH_SHORT).show();
                    }
                    if (weights_array.length != weights_count) {
                        Toast.makeText(getActivity(), "Number of weights and its count entered do not match", Toast.LENGTH_SHORT).show();
                    }
                    if (values_array.length != values_count) {
                        Toast.makeText(getActivity(), "Number of values and its count entered do not match", Toast.LENGTH_SHORT).show();
                    } else {
                        k = new int[weights_count+1][max_weight+1];
                        array = new String[(weights_count+1)*(max_weight+1)];
                        int result = knapsack(max_weight, weights_array, values_array, weights_count);
                        //Intent intent = new Intent(getActivity(), ResultActivity.class);
                        //startActivity(intent);
                        //intent.putExtra("result", result);
                        convert2dtoarray(array,k);
                        Bundle arguments = new Bundle();
                        arguments.putString("result",String.valueOf(result));
                        arguments.putString("weights_count",String.valueOf(weights_count));
                        arguments.putString("max_weight",String.valueOf(max_weight));
                        arguments.putStringArray("array",array);
                        arguments.putStringArray("weights_array",weights_array_string);
                        arguments.putStringArray("values_array",values_array_string);
                        Fragment fragment = new FragmentResult();
                        fragment.setArguments(arguments);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.home_frame, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
            }
        });

        return v;
    }
    public void convert2dtoarray(String[] array,int k[][])
    {
        int m=0;
        for(int i=0;i<k.length;i++)
        {
            for(int j=0;j<k[0].length;j++)
            {
                array[i] = String.valueOf(k[i][j]);
            }
        }
    }

    public void convertStringToIntArray(String array[],int storeArray[])
    {
        storeArray = new int[array.length];
        for(int i=0;i<array.length;i++)
        {
            storeArray[i]=Integer.parseInt(array[i]);
        }
    }

    public int knapsack(int max_weight,int []weights_array,int []values_array,int n)
    {
        int i,weight;
       // int k[][] = new int[n+1][max_weight+1];
        for(i=0;i<=n;i++)
        {
            for(weight=0;weight<=max_weight;weight++)
            {
                if(i==0||weight==0)
                    k[i][weight]=0;
                else if(weights_array[i-1]<=weight)
                    k[i][weight] = max(values_array[i-1]+k[i-1][weight-weights_array[i-1]],k[i-1][weight]);
                else
                    k[i][weight] = k[i-1][weight];
            }
        }
        return k[n][max_weight];
    }
    public int max(int a,int b)
    {
        return a>b?a:b;
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
    public void onResume(){
        super.onResume();
        ((MainActivity)getActivity()).setActionBarTitle("Knapsack Simulation");
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
