package com.hallel.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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
        setLayoutConfigs()
        initObservers()
        viewModel.onLoadEventContent()
    }

    private fun setLayoutConfigs() {
        setActionBarVisible(true)
        setActionBarTitle(getString(R.string.title_home))
        homeRVParticipants.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initObservers() {
        viewModel.hasContentAvailable().observe(this) { eventVO ->
            eventVO.title?.let { homeTxtTitle.text = it }
            eventVO.subtitle?.let { homeTxtSubtitle.text = it }
            eventVO.eventImage?.let { setBanner(it) }
            eventVO.id?.let {
                viewModel.onLoadEventGuests(it)
            } ?: showToast("no Id")
        }

        viewModel.noContentAvailable().observe(this) {
            showToast("Ops...Não há conteudo disponível no momento")
        }

        viewModel.noGuestAvailableForEvent().observe(this) {
            showToast("Ops...Não há participantes disponível no momento")
        }

        viewModel.hasGuestAvailableForEvent().observe(this) {
            homeRVParticipants.adapter = HomeGuestAdapter(
                guestList = it,
                listener = object : HomeGeneralClickListener {
                    override fun onAdapterItemClicked(itemId: Int) {
                        showToast("Navegar para a programação assim que criar a tela")
                    }
                }
            )
        }
    }

    private fun setBanner(url: String) {
        Glide
            .with(this)
            .load(url)
            .error(R.drawable.hallel_splash)
            .centerCrop()
            .into(homeImgLogo)
    }
}