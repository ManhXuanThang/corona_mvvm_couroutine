package dev.best.covidkotlin.ui.screen.sumarychart

import androidx.recyclerview.widget.DiffUtil
import dev.best.covidkotlin.R
import dev.best.covidkotlin.data.model.ItemMenu
import dev.best.covidkotlin.databinding.ItemMenuListBinding
import dev.best.covidkotlin.ui.base.BaseListAdapter
import dev.best.covidkotlin.utils.setSingleClick

class MenuAdapter(val itemClickListener: (ItemMenu) -> Unit = {}) :
    BaseListAdapter<ItemMenu, ItemMenuListBinding>(object : DiffUtil.ItemCallback<ItemMenu>() {
        override fun areItemsTheSame(oldItem: ItemMenu, newItem: ItemMenu): Boolean {
            return oldItem.itemName == newItem.itemName
        }

        override fun areContentsTheSame(oldItem: ItemMenu, newItem: ItemMenu): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun getLayoutRes(viewType: Int): Int {
        return R.layout.item_menu_list
    }

    override fun bindFirstTime(binding: ItemMenuListBinding) {
        binding.apply {
            root.setSingleClick {
                item?.apply {
                    itemClickListener(this)
                }
            }
        }
    }


}