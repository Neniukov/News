package com.example.news.presentation.news.list.widget

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import com.example.news.R
import com.example.news.presentation.base.InAppFragmentDialog
import com.example.news.presentation.base.InAppFragmentDialog.Companion.showMessage
import com.example.news.databinding.DialogFilterSourceBinding
import com.example.news.domain.news.model.Source
import com.example.news.domain.news.model.getSources
import com.example.news.domain.news.model.getSourcesByIds
import com.example.news.common.DateExtension.SERVER_DATE_FORMAT
import com.example.news.common.DateExtension.UI_DATE_FORMAT
import com.example.news.common.DateExtension.formatToAnotherFormat
import com.example.news.common.DateExtension.getDataFromString
import com.example.news.common.DateExtension.getDateString
import com.example.news.common.DateExtension.getFieldDate
import com.example.news.presentation.base.BaseAdapter
import com.example.news.presentation.news.list.adapter.SourceViewHolder
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*


class FilterSourceDialog : BottomSheetDialogFragment() {

    var selectedSources: ((List<Source>, Pair<String, String>) -> Unit)? = null

    private val binding: DialogFilterSourceBinding get() = requireNotNull(bindingNullable)
    private var bindingNullable: DialogFilterSourceBinding? = null

    private val adapter = BaseAdapter()
        .map(R.layout.item_source, SourceViewHolder())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingNullable =
            DataBindingUtil.inflate(inflater, R.layout.dialog_filter_source, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            rvSources.adapter = adapter
            adapter.itemsLoaded(arguments?.getParcelableArrayList(SOURCES_KEY) ?: getSources())
            val dateFrom: Date = arguments?.getString(DATE_FROM_KEY)?.let { date ->
                tvFrom.text = formatToAnotherFormat(date, SERVER_DATE_FORMAT, UI_DATE_FORMAT)
                getDataFromString(date, SERVER_DATE_FORMAT)
            } ?: Date()
            val dateTo: Date = arguments?.getString(DATE_TO_KEY)?.let { date ->
                tvTo.text = formatToAnotherFormat(date, SERVER_DATE_FORMAT, UI_DATE_FORMAT)
                getDataFromString(date, SERVER_DATE_FORMAT)
            } ?: Date()
            tvCancel.setOnClickListener { dismiss() }
            saveFilter()
            selectDateFrom(dateFrom)
            selectDateTo(dateTo)
        }
    }

    private fun DialogFilterSourceBinding.selectDateFrom(dateFrom: Date) {
        tvFrom.setOnClickListener {
            val dialog = DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    val selectedDate = Calendar.getInstance().also {
                        it.set(year, month, day, 0, 0, 0)
                    }.time
                    tvFrom.text = getDateString(selectedDate, UI_DATE_FORMAT)
                    if (selectedDate.after(getDataFromString(tvTo.text.toString(), UI_DATE_FORMAT))) {
                        tvFrom.text = tvTo.text
                    }
                },
                dateFrom.getFieldDate(Calendar.YEAR),
                dateFrom.getFieldDate(Calendar.MONTH),
                dateFrom.getFieldDate(Calendar.DAY_OF_MONTH)
            )
            dialog.show()
        }
    }

    private fun DialogFilterSourceBinding.selectDateTo(dateTo: Date) {
        tvTo.setOnClickListener {
            val dialog = DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    val selectedDate = Calendar.getInstance().also {
                        it.set(year, month, day, 0, 0, 0)
                    }.time
                    tvTo.text = getDateString(selectedDate, UI_DATE_FORMAT)
                    if (selectedDate.before(getDataFromString(tvFrom.text.toString(), UI_DATE_FORMAT))) {
                        tvTo.text = tvFrom.text
                    }
                },
                dateTo.getFieldDate(Calendar.YEAR),
                dateTo.getFieldDate(Calendar.MONTH),
                dateTo.getFieldDate(Calendar.DAY_OF_MONTH)
            )
            dialog.show()
        }
    }

    private fun DialogFilterSourceBinding.saveFilter() {
        tvSave.setOnClickListener {
            val items = adapter.getAllItems().filterIsInstance<Source>()
            if (items.none { it.selected }) {
                requireActivity().showMessage(
                    getString(R.string.error_min_source),
                    InAppFragmentDialog.Companion.Type.Error
                )
            } else {
                val dateFrom = formatToAnotherFormat(
                    tvFrom.text.toString(),
                    UI_DATE_FORMAT,
                    SERVER_DATE_FORMAT
                )
                val dateTo =
                    formatToAnotherFormat(tvTo.text.toString(), UI_DATE_FORMAT, SERVER_DATE_FORMAT)
                selectedSources?.invoke(items, Pair(dateFrom, dateTo))
                dismiss()
            }
        }
    }

    companion object {
        private const val SOURCES_KEY = "SOURCES_KEY"
        private const val DATE_FROM_KEY = "DATE_FROM_KEY"
        private const val DATE_TO_KEY = "DATE_TO_KEY"

        fun newInstance(sources: String, dateFrom: String?, dateTo: String?) =
            FilterSourceDialog().apply {
                arguments = bundleOf(
                    SOURCES_KEY to getSourcesByIds(sources),
                    DATE_FROM_KEY to dateFrom,
                    DATE_TO_KEY to dateTo
                )
            }
    }
}