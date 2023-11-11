package com.bs.kotha.framework.base

import android.content.Context
import android.os.Bundle
import android.widget.CompoundButton
import com.bs.kotha.KothaApp
import com.example.kothamigration.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class LanguageSelectActivity : BaseBindableActivity() {
    private val TAG = "LanguageSelectActivity"
    private var mBinding: ActivityLanguageSelectBinding? = null
    private var materialChipsUtils: MaterialChipsUtils? = null
    override val layoutId: Int
        protected get() = R.layout.activity_language_select

    override fun startUI(savedInstanceState: Bundle?) {
        mBinding = viewDataBinding as ActivityLanguageSelectBinding?

        //apply material theme
        this.theme.applyStyle(R.style.MaterialTheme, true)

        //setup material chips
        materialChipsUtils = MaterialChipsUtils(
            this, false, true,
            LinkedHashMap<K, V>(), true, false, 5
        )

        //add material chips group to view
        mBinding.llInterestsChips.addView(materialChipsUtils.getChipGroupView())

        //put values of available language with keys
        materialChipsUtils.putNewPairInMapAndUpdateChip(
            LanguageConstants.BANGLA,
            getString(R.string.bangla_lang)
        )
        materialChipsUtils.putNewPairInMapAndUpdateChip(
            LanguageConstants.ENGLISH,
            getString(R.string.english_lang)
        )

        //check existing...
        val isLanguageSet: Boolean =
            PrefManager.getPreference().isKeyStayed(LanguageConstants.KEY_APP_LANGUAGE)
        if (isLanguageSet) {
            //we preferred bangla
            val language: String = PrefManager.getPreference()
                .getString(LanguageConstants.KEY_APP_LANGUAGE, LanguageConstants.BANGLA)
            materialChipsUtils.setCheckedParticularChipByKey(language, true)
        }
        setupListener()
    }

    private fun setupListener() {
        val chipGroup: ChipGroup = materialChipsUtils.getChipGroupView()
        for (index in 0 until chipGroup.getChildCount()) {
            val chip: Chip = chipGroup.getChildAt(index) as Chip
            chip.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton: CompoundButton?, checked: Boolean ->
                if (checked) {
                    val keys = checkedLanguageKey
                    if (keys.size > 0) {
                        PrefManager.getPreference()
                            .putString(LanguageConstants.KEY_APP_LANGUAGE, keys[0])
                        KothaApp.getInstance().initLanguage()
                        finish()
                        LoginActivity.startThisActivity(this)
                    }
                }
            })
        }
    }

    private val checkedLanguageKey: List<String>
        //get selected interests id/key
        private get() = materialChipsUtils.getAllCheckedChipsKey()

    override fun onBackPressed() {
        super.onBackPressed()
    }

    companion object {
        fun startThisActivity(context: Context) {
            val intent = Intent(context, LanguageSelectActivity::class.java)
            context.startActivity(intent)
        }
    }
}