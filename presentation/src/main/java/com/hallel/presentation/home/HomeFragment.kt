package com.hallel.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.hallel.presentation.R
import com.hallel.presentation.base.BaseFragment
import com.hallel.presentation.extensions.observe
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject

class HomeFragment: BaseFragment() {

    private val viewModel by inject<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBarVisible(true)
        setActionBarTitle(getString(R.string.title_home))
        initObservers()
        viewModel.onLoadEventContent()
    }

    private fun initObservers() {
        viewModel.hasContentAvailable().observe(this) { eventVO ->
            eventVO.title?.let { homeTextViewTitle.text = it }
            eventVO.subtitle?.let { homeTextViewSubtitle.text = it }
            eventVO.eventImage?.let { setBanner(it) }
        }

        viewModel.noContentAvailable().observe(this) {
            showToast("Ops...Não há conteudo disponível no momento")
        }
    }

    private fun setBanner(url: String) {
        Glide
            .with(this)
            .load(url)
            .error(R.drawable.hallel_splash)
            .into(homeImageViewBanner)
    }
}