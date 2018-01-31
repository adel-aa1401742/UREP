package qa.edu.qu.cmps312.fahim.models;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import qa.edu.qu.cmps312.fahim.R;

/**
 * Created by adel_ on 10/27/2017.
 */

 class ViewHolder{
     ImageView imageV,fingerIV;

     ViewHolder(View v){
         imageV = v.findViewById(R.id.imageIV);
         fingerIV = v.findViewById(R.id.fingerIV);
     }

}
public class GridAdapter extends BaseAdapter {
    private ArrayList<Choice> listOfChoices;
    private Context context;

    // We can pass unite,lesson,level ....??
    public GridAdapter(ArrayList<Choice> list, Context context){
       this.context = context;
       this.listOfChoices = list;
    }

    @Override
    public int getCount() {
        return listOfChoices.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfChoices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if(row ==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_image,parent,false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        }else {
            holder = (ViewHolder) row.getTag();
        }
        Choice temp = listOfChoices.get(position);
        // holder.imageV.setImageResource(R.drawable.ic_launcher_background);
        Picasso.with(context).load(temp.getImageUrl()).into(holder.imageV);
        if(temp.isShowFinger()){
           holder.fingerIV.setVisibility(View.VISIBLE);
        }

        return row;

    }





}
