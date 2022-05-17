package ch.bbbaden.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.messengerapplication.R
import com.squareup.picasso.Picasso

class KontaktAdapter(private val context: Context, private val dataSource : ArrayList<Kontakt>) : BaseAdapter() {
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(postition: Int): Any {
        return dataSource[postition]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item, parent, false)
        val nameTextView = rowView.findViewById(R.id.contactName) as TextView
        val profilepictureImageView = rowView.findViewById(R.id.profile_pic) as ImageView
        val contact = getItem(position) as Kontakt
        nameTextView.text = contact.name
        Picasso.get().load(contact.imageURL).placeholder(R.mipmap.ic_launcher).into(profilepictureImageView);
        return rowView
    }
}