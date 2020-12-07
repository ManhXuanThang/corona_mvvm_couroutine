package dev.best.covidkotlin.ui.screen.search

import androidx.recyclerview.widget.DiffUtil
import dev.best.covidkotlin.R
import dev.best.covidkotlin.data.model.Country
import dev.best.covidkotlin.databinding.ItemSearchListBinding
import dev.best.covidkotlin.ui.base.BaseListAdapter
import dev.best.covidkotlin.utils.setSingleClick

class ListSearchAdapter(val itemClickListener: (Country) -> Unit = {}) :
    BaseListAdapter<Country, ItemSearchListBinding>(object : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem.CountryCode == newItem.CountryCode
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun getLayoutRes(viewType: Int): Int {
        return R.layout.item_search_list
    }

    override fun bindFirstTime(binding: ItemSearchListBinding) {
        binding.apply {
            root.setSingleClick {
                item?.apply {
                    itemClickListener(this)
                }
            }
        }
    }


}