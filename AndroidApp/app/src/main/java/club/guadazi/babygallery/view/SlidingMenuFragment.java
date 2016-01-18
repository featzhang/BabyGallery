package club.guadazi.babygallery.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import club.guadazi.babygallery.MainActivity;
import club.guadazi.babygallery.R;

public class SlidingMenuFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.slide_menu, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);/*
        ListView  listView=(ListView) view.findViewById(R.id.list_slide);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, listArrStrings));
        listView.setOnItemClickListener(this);*/
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if (getActivity() instanceof MainActivity) {
//            ((MainActivity)getActivity()).changeImage(position);
        }
    }

}