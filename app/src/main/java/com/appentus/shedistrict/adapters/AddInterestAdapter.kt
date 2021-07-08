


import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RecyclerTouchListener
import com.appentus.shedistrict.adapters.AddsubIntrestAdapter
import com.appentus.shedistrict.models.IntrestBean
import kotlinx.android.synthetic.main.checkbox.view.*
import kotlinx.android.synthetic.main.intresttext.view.*
import kotlinx.android.synthetic.main.item_interests.view.*
import org.json.JSONObject
import java.util.ArrayList


class AddInterestAdapter(val context: Activity, val result: MutableList<IntrestBean.ResultBean>?, val valuelist: ArrayList<String>) : RecyclerView.Adapter<AddInterestAdapter.ItemHolder>() {

    private var lastPosition = -1
    lateinit var list : MutableList<IntrestBean.ResultBean.ValueBean>
    var flag:Boolean=true
    var blank:String=""
    lateinit var json: JSONObject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_interests, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

                                   Log.e("dsnfdsjh",valuelist.size.toString())
                                   Log.e("sadadsa",result?.size.toString())

            holder.tvName.text = result?.get(position)?.interest

            holder.tvValue.setText(valuelist[position])


        if (result?.get(position)?.input_type == "text") {
            holder.llPersonalValue.removeAllViews()
            val view: View = context.layoutInflater.inflate(R.layout.intresttext, null)
            holder.llPersonalValue.addView(view)
            view.etText.hint="Type in your"+" "+result?.get(position)?.interest

            view.ic_close.setOnClickListener(){
                Log.e("clcik","click")
                view.etText.text.clear()
            }

            view.etText.setText(result[position].selectedValue)
            view.etText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    result[position].selectedValue = s.toString()
                    holder.tvValue.setText(s.toString())
                }
            })
            holder.tvValue.setText( result[position].selectedValue)
        }
        if (result?.get(position)?.input_type == "checkbox") {
            holder.llPersonalValue.removeAllViews()
            val view: View = context.layoutInflater.inflate(R.layout.checkbox, null)
            holder.llPersonalValue.addView(view)


            view.rvSubPreference.layoutManager = GridLayoutManager(context, 2)
            val adapter =  AddsubIntrestAdapter(context, result[position].value)
            view.rvSubPreference.adapter =adapter
            view.rvSubPreference.isNestedScrollingEnabled = false

            view.rvSubPreference.addOnItemTouchListener(RecyclerTouchListener(context, view.rvSubPreference, RecyclerTouchListener.ClickListener { _, pos ->

                for (i in 0 until result[position].value.size) {
                    if (i == pos) {
                        if (result[position].value[i].isSelected) {
                            result[position].selectedValue = ""
                            result[position].value[i].isSelected = false
                        } else {

                            result[position].value[i].isSelected = true
                        }

                    } else {
                        result[position].value[i].isSelected = false
                    }
                }
                holder.tvValue.setText(result!!.get(position).value[pos].interest_value)
                result[position].selectedValue = result!!.get(position).value[pos].interest_value
                adapter.notifyDataSetChanged()


            }))
            holder.tvValue.setText( result[position].selectedValue)
        }


            holder.root.setOnClickListener() {
                if(flag) {
                    flag=false
                    holder.ivMore1.setImageResource(R.drawable.up)
                    holder.llPersonalValue.visibility = View.VISIBLE

                } else{
                    flag=true
                    holder.ivMore1.setImageResource(R.drawable.edit)
                    holder.llPersonalValue.visibility = View.GONE
                }

            }


    }

    override fun getItemCount(): Int {
        return result?.size!!
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName = itemView.tvName
        var root  = itemView.root
        var ivMore1  = itemView.ivMore1
        var llPersonalValue  = itemView.llPersonalValue
        var tvValue  = itemView.tvValue

    }


    private fun setAnimation(viewToAnimate: View, position: Int) { // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation: Animation = AnimationUtils.loadAnimation(context,R.anim.item_animation_fall_down)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

}