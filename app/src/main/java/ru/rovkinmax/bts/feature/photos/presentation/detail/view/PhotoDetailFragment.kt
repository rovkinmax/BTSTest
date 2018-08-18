package ru.rovkinmax.bts.feature.photos.presentation.detail.view

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fmt_photo_detail.*
import ru.rovkinmax.bts.R
import ru.rovkinmax.bts.di.DI
import ru.rovkinmax.bts.di.module
import ru.rovkinmax.bts.feature.global.presentation.view.BaseFragment
import ru.rovkinmax.bts.feature.photos.domain.PhotoEntity
import ru.rovkinmax.bts.feature.photos.presentation.detail.presenter.PhotoDetailPresenter
import toothpick.Toothpick

class PhotoDetailFragment : BaseFragment(), PhotoDetailView {

    companion object {
        private const val KEY_PHOTO = "PHOTO"
        fun newInstance(photoEntity: PhotoEntity): PhotoDetailFragment {
            return PhotoDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_PHOTO, photoEntity)
                }
            }
        }
    }

    override val layoutRes: Int = R.layout.fmt_photo_detail

    @InjectPresenter
    lateinit var presenter: PhotoDetailPresenter

    @ProvidePresenter
    fun providePresenter(): PhotoDetailPresenter {
        return Toothpick.openScopes(DI.SCOPE_FLOW_PHOTOS, scopeName).module {
            bind(PhotoEntity::class.java).toInstance(arguments!!.getSerializable(KEY_PHOTO) as PhotoEntity)
        }.getInstance(PhotoDetailPresenter::class.java).also {
            Toothpick.closeScope(scopeName)
        }
    }

    override fun onBackPressed() = presenter.onBackPressed()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEnableToolbarBackButton(enable = true)
    }

    override fun showPhoto(url: String) {
        Picasso.get()
                .load(url)
                .into(ivImage)
    }

    override fun showTitle(title: String) {
        setTitle(title)
    }
}