package dev.atharvakulkarni.messenger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Mark_attendance extends Fragment
{
    Spinner year_spinner,division_spinner;
    String year,division;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.mark_attendance,container,false);

        /*year_spinner = view.findViewById(R.id.year);
        division_spinner = view.findViewById(R.id.division);

        ArrayList<String> year_arraylist = new ArrayList<>();
        year_arraylist.add("FE");
        year_arraylist.add("SE");
        year_arraylist.add("TE");
        year_arraylist.add("BE");


        ArrayList<String> division_arraylist = new ArrayList<>();
        division_arraylist.add("A");
        division_arraylist.add("B");
        division_arraylist.add("C");
        division_arraylist.add("D");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,year_arraylist);
        year_spinner.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,division_arraylist);
        division_spinner.setAdapter(adapter2);


        year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                year = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        division_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                division = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });*/





        return view;
    }
}
