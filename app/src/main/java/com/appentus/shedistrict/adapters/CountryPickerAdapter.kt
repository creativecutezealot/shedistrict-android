package app.teesas.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.models.CountriesBean
import kotlinx.android.synthetic.main.item_country_picker.view.*


class CountryPickerAdapter(private val countries: List<CountriesBean.Countries>) : RecyclerView.Adapter<CountryPickerAdapter.ItemHolder>() , Filterable {

    private var countriesFiltered: List<CountriesBean.Countries> = countries


    override fun getItemCount(): Int {
        return countriesFiltered.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_country_picker, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.name.text  = countriesFiltered[position].country_name
      
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.name as TextView
      
    }

    override fun getFilter(): Filter {
        return  object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString: String = charSequence.toString()
                Log.e("data",charString);
                countriesFiltered = if (charString.isEmpty()) {
                    countries
                } else {
                    val filteredList: MutableList<CountriesBean.Countries> = ArrayList()
                    for (row in countries) {
                        if (row.country_name.toLowerCase().contains(charString.toLowerCase()) || row.country_isd.contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = countriesFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countriesFiltered = results!!.values as ArrayList<CountriesBean.Countries>
                notifyDataSetChanged()
            }
        }
    }

}