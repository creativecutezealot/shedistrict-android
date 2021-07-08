package com.appentus.shedistrict.adapters

import android.R.string
import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.teesas.ui.adapters.CountryPickerAdapter
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.Country
import com.appentus.shedistrict.Utils.RecyclerTouchListener
import com.appentus.shedistrict.models.PrefrenceBean
import com.appyvet.materialrangebar.RangeBar
import com.appyvet.materialrangebar.RangeBar.OnRangeBarChangeListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_both_write_record.ivClose
import kotlinx.android.synthetic.main.checkbox.view.*
import kotlinx.android.synthetic.main.country_code_picker.*
import kotlinx.android.synthetic.main.item_preference.view.*
import kotlinx.android.synthetic.main.rangebar.view.*
import kotlinx.android.synthetic.main.text.view.*
import org.json.JSONArray
import java.util.*
import kotlin.collections.ArrayList


class PreferencesAdapter(val context: Activity, val result: List<PrefrenceBean.ResultBean>, val preference: ArrayList<String>, val keylist: ArrayList<String>) : RecyclerView.Adapter<PreferencesAdapter.ItemHolder>() {
    var flag: Boolean = true
    private var mExpandedPosition = -1
    private var flotv: Float= 20.0F
    lateinit var view: View
    var values: ArrayList<String>? = null

    lateinit var list: MutableList<PrefrenceBean.ResultBean.ValuesBean>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_preference, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        list = ArrayList()

        holder.tvTitle.text = result[position].preference
        val isExpanded: Boolean = position == mExpandedPosition;
        holder.ccPreference.isActivated = isExpanded;

        if (result[position].input_type == "range") {
            holder.llInflater.removeAllViews()
            val view = context.layoutInflater.inflate(R.layout.rangebar, null)
            holder.llInflater.addView(view)

                if (result[position].preference == "Distance") {

                    view.tvMin.text = "${result[position].values[0].value_name} ${result[position].unit}"
                    view.tvMax.text = result[position].values[1].value_name + result[position].unit

                    view.seekbars.tickEnd = result[position].values[1].value_name.toFloat()
                    view.seekbars.tickStart = result[position].values[0].value_name.toFloat()
                    if(preference.size!=0) {
                        if (!preference[position].isNullOrEmpty()) {

                            val strs = stringToWords(preference[position])
                            view.seekbars.setRangePinsByValue(strs[0].toFloat(), strs[1].toFloat())
                            view.seekbars.setTemporaryPins(false)

                        }
                    }



                }
                else {

                    view.tvMin.text = result[position].values[0].value_name
                    view.tvMax.text = result[position].values[1].value_name
                    view.seekbars.tickEnd = result[position].values[1].value_name.toFloat()
                    view.seekbars.tickStart = result[position].values[0].value_name.toFloat()

                    if(preference.size!=0) {
                        if (!preference[position].isNullOrEmpty()) {

                            val strs = stringToWords(preference[position])

                            view.seekbars.setRangePinsByValue(strs[0].toFloat(), strs[1].toFloat())
                            view.seekbars.setTemporaryPins(false)
                        }
                    }



                }

           // view.seekbars.tickEnd = result[position].values[1].value_name.toFloat()
           // view.seekbars.tickStart = result[position].values[0].value_name.toFloat()

            view.seekbars.setOnRangeBarChangeListener(object : OnRangeBarChangeListener {
                override fun onRangeChangeListener(rangeBar: RangeBar, leftPinIndex: Int, rightPinIndex: Int, leftPinValue: String, rightPinValue: String) {


                        view.seekbars.setTemporaryPins(false)

                    result[position].selectedValue = "[${view.seekbars.leftPinValue},${view.seekbars.rightPinValue}]"

                }
                override fun onTouchEnded(rangeBar: RangeBar) {
                    view.seekbars.setTemporaryPins(true)
                }
                override fun onTouchStarted(rangeBar: RangeBar) {
                    view.seekbars.setTemporaryPins(true)
                }
            })
        }

        if (result[position].input_type == "checkbox"||result[position].input_type == "radio") {
            holder.llInflater.removeAllViews()
            val view: View = context.layoutInflater.inflate(R.layout.checkbox, null)
            holder.llInflater.addView(view)

            if (result[position].input_type == "radio"){

                view.rvSubPreference.layoutManager = LinearLayoutManager(context)

            }else{
                view.rvSubPreference.layoutManager = GridLayoutManager(context, 2)
            }

            if(!preference.isNullOrEmpty()) {
                if (keylist[position].equals("Nationality")) {

                    result[position].selectedValue = preference[position]
                    Log.e("aknask", preference[position])
                    view.autoTextView.setText(preference[position])
                } }

            if(!preference.isNullOrEmpty()) {
                val strs = stringToWords(preference[position])

                    for(i in 0 until result[position].values.size){
                        for(j in 0 until strs.size){
                            if(result[position].values[i].value_name.equals(strs[j])){

                                result[position].values[i].isSelect = true
                            }

                        }
                    }
                }

            val adapter =  AddSubPrefrenceAdapter(context, result[position].values)
            view.rvSubPreference.adapter =adapter
            view.rvSubPreference.isNestedScrollingEnabled = false

            view.rvSubPreference.addOnItemTouchListener(RecyclerTouchListener(context, view.rvSubPreference, RecyclerTouchListener.ClickListener { _, pos ->


                if(result[position].input_type == "radio"){
                    for (i in 0 until result[position].values.size) {
                        if (i == pos) {
                            if (result[position].values[i].isSelect){
                                result[position].selectedValue = ""
                                result[position].values[i].isSelect = false
                            }else{

                                result[position].values[i].isSelect = true
                            }
                        }
                        else{
                            result[position].values[i].isSelect = false }
                    } }
                else{
                    result[position].values[pos].isSelect = !result[position].values[pos].isSelect
                }

                val json = JSONArray()
                result[position].values.forEach {
                    if (it.isSelect){
                        json.put(it.value_name)
                    }
                }
                result[position].selectedValue = json.toString()
                adapter.notifyDataSetChanged()
            }))

        }



        if (result[position].input_type == "text") {
            holder.llInflater.removeAllViews()
            val view: View = context.layoutInflater.inflate(R.layout.text, null)
            holder.llInflater.addView(view)
            val countries = context.resources.getStringArray(R.array.countries_array)
            val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, countries)
            view.autoTextView.setAdapter(adapter)

            if(!preference.isNullOrEmpty()) {
                if (keylist[position].equals("Nationality")) {

                    result[position].selectedValue = preference[position]
                    Log.e("aknask", preference[position])
                    view.autoTextView.setText(preference[position])
                }
            }

            view.autoTextView.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    result[position].selectedValue = s.toString()
                }
            })
            view.autoTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, _, pos, _ ->
                result[position].selectedValue  = parent.getItemAtPosition(pos).toString()
            }
        }

      /*  if (result[position].input_type == "radio") {
            holder.llInflater.removeAllViews()
            val view: View = context.layoutInflater.inflate(R.layout.checkbox, null)
            view.rvSubPreference.layoutManager = LinearLayoutManager(context)
            view.rvSubPreference.adapter = AddSubPrefrenceAdapter(context, result[position].values)
            view.rvSubPreference.isNestedScrollingEnabled = false
            holder.llInflater.addView(view)
        }*/

        holder.ccPreference.setOnClickListener {
            if (flag) {
                flag = false
                holder.llInflater.visibility = View.VISIBLE
                holder.ivmore.setImageResource(R.drawable.up)
            } else {
                flag = true
                holder.llInflater.visibility = View.GONE
                holder.ivmore.setImageResource(R.drawable.down)
            }
        }
    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.tvTitle
        val ccPreference: CardView = itemView.ccPreference
        val llInflater: LinearLayout = itemView.llInflater
        val ivmore: ImageView = itemView.ivmore
    }


    private fun showPicker() {
        val view: View = context.layoutInflater.inflate(R.layout.country_code_picker, null)
        val dialog = BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme)
        dialog.setContentView(view)
        val llSearch = dialog.llSearch
        val etSearch = dialog.etSearch
        val ivClose = dialog.ivClose
        val rlCountries = dialog.rlCountries
        rlCountries.layoutManager = LinearLayoutManager(context)
        val adapter = CountryPickerAdapter(Country.getCountries(context).countries)
        rlCountries.adapter = adapter
        //  llSearch.background = RoundView(Color.WHITE, Utils.getRadius(100f), resources.getColor(R.color.txtGray))
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(etSearch.text.toString())
            }
        })
        rlCountries.addOnItemTouchListener(RecyclerTouchListener(context, rlCountries) { _, position ->
            Country.getCountries(context).countries[position].country_isd
            dialog.dismiss()
        })
        ivClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun JSONArray.toCredentialString(): String {
        val credentialString = this.toString()
        return credentialString.substring(1, credentialString.length - 1)
    }

    private fun stringToWords(mnemonic: String): ArrayList<String> {
        val words = ArrayList<String>()
        for (w in mnemonic.trim(' ').split(",")) {
            if (w.isNotEmpty()) {
                words.add(w)
            }
        }
        return words
    }

    /*

            if(!preference.isNullOrEmpty()){
//                values = preference[0].split(",") as ArrayList<String>
                Log.e("cjsjcbsj", preference.toString())
                Log.e("keylist", keylist.toString())

                if (result[position].preference == "Distance") {
                    view.tvMin.text = "${result[position].values[0].value_name} ${result[position].unit}"
                    view.tvMax.text = result[position].values[1].value_name + result[position].unit
                } else {
                    view.tvMin.text = result[position].values[0].value_name
                    view.tvMax.text = result[position].values[1].value_name
                }
//                Log.e("sankd",values!![0])
             //   view.seekbars.tickEnd =  values!![1].toFloat()
               // view.seekbars.tickStart =values!![0].toFloat()

            }
            else {
*/
}
