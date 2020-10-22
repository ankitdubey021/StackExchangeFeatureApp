package com.ankitdubey021.stackexchangefeatureapp.ui.search

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ankitdubey021.stackexchangefeatureapp.R
import com.ankitdubey021.stackexchangefeatureapp.data.State
import com.ankitdubey021.stackexchangefeatureapp.data.User
import com.ankitdubey021.stackexchangefeatureapp.data.UserList
import com.ankitdubey021.stackexchangefeatureapp.databinding.FragmentSearchBinding
import com.ankitdubey021.stackexchangefeatureapp.extensions.*
import com.ankitdubey021.stackexchangefeatureapp.ui.details.UserDetail
import com.ankitdubey021.stackexchangefeatureapp.utils.ProgressBarUtils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import java.util.*

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchAdapter.UserClickListener {

    private val searchViewModel: SearchViewModel by viewModels()
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter(userClickListener = this) }

    lateinit var binding : FragmentSearchBinding

    lateinit var searchJob: Job
    private val REQ_CODE_SPEECH_INPUT = 100
    private var searchedByMic = false
    private val userList = mutableListOf<User>()
    private var page =  1
    private var isLoading = false
    private var hasMore = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater,container,false)

        initViews()

        return binding.root
    }

    private fun initViews() {
        binding.apply {

            backArrow.setOnClickListener { activity?.onBackPressed() }

            searchEt.addTextChangedListener(object  : TextWatcher {

                override fun afterTextChanged(str: Editable?) {
                    scheduleSearchQuery(str.toString())
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(this@SearchFragment::searchJob.isInitialized) searchJob.cancel()
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            })

            microphone.setOnClickListener { promptSpeechInput() }

            searchAdapter.daos = userList

            userRv.apply {
                adapter = searchAdapter

                xOnScrollListener {
                    if(!isLoading && hasMore) {
                        page++
                        searchViewModel.loadUsers(binding.searchEt.text.toString(), page)
                    }
                }
            }

            fetchUsers()
        }
    }

    private fun scheduleSearchQuery(str: String?) {
        searchJob = lifecycleScope.launch {
            if(!searchedByMic)
                delay(1200)
            else
                searchedByMic = false

            userList.clear()
            page=1
            searchViewModel.loadUsers(str!!,page)
        }
    }

    private fun fetchUsers() {
        searchViewModel.usersLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {state->

            when(state){
                is State.Loading -> {
                    isLoading = true
                    ProgressBarUtils.showProgressDialog(requireContext())
                }

                is State.Success -> {
                    isLoading = false
                    ProgressBarUtils.removeProgressDialog()
                    processResponse(state.data)
                }

                is State.Error -> {
                    isLoading = false
                    ProgressBarUtils.removeProgressDialog()
                    renderEmptyState()
                }
            }
        })
    }

    private fun processResponse(response: ResponseBody) {
        val res = response.string()
        if(res.isNotBlank()) {
            hasMore = JSONObject(res).getBoolean("has_more")
            val list = Gson().fromJson<UserList>(res, UserList::class.java)
            userList.addAll(list.items)
            searchAdapter.notifyDataSetChanged()

            renderEmptyState()
        }
    }

    private fun renderEmptyState() {
        if (userList.isEmpty())
            binding.noDataLayout.show()
        else
            binding.noDataLayout.hide()
    }


    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            getString(R.string.speech_prompt)
        )
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {
            requireContext().toast(getString(R.string.speech_not_supported))
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    val result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    searchedByMic = true
                    binding.searchEt.setText(result?.get(0))
                    binding.searchEt.setSelection(binding.searchEt.text!!.length);
                }
            }
        }
    }

    override fun onUserClicked(user: User, imageView: ImageView) {
        activity?.launchActivity<UserDetail> {
            putExtra("dao",user)
        }
    }
}