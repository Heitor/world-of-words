package br.ufcg.les.wow.util;
import java.util.List;

import br.ufcg.les.wow.R;
import br.ufcg.les.wow.adedonha.model.Letra;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class InteractiveArrayAdapter extends ArrayAdapter<Letra> {

	private final List<Letra> list;
	private final Activity context;
	
	public InteractiveArrayAdapter(Activity context, List<Letra> list) {
		super(context, R.layout.checkbox_letras, list);
		this.context = context;
		this.list = list;
	}

	static class ViewHolder {
		protected TextView text;
		protected CheckBox checkbox;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;

		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.checkbox_letras, null);
			
			final ViewHolder viewHolder = new ViewHolder();

			viewHolder.text = (TextView) view.findViewById(R.id.label);
			viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
			viewHolder.checkbox
					.setOnCheckedChangeListener(checkedChangeListener(viewHolder));
			
			view.setTag(viewHolder);
			viewHolder.checkbox.setTag(list.get(position));
		
		} else {
			view = convertView;
			((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.text.setText(list.get(position).getLetra());
		holder.checkbox.setChecked(list.get(position).isSelecioada());
		return view;
	}

	private OnCheckedChangeListener checkedChangeListener(
			final ViewHolder viewHolder) {
		return new CompoundButton.OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Letra element = (Letra) viewHolder.checkbox
						.getTag();
				element.setSelecioada(buttonView.isChecked());

			}
		};
	}
}
