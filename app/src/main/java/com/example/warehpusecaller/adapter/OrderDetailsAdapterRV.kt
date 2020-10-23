package com.example.warehpusecaller.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.example.warehpusecaller.Call_Activity
import com.example.warehpusecaller.R
import com.example.warehpusecaller.model.OrderDetailsPojo
import com.example.warehpusecaller.utils.FileUtils


class OrderDetailsAdapterRV(
    val context: Context,
    val orderDetailsList: ArrayList<OrderDetailsPojo>,
    val stateId : String,
    val wareHouseId: String,
    val callerId: String,
    private var recyclerView: RecyclerView?
) :
    RecyclerView.Adapter<OrderDetailsAdapterRV.MyViewHolder>() {

    private lateinit var fileUtils: FileUtils
    private var mExpandedPosition = -1

    override fun getItemCount(): Int {
        return orderDetailsList.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        fileUtils = FileUtils()
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.rv_item_order_details, parent, false)
        )
    }

    override fun onBindViewHolder(holder: OrderDetailsAdapterRV.MyViewHolder, position: Int) {

        holder.orderDetailsPojo = orderDetailsList[position]

        holder.tv_number.text = (position+1).toString()
        holder.tv_customer_name.text = holder.orderDetailsPojo.getCustomerName()
        holder.tv_loan_id.text = holder.orderDetailsPojo.getLoanproposalID()
        holder.tv_spouseName.text = holder.orderDetailsPojo.getSpouseName()
        holder.model_name.text = holder.orderDetailsPojo.getModel()
        holder.tv_skuCode.text = holder.orderDetailsPojo.getSkuCode()
        holder.tv_address.text = holder.orderDetailsPojo.getAddress()
        holder.tv_village.text = holder.orderDetailsPojo.getVillage()
        holder.tv_district.text = holder.orderDetailsPojo.getDistrict()
        /*holder.contact_number.text = holder.orderDetailsPojo.getMobileNo()
        holder.alternate_number.text = holder.orderDetailsPojo.getAlternateMobileNo()*/

        val isExpanded = position == mExpandedPosition
        holder.expanded_layout.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.itemView.isActivated = isExpanded

        holder.parent.setOnClickListener {
            mExpandedPosition = if (isExpanded) -1 else position
            recyclerView?.let { it1 -> TransitionManager.beginDelayedTransition(it1) }
            notifyDataSetChanged()
        }

        holder.ll_start_calling.setOnClickListener {
            val intent = Intent(context, Call_Activity::class.java)
            intent.putExtra("orderDetailsPojo", holder.orderDetailsPojo)
            intent.putExtra("stateId", stateId)
            intent.putExtra("wareHouseId", wareHouseId)
            intent.putExtra("callerId", callerId)
            context.startActivity(intent)
        }

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val parent = itemView.findViewById(R.id.parent) as LinearLayout
        val expanded_layout = itemView.findViewById(R.id.expanded_layout) as LinearLayout
        val tv_customer_name = itemView.findViewById(R.id.tv_customer_name) as TextView
        val tv_loan_id = itemView.findViewById(R.id.tv_loan_id) as TextView
        val tv_spouseName = itemView.findViewById(R.id.tv_spouseName) as TextView
        val model_name = itemView.findViewById(R.id.model_name) as TextView
        val tv_skuCode = itemView.findViewById(R.id.tv_skuCode) as TextView
        val tv_address = itemView.findViewById(R.id.tv_address) as TextView
        val tv_village = itemView.findViewById(R.id.tv_village) as TextView
        val tv_district = itemView.findViewById(R.id.tv_district) as TextView
        val ll_start_calling = itemView.findViewById(R.id.ll_start_calling) as LinearLayout
        /*val alternate_number = itemView.findViewById(R.id.alternate_number) as TextView*/
        val tv_number = itemView.findViewById(R.id.tv_number) as TextView

        lateinit var orderDetailsPojo: OrderDetailsPojo

        /*val call1 = itemView.findViewById(R.id.call1) as ImageView
        val call2 = itemView.findViewById(R.id.call2) as ImageView*/

    }

}
