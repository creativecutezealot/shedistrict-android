package com.appentus.shedistrict.adapters

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RecyclerTouchListener
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.Utils.select
import com.appentus.shedistrict.models.DateAndTimeBean
import com.appentus.shedistrict.models.PrefrenceBean
import com.appentus.shedistrict.network.ApiController
import com.jaredrummler.materialspinner.MaterialSpinner
import kotlinx.android.synthetic.main.age.view.*
import kotlinx.android.synthetic.main.checkbox.view.*
import kotlinx.android.synthetic.main.distance.view.*
import kotlinx.android.synthetic.main.item_interests.view.*
import kotlinx.android.synthetic.main.text.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddPersonalInfoAdapter(val context: Activity, val result: MutableList<PrefrenceBean.ResultBean>, val sel: select,val personalvaluelist: ArrayList<String>) : RecyclerView.Adapter<AddPersonalInfoAdapter.ItemHolder>() {
    lateinit var apiController: ApiController
    private var lastPosition = -1
    lateinit var list: MutableList<String>
    var flag: Boolean = true
    var flagcheck: Boolean = true
    var value= ""
    lateinit var json: JSONObject
    var dateList: ArrayList<DateAndTimeBean>? = null
    var month: MutableList<DateAndTimeBean>? = null
    var yearList: MutableList<DateAndTimeBean>? = null
    var tvDayValue: String? = null
    var tvMonthValue: String? = null
    var tvYearValue: String? = null
    var a=""

    var tvDayValuebydefault=""
    var  tvMonthValuebydefault: String? = null
    var  tvYearValuebydefault: String? = null

    var dat = ""
    var age: String? = null
    var religion = ""
    var education = ""
    var ethenicity = ""
    var relationship = ""
    var sexual = ""
    var Kids = ""
    var Drinking = ""
    var Smoking = ""
    var Political = ""
    var hobbiesObj: JSONObject? = null
    var interestArr: JSONArray? = null
    var personalInfoValue: String? = null
    var listforvalu = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_interests, parent, false)
        return ItemHolder(itemView)
    }


    override fun onBindViewHolder(holder: AddPersonalInfoAdapter.ItemHolder, position: Int) {
        dateList = ArrayList()
        month = ArrayList()
        yearList = ArrayList()

        Log.e("vvvfadasd",result.size.toString())
        Log.e("dsfdsfdf",personalvaluelist.size.toString())

        if(result?.get(position)?.preference.equals("Distance")){
            holder.tvName.text = "Location"

        } else{

            holder.tvName.text = result?.get(position)?.preference
        }

        list = ArrayList()




        if(position==0)
        {
            val datesss = personalvaluelist[position]

            if(!datesss.isNullOrEmpty()) {
                Log.e("kvndk", datesss)
                val items1 = datesss.split("/")

                tvMonthValue = items1[0]
                tvDayValue = items1[1]
                tvYearValue = items1[2]

                holder.tvValue.text=getAge(tvYearValue!!.toInt(), tvMonthValue!!.toInt(), tvDayValue!!.toInt()-1)

            }
            else{
                val realdob = Utility().getUser()!!.user_dob
                Log.e("kvsfsfndk", realdob)
                val items2 = realdob.split("/")
                tvMonthValue = items2[0]
                tvDayValue = items2[1]
                tvYearValue = items2[2]
                holder.tvValue.text = getAge(tvYearValue!!.toInt(), tvMonthValue!!.toInt(), tvDayValue!!.toInt()-1)
            }
        }
        else{
            holder.tvValue.text= personalvaluelist[position]
        }

        if (result[position].input_type == "range") {
            if(result[position].preference == "Age"){

                holder.llPersonalValue.removeAllViews()
                val view = context.layoutInflater.inflate(R.layout.age, null)
                holder.llPersonalValue.addView(view)

//                Log.e("date",result[position].selectedValue)
//                val df: DateFormat = SimpleDateFormat("MM/dd/yyyy")
//                                         if(!result[position].selectedValue.equals("")) {
//                                             Log.e("kcjhsajk", result[position].selectedValue)
//                                             Log.e("kcjhsajdfk", "kjcbhjkbc")
//                                             val readDate: Date = df.parse(result[position].selectedValue)
//                                             val calwxcxd = Calendar.getInstance()
//                                             calwxcxd.timeInMillis = readDate.time
//                                             tvMonthValue = calwxcxd.get(Calendar.MONTH).toString()
//                                             tvDayValue= calwxcxd.get(Calendar.DATE).toString()
//                                             tvYearValue= calwxcxd.get(Calendar.YEAR).toString()
//
//
//                                        }

               /* if(!result[position].selectedValue.equals("")) {
                    val datesss = result[position].selectedValue;
                    Log.e("kvndk",datesss)
                    val items1 = datesss.split("/")
                    tvMonthValue = items1[0]
                    tvDayValue = items1[1]
                    tvYearValue = items1[2]

                    Log.e("akdnk",tvMonthValue)
                    Log.e("cdh",tvDayValue)
                    Log.e("dknak",tvYearValue)

                    Log.e("kc",a)
                    Log.e("tvYearValue",tvYearValue)

                }*/
                if(!tvMonthValue.equals(null)) {
                    tvMonthValue = getMonthShortName(tvMonthValue!!.toInt())
                }


              if(tvMonthValue==null){
                  month?.add(DateAndTimeBean("Month"))
              }
                else{
                  month?.add(DateAndTimeBean(tvMonthValue))
              }



                month?.add(DateAndTimeBean("Jan"))
                month?.add(DateAndTimeBean("Feb"))
                month?.add(DateAndTimeBean("Mar"))
                month?.add(DateAndTimeBean("Apr"))
                month?.add(DateAndTimeBean("May"))
                month?.add(DateAndTimeBean("Jun"))
                month?.add(DateAndTimeBean("Jul"))
                month?.add(DateAndTimeBean("Aug"))
                month?.add(DateAndTimeBean("Sep"))
                month?.add(DateAndTimeBean("Oct"))
                month?.add(DateAndTimeBean("Nov"))
                month?.add(DateAndTimeBean("Dec"))

                val months = ArrayList<String>()
                month!!.forEach {
                    months.add(it.text)
                }


                val Month: ArrayAdapter<String> = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,months)
                Month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                view.spinnerMonth.setAdapter(Month)
                view.spinnerMonth.setOnItemSelectedListener(MaterialSpinner.OnItemSelectedListener<Any?> { _, pos, _, _ ->

                    tvMonthValue = months.get(pos)

                  //  value = "$tvMonthValue/$tvDayValue/$tvYearValue"

                    sel.onselect(tvMonthValue,tvDayValue,tvYearValue,position,view.tvValue)


                })
                if(tvDayValue==null){
                    dateList?.add(0, DateAndTimeBean("Date"))
                }
                else{
                    dateList?.add(DateAndTimeBean(tvDayValue))
                }

                for (i in 1..31) {
                    dateList?.add(DateAndTimeBean(i.toString()))
                }


                val dates = ArrayList<String>();

                dateList!!.forEach {
                    dates.add(it.text)
                }

                val date: ArrayAdapter<String> = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,dates)
                date.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                  view.spinnerDate.setAdapter(date)
                  view.spinnerDate.setOnItemSelectedListener(MaterialSpinner.OnItemSelectedListener<Any?> { view, pos, _, item ->

                      tvDayValue = dates?.get(pos)

                      Log.e("tvDayValue",tvDayValue)
                      sel.onselect(tvMonthValue,tvDayValue,tvYearValue,position,view.tvValue)
                      //setDate(value, pos)

                  })

                if(tvYearValue==null){
                    yearList?.add(0, DateAndTimeBean("Year"))
                }
                else{
                    yearList?.add(DateAndTimeBean(tvYearValue))
                }



                val cal = Calendar.getInstance()
                // yearList?.add(DateAndTimeBean(cal.get(Calendar.YEAR).toString()))
                for (i in 17..90) {
                    //cal.add(Calendar.YEAR, +1)
                    yearList?.add(DateAndTimeBean((cal.get(Calendar.YEAR) - i).toString()))
                }


                val years = ArrayList<String>();
                yearList!!.forEach {
                    years.add(it.text)
                }

                val yearss: ArrayAdapter<String> = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,years)
                yearss.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                  view.spinnerYYYY.setAdapter(yearss)
                view.spinnerYYYY.setOnItemSelectedListener(MaterialSpinner.OnItemSelectedListener<Any?> { _, pos, id, item ->

                      tvYearValue = years?.get(pos)
                    Log.e("dsdlsj",tvYearValue)
                    sel.onselect(tvMonthValue,tvDayValue,tvYearValue,position,view.tvValue)

                  })

                     holder.tvValue.text= result[position].selectedValue
            }

            if(result[position].preference == "Distance"){

                holder.llPersonalValue.removeAllViews()
                val view = context.layoutInflater.inflate(R.layout.distance, null)
                holder.llPersonalValue.addView(view)

                view.etLocation.setText(result[position].selectedValue)
                holder.tvValue.text=view.etLocation.text

                view.etLocation.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        result[position].selectedValue = s.toString()
                        holder.tvValue.text=s.toString()
                    }
                })


            }

        }
        if (result[position].input_type == "checkbox"||result[position].input_type == "radio") {
         holder.llPersonalValue.removeAllViews()
            val view: View = context.layoutInflater.inflate(R.layout.checkbox, null)
         holder.llPersonalValue.addView(view)

            if (result[position].input_type == "radio"){
                view.rvSubPreference.layoutManager = LinearLayoutManager(context)



            }else{
                view.rvSubPreference.layoutManager = GridLayoutManager(context, 2)
            }
            val adapter =  AddsubPersonaltAdapter(context, result[position].values)
            view.rvSubPreference.adapter =adapter
            view.rvSubPreference.isNestedScrollingEnabled = false

            view.rvSubPreference.addOnItemTouchListener(RecyclerTouchListener(context, view.rvSubPreference, RecyclerTouchListener.ClickListener { _, pos ->

                for (i in 0 until result[position].values.size) {
                    if (i == pos) {
                        if (result[position].values[i].isSelect) {
                            result[position].selectedValue = ""
                            result[position].values[i].isSelect = false
                        } else {

                            result[position].values[i].isSelect = true
                        }

                    } else {
                        result[position].values[i].isSelect = false
                    }
                }

                result[position].selectedValue = result!!.get(position).values[pos].value_name
                holder.tvValue.text= result!!.get(position).values[pos].value_name
                adapter.notifyDataSetChanged()

               /*  val json = JSONArray()
                result[position].values.forEach {
                    if (it.isSelect){
                        json.put(it.value_name)
                    }
                }
               */
            }))

            holder.tvValue.text=result[position].selectedValue

        }

        if (result[position].input_type == "text") {
         holder.llPersonalValue.removeAllViews()
            val view: View = context.layoutInflater.inflate(R.layout.text, null)
         holder.llPersonalValue.addView(view)
            view.ic_close.visibility=View.VISIBLE
            view.ic_close.setOnClickListener(){
                view.autoTextView.text.clear()
            }

            view.autoTextView.setText(result[position].selectedValue)

            val countries = context.resources.getStringArray(R.array.countries_array)
            val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, countries)
            view.autoTextView.setAdapter(adapter)
            view.autoTextView.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    result[position].selectedValue = s.toString()
                    holder.tvValue.text= s.toString()
                }
            })
            view.autoTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, _, pos, _ ->
                result[position].selectedValue  = parent.getItemAtPosition(pos).toString()
               // holder.tvValue.text= parent.getItemAtPosition(pos).toString()

            }
            holder.tvValue.text=result[position].selectedValue
        }

        holder.root.setOnClickListener() {
            if (flag) {
                flag = false
                holder.ivMore1.setImageResource(R.drawable.up)
                holder.llPersonalValue.visibility = View.VISIBLE

            }else{
                flag = true
                holder.ivMore1.setImageResource(R.drawable.edit)
                holder.llPersonalValue.visibility = View.GONE
            }

        }

    }


    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName = itemView.tvName
        var root = itemView.root
        var llPersonalValue = itemView.llPersonalValue
        var ivMore1 = itemView.ivMore1
        var ic_close = itemView.ic_close
        var tvValue = itemView.tvValue

    }

    override fun getItemCount(): Int {
        return result.size
    }

    /*private fun setAnimation(viewToAnimate: View, position: Int) { // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.i)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }
*/
    fun getAge(year: Int?, month: Int, day: Int): Int? {
        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()
        dob[year!!, month] = day
        var age = today[Calendar.YEAR] - dob[Calendar.YEAR]
        if (month === today[Calendar.MONTH] + 1 && day > today[Calendar.DAY_OF_MONTH]) {
            age--
        }
        return age
    }

    fun getMonthShortName(monthNumber: Int): String{
        var monthName = ""
        if (monthNumber >= 0 && monthNumber < 12) try {
            val calendar = Calendar.getInstance()
            calendar[Calendar.MONTH] = monthNumber
            val simpleDateFormat = SimpleDateFormat("MMM")
            simpleDateFormat.calendar = calendar
            monthName = simpleDateFormat.format(calendar.time)
        } catch (e: Exception) {
            e?.printStackTrace()
        }
        return monthName
    }

    fun getAge(year: Int, month: Int, day: Int): String {
        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()

        dob.set(year, month, day)

        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        val ageInt = age + 1

        return ageInt.toString()
    }
}