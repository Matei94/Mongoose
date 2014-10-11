package ro.hackzurich.mongoose;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<String> {
  private final Context context;
  private final String[] values;

  public MyArrayAdapter(Context context, String[] values) {
    super(context, R.layout.challenge_item, values);
    
    this.context = context;
    this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    
    View rowView = inflater.inflate(R.layout.challenge_item, parent, false);
    
    TextView textView = (TextView) rowView.findViewById(R.id.txtvwChallenge);
    ImageView imageView = (ImageView) rowView.findViewById(R.id.imgvwChallenge);
    
    textView.setText(values[position]);
    
    // change the icon for Windows and iPhone
    // String s = values[position];
    imageView.setImageResource(R.drawable.ic_launcher);

    return rowView;
  }
}