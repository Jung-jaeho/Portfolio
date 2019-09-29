package zojae031.portfolio.main

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.util.DataConvertUtil

class MainPresenter(private val view: MainContract.View, private val repository: Repository) :
    MainContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate() {

    }

    override fun onResume() {
        repository
            .getData(RepositoryImpl.ParseData.MAIN)
            .map { data ->
                Log.e("data",data)
                DataConvertUtil.StringToMain(data)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { view.hideProgress() }
            .doOnSubscribe { view.showProgress() }
            .subscribe({ entity ->
                Log.e("MainP", entity.toString())
                view.showUserImage(entity.userImage)
                view.setNotice(entity.notice)
            }, { t ->
                view.showToast(t.message.toString())
            }).also { compositeDisposable.add(it) }
    }

    override fun onPause() {
        compositeDisposable.clear()
    }
}