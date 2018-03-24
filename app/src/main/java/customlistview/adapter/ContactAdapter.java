package customlistview.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fullsuper.danhba.R;

import java.util.List;

import customlistview.model.Contact;

public class ContactAdapter extends ArrayAdapter<Contact> {

    private  Context context;
    private  int resource;
    private List<Contact> listContact;

    public ContactAdapter( Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listContact = objects;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.items_contact,parent,false);
            viewHolder.imgAvatar = (ImageView) convertView.findViewById(R.id.img_Avatar);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.txtNumber = (TextView) convertView.findViewById(R.id.txtNumber);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Contact contact =  listContact.get(position);

        viewHolder.txtName.setText(contact.getmName());
        viewHolder.txtNumber.setText(contact.getmNumber());

        if(contact.isMale())
            viewHolder.imgAvatar.setBackgroundResource(R.drawable.male2);
        else
            viewHolder.imgAvatar.setBackgroundResource(R.drawable.female);

        return convertView;
    }
    public  class  ViewHolder{
        ImageView imgAvatar;
        TextView txtName;
        TextView txtNumber;
    }
}
